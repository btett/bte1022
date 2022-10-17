package com.bteposdemo.exceptions;

public class InvalidDiscountException extends Exception {
    public InvalidDiscountException(String errorMessage) {
        super(errorMessage);
    }
}
