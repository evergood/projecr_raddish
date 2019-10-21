import com.foxminded.math.operation.IntegerDivider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntegerDividerTest {
    private final IntegerDivider integerDivider = new IntegerDivider();

    @Test
    void integerDividerShouldThrownExceptionForZeroDivisor() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> integerDivider.integerDivide(123, 0));
        assertEquals(exception.getMessage(), "Can't divide by zero");
    }

    @ParameterizedTest
    @CsvSource({"-1,7", "-10,7", "-500,7"})
    void integerDividerShouldThrowExceptionForNegativeDividends(int divident, int divisor) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                integerDivider.integerDivide(divident, divisor));
        assertEquals(exception.getMessage(), "Don't use negative arguments");
    }

    @Test
    void integerDividerShouldReturnCorrectLongDivisionForOneDigitDivisor() {
        final String actual = integerDivider.integerDivide(12045, 7);
        final String expected =
                        "_12045|7\n" +
                        "  7   |----\n" +
                        "  -   |1720\n" +
                        " _50\n" +
                        "  49\n" +
                        "  --\n" +
                        "  _14\n" +
                        "   14\n" +
                        "   --\n" +
                        "     5";
        assertEquals(expected, actual);
    }

    @Test
    void integerDividerShouldReturnCorrectLongDivisionForTwoDigitsDivisor() {
        final String actual = integerDivider.integerDivide(12045, 77);
        final String expected =
                        "_12045|77\n" +
                        "  77  |---\n" +
                        "  --  |156\n" +
                        " _434\n" +
                        "  385\n" +
                        "  ---\n" +
                        "  _495\n" +
                        "   462\n" +
                        "   ---\n" +
                        "    33";
        assertEquals(expected, actual);
    }

    @Test
    void integerDividerShouldReturnCorrectLongDivisionThreeDigitsDivisor() {
        final String actual = integerDivider.integerDivide(12045, 777);
        final String expected =
                        "_12045|777\n" +
                        "  777 |---\n" +
                        "  --- |15\n" +
                        " _4275\n" +
                        "  3885\n" +
                        "  ----\n" +
                        "   390";
        assertEquals(expected, actual);
    }

    @Test
    void integerDividerShouldReturnCorrectLongDivisionIfDividendIsSmallerThanDivisor() {
        final String actual = integerDivider.integerDivide(120, 777);
        final String expected =
                        "_120|777\n" +
                        "    |---\n" +
                        "    |0";
        assertEquals(expected, actual);
    }

    @Test
    void integerDividerShouldReturnCorrectLongDivisionTwoZeroesSmallerDivisor() {
        final String actual = integerDivider.integerDivide(1000, 10);
        final String expected =
                        "_1000|10\n" +
                        " 10  |---\n" +
                        " --  |100";
        assertEquals(expected, actual);
    }

    @Test
    void integerDividerShouldReturnCorrectLongDivisionForDivisorIsEqualToDividend() {
        final String actual = integerDivider.integerDivide(1000, 1000);
        final String expected =
                        "_1000|1000\n" +
                        " 1000|----\n" +
                        " ----|1\n" +
                        "    0";
        assertEquals(expected, actual);
    }

    @Test
    void integerDividerShouldReturnCorrectLongDivisionForDivisorIsEqualOne() {
        final String actual = integerDivider.integerDivide(1000, 1);
        final String expected =
                        "_1000|1\n" +
                        " 1   |----\n" +
                        " -   |1000";
        assertEquals(expected, actual);
    }

    @Test
    void integerDividerShouldReturnCorrectLongDivisionForDivisorIncludingSeveralZeroes() {
        final String actual = integerDivider.integerDivide(1003, 15);
        final String expected =
                        "_1003|15\n" +
                        "  90 |--\n" +
                        "  -- |66\n" +
                        " _103\n" +
                        "   90\n" +
                        "   --\n" +
                        "   13";
        assertEquals(expected, actual);
    }
}
