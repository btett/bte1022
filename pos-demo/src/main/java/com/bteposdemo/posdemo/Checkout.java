package com.bteposdemo.posdemo;

import com.bteposdemo.staticdata.RentalTool;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Date;
import java.util.Scanner;
import java.util.stream.Stream;

public class Checkout {
    private RentalTool rentalTool;
    private int rentalDayCount;
    private double discountPercent;
    private Date checkoutDate;
    private Scanner inputScanner;


   public RentalAgreement userInputPrompt() {
        inputScanner = new Scanner(System.in);


       return null;
   }

   private RentalTool toolCodeUserPrompt() {
       boolean validToolEntered = false;
       while(!validToolEntered) {
           System.out.println("Enter tool code of rental tool : ");
           String userInput = inputScanner.nextLine();
           validToolEntered = Stream.of(RentalTool.values()).anyMatch(e -> e.name().equals(userInput));
           if (validToolEntered) {
               this.rentalTool = RentalTool.valueOf(userInput);
           }
       }
   }
}
