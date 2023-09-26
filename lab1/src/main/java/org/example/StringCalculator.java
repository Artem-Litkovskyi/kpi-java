package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.*;

public class StringCalculator {
    private static final String DEFAULT_DELIMITERS = ",\n";

    private static final String CUSTOM_DELS_BEGIN = "//";
    private static final String CUSTOM_DELS_END = "\n";
    private static final char CUSTOM_DEL_BEGIN = '[';
    private static final char CUSTOM_DEL_END = ']';

    private static final int IGNORE_THRESHOLD = 1000;
    
    private static final String REGEX_METACHARACTERS = "*+?^$.";
    

    public static int add(String value) {
        int result = 0;
        StringBuilder negativeNumbers = new StringBuilder();

        // Check for empty string
        if (value.isBlank()) { return result; }

        // Get custom delimiters
        ArrayList<String> customDelimiters = new ArrayList<>() {};
        Pattern customShortDelPattern = Pattern.compile(String.format("\\A%s.%s", CUSTOM_DELS_BEGIN, CUSTOM_DELS_END));
        Matcher customShortDelMatcher = customShortDelPattern.matcher(value);
        Pattern customDelPattern = Pattern.compile(String.format("\\A%s.*?%s", CUSTOM_DELS_BEGIN, CUSTOM_DELS_END));
        Matcher customDelMatcher = customDelPattern.matcher(value);
        String customDelSubstring = "";
        String numbersSubstring = "";

        if (customShortDelMatcher.find()) {  // Single custom one-character delimiter
            customDelSubstring = customShortDelMatcher.group();
            customDelimiters.add(trimCustomDelSubstring(customDelSubstring));
        } else if (customDelMatcher.find()) {  // Other more complex cases
            customDelSubstring = customDelMatcher.group();
            customDelimiters.addAll(getCustomDelimiters(trimCustomDelSubstring(customDelSubstring)));
        }

        // Split numbers by delimiters
        numbersSubstring = value.substring(customDelSubstring.length());
        String[] numbers = numbersSubstring.split(buildDelimitersRegex(customDelimiters), -1);
        if (numbers.length == 0) throw new IllegalArgumentException("found no numbers");

        // Add numbers
        int currentInt;
        for (String item : numbers) {
            try {
                currentInt = Integer.parseInt(item);

                if (currentInt < 0) {  // Remember negative numbers
                    if (!negativeNumbers.isEmpty()) negativeNumbers.append(", ");
                    negativeNumbers.append(item);
                }

                if (currentInt > IGNORE_THRESHOLD) continue;

                result += currentInt;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(  // If failed parsing
                        String.format("\"%s\" is not an integer", item.replace("\n", "\\n")));
            }
        }

        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException(  // If found negative numbers
                    String.format("negative numbers were found (%s)", negativeNumbers));
        }

        return result;
    }

    private static String trimCustomDelSubstring(String customDelSubstring) {
        return customDelSubstring.substring(
                CUSTOM_DELS_BEGIN.length(),
                customDelSubstring.length() - CUSTOM_DELS_END.length());
    }

    private static ArrayList<String> getCustomDelimiters(String trimmedCustomDelSubstring) {
        ArrayList<String> customDelimiters = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        boolean delBegan = false;

        for (int i = 0; i < trimmedCustomDelSubstring.length(); i++) {
            char currentChar = trimmedCustomDelSubstring.charAt(i);
            if (!delBegan) {
                if (currentChar == CUSTOM_DEL_BEGIN) {
                    delBegan = true;
                    continue;
                }
                throw new IllegalArgumentException(  // If "[" is missing
                        String.format("can't recognise custom delimiters (\"%c\" is missing)",
                                CUSTOM_DEL_BEGIN));
            } else {
                if (currentChar != CUSTOM_DEL_END) {
                    buffer.append(currentChar);
                    continue;
                } else if (!buffer.isEmpty()) {
                    delBegan = false;
                    customDelimiters.add(buffer.toString());
                    buffer.setLength(0);
                    continue;
                }
                throw new IllegalArgumentException(  // If "[]" was found
                        String.format("custom delimiters can't be empty (\"%c%c\" was found)",
                                CUSTOM_DEL_BEGIN, CUSTOM_DEL_END));
            }
        }

        if (delBegan)  // If "]" is missing
            throw new IllegalArgumentException(
                String.format("can't recognise custom delimiters (\"%c\" is missing)", CUSTOM_DEL_END));

        return customDelimiters;
    }

    private static String buildDelimitersRegex(ArrayList<String> customDelimiters) {
        StringBuilder regex = new StringBuilder();
        regex.append(String.format("[%s]", DEFAULT_DELIMITERS));

        if (customDelimiters.isEmpty()) return regex.toString();

        customDelimiters.sort(Comparator.comparingInt(String::length).reversed());

        for (var delimiter : customDelimiters) {
            char metaChar;

            // Replace meta characters with escape sequences
            for (int i = 0; i < REGEX_METACHARACTERS.length(); i++) {
                metaChar = REGEX_METACHARACTERS.charAt(i);
                delimiter = delimiter.replace(String.format("%c", metaChar), String.format("\\%c", metaChar));
            }

            regex.append("|").append(delimiter);
        }

        return regex.toString();
    }
}
