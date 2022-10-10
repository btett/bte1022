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

    public void setDailyChargePrice(double dailyChargePrice) {
        this.dailyChargePrice = dailyChargePrice;
    }

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }
}
