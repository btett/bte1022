package com.bteposdemo.posdemo;

import com.bteposdemo.exceptions.InvalidToolCodeException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PosDemoApplication {

    public static void main(String[] args) throws InvalidToolCodeException {
        SpringApplication.run(PosDemoApplication.class, args);
        Checkout checkout = new Checkout();
        RentalAgreement rentalAgreement = checkout.userInputPrompt();
        rentalAgreement.printRentalAgreement();
        System.out.println("Done");
    }

}
