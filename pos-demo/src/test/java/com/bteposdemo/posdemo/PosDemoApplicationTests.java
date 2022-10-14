package com.bteposdemo.posdemo;

import com.bteposdemo.staticdata.RentalTool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PosDemoApplicationTests {

    @Test
    void checkoutTest1() {
        //since checkout takes in command line input which is not easily tested, bypass that and construct the rental agreement that the checkout user input prompts would generate
        assertThrows(IllegalArgumentException.class, () -> new RentalAgreement(RentalTool.JAKR, 5, LocalDate.of(2015, 9, 3), 101));
    }

    @Test
    void checkoutTest2() {
        RentalAgreement rentalAgreement = new RentalAgreement(RentalTool.LADW, 3, LocalDate.of(2020, 7, 2), 10);
        rentalAgreement.printRentalAgreement();
        //manual calc = 2
    }

    @Test
    void checkoutTest3() {
        RentalAgreement rentalAgreement = new RentalAgreement(RentalTool.CHNS, 5, LocalDate.of(2015, 7, 2), 25);
        rentalAgreement.printRentalAgreement();
        //manual calc = 3

    }

    @Test
    void checkoutTest4() {
        RentalAgreement rentalAgreement = new RentalAgreement(RentalTool.JAKD, 6, LocalDate.of(2015, 9, 3), 0);
        rentalAgreement.printRentalAgreement();
        //manual calc = 3

    }

    @Test
    void checkoutTest5() {
        RentalAgreement rentalAgreement = new RentalAgreement(RentalTool.JAKR, 9, LocalDate.of(2015, 7, 2), 0);
        rentalAgreement.printRentalAgreement();
        //manual calc = 5
    }

    @Test
    void checkoutTest6() {
        RentalAgreement rentalAgreement = new RentalAgreement(RentalTool.JAKR, 4, LocalDate.of(2020, 7, 2), 50);
        rentalAgreement.printRentalAgreement();
        //manual calc = 1
    }

}
