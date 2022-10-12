package com.bteposdemo.posdemo;

import com.bteposdemo.staticdata.RentalTool;
import com.bteposdemo.staticdata.RentalToolCharges;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RentalAgreement {
    private RentalTool rentalTool;
    private int rentalDayCount;

    private LocalDate checkoutDate;
    private LocalDate dueDate;

    private double dailyRentalChargeRate;
    private int chargeableRentalDays;

    private double prediscountTotalCost;
    private double discountPercent;
    private double discountedCostAmount;
    private double finalTotalCost;

    private Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    LocalDate getDueDate() {
        return checkoutDate.plusDays(rentalDayCount);
    }

    int getChargeableRentalDays() {
        if(RentalToolCharges.)
    }

    long calcWeekdaysBetweenDates() {
         return checkoutDate.datesUntil(getDueDate())
                .filter(d -> !weekend.contains(d.getDayOfWeek()))
                .count();
    }


    long calcWeekendDaysBetween() {
        return checkoutDate.datesUntil(getDueDate())
                .filter(d -> weekend.contains(d.getDayOfWeek()))
                .count();
    }

    long calcAllDaysBetween() {
        return checkoutDate.datesUntil(getDueDate()).count();
    }

    int calcHolidaysBetween() {
        int holidaysFound = 0;
        LocalDate julyDateToCheck;
        boolean laborDayFound = false;
        for(LocalDate date : checkoutDate.datesUntil(getDueDate()).collect(Collectors.toList())){
            if(date.getMonth().equals(Month.JULY) && date.getDayOfMonth() == 4){
                if(date.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                    if(getDueDate().getDayOfMonth() != 5){
                        holidaysFound++;
                        continue;
                    }
                }
                else {
                    holidaysFound++;
                    continue;
                }
            }
            if(!laborDayFound && date.getMonth().equals(Month.SEPTEMBER) && date.getDayOfWeek().equals(DayOfWeek.MONDAY)){
                laborDayFound = true;
                holidaysFound++;
                continue;
            }
        }
        return holidaysFound;
    }



    void printRentalAgreement() {

    }


    double getDailyRentalChargeRate(){
        return RentalToolCharges.valueOf(rentalTool.getType()).getDailyChargePrice();
    }
}
