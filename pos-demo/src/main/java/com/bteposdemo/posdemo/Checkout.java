package com.bteposdemo.posdemo;

import com.bteposdemo.staticdata.RentalTool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.stream.Stream;

public class Checkout {
    private RentalTool rentalTool;
    private int rentalDayCount;
    private double discountPercent;
    private LocalDate checkoutDate;
    private Scanner inputScanner;


    public RentalAgreement userInputPrompt() {
        inputScanner = new Scanner(System.in);

        try {

        } catch {

        }
        return null;
    }

    private RentalTool toolCodeUserPrompt() {
        String userInput;
        boolean validToolEntered = false;
        System.out.println("Enter tool code of rental tool : ");
        userInput = inputScanner.nextLine();
        String finalUserInput = userInput;
        validToolEntered = Stream.of(RentalTool.values()).anyMatch(e -> e.name().equals(finalUserInput));
        if (validToolEntered) {
            this.rentalTool = RentalTool.valueOf(userInput);
        } else {
            throw new RuntimeException();
        }

        return this.rentalTool;
    }

    private LocalDate checkoutDateUserPrompt() throws ParseException {
        String userInput;
        boolean validToolEntered = false;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        System.out.println("Please enter a checkout date (mm/dd/yyyy) : ");
        String uDate = inputScanner.nextLine();
        sdf.parse(uDate);

        this.checkoutDate = LocalDate.parse(uDate);
        return this.checkoutDate;
    }

    private int rentalDayUserPrompt() {
        System.out.println("Enter amount of rental days : ");
        int userInput = inputScanner.nextInt();

        if (userInput < 1) {
            throw new RuntimeException();
        }
        this.rentalDayCount = userInput;
        return this.rentalDayCount;
    }

    private double discountPercentUserPrompt() {
        System.out.println("Enter amount of discount percent : ");
        int userInput = inputScanner.nextInt();

        if (userInput < 0 || userInput > 100) {
            throw new RuntimeException();
        }
        this.discountPercent = userInput / 100;
        return this.discountPercent;
    }
}
