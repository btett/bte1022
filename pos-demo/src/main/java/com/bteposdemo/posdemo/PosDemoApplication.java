package com.bteposdemo.posdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PosDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosDemoApplication.class, args);
        Checkout checkout = new Checkout();
        //invoke the checkout's input prompts, which produces a rental agreement
        RentalAgreement rentalAgreement = checkout.userInputPrompt();
        rentalAgreement.printRentalAgreement();
    }

}
