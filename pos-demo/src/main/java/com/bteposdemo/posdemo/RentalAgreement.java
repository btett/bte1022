package com.bteposdemo.posdemo;

import com.bteposdemo.staticdata.RentalTool;
import com.bteposdemo.staticdata.RentalToolCharges;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RentalAgreement {
    private final Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private final RentalTool rentalTool;
    private final int rentalDayCount;
    private final LocalDate checkoutDate;
    private final double discountPercent;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final DecimalFormat currencyFormatter = new DecimalFormat("#,###.00");

    private int chargeableRentalDays;
    private double prediscountTotal;
    private double dailyRentalChargeRate;
    private LocalDate dueDate;


    public RentalAgreement(RentalTool rentalTool, int rentalDayCount, LocalDate checkoutDate, double discountPercent) {
        this.rentalTool = rentalTool;
        if (rentalDayCount < 1) {
            throw new IllegalArgumentException();
        }
        this.rentalDayCount = rentalDayCount;
        this.checkoutDate = checkoutDate;
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException();
        }
        this.discountPercent = discountPercent * .01;
    }

    public void calcDueDate() {
        this.dueDate = checkoutDate.plusDays(rentalDayCount);
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    private void calcChargeableRentalDays() {
        int chargeableDays = 0;
        RentalToolCharges toolCharges = RentalToolCharges.valueOf(rentalTool.getType());
        if (toolCharges.isWeekdayCharge() && toolCharges.isWeekendCharge()) {
            chargeableDays += calcAllDaysBetween();
        } else if (toolCharges.isWeekdayCharge()) {
            chargeableDays += calcWeekdaysBetween();
        } else if (toolCharges.isWeekendCharge()) {
            chargeableDays += calcWeekendDaysBetween();
        }
        //if tool is exempt from holiday charge, we remove any holidays from the charge count
        if (!toolCharges.isHolidayCharge()) {
            chargeableDays -= calcHolidaysBetween();
        }
        this.chargeableRentalDays = chargeableDays;
    }

    public int getChargeableRentalDays() {
        return chargeableRentalDays;
    }


    private long calcWeekdaysBetween() {
        return checkoutDate.plusDays(1).datesUntil(getDueDate().plusDays(1))
                .filter(d -> !weekend.contains(d.getDayOfWeek()))
                .count();
    }

    private long calcWeekendDaysBetween() {
        return checkoutDate.datesUntil(getDueDate())
                .filter(d -> weekend.contains(d.getDayOfWeek()))
                .count();
    }

    private long calcAllDaysBetween() {
        return checkoutDate.plusDays(1).datesUntil(getDueDate().plusDays(1)).count();
    }

    private int calcHolidaysBetween() {
        int holidaysFound = 0;
        boolean laborDayFound = false;
        if (checkoutDate.getMonth().equals(Month.SEPTEMBER)) {
            for (LocalDate date : checkoutDate.minusDays(checkoutDate.getDayOfMonth() - 1).datesUntil(checkoutDate).collect(Collectors.toList())) {
                if (date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                    laborDayFound = true;
                }
            }
        }
//        for (LocalDate date : checkoutDate.plusDays(1).datesUntil(getDueDate()).collect(Collectors.toList())) {
        for (LocalDate date : checkoutDate.datesUntil(getDueDate()).collect(Collectors.toList())) {
            if (date.getMonth().equals(Month.JULY) && date.getDayOfMonth() == 4) {
                if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                    if (getDueDate().getDayOfMonth() != 4) {
                        holidaysFound++;
                        continue;
                    }
                } else {
                    holidaysFound++;
                    continue;
                }
            }
            if (!laborDayFound && date.getMonth().equals(Month.SEPTEMBER) && date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                laborDayFound = true;
                holidaysFound++;
                continue;
            }
        }
        return holidaysFound;
    }

    private void calcPrediscountTotal() {
        this.prediscountTotal = getChargeableRentalDays() * getDailyRentalChargeRate();
    }

    public double getPrediscountTotal() {
        return this.prediscountTotal;
    }

    public double getDiscountAmount() {
        return getPrediscountTotal() * discountPercent;
    }

    private double getFinalTotal() {
        return getPrediscountTotal() - getDiscountAmount();
    }

    private void calcDailyRentalChargeRate() {
        this.dailyRentalChargeRate = RentalToolCharges.valueOf(rentalTool.getType()).getDailyChargePrice();
    }

    private double getDailyRentalChargeRate() {
        return this.dailyRentalChargeRate;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    private void processRentalAgreement() {
        calcDueDate();
        calcDailyRentalChargeRate();
        calcChargeableRentalDays();
        calcPrediscountTotal();
    }

    public void printRentalAgreement() {
        processRentalAgreement();
        System.out.println("Tool code: " + rentalTool.name());
        System.out.println("Tool type: " + rentalTool.getType());
        System.out.println("Tool brand: " + rentalTool.getBrand());
        System.out.println("Rental days: " + rentalDayCount);
        System.out.println("Checkout date: " + formatter.format(checkoutDate));
        System.out.println("Due date: " + formatter.format(getDueDate()));
        System.out.println("Daily rental charge: $" + currencyFormatter.format(getDailyRentalChargeRate()));
        System.out.println("Chargeable rental days: " + getChargeableRentalDays());
        System.out.println("Pre-discount total charge: $" + currencyFormatter.format(getPrediscountTotal()));
        System.out.println("Discount percent: " + (int) (getDiscountPercent() * 100) + "%");
        System.out.println("Discount amount: $" + currencyFormatter.format(getDiscountAmount()));
        System.out.println("Final total charge: $" + currencyFormatter.format(getFinalTotal()));
    }

}
