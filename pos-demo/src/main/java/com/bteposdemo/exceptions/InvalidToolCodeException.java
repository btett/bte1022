package com.bteposdemo.exceptions;

public class InvalidToolCodeException extends Exception{
    public InvalidToolCodeException(String errorMessage) {
        super(errorMessage);
    }
}
