package org.example;

import java.util.Scanner;

public class Main {
    private static final char SEP_SYMBOL = '=';
    private static final char SEP_SYMBOL_N = 64;
    private static final String CMD_QUIT = "q";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Print the title screen
        System.out.printf("\n\n\n%s\n", getSeparator("Lab1: TDD Kata", SEP_SYMBOL, SEP_SYMBOL_N));
        System.out.println("Enter numbers separated by comma or a newline character.");
        System.out.println("When using newline characters as delimiters leave a new line empty to mark the end of input.");
        System.out.printf("You can also enter \"%s\" if you want to quit.\n", CMD_QUIT);

        while (true) {
            boolean isFirstLine = true;
            boolean isCmdQuitEntered = false;
            StringBuilder input = new StringBuilder();

            System.out.print("\nEnter a string: ");

            // Get input line by line
            while (true) {
                String line = scanner.nextLine();

                if (line.isEmpty()) break;  // Empty line marks an end of input

                if (!isFirstLine) input.append("\n");  // Add a "\n" between lines
                input.append(line);

                if (!isFirstLine) continue;
                isFirstLine = false;
                if (!line.equalsIgnoreCase(CMD_QUIT)) continue;  // Detect a CMD_QUIT string
                isCmdQuitEntered = true;
                break;
            }

            if (isCmdQuitEntered) { break; }  // Stop if a CMD_QUIT string was entered

            // Print result
            try {
                int result = StringCalculator.add(input.toString());
                System.out.printf("Result: %d\n", result);
            } catch (IllegalArgumentException e) {
                System.out.printf("Something went wrong: %s\n", e.getMessage());
            }
        }

        // Print the "end" string
        System.out.printf("%s\n\n", getSeparator("end", SEP_SYMBOL, SEP_SYMBOL_N));
    }

    private static String getSeparator(String message, char symbol, int separatorLength) {
        String messageStripped = message.strip();
        int symbolsNumber = (separatorLength - messageStripped.length() - 2) / 2;
        int symbolsAdditional = (separatorLength - messageStripped.length() - 2) % 2;

        return String.valueOf(symbol).repeat(symbolsNumber + symbolsAdditional) +
                String.format(" %s ", messageStripped) +
                String.valueOf(symbol).repeat(symbolsNumber);
    }
}