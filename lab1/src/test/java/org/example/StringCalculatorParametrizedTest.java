package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorParametrizedTest {
    @ParameterizedTest
    @MethodSource({
            "provideAssertEqualsBasicComma",
            "provideAssertEqualsBasicNewline"
    })
    public void runAssertEqualsTest(String value, int expected) {
        assertEquals(
                expected,
                StringCalculator.add(value),
                String.format(
                        "StringCalculator.add(\"%s\")",
                        value.replace("\n", "\\n")
                )
        );
    }

    @ParameterizedTest
    @MethodSource({
            "provideAssertThrowsBasicComma",
            "provideAssertThrowsBasicNewline",
    })
    public void runAssertThrowsTest(String value, Class<? extends Throwable> exceptionClass) {
        assertThrows(
                exceptionClass,
                () -> StringCalculator.add(value),
                String.format(
                        "StringCalculator.add(\"%s\")",
                        value.replace("\n", "\\n")
                )
        );
    }

    public static Stream<Arguments> provideAssertEqualsBasicComma() {
        return Stream.of(
                Arguments.of("", 0),
                Arguments.of("1", 1),
                Arguments.of("1,2", 3),
                Arguments.of("1,22", 23)
        );
    }

    public static Stream<Arguments> provideAssertThrowsBasicComma() {
        return Stream.of(
                Arguments.of("1,,2", IllegalArgumentException.class),
                Arguments.of(",", IllegalArgumentException.class),
                Arguments.of("jfasd", IllegalArgumentException.class)
        );
    }

    public static Stream<Arguments> provideAssertEqualsBasicNewline() {
        return Stream.of(
                Arguments.of("1\n2,3", 6),
                Arguments.of("1\n1,34", 36)
        );
    }

    public static Stream<Arguments> provideAssertThrowsBasicNewline() {
        return Stream.of(
                Arguments.of("1,\n", IllegalArgumentException.class)
        );
    }
}