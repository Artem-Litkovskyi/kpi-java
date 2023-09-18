package org.example;

import java.util.Scanner;

public class Main {
    private static final String CMD_QUIT = "q";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String titleSeparator = getSeparator("Lab1: TDD Kata", '=', 10);
        System.out.printf("\n\n\n%s", titleSeparator);

        while (true) {
            // Get input
            System.out.printf("\nEnter numbers separated by comma (enter \"%s\" to quit): ", CMD_QUIT);
            String input = scanner.nextLine();

            // Stop if user entered CMD_QUIT
            if (input.equalsIgnoreCase(CMD_QUIT)) { break; }

            // Print result
            try {
                int result = StringCalculator.add(input);
                System.out.printf("Result: %d", result);
            } catch (IllegalArgumentException e) {
                System.out.printf("Something went wrong: %s", e.getMessage());
            }
        }

        System.out.printf("\n%s\n\n", getSeparatorOfLength("end", '=', titleSeparator.length()));
    }

    private static String getSeparator(String message, char symbol, int symbolsNumber) {
        String messageStripped = message.strip();
        return String.valueOf(symbol).repeat(symbolsNumber) +
                String.format(" %s ", messageStripped) +
                String.valueOf(symbol).repeat(symbolsNumber);
    }

    private static String getSeparatorOfLength(String message, char symbol, int separatorLength) {
        String messageStripped = message.strip();
        int symbolsNumber = (separatorLength - messageStripped.length() - 2) / 2;
        int symbolsAdditional = (separatorLength - messageStripped.length() - 2) % 2;

        return String.valueOf(symbol).repeat(symbolsNumber + symbolsAdditional) +
                String.format(" %s ", messageStripped) +
                String.valueOf(symbol).repeat(symbolsNumber);
    }
}