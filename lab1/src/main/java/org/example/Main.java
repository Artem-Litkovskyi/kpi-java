package org.example;

import java.util.Scanner;

public class Main {
    private static final char SEP_SYMBOL = '=';
    private static final char SEP_LENGTH = 64;
    private static final String CMD_QUIT = "q";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Print the title screen
        System.out.printf("\n\n\n%s\n\n", getSeparator("Lab1: TDD Kata"));
        System.out.println("Enter numbers separated by delimiters (\",\" or \"\\n\" by default)");
        System.out.println("\nTo add custom delimiters, enter them before numbers in a one of these ways:");
        System.out.println("\ta) One character: \"//*\\n[numbers]\" (\"*\" is a delimiter)");
        System.out.println("\tb) Long: \"//[***]\\n[numbers]\" (\"***\" is a delimiter)");
        System.out.println("\n[!] Note that:");
        System.out.println("\t1. Negative numbers are not supported;");
        System.out.println("\t2. Numbers greater than 1000 will be ignored.");
        System.out.printf("\nYou can also enter \"%s\" if you want to quit.\n\n", CMD_QUIT);
//        System.out.printf("\n%s\n\n", getSeparator());

        while (true) {
            // Get input
            System.out.print("Enter a string: ");
            String input = scanner.nextLine();
            input = input.replace("\\n", "\n");  // Replace each "\n" by a real \n

            // Detect a CMD_QUIT
            if (input.equalsIgnoreCase(CMD_QUIT)) { break; }

            // Print result
            try {
                int result = StringCalculator.add(input);
                System.out.printf("Result: %d\n\n", result);
            } catch (IllegalArgumentException e) {
                System.out.printf("Something went wrong: %s\n\n", e.getMessage());
            }
        }

        // Print the "end" string
        System.out.printf("\n%s\n\n", getSeparator("end"));
    }

    private static String getSeparator() {
        return String.valueOf(SEP_SYMBOL).repeat(SEP_LENGTH);
    }

    private static String getSeparator(String message) {
        if (message.isEmpty()) return getSeparator();

        String messageStripped = message.strip();
        int symbolsNumber = (SEP_LENGTH - messageStripped.length() - 2) / 2;
        int symbolsAdditional = (SEP_LENGTH - messageStripped.length() - 2) % 2;

        return String.valueOf(SEP_SYMBOL).repeat(symbolsNumber + symbolsAdditional) +
                String.format(" %s ", messageStripped) +
                String.valueOf(SEP_SYMBOL).repeat(symbolsNumber);
    }
}