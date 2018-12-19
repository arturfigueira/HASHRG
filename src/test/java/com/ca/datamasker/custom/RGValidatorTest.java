package com.ca.datamasker.custom;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class RGValidatorTest {

    private static final String invalidRG = "490570852";
    private static final String validRG = "483296739";
    private static final String validRGWithXAsDV = "10354222X";
    private static final String validRGWithZeroAsDV = "310111110";

    @Test
    public void checkValidRG() {
        assertTrue(RGValidator.isValid(validRG));
    }


    @Test
    public void checkValidRGWithXAsDV() {
        assertTrue(RGValidator.isValid(validRGWithXAsDV));
    }

    @Test
    public void checkValidRGWithZeroAsDV() {
        assertTrue(RGValidator.isValid(validRGWithZeroAsDV));
    }

    @Test
    public void checkInValidRG() {
        assertFalse(RGValidator.isValid(invalidRG));
    }

    @Test
    public void checkInNullRG() {
        assertFalse(RGValidator.isValid(null));
    }

    @Test
    public void checkInEmptyRG() {
        assertFalse(RGValidator.isValid("   "));
    }

    @Test
    public void checkSmallerInvalidLengthRG() {
        final Random rand = new Random();
        int n = rand.nextInt(99999) + 1;
        assertFalse(RGValidator.isValid(Integer.toString(n)));
    }

    @Test
    public void checkBiggerInvalidLengthRG() {
        final Random rand = new Random();
        int n = rand.nextInt(999999) + 100000;
        assertFalse(RGValidator.isValid("236589"+Integer.toString(n)));
    }
}