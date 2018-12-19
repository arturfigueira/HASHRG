package com.ca.datamasker.custom;

/**
 * Object that represents a Brazilian Identity Number called in Portuguese RegistroRegal
 */
class RegistroGeral {
    final String number;
    final String checkDigit;

    private static final int INITIAL_WEIGHT = 2;

    /**
     * Creates a new RegistroGeral.
     *
     * @param number RG number without its CheckDigit. Number can formatted with or without its mask (usually: xx.xx.xxx)
     * @param checkDigit RG check Digit. X as value also
     * @throws IllegalArgumentException If {@param number} is considered invalid
     */
    public RegistroGeral(final String number, final String checkDigit) {
        this.number = number;
        this.checkDigit = checkDigit;
    }

    /**
     * Creates a new RegistroGeral. Check Digit will calculated automatically
     *
     * @param number RG number without its CheckDigit. Number can formatted with or without its mask (usually: xx.xx.xxx)
     * @throws IllegalArgumentException If {@param number} is considered invalid
     */
    public RegistroGeral(final String number) {
        this.number = number;
        this.checkDigit = getCheckDigit(number);
    }

    public String getNumber() {
        return number;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    private String getCheckDigit(final String originalNumber){
        int columnsSum = 0;
        final String number = cleanNumber(originalNumber);
        for(int i=0; i< number.length(); i++){
            final int numericValue = Character.getNumericValue(number.charAt(i));
            columnsSum += (INITIAL_WEIGHT + i) * numericValue;
        }

        final int modOfTotalSum = mod11(columnsSum);
        String checkDigit = null;
        switch (modOfTotalSum){
            case 10: checkDigit = "X"; break;
            case 11: checkDigit = "0"; break;
            default: checkDigit = Integer.toString(modOfTotalSum);
        }

        return checkDigit;
    }

    private static int mod11(final int value){
        return 11 - (value % 11);
    }

    private String cleanNumber(final String originalNumber){
        return originalNumber.replaceAll("\\D", "");
    }

    @Override
    public String toString() {
        return number+""+checkDigit;
    }
}
