package com.bteposdemo.posdemo;

import com.bteposdemo.staticdata.RentalTool;
import com.bteposdemo.staticdata.RentalToolCharges;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class RentalAgreement {
    private RentalTool rentalTool;
    private int rentalDayCount;

    private Date checkoutDate;
    private Date dueDate;

    private double dailyRentalChargeRate;
    private int chargeableRentalDays;

    private double prediscountTotalCost;
    private double discountPercent;
    private double discountedCostAmount;
    private double finalTotalCost;


    LocalDateTime getDueDate() {
        return LocalDateTime.from(checkoutDate.toInstant().atZone(ZoneId.of("UTC"))).plusDays(rentalDayCount);
    }

    void printRentalAgreement() {

    }


    double getDailyRentalChargeRate(){
        return RentalToolCharges.valueOf(rentalTool.getType()).getDailyChargePrice();
    }
}
