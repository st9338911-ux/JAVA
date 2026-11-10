package org.example;

public class Calculator {
    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Ділення на нуль неможливе.");
        }
        return a / b;
    }

    public double sqrt(double a) throws InvalidInputException {
        if (a < 0) {
            throw new InvalidInputException("Число для обчислення квадратного кореня не може бути від'ємним.");
        }
        return Math.sqrt(a);
    }
}
