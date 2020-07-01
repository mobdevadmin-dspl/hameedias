package com.datamation.hmdsfa.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

public class EnglishNumberToWords {

//    private static final String[] tensNames = { "", " ten", " twenty", " thirty", " forty",
//            " fifty", " sixty", " seventy", " eighty", " ninety" };
//
//    private static final String[] numNames = { "", " one", " two", " three", " four", " five",
//            " six", " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen",
//            " fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen" };
//
//    private static String convertLessThanOneThousand(int number)
//    {
//        String soFar;
//
//        if (number % 100 < 20)
//        {
//            soFar = numNames[number % 100];
//            number /= 100;
//        } else
//        {
//            soFar = numNames[number % 10];
//            number /= 10;
//
//            soFar = tensNames[number % 10] + soFar;
//            number /= 10;
//        }
//        if (number == 0)
//            return soFar;
//        return numNames[number] + " hundred" + soFar;
//    }
//
//    public static String convert(long number)
//    {
//        // 0 to 999 999 999 999
//        if (number == 0)
//        {
//            return "zero";
//        }
//
//        String snumber = Long.toString(number);
//
//        // pad with "0"
//        String mask = "000000000000";
//        DecimalFormat df = new DecimalFormat(mask);
//        snumber = df.format(number);
//
//        // XXXnnnnnnnnn
//        int billions = Integer.parseInt(snumber.substring(0, 3));
//        // nnnXXXnnnnnn
//        int millions = Integer.parseInt(snumber.substring(3, 6));
//        // nnnnnnXXXnnn
//        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
//        // nnnnnnnnnXXX
//        int thousands = Integer.parseInt(snumber.substring(9, 12));
//
//        String tradBillions;
//        switch (billions)
//        {
//            case 0:
//                tradBillions = "";
//                break;
//            case 1:
//                tradBillions = convertLessThanOneThousand(billions) + " billion ";
//                break;
//            default:
//                tradBillions = convertLessThanOneThousand(billions) + " billion ";
//        }
//        String result = tradBillions;
//
//        String tradMillions;
//        switch (millions)
//        {
//            case 0:
//                tradMillions = "";
//                break;
//            case 1:
//                tradMillions = convertLessThanOneThousand(millions) + " million ";
//                break;
//            default:
//                tradMillions = convertLessThanOneThousand(millions) + " million ";
//        }
//        result = result + tradMillions;
//
//        String tradHundredThousands;
//        switch (hundredThousands)
//        {
//            case 0:
//                tradHundredThousands = "";
//                break;
//            case 1:
//                tradHundredThousands = "one thousand ";
//                break;
//            default:
//                tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
//        }
//        result = result + tradHundredThousands;
//
//        String tradThousand;
//        tradThousand = convertLessThanOneThousand(thousands);
//        result = result + tradThousand;
//
//        // remove extra spaces!
//        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
//    }

    private static final double MAX_LIMIT = 99999999999.99;
    private static final String OUT_OF_BOUNDS_INPUT = "Value must be greater than 0 "
            + "and lower or equal to " + String.valueOf(MAX_LIMIT);
    private static final String INVALID_INPUT = "Unknow number pattern informed";
    private static final String ZERO = "zero dollars";

    private static final String[] oneToNineteenNames = {
            "", // sentinel value
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen"
    };

    private static final String[] tenToNinetyNames = {
            "", // sentinel value
            "ten",
            "twenty",
            "thirty",
            "forty",
            "fifty",
            "sixty",
            "seventy",
            "eighty",
            "ninety"
    };

    /**
     * Private constructor prevents initialization
     * as this is only a method factory
     */
    private EnglishNumberToWords() {/*deliberately empty*/}

    /**
     * Calls the chain of methods that translate the value to a string
     * representation of the decimal values.
     * Dollars need to be separated by commas after three digits, such as:
     * 12,345 for ten thousand three hundred and forty three,
     * and cents must be informed as two digits after a dot, such as
     * 1.45 for one and forty five
     * @param value
     * @return String with the decimal translation of value
     * @throws IllegalArgumentException if value informed does not match the needed pattern
     */
    public static String convert(String value) throws IllegalArgumentException {
        double number;
        try {
            number = Double.parseDouble(getUSPatternNumber(value));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if (number < 0.0 || number > (int) MAX_LIMIT) {
            throw new IllegalArgumentException(OUT_OF_BOUNDS_INPUT);
        }
        if (number == 0.0) {
            return ZERO;
        }

        return capitalize(translate(value));
    }

    /**
     * enum type that represents either integer of floating point types
     */
    private enum NUM_TYPE {
        INTEGER,
        FLOATING_POINT
    }

    /**
     * Gets the words representation for numbers according to the decimal system
     * @param value to be translated to words
     * @return String decimal representation of the digits
     */
    private static String getHundredsTensOnes(int value) {
        StringBuilder returnValue = new StringBuilder();
        if (value % 100 < 20) {
            returnValue.append(oneToNineteenNames[value % 100]);
            value /= 100;
        } else {
            returnValue.append(oneToNineteenNames[value % 10]);
            value /= 10;
            returnValue.insert(0, tenToNinetyNames[value % 10] + " ");
            value /= 10;
        }
        if (value > 0) {
            returnValue.insert(0, oneToNineteenNames[value] + " hundred ");
        }
        return returnValue.toString();
    }

    /**
     *  Translates the dollars value of a number
     * @param dollars value
     * @return words representation of dollars, empty string if dollars equal to zero
     */
    private static String translateDollars(String dollars) throws IllegalArgumentException, ParseException {
        StringBuilder dollarsStrBuilder = new StringBuilder(256);
        String[] values = dollars.split(",");
        int valuesIndex = 0;
        if (getIntValue(dollars) == 0) {
            // value is lower than one dollar, so print only cents
            return "";
        }
        switch (values.length) {
            case 2: // thousands
                dollarsStrBuilder.append(getHundredsTensOnes(getIntValue(values[valuesIndex++])));
                dollarsStrBuilder.append(" thousand ");
            case 1: // hundreds or less
                dollarsStrBuilder.append(getHundredsTensOnes(getIntValue(values[valuesIndex])));
                break;
        }
        return dollarsStrBuilder.append(" dollar(s)").toString();
    }

    /**
     * Translates the cents values of a floating point value
     * @param cents value
     * @return words representation of cents, empty string if cents equal to zero
     */
    private static String translateCents(String cents) throws ParseException {
        if (cents.equals("00")) {
            return "";
        }
        return getHundredsTensOnes(getIntValue(cents)) + " cent(s)";
    }

    /**
     * Does the translation of values from numeral to words
     * @param number to be translated
     * @return a String containing the word version of the informed number
     * @throws Exception
     */
    private static String translate(String number) throws IllegalArgumentException {
        StringBuilder numberInWords = new StringBuilder(256);
        try {
            switch (identifyType(number)) {
                case INTEGER:
                    numberInWords.append(translateDollars(number));
                    break;
                case FLOATING_POINT:
                    int cents_position = number.length()-2;
                    boolean isMoreThanOneDollar = true;
                    String cents = "";

                    numberInWords.append(translateDollars(number.substring(0, cents_position-1)));
                    if (numberInWords.toString().equals("")) {
                        isMoreThanOneDollar = false;
                    }
                    cents = translateCents(number.substring(cents_position));
                    if (isMoreThanOneDollar && !cents.equals("")) {
                        // gets the "and zero cents" case
                        numberInWords.append(" and ");
                    }
                    numberInWords.append(cents);
                    break;
                default:
                    throw new IllegalArgumentException(INVALID_INPUT);
            }
        } catch (IllegalArgumentException | ParseException ex) {
            System.err.println(ex);
            throw new IllegalArgumentException(ex);
        }
        return numberInWords.toString();
    }

    /**
     * Identifies if the informed number is an integer or a floating point number
     * @param number to be tested
     * @return {@code enum NUM_TYPE} value representing integer or floating point
     */
    private static NUM_TYPE identifyType(String number) {
        if (isFloatingPoint(number)) {
            return NUM_TYPE.FLOATING_POINT;
        }
        return NUM_TYPE.INTEGER;
    }

    /**
     * @param number to be evaluated
     * @return true if value fits floating point pattern of string
     * @throws IllegalArgumentException if argument is of invalid type
     */
    private static boolean isFloatingPoint(String number) throws IllegalArgumentException {
        if (!isValid(number)) {
            throw new IllegalArgumentException(INVALID_INPUT);
        }
        return Pattern.matches(".*(\\.\\d\\d)$", number);
    }

    /**
     * Gets the digits of the given value by the US number standards, that is,
     * 99,999 is transformed to 99999
     * @param value to be evaluated
     * @return String containing the digits of the number
     * @throws ParseException if {@code value} is of unrecognizable value
     */
    private static String getUSPatternNumber(String value) throws ParseException {
        return NumberFormat.getNumberInstance(Locale.US).parse(value).toString();
    }

    /**
     * Capitalizes the first character of a string
     * @param line
     * @return String containing capitalized line
     */
    private static String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    /**
     * Helper function
     * @param string to be evaluated
     * @return returns the integer representation of a string
     */
    private static int getIntValue(String s) throws ParseException {
        return Math.abs(Integer.valueOf(getUSPatternNumber(s)));
    }

    /**
     * Tests if user entered a number with error format regarding commas.
     * Commas required between powers of 1,000
     * Can't start with "."
     * Pass: (1,000,000), (0.01)
     * Fail: (1000000), (1,00,00,00), (.01)
     * @param values to be tested
     * @throws IllegalArgumentException if a single value has more than 3 digits
     * @return true if value informed is within valid restrictions, false otherwise
     */
    private static boolean isValid(String number) {
        if (!Pattern.matches("^\\d{1,3}(,\\d{3})*(\\.\\d\\d)?$", number)) {
            return false;
        }
        return true;
    }
}
