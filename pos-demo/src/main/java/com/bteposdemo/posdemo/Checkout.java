package com.bteposdemo.posdemo;

import com.bteposdemo.exceptions.InvalidCheckoutDateException;
import com.bteposdemo.exceptions.InvalidDiscountException;
import com.bteposdemo.exceptions.InvalidRentalDaysException;
import com.bteposdemo.exceptions.InvalidToolCodeException;
import com.bteposdemo.staticdata.RentalTool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

public class Checkout {
    private RentalTool rentalTool;
    private int rentalDayCount;
    private double discountPercent = -1;
    private LocalDate checkoutDate;
    private Scanner inputScanner = new Scanner(System.in);;

    //used to gather user (employee) input to produce a rental agreement
    public RentalAgreement userInputPrompt() {
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
                System.out.println("Invalid checkout date, please try again. [Enter date as mm/dd/yy]");
            }
        }
        while (rentalDayCount < 1) {
            try {
                rentalDayUserPrompt();
            } catch (InvalidRentalDaysException e) {
                System.out.println("Invalid rental days, please try again. [Enter as whole number > 0]");
            }
        }

        while (discountPercent < 0 || discountPercent > 100) {
            try {
                discountPercentUserPrompt();
            } catch (InvalidDiscountException | InputMismatchException e) {
                System.out.println("Invalid discount percentage, please try again. [Enter as whole number from 0 - 100]");
            }
        }

        return new RentalAgreement(rentalTool, rentalDayCount, checkoutDate, discountPercent);
    }

    public RentalTool toolCodeUserPrompt() throws InvalidToolCodeException {
        String userInput;
        boolean validToolEntered;
        System.out.println("Enter the code for one of these available tools to rent: ");
        for (RentalTool tool : RentalTool.values()) {
            System.out.println(tool);
        }        userInput = inputScanner.nextLine();
        String finalUserInput = userInput;
        validToolEntered = Stream.of(RentalTool.values()).anyMatch(e -> e.name().equals(finalUserInput));
        if (validToolEntered) {
            this.rentalTool = RentalTool.valueOf(userInput);
        } else {
            throw new InvalidToolCodeException("Invalid tool code entered.");
        }

        return this.rentalTool;
    }

    public LocalDate checkoutDateUserPrompt() throws InvalidCheckoutDateException {
        System.out.println("Please enter a checkout date (mm/dd/yy) : ");
        String uDate = inputScanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        formatter = formatter.withLocale(Locale.US);

        try {
            this.checkoutDate = LocalDate.parse(uDate, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidCheckoutDateException("Invalid checkout date entered.");
        }
        return this.checkoutDate;
    }

    public int rentalDayUserPrompt() throws InvalidRentalDaysException {
        System.out.println("Enter amount of rental days : ");
        String inputString = inputScanner.nextLine();
        int userInput;
        try {
            userInput = Integer.parseInt(inputString);
        }
        catch (NumberFormatException e){
            throw new InvalidRentalDaysException("Invalid rental days entered.");
        }

        if (userInput < 1) {
            throw new InvalidRentalDaysException("Invalid rental days entered.");
        }
        this.rentalDayCount = userInput;
        return this.rentalDayCount;
    }

    public double discountPercentUserPrompt() throws InvalidDiscountException {
        System.out.println("Enter amount of discount percent : ");
        String inputString = inputScanner.nextLine();
        int userInput;
        try {
            userInput = Integer.parseInt(inputString);
        }
        catch (NumberFormatException e){
            throw new InvalidDiscountException("Invalid discount percentage entered.");
        }

        if (userInput < 0 || userInput > 100) {
            throw new InvalidDiscountException("Invalid discount percentage entered.");
        }
        this.discountPercent = userInput;

        return this.discountPercent;
    }

}
