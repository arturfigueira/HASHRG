package com.ca.datamasker.custom;

/**
 * Class with sole purpose to expose methods to validate a Brazilian identity card number (aka RG)
 * More about Brazilian RG here {@link https://en.wikipedia.org/wiki/Brazilian_identity_card}
 */
public class RGValidator {

    private static final int CLEAN_RG_LENGTH = 9;

    private RGValidator(){}

    /**
     * Check whether a given {@param rg} is valid or not,
     * based on the rules from Brazilian government
     * @param rg Identity Card number, with its check digit,
     * without its mask
     * @return TRUE if its valid, false otherwise
     */
    public static boolean isValid(final String rg){
        boolean rgIsValid = false;
        if(rg != null && !rg.isEmpty() && rg.length() == CLEAN_RG_LENGTH){
            final RegistroGeral registroGeral = new RegistroGeral(rg.substring(0, rg.length() - 1));
            rgIsValid = registroGeral.getCheckDigit().equalsIgnoreCase(rg.substring( rg.length() - 1));
        }
        return rgIsValid;
    }

    private static int mod11(final int value){
        return 11 - (value % 11);
    }
}
