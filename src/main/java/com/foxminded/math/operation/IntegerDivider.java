package com.foxminded.math.operation;

public class IntegerDivider {

    private static final char SEPARATOR = '|';
    private static final char BAR = '_';
    private static final char LINE = '-';
    private static final char DEFAULT_CHAR = Character.MIN_VALUE;
    private static final String STRING_END_SPACES = "\\u0000+$";

    public String integerDivide(int dividend, int divisor) {
        validate(dividend, divisor);
        char[][] charsOfDivisionField = writeDividendAndDivisorToField(dividend, divisor);
        if (dividend < divisor) {
            charsOfDivisionField[2][String.valueOf(dividend).length() + 2] = '0';
            charsOfDivisionField[0][0] = BAR;
        } else {
            divide(charsOfDivisionField, dividend, divisor);
        }
        return arrayToString(charsOfDivisionField);
    }

    private static void validate(int dividend, int divisor) {

        if (divisor == 0) {
            throw new IllegalArgumentException("Can't divide by zero");
        }
        if (dividend < 0 || divisor < 0) {
            throw new IllegalArgumentException("Don't use negative arguments");
        }
    }

    private static String arrayToString(char[][] charsOfDivisionField) {
        StringBuilder output = new StringBuilder();
        for (char[] stringOfDivisionField : charsOfDivisionField) {
            String lineOutput = new String(stringOfDivisionField);
            output.append(lineOutput.replaceAll(STRING_END_SPACES, "")).append("\n");
        }
        return output.toString().replace(DEFAULT_CHAR, ' ').trim();
    }

    private char[][] writeDividendAndDivisorToField(int dividend, int divisor) {
        String dividendAsText = String.valueOf(dividend);
        String divisorAsText = String.valueOf(divisor);
        char [][] charsOfDivisionField = createDivisionField(dividendAsText);
        for (int i = 1; i <= dividendAsText.length(); i++) {
            charsOfDivisionField[0][i] = dividendAsText.charAt(i - 1);
        }
        for (int i = 1; i <= divisorAsText.length(); i++) {
            charsOfDivisionField[0][dividendAsText.length() + 1 + i] = divisorAsText.charAt(i - 1);
            charsOfDivisionField[1][dividendAsText.length() + 1 + i] = LINE;
        }

        charsOfDivisionField[0][dividendAsText.length() + 1] = SEPARATOR;
        charsOfDivisionField[1][dividendAsText.length() + 1] = SEPARATOR;
        charsOfDivisionField[2][dividendAsText.length() + 1] = SEPARATOR;
        return charsOfDivisionField;
    }

    private static void divide(char[][] charsOfDivisionField, int dividend, int divisor) {
        String dividendAsText = String.valueOf(dividend);
        int row = 0;
        int column = 1;
        int resultPointer = dividendAsText.length() + 2;

        while (column <= dividendAsText.length()) {
            if (getMiddleResult(charsOfDivisionField, row, column) < divisor) {
                if (Character.isDigit(charsOfDivisionField[2][resultPointer - 1])) {
                    charsOfDivisionField[2][resultPointer] = '0';
                    charsOfDivisionField[1][resultPointer++] = LINE;
                }
                if (isEligibleForTransferAfterZeroResultDigit(charsOfDivisionField, row, ++column)) {
                    charsOfDivisionField[row][column] = charsOfDivisionField[0][column];
                }
            } else {
                charsOfDivisionField[2][resultPointer] = Character.forDigit(getQuotient(charsOfDivisionField,
                        row, column, divisor), 10);
                charsOfDivisionField[1][resultPointer++] = LINE;
                writeSubtrahend(charsOfDivisionField, ++row, column, divisor);
                putMinus(charsOfDivisionField, row++, column);
                writeDifference(charsOfDivisionField, ++row, column, dividendAsText);
                if (isEligibleForTransferAfterDifferenceCalculation(charsOfDivisionField, row,
                        column, dividendAsText)) {
                    charsOfDivisionField[row][++column] = charsOfDivisionField[0][column];
                } else {
                    column++;
                }
            }
        }
    }

    private static char[][] createDivisionField(String dividendAsText) {
        return new char[dividendAsText.length() * 2 + 10][dividendAsText.length() * 3 + 10];
    }

    private static int getMiddleResult(char[][] charsOfDivisionField, int row, int column) {
        StringBuilder result = new StringBuilder();
        while (Character.isDigit(charsOfDivisionField[row][column])) {
            result.append(charsOfDivisionField[row][column--]);
        }
        return result.toString().equals("") ? 0 : Integer.parseInt(result.reverse().toString());
    }

    private static boolean isEligibleForTransferAfterZeroResultDigit(char[][] charsOfDivisionField,
                                                                     int row, int column) {
        return Character.isDigit(charsOfDivisionField[0][column]) &&
                (Character.getNumericValue(charsOfDivisionField[0][column]) > 0 ||
                        Character.isDigit(charsOfDivisionField[row][column - 1]));
    }

    private static boolean isEligibleForTransferAfterDifferenceCalculation(char[][] charsOfDivisionField, int row,
                                                                           int column, String dividend) {
        return column < dividend.length() && ((Character.getNumericValue(charsOfDivisionField[0][column + 1]) > 0 ||
                Character.isDigit(charsOfDivisionField[row][column])));
    }

    private static int getQuotient(char[][] charsOfDivisionField, int row, int column, int divisor) {
        return getMiddleResult(charsOfDivisionField, row, column) / divisor;
    }

    private static int getSubtrahend(char[][] charsOfDivisionField, int row, int column, int divisor) {
        return getQuotient(charsOfDivisionField, row, column, divisor) * divisor;
    }

    private static void writeSubtrahend(char[][] charsOfDivisionField, int row, int column, int divisor) {
        StringBuilder subtrahend = new StringBuilder().append(getSubtrahend(charsOfDivisionField,
                row - 1, column, divisor));
        for (int i = 0; i < subtrahend.length(); i++) {
            charsOfDivisionField[row][column] = subtrahend.charAt(subtrahend.length() - 1 - i);
            charsOfDivisionField[row + 1][column--] = LINE;
        }
    }

    private static void putMinus(char[][] charsOfDivisionField, int row, int column) {
        while (Character.isDigit(charsOfDivisionField[row - 1][column])) {
            column--;
        }
        charsOfDivisionField[row - 1][column] = BAR;
    }

    private static int getDifference(char[][] charsOfDivisionField, int row, int column) {
        return getMiddleResult(charsOfDivisionField, row - 3, column) -
                getMiddleResult(charsOfDivisionField, row - 2, column);
    }

    private static void writeDifference(char[][] charsOfDivisionField, int row, int column, String dividendAsText) {
        int difference = getDifference(charsOfDivisionField, row, column);
        if (difference > 0 || column == dividendAsText.length()) {
            StringBuilder differenceString = new StringBuilder().append(difference).reverse();
            for (int i = 0; i < differenceString.length(); i++) {
                charsOfDivisionField[row][column--] = differenceString.charAt(i);
            }
        }
    }
}
