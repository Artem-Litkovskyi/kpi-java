package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {
    private record assertEqualsTest(
            String value,
            int expected
    ) {}

    private record assertThrowsTest(
            String value,
            Class<? extends Throwable> exceptionClass
    ) {}

    private void runAssertEqualsTests(assertEqualsTest[] tests) {
        for (var data : tests) {
            assertEquals(
                    data.expected,
                    StringCalculator.add(data.value),
                    String.format(
                            "StringCalculator.add(\"%s\")",
                            data.value.replace("\n", "\\n")
                    )
            );
        }
    }

    private void runAssertThrowsTests(assertThrowsTest[] tests) {
        for (var data : tests) {
            assertThrows(
                    data.exceptionClass,
                    () -> StringCalculator.add(data.value),
                    String.format(
                            "StringCalculator.add(\"%s\")",
                            data.value.replace("\n", "\\n")
                    )
            );
        }
    }

    @Test
    public void testAddBasicComma() {
        assertEqualsTest[] assertEqualsTests = new assertEqualsTest[] {
                new assertEqualsTest("", 0),
                new assertEqualsTest("1", 1),
                new assertEqualsTest("1,2", 3),
                new assertEqualsTest("1,22", 23)
        };

        assertThrowsTest[] assertThrowsTests = new assertThrowsTest[] {
                new assertThrowsTest("1,,2", IllegalArgumentException.class),
                new assertThrowsTest(",", IllegalArgumentException.class),
                new assertThrowsTest("jfasd", IllegalArgumentException.class)
        };

        runAssertEqualsTests(assertEqualsTests);
        runAssertThrowsTests(assertThrowsTests);
    }

    @Test
    public void testAddBasicNewline() {
        assertEqualsTest[] assertEqualsTests = new assertEqualsTest[] {
                new assertEqualsTest("1\n2,3", 6),
                new assertEqualsTest("1\n1,34", 36)
        };

        assertThrowsTest[] assertThrowsTests = new assertThrowsTest[] {
                new assertThrowsTest("1,\n", IllegalArgumentException.class)
        };

        runAssertEqualsTests(assertEqualsTests);
        runAssertThrowsTests(assertThrowsTests);
    }

    @Test
    public void testAddCustomShortDelimiter() {
        assertEqualsTest[] assertEqualsTests = new assertEqualsTest[] {
                new assertEqualsTest("//;\n1,2\n3;4", 10),
                new assertEqualsTest("//.\n1.2", 3),
                new assertEqualsTest("//*\n1*2", 3),
        };

        assertThrowsTest[] assertThrowsTests = new assertThrowsTest[] {
                new assertThrowsTest("1,//a\n", IllegalArgumentException.class),
                new assertThrowsTest("//;\n1,//a\n", IllegalArgumentException.class),
                new assertThrowsTest("//;\n1,2\n3;;4", IllegalArgumentException.class),
                new assertThrowsTest("//;^#\n1,2\n3;;4", IllegalArgumentException.class)
        };

        runAssertEqualsTests(assertEqualsTests);
        runAssertThrowsTests(assertThrowsTests);
    }

    @Test
    public void testAddCustomLongDelimiter() {
        assertEqualsTest[] assertEqualsTests = new assertEqualsTest[] {
                new assertEqualsTest("//[**]\n1**11**111,1\n4", 128)
        };

        assertThrowsTest[] assertThrowsTests = new assertThrowsTest[] {
                new assertThrowsTest("1,2//[^^]\n", IllegalArgumentException.class),
                new assertThrowsTest("//[**]\n1**//[^^]\n", IllegalArgumentException.class),
                new assertThrowsTest("//[]\n1,2", IllegalArgumentException.class),
                new assertThrowsTest("//[**]\n1**11***111,1\n4", IllegalArgumentException.class),
                new assertThrowsTest("//[**]qwe\n1**11***111,1\n4", IllegalArgumentException.class)
        };

        runAssertEqualsTests(assertEqualsTests);
        runAssertThrowsTests(assertThrowsTests);
    }

    @Test
    public void testAddMultipleCustomDelimiters() {
        assertEqualsTest[] assertEqualsTests = new assertEqualsTest[] {
                new assertEqualsTest("//[*][%]\n1*2%3", 6),
                new assertEqualsTest("//[**][delimiter]\n11delimiter22**33", 66),
                new assertEqualsTest("//[*][***][**]\n1*1***1,1**1\n1", 6)
        };

        assertThrowsTest[] assertThrowsTests = new assertThrowsTest[] {
                new assertThrowsTest("//[**][delimiter]\n11dewimitew22**33", IllegalArgumentException.class),
                new assertThrowsTest("//[**]qwe[delimiter]rty\n11dewimitew22**33", IllegalArgumentException.class)
        };

        runAssertEqualsTests(assertEqualsTests);
        runAssertThrowsTests(assertThrowsTests);
    }

    @Test
    public void testAddNegative() {
        assertThrowsTest[] assertThrowsTests = new assertThrowsTest[] {
                new assertThrowsTest("-1,2,-3", IllegalArgumentException.class),
                new assertThrowsTest("-1,2\n-3", IllegalArgumentException.class),
                new assertThrowsTest("//&\n-1,2&3\n-4", IllegalArgumentException.class),
                new assertThrowsTest("//[**][^]\n11^-22**33", IllegalArgumentException.class),
        };

        runAssertThrowsTests(assertThrowsTests);
    }

    @Test
    public void testAddGreaterThan1000() {
        assertEqualsTest[] assertEqualsTests = new assertEqualsTest[] {
                new assertEqualsTest("1000,999,1001", 1999)
        };

        runAssertEqualsTests(assertEqualsTests);
    }
}