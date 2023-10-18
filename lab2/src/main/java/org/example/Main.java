package org.example;

public class Main {
    private static final char SEP_SYMBOL = '=';
    private static final char SEP_LENGTH = 64;

    public static void main(String[] args) {
        System.out.printf("\n\n\n%s\n\n", getSeparator("Lab2: Моделювання математичних об'єктів"));

        Double[][] m1Data = new Double[][] {
                {0d, 0d, 0d},
                {0d, 0d, 0d},
                {0d, 0d, 0d},
        };

        Double[][] m2Data = new Double[][] {
                {0d, 0d, 0d},
                {0d, 0d, 0d},
                {0d, 0d, 0d},
        };

        MatrixMutable m1 = new MatrixMutable(m1Data);
        MatrixMutable m2 = new MatrixMutable(m2Data);
        MatrixMutable result = m1.add(m2);

        print(result);

        System.out.printf("\n%s\n\n\n", getSeparator());
    }

    static void print(Matrix matrix) {
        for (int i = 0; i < matrix.getRowsNumber(); i++) {
            System.out.print("[");
            for (int j = 0; j < matrix.getColumnsNumber(); j++) {
                System.out.printf("%12.2f", matrix.getElement(i, j));
            }
            System.out.print("]\n");
        }
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