package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    public void testDivideByZeroThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(10.0, 0.0));
    }

    @Test
    public void testNegativeSqrtThrowsInvalidInputException() {
        assertThrows(InvalidInputException.class, () -> calculator.sqrt(-4.0));
    }

    @Test
    public void testValidAddition() {
        assertEquals(5.0, calculator.add(2.0, 3.0));
    }

    @Test
    public void testValidDivision() {
        assertEquals(2.5, calculator.divide(5.0, 2.0));
    }

    @Test
    public void testValidSqrt() throws InvalidInputException {
        assertEquals(4.0, calculator.sqrt(16.0));
    }
}
