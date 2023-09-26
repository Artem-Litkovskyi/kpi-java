package org.example;

import java.util.regex.*;

public class StringCalculator {
    private static final String CUSTOM_DEL_START = "//";
    private static final String CUSTOM_DEL_END = "\n";
    private static final String DEFAULT_DELIMITERS = ",\n";

    public static int add(String value) {
        int result = 0;
        int number;

        StringBuilder neg_numbers = new StringBuilder();
        StringBuilder delimiters = new StringBuilder();

        Pattern custom_short_del_pattern = Pattern.compile(String.format("\\A%s.%s", CUSTOM_DEL_START, CUSTOM_DEL_END));
        Matcher custom_short_del_matcher = custom_short_del_pattern.matcher(value);

        // Check for empty string
        if (value.isBlank()) { return result; }

        // Get delimiters
        delimiters.append(DEFAULT_DELIMITERS);
        String custom_del_substring = "";
        if (custom_short_del_matcher.find()) {
            custom_del_substring = custom_short_del_matcher.group();
            delimiters.append(
                    custom_del_substring,
                    CUSTOM_DEL_START.length(),
                    custom_del_substring.length() - CUSTOM_DEL_END.length()
            );
        }

        // Split by delimiters
        String[] array = value.substring(custom_del_substring.length()).split(
                String.format("[%s]", delimiters), -1);
        if (array.length == 0) throw new IllegalArgumentException("found no numbers");

        // Add numbers
        for (String item : array) {
            try {
                number = Integer.parseInt(item);

                if (number < 0) {
                    if (!neg_numbers.isEmpty()) neg_numbers.append(", ");
                    neg_numbers.append(item);
                }

                result += number;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        String.format("\"%s\" is not an integer", item.replace("\n", "\\n")));
            }
        }

        if (!neg_numbers.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("negative numbers were found (%s)", neg_numbers));
        }

        return result;
    }
}
