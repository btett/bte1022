package com.bteposdemo.posdemo;

import com.bteposdemo.staticdata.RentalTool;
import com.bteposdemo.staticdata.RentalToolCharges;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final DecimalFormat currencyFormatter = new DecimalFormat("#,###.00");

    //provided values from checkout input prompts
    private final RentalTool rentalTool;
    private final int rentalDayCount;
    private final LocalDate checkoutDate;
    private final double discountPercent;

    //calculated based on checkout provided values
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

    //calculates actual days to be charged based on checkout/due dates and the specific tool's rental charges
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

    private long calcWeekdaysBetween() {
        return checkoutDate.plusDays(1).datesUntil(this.dueDate.plusDays(1))
                .filter(d -> !weekend.contains(d.getDayOfWeek()))
                .count();
    }

    private long calcWeekendDaysBetween() {
        return checkoutDate.datesUntil(this.dueDate)
                .filter(d -> weekend.contains(d.getDayOfWeek()))
                .count();
    }

    private long calcAllDaysBetween() {
        return checkoutDate.plusDays(1).datesUntil(this.dueDate.plusDays(1)).count();
    }

    private int calcHolidaysBetween() {
        int holidaysFound = 0;

        //special case - if checkout date is in september, we need to see if labor day already happened before checkout date
        boolean laborDayFound = false;
        if (checkoutDate.getMonth().equals(Month.SEPTEMBER)) {
            for (LocalDate date : checkoutDate.minusDays(checkoutDate.getDayOfMonth() - 1).datesUntil(checkoutDate).collect(Collectors.toList())) {
                if (date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                    laborDayFound = true;
                }
            }
        }
        for (LocalDate date : checkoutDate.datesUntil(this.dueDate).collect(Collectors.toList())) {
            if (date.getMonth().equals(Month.JULY) && date.getDayOfMonth() == 4) {
                if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                    //special case for Independence day - we don't give free holiday charge on exact day July,4 Sun since checkout date is before observed holiday
                    if (this.dueDate.getDayOfMonth() != 4) {
                        holidaysFound++;
                        continue;
                    }
                } else {
                    holidaysFound++;
                    continue;
                }
            }
            //if we didn't already find labor day in our special case check, we now look for first Monday after checkout date
            if (!laborDayFound && date.getMonth().equals(Month.SEPTEMBER) && date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                laborDayFound = true;
                holidaysFound++;
                continue;
            }
        }
        return holidaysFound;
    }

    private void calcPrediscountTotal() {
        this.prediscountTotal = getChargeableRentalDays() * dailyRentalChargeRate;
    }

    private void calcDailyRentalChargeRate() {
        this.dailyRentalChargeRate = RentalToolCharges.valueOf(rentalTool.getType()).getDailyChargePrice();
    }

    private void processRentalAgreement() {
        calcDueDate();
        calcDailyRentalChargeRate();
        calcChargeableRentalDays();
        calcPrediscountTotal();
    }

    public void printRentalAgreement() {
        //processRentalAgreement is called first to perform necessary date and currency calculations
        processRentalAgreement();

        System.out.println("Tool code: " + rentalTool.name());
        System.out.println("Tool type: " + rentalTool.getType());
        System.out.println("Tool brand: " + rentalTool.getBrand());
        System.out.println("Rental days: " + getRentalDayCount());
        System.out.println("Checkout date: " + getCheckoutDate());
        System.out.println("Due date: " + getDueDate());
        System.out.println("Daily rental charge: $" + getDailyRentalChargeRate());
        System.out.println("Chargeable rental days: " + getChargeableRentalDays());
        System.out.println("Pre-discount total charge: $" + getPrediscountTotal());
        System.out.println("Discount percent: " + getDiscountPercent() + "%");
        System.out.println("Discount amount: $" + getDiscountAmount());
        System.out.println("Final total charge: $" + getFinalTotal());
    }

    public int getChargeableRentalDays() {
        return chargeableRentalDays;
    }

    public RentalTool getRentalTool() {
        return rentalTool;
    }

    public int getRentalDayCount() {
        return rentalDayCount;
    }

    public String getCheckoutDate() {
        return formatter.format(checkoutDate);
    }

    public String getDueDate() {
        return formatter.format(dueDate);
    }

    public String getDailyRentalChargeRate() {
        return currencyFormatter.format(dailyRentalChargeRate);
    }

    public String getPrediscountTotal() {
        return currencyFormatter.format(prediscountTotal);
    }

    public int getDiscountPercent() {
        return (int) (discountPercent * 100);
    }

    public BigDecimal getDiscountAmount() {
        return new BigDecimal(prediscountTotal * discountPercent).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getFinalTotal() {
        return new BigDecimal(String.valueOf(new BigDecimal(prediscountTotal).subtract(getDiscountAmount()))).setScale(2, RoundingMode.HALF_UP);
    }
}
