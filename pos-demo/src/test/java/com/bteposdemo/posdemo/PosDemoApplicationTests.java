package com.bteposdemo.posdemo;

import com.bteposdemo.staticdata.RentalTool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PosDemoApplicationTests {

    //since checkout takes in command line input which is not easily tested, bypass that and construct the rental agreement that the checkout user input prompts would generate

    @Test
    void checkoutTest1() {
        assertThrows(IllegalArgumentException.class, () -> new RentalAgreement(RentalTool.JAKR, 5, LocalDate.of(2015, 9, 3), 101));
    }

    @Test
    void checkoutTest2() {
        RentalAgreement rentalAgreement = new RentalAgreement(RentalTool.LADW, 3, LocalDate.of(2020, 7, 2), 10);
        rentalAgreement.printRentalAgreement();
        //manual calc = 2
        assertEquals(RentalTool.LADW, rentalAgreement.getRentalTool());
        assertEquals(3, rentalAgreement.getRentalDayCount());
        assertEquals("07/02/2020", rentalAgreement.getCheckoutDate());
        assertEquals("07/05/2020", rentalAgreement.getDueDate());
        assertEquals("1.99", rentalAgreement.getDailyRentalChargeRate());
        assertEquals(2, rentalAgreement.getChargeableRentalDays());
        assertEquals("3.98", rentalAgreement.getPrediscountTotal());
        assertEquals(10, rentalAgreement.getDiscountPercent());
        assertEquals("0.40", rentalAgreement.getDiscountAmount().toString());
        assertEquals("3.58", rentalAgreement.getFinalTotal().toString());
    }

    @Test
    void checkoutTest3() {
        RentalAgreement rentalAgreement = new RentalAgreement(RentalTool.CHNS, 5, LocalDate.of(2015, 7, 2), 25);
        rentalAgreement.printRentalAgreement();
        //manual calc = 3
        assertEquals(RentalTool.CHNS, rentalAgreement.getRentalTool());
        assertEquals(5, rentalAgreement.getRentalDayCount());
        assertEquals("07/02/2015", rentalAgreement.getCheckoutDate());
        assertEquals("07/07/2015", rentalAgreement.getDueDate());
        assertEquals("1.49", rentalAgreement.getDailyRentalChargeRate());
        assertEquals(3, rentalAgreement.getChargeableRentalDays());
        assertEquals("4.47", rentalAgreement.getPrediscountTotal());
        assertEquals(25, rentalAgreement.getDiscountPercent());
        assertEquals("1.12", rentalAgreement.getDiscountAmount().toString());
        assertEquals("3.35", rentalAgreement.getFinalTotal().toString());
    }

    @Test
    void checkoutTest4() {
        RentalAgreement rentalAgreement = new RentalAgreement(RentalTool.JAKD, 6, LocalDate.of(2015, 9, 3), 0);
        rentalAgreement.printRentalAgreement();
        //manual calc = 3
        assertEquals(RentalTool.JAKD, rentalAgreement.getRentalTool());
        assertEquals(6, rentalAgreement.getRentalDayCount());
        assertEquals("09/03/2015", rentalAgreement.getCheckoutDate());
        assertEquals("09/09/2015", rentalAgreement.getDueDate());
        assertEquals("2.99", rentalAgreement.getDailyRentalChargeRate());
        assertEquals(3, rentalAgreement.getChargeableRentalDays());
        assertEquals("8.97", rentalAgreement.getPrediscountTotal());
        assertEquals(0, rentalAgreement.getDiscountPercent());
        assertEquals("0.00", rentalAgreement.getDiscountAmount().toString());
        assertEquals("8.97", rentalAgreement.getFinalTotal().toString());

    }

    @Test
    void checkoutTest5() {
        RentalAgreement rentalAgreement = new RentalAgreement(RentalTool.JAKR, 9, LocalDate.of(2015, 7, 2), 0);
        rentalAgreement.printRentalAgreement();
        //manual calc = 5
        assertEquals(RentalTool.JAKR, rentalAgreement.getRentalTool());
        assertEquals(9, rentalAgreement.getRentalDayCount());
        assertEquals("07/02/2015", rentalAgreement.getCheckoutDate());
        assertEquals("07/11/2015", rentalAgreement.getDueDate());
        assertEquals("2.99", rentalAgreement.getDailyRentalChargeRate());
        assertEquals(5, rentalAgreement.getChargeableRentalDays());
        assertEquals("14.95", rentalAgreement.getPrediscountTotal());
        assertEquals(0, rentalAgreement.getDiscountPercent());
        assertEquals("0.00", rentalAgreement.getDiscountAmount().toString());
        assertEquals("14.95", rentalAgreement.getFinalTotal().toString());
    }

    @Test
    void checkoutTest6() {
        RentalAgreement rentalAgreement = new RentalAgreement(RentalTool.JAKR, 4, LocalDate.of(2020, 7, 2), 50);
        rentalAgreement.printRentalAgreement();
        //manual calc = 1
        assertEquals(RentalTool.JAKR, rentalAgreement.getRentalTool());
        assertEquals(4, rentalAgreement.getRentalDayCount());
        assertEquals("07/02/2020", rentalAgreement.getCheckoutDate());
        assertEquals("07/06/2020", rentalAgreement.getDueDate());
        assertEquals("2.99", rentalAgreement.getDailyRentalChargeRate());
        assertEquals(1, rentalAgreement.getChargeableRentalDays());
        assertEquals("2.99", rentalAgreement.getPrediscountTotal());
        assertEquals(50, rentalAgreement.getDiscountPercent());
        assertEquals("1.50", rentalAgreement.getDiscountAmount().toString());
        assertEquals("1.49", rentalAgreement.getFinalTotal().toString());
    }

}
