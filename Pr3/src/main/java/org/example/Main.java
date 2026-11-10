package org.example;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        try {
            System.out.print("Введіть операцію (+, -, *, /, sqrt): ");
            String operation = scanner.next();

            if (operation.equals("sqrt")) {
                System.out.print("Введіть число: ");
                double num = scanner.nextDouble();
                double result = calculator.sqrt(num);
                System.out.println("Результат: " + result);
            } else {
                System.out.print("Введіть перше число: ");
                double num1 = scanner.nextDouble();

                System.out.print("Введіть друге число: ");
                double num2 = scanner.nextDouble();

                double result;
                switch (operation) {
                    case "+":
                        result = calculator.add(num1, num2);
                        break;
                    case "-":
                        result = calculator.subtract(num1, num2);
                        break;
                    case "*":
                        result = calculator.multiply(num1, num2);
                        break;
                    case "/":
                        result = calculator.divide(num1, num2);
                        break;
                    default:
                        throw new InvalidInputException("Непідтримувана операція: " + operation);
                }
                System.out.println("Результат: " + result);
            }
        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу: введене значення має бути числом.");
        } catch (ArithmeticException e) {
            System.out.println("Арифметична помилка: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        } finally {
            System.out.println("Обробка запиту завершена.");
            scanner.close();
        }
    }
}