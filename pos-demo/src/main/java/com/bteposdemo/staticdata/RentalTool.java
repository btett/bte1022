package com.bteposdemo.staticdata;

public enum RentalTool {
    CHNS("Chainsaw","Stihl"),
    LADW("Ladder","Werner"),
    JAKD("Jackhammer","DeWalt"),
    JAKR("Jackhammer","Ridgid");

    private String type;
    private String brand;

    RentalTool(String type, String brand) {
        this.type = type;
        this.brand = brand;
    }
}
