package org.example;

public class Main {
    private static final char SEP_SYMBOL = '=';
    private static final char SEP_LENGTH = 64;

    public static void main(String[] args) {
        System.out.printf("\n\n\n%s\n\n", getSeparator("Lab2: Моделювання математичних об'єктів"));

        Double[][] m1Data = new Double[][] {
                {1d, 2d, 3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d},
        };

        Double[][] m2Data = new Double[][] {
                {-1d, 2d, -3d},
                {4d, -5d, 6d},
                {-7d, 8d, -9d},
        };

        // Matrix creation
//        MatrixMutable m1 = new MatrixMutable();
        MatrixImmutable m1 = MatrixImmutable.randomMatrix(3, 3);
        MatrixMutable m2 = new MatrixMutable(m1);
//        MatrixMutable m1 = new MatrixMutable(3, 4);
//        MatrixMutable m1 = new MatrixMutable(m1Data);
//        MatrixMutable m2 = new MatrixMutable(m1);
//        MatrixMutable m2 = new MatrixMutable(m2Data);

        System.out.println("m1:");
        print(m1);
//        System.out.println("m2:");
//        print(m2);
        System.out.println();

        // Setters
//        setters(m1, m2Data);

        // Getters
//        getters(m1);

        // Dimension
//        dimension(m1);

        // Operations
//        operations(m1, m2);

        // Default matrices
//        defaultMatrices();

        // Upper and lower triangular
//        luDecomposition(m1);
        Matrix[] lu = m1.luDecomposition();
        MatrixImmutable ml = new MatrixImmutable(lu[0]);
        MatrixImmutable mu = new MatrixImmutable(lu[1]);

        System.out.println("Lower triangular:");
        print(lu[0]);
        System.out.println("Upper triangular:");
        print(lu[1]);

        System.out.println();
        print(ml.multiply(mu));


        System.out.printf("\n%s\n\n\n", getSeparator());
    }

    public static void print(Double[] array) {
        System.out.print("[");

        for (Double value : array)
            System.out.printf("%12.2f", value);

        System.out.print("]\n");
    }

    public static void print(Matrix matrix) {
        if (matrix.getRowsNumber() == 0)
            System.out.println("No elements");
        for (int i = 0; i < matrix.getRowsNumber(); i++) {
            print(matrix.getRowElements(i));
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

    private static void setters(MatrixMutable a, Double[][] newData) {
        System.out.println("Set element:");
        a.setElement(0, 1, -100d);
        print(a);
        System.out.println("Set row:");
        a.setRowElements(1, new Double[] {-100d, -200d, -300d});
        print(a);
        System.out.println("Set column:");
        a.setColumnElements(1, new Double[] {-100d, -200d, -300d});
        print(a);
        System.out.println("Set all elements:");
        a.setElements(newData);
        print(a);
    }

    private static void getters(Matrix a) {
        System.out.printf("Get element: %.2f\n", a.getElement(0, 1));
        System.out.print("Get row: ");
        print(a.getRowElements(1));
        System.out.print("Get column: ");
        print(a.getColumnElements(1));
    }

    private static void dimension(MatrixMutable a) {
        System.out.printf("Rows: %d\n", a.getRowsNumber());
        System.out.printf("Columns: %d\n", a.getColumnsNumber());
        int[] dim = a.getDimension();
        System.out.printf("Dimension: %dx%d\n", dim[0], dim[1]);

        a.changeDimension(2, 5);
        System.out.println("\nChanged dimension:");
        print(a);
    }

    private static void operations(Matrix a, Matrix b) {
        System.out.println("Add:");
        print(a.add(b));
        System.out.println("Multiply by number:");
        print(a.multiply(2d));
        System.out.println("Multiply by matrix:");
        print(a.multiply(b));
        System.out.println("Transpose:");
        print(a.transpose());
    }

    private static void defaultMatrices() {
        System.out.println("Diagonal:");
        print(MatrixMutable.diagonalMatrix(new Double[] {-100d, -200d, -300d}));
        System.out.println("Identity:");
        print(MatrixMutable.identityMatrix(5));
        System.out.println("Row:");
        print(MatrixMutable.rowMatrix(new Double[] {-100d, -200d, -300d}));
        System.out.println("Column:");
        print(MatrixMutable.columnMatrix(new Double[] {-100d, -200d, -300d}));
    }

    private static void luDecomposition(Matrix a) {
        Matrix[] lu = a.luDecomposition();
        System.out.println("Lower triangular:");
        print(lu[0]);
        System.out.println("Upper triangular:");
        print(lu[1]);
    }
}