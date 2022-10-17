package com.bteposdemo.staticdata;

public enum RentalToolCharges {
    Ladder(1.99, true, true, false),
    Chainsaw(1.49, true, false, true),
    Jackhammer(2.99, true, false, false);

    private double dailyChargePrice;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    RentalToolCharges(double dailyChargePrice, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.dailyChargePrice = dailyChargePrice;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public double getDailyChargePrice() {
        return dailyChargePrice;
    }

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }

}
