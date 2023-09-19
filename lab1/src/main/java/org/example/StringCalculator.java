package org.example;

import java.util.ArrayList;
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
        StringBuilder neg_numbers = new StringBuilder();

        // Check for empty string
        if (value.isBlank()) { return result; }

        // Get custom delimiters
        ArrayList<String> custom_delimiters = new ArrayList<>() {};
        Pattern custom_short_del_pattern = Pattern.compile(String.format("\\A%s.%s", CUSTOM_DELS_BEGIN, CUSTOM_DELS_END));
        Matcher custom_short_del_matcher = custom_short_del_pattern.matcher(value);
        Pattern custom_del_pattern = Pattern.compile(String.format("\\A%s.*?%s", CUSTOM_DELS_BEGIN, CUSTOM_DELS_END));
        Matcher custom_del_matcher = custom_del_pattern.matcher(value);
        String custom_del_substring = "";
        String numbers_substring = "";

        if (custom_short_del_matcher.find()) {  // Single custom one character delimiter
            custom_del_substring = custom_short_del_matcher.group();
            custom_delimiters.add(getCustomShortDelimiter(custom_del_substring));
        } else if (custom_del_matcher.find()) {  // Other more complex cases
            custom_del_substring = custom_del_matcher.group();
            custom_delimiters.addAll(getCustomDelimiters(custom_del_substring));
        }

        numbers_substring = value.substring(custom_del_substring.length());

        // Split by delimiters
        String[] numbers = numbers_substring.split(buildDelimitersRegex(custom_delimiters), -1);
        if (numbers.length == 0) throw new IllegalArgumentException("found no numbers");

        // Add numbers
        int current_int;
        for (String item : numbers) {
            try {
                current_int = Integer.parseInt(item);

                if (current_int < 0) {
                    if (!neg_numbers.isEmpty()) neg_numbers.append(", ");
                    neg_numbers.append(item);
                }

                if (current_int > IGNORE_THRESHOLD) continue;

                result += current_int;
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

    private static String _trimCustomDelSubstring(String custom_del_substring) {
        return custom_del_substring.substring(
                CUSTOM_DELS_BEGIN.length(),
                custom_del_substring.length() - CUSTOM_DELS_END.length());
    }

    private static String getCustomShortDelimiter(String custom_del_substring) {
        return _trimCustomDelSubstring(custom_del_substring);
    }

    private static ArrayList<String> getCustomDelimiters(String custom_del_substring) {
        String trimmed_substring = _trimCustomDelSubstring(custom_del_substring);
        ArrayList<String> custom_delimiters = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        boolean del_began = false;

        for (int i = 0; i < trimmed_substring.length(); i++) {
            char current_char = trimmed_substring.charAt(i);
            if (!del_began) {
                if (current_char == CUSTOM_DEL_BEGIN) {
                    del_began = true;
                    continue;
                }
                throw new IllegalArgumentException(  // If "[" is missing
                        String.format("can't recognise custom delimiters (\"%c\" is missing)", CUSTOM_DEL_BEGIN));
            } else {
                if (current_char == CUSTOM_DEL_END) {
                    del_began = false;
                    if (buffer.isEmpty())
                        throw new IllegalArgumentException(  // If "[]" was found
                                String.format("custom delimiters can't be empty (\"%c%c\" was found)",
                                        CUSTOM_DEL_BEGIN, CUSTOM_DEL_END));
                    custom_delimiters.add(buffer.toString());
                    buffer.setLength(0);
                    continue;
                }
                buffer.append(current_char);
            }
        }

        if (del_began)  // If "]" is missing
            throw new IllegalArgumentException(
                String.format("can't recognise custom delimiters (\"%c\" is missing)", CUSTOM_DEL_END));

        return custom_delimiters;
    }

    private static String buildDelimitersRegex(ArrayList<String> custom_delimiters) {
        StringBuilder regex = new StringBuilder();
        regex.append(String.format("[%s]", DEFAULT_DELIMITERS));

        if (custom_delimiters.isEmpty()) return regex.toString();

        for (var delimiter : custom_delimiters) {
            char meta_char;

            // Replace meta characters with escape sequences
            for (int i = 0; i < REGEX_METACHARACTERS.length(); i++) {
                meta_char = REGEX_METACHARACTERS.charAt(i);
                delimiter = delimiter.replace(String.format("%c", meta_char), String.format("\\%c", meta_char));
            }

            regex.append("|").append(delimiter);
        }

        return regex.toString();
    }
}
