package com.bteposdemo.exceptions;

public class InvalidRentalDaysException extends Exception{
    public InvalidRentalDaysException(String errorMessage) {
        super(errorMessage);
    }
}
