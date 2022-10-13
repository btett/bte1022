package com.bteposdemo.posdemo;

import com.bteposdemo.exceptions.InvalidCheckoutDateException;
import com.bteposdemo.exceptions.InvalidDiscountException;
import com.bteposdemo.exceptions.InvalidRentalDaysException;
import com.bteposdemo.exceptions.InvalidToolCodeException;
import com.bteposdemo.staticdata.RentalTool;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

public class Checkout {
    private RentalTool rentalTool;
    private int rentalDayCount;
    private double discountPercent = -1;
    private LocalDate checkoutDate;
    private Scanner inputScanner;


    public RentalAgreement userInputPrompt() throws InvalidToolCodeException {
        inputScanner = new Scanner(System.in);

        while (rentalTool == null) {
            try {
                toolCodeUserPrompt();
            } catch (InvalidToolCodeException e) {
                System.out.println("Invalid tool code, please try again");
            }
        }
        while (checkoutDate == null) {
            try {
                checkoutDateUserPrompt();
            } catch (InvalidCheckoutDateException e) {
                System.out.println("Invalid checkout date, please try again");
            }
        }
        while (rentalDayCount < 1) {
            try {
                rentalDayUserPrompt();
            } catch (InvalidRentalDaysException e) {
                System.out.println("Invalid rental days, please try again");
            }
        }
        while (discountPercent < 0 || discountPercent > 100) {
            try {
                discountPercentUserPrompt();
            } catch (InvalidDiscountException e) {
                System.out.println("Invalid discount percentage, please try again");
            }
        }

        return new RentalAgreement(rentalTool, rentalDayCount, checkoutDate, discountPercent);
    }

    private RentalTool toolCodeUserPrompt() throws InvalidToolCodeException {
        String userInput;
        boolean validToolEntered = false;
        System.out.println("Enter tool code of rental tool : ");
        userInput = inputScanner.nextLine();
        String finalUserInput = userInput;
        validToolEntered = Stream.of(RentalTool.values()).anyMatch(e -> e.name().equals(finalUserInput));
        if (validToolEntered) {
            this.rentalTool = RentalTool.valueOf(userInput);
        } else {
            throw new InvalidToolCodeException("Invalid tool code entered.");
        }

        return this.rentalTool;
    }

    private LocalDate checkoutDateUserPrompt() throws InvalidCheckoutDateException {
        String userInput;
        boolean validToolEntered = false;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        System.out.println("Please enter a checkout date (mm/dd/yyyy) : ");
        String uDate = inputScanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        formatter = formatter.withLocale(Locale.US);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH


        try {
            //   sdf.parse(uDate);
            this.checkoutDate = LocalDate.parse(uDate, formatter);

        } catch (DateTimeParseException e) {
            throw new InvalidCheckoutDateException("Invalid checkout date entered.");
        }
        //   this.checkoutDate = date;
        return this.checkoutDate;
    }

    private int rentalDayUserPrompt() throws InvalidRentalDaysException {
        System.out.println("Enter amount of rental days : ");
        int userInput = inputScanner.nextInt();

        if (userInput < 1) {
            throw new InvalidRentalDaysException("Invalid rental days entered.");
        }
        this.rentalDayCount = userInput;
        return this.rentalDayCount;
    }

    private double discountPercentUserPrompt() throws InvalidDiscountException {
        System.out.println("Enter amount of discount percent : ");
        int userInput = inputScanner.nextInt();

        if (userInput < 0 || userInput > 100) {
            throw new InvalidDiscountException("Invalid discount percentage entered.");
        }
        this.discountPercent = userInput / 100;
        return this.discountPercent;
    }
}
