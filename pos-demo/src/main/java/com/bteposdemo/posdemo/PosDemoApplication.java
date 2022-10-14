package com.bteposdemo.posdemo;

import com.bteposdemo.exceptions.InvalidToolCodeException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class PosDemoApplication {

    public static void main(String[] args) throws InvalidToolCodeException {
        SpringApplication.run(PosDemoApplication.class, args);

        Checkout checkout = new Checkout();
        RentalAgreement rentalAgreement = checkout.userInputPrompt();
        rentalAgreement.printRentalAgreement();

    }

}
