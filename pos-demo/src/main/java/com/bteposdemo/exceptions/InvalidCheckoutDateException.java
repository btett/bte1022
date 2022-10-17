package com.bteposdemo.exceptions;

public class InvalidCheckoutDateException extends Exception {
    public InvalidCheckoutDateException(String errorMessage) {
        super(errorMessage);
    }
}
