package com.ca.datamasker.custom;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class HashRGTest {

    private static final String invalidRGWithFormatMask = "29.097.999-2";
    private static final String validWithFormatMaskRG = "29.097.450-1";


    HashRG hasher;

    @Before
    public void setUp() throws Exception {
        hasher = new HashRG();
    }

    @Test
    public void hashValidRG() {
        final String hashedRG = hasher.hashIt(validWithFormatMaskRG);
        assertTrue(RGValidator.isValid(cleanUp(hashedRG)));
    }

    @Test
    public void hashValidCleanRG() {
        final String hashedRG = hasher.hashIt(cleanUp(validWithFormatMaskRG));
        assertTrue(RGValidator.isValid(hashedRG));
    }

    @Test(expected = IllegalArgumentException.class)
    public void hashInvalidRG() {
        hasher.hashIt(invalidRGWithFormatMask);
    }

    @Test(expected = IllegalArgumentException.class)
    public void hashInvalidRGWithoutFormatMask() {
        hasher.hashIt(cleanUp(invalidRGWithFormatMask));
    }


    @Test(expected = IllegalArgumentException.class)
    public void hashSmallerThanAllowedLengthRG() {
        final Random rand = new Random();
        int n = rand.nextInt(99999) + 1;
        hasher.hashIt(Integer.toString(n));
    }

    @Test(expected = IllegalArgumentException.class)
    public void hashBiggerThanAllowedLengthRG() {
        final Random rand = new Random();
        int n = rand.nextInt(999999) + 100000;
        hasher.hashIt("23698"+Integer.toString(n));
    }

    @Test
    public void checkMaskIntegrityForCleanRG(){
        final String firstHash = hasher.hashIt(cleanUp(validWithFormatMaskRG));
        final String secondHash = hasher.hashIt(cleanUp(validWithFormatMaskRG));
        assertEquals("same input value, should return always the same hashed value", firstHash, secondHash);
    }

    @Test
    public void checkMaskIntegrityForUncleanRG(){
        final String firstHash = hasher.hashIt(validWithFormatMaskRG);
        final String secondHash = hasher.hashIt(validWithFormatMaskRG);
        assertEquals("same input value, should return always the same hashed value", firstHash, secondHash);
    }

    @Test
    public void checkMaskIntegrityIgnoreFormatMaskRG(){
        final String firstHash = hasher.hashIt(validWithFormatMaskRG);
        final String secondHash = hasher.hashIt(cleanUp(validWithFormatMaskRG));
        assertEquals("same RG input value, should return always the same hashed value, if we strip its format mask",cleanUp(firstHash), secondHash);
    }

    private String cleanUp(final String originalRG){
        return originalRG.replaceAll("\\D", "");
    }
}