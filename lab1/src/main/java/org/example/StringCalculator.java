package org.example;

public class StringCalculator {
    public static int add(String value) {
        int result = 0;

        // Check for empty string
        if (value.isBlank()) { return result; }

        // Split by a delimiter
        String[] array = value.split("[,\n]", -1);
        if (array.length == 0) { throw new IllegalArgumentException("found no numbers"); }

        // Add numbers
        for (String item : array) {
            try {
                result += Integer.parseInt(item);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        String.format(
                                "\"%s\" is not an integer",
                                item.replace("\n", "\\n")
                        )
                );
            }
        }

        return result;
    }
}
