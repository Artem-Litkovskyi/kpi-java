package org.example;

import java.util.Scanner;

public class Main {
    private static final char SEP_SYMBOL = '=';
    private static final char SEP_SYMBOL_N = 64;
    private static final String CMD_QUIT = "q";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Print the title screen
        System.out.printf("\n\n\n%s\n", getSeparator("Lab1: TDD Kata"));
        System.out.println("Enter numbers separated by delimiters (\",\" or \"\\n\").");
        System.out.println("To set custom delimiters enter them before numbers in one of these ways:");
        System.out.println("\ta) \"//*\\n[numbers]\", where \"*\" is a custom one character delimiter");
        System.out.printf("You can also enter \"%s\" if you want to quit.\n", CMD_QUIT);

        while (true) {
            // Get input
            System.out.print("\nEnter a string: ");
            String input = scanner.nextLine();
            input = input.replace("\\n", "\n");  // Replace each "\n" by a real \n

            // Detect a CMD_QUIT
            if (input.equalsIgnoreCase(CMD_QUIT)) { break; }

            // Print result
            try {
                int result = StringCalculator.add(input);
                System.out.printf("Result: %d\n", result);
            } catch (IllegalArgumentException e) {
                System.out.printf("Something went wrong: %s\n", e.getMessage());
            }
        }

        // Print the "end" string
        System.out.printf("%s\n\n", getSeparator("end"));
    }

    private static String getSeparator(String message) {
        String messageStripped = message.strip();
        int symbolsNumber = (SEP_SYMBOL_N - messageStripped.length() - 2) / 2;
        int symbolsAdditional = (SEP_SYMBOL_N - messageStripped.length() - 2) % 2;

        return String.valueOf(SEP_SYMBOL).repeat(symbolsNumber + symbolsAdditional) +
                String.format(" %s ", messageStripped) +
                String.valueOf(SEP_SYMBOL).repeat(symbolsNumber);
    }
}