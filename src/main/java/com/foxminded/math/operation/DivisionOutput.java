package com.foxminded.math.operation;

public class DivisionOutput {
    public static void main(String[] args) {
        IntegerDivider integerDivider = new IntegerDivider();
        int dividend = 12345;
        int divisor = 150;
        System.out.println(integerDivider.integerDivide(dividend, divisor));
    }
}
