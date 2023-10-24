package org.example;

import java.util.Arrays;

public abstract class AbstractMatrix implements Matrix {
    protected static Double[][] add(Matrix a, Matrix b) {
        int m = a.getRowsNumber();
        int n = a.getColumnsNumber();

        validateDimension(b, m, n);

        Double[][] result = a.getElements();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] += b.getElement(i, j);
            }
        }

        return result;
    }

    protected static Double[][] multiply(Matrix a, Double k) {
        int m = a.getRowsNumber();
        int n = a.getColumnsNumber();

        Double[][] result = a.getElements();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] *= k;
            }
        }

        return result;
    }

    protected static Double[][] multiply(Matrix a, Matrix b) {
        int m = a.getRowsNumber();
        int n = a.getColumnsNumber();
        int p = b.getColumnsNumber();

        validateRowsNumber(b, n);

        Double[][] result = zeroMatrixArray(m, p);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += a.getElement(i, k) * b.getElement(k, j);
                }
            }
        }

        return result;
    }

    protected static Double[][] transpose(Matrix a) {
        int m = a.getRowsNumber();
        int n = a.getColumnsNumber();

        Double[][] result = new Double[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[j][i] = a.getElement(i, j);
            }
        }

        return result;
    }

    protected static Double[][] zeroMatrixArray(int rows, int columns) {
        Double[][] result = new Double[rows][columns];

        for (var row : result) {
            Arrays.fill(row, 0d);
        }

        return result;
    }

    protected static Double[][] diagonalMatrixArray(Double[] diagonalElements) {
        int n = diagonalElements.length;
        Double[][] result = zeroMatrixArray(n, n);

        for (int i = 0; i < n; i++) {
            result[i][i] = diagonalElements[i];
        }

        return result;
    }

    protected static Double[][] identityMatrixArray(int size) {
        Double[][] result = zeroMatrixArray(size, size);

        for (int i = 0; i < size; i++) {
            result[i][i] = 1d;
        }

        return result;
    }

    protected static Double[][] rowMatrixArray(Double[] rowElements) {
        return new Double[][] { rowElements };
    }

    protected static Double[][] columnMatrixArray(Double[] columnElements) {
        Double[][] result = new Double[columnElements.length][1];

        for (int i = 0; i < columnElements.length; i++) {
            result[i][0] = columnElements[i];
        }

        return result;
    }

    protected static Double[][] randomMatrixArray(int rows, int columns) {
        Double[][] result = new Double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = Math.random();
            }
        }

        return result;
    }

    protected static Double determinant(Double[][] matrix) {
        int n = matrix.length;

        validateDimensionSquare(matrix);

        switch (n) {
            case 1:
                return matrix[0][0];
            case 2:
                return determinant2x2(
                        matrix[0][0], matrix[0][1],
                        matrix[1][0], matrix[1][1]);
            case 3:
                return determinant3x3(
                        matrix[0][0], matrix[0][1], matrix[0][2],
                        matrix[1][0], matrix[1][1], matrix[1][2],
                        matrix[2][0], matrix[2][1], matrix[2][2]);
        }

        double det = 0d;
        double tmp;
        boolean sign = false;

        for (int j = 0; j < n; j++) {
            tmp = matrix[0][j] * determinant(getMinor(matrix, 0, j));
            if (sign)
                det += tmp;
            else
                det -= tmp;
            sign = !sign;
        }

        return det;
    }

    protected static Double[][][] luDecomposition(Double[][] matrix) {
        int size = matrix.length;

        validateDimensionSquare(matrix);

        Double[][] l = identityMatrixArray(size);
        Double[][] u = zeroMatrixArray(size, size);

        for (int i = 0; i < size; i++) {
            // Calculate U
            for (int k = i; k < size; k++) {
                Double sum = 0d;

                for (int j = 0; j < i; j++) {
                    sum += l[i][j] * u[j][k];
                }

                u[i][k] = matrix[i][k] - sum;
            }

            // Calculate L
            for (int k = i; k < size; k++) {
                if (i == k) continue;

                Double sum = 0d;

                for (int j = 0; j < i; j++) {
                    sum += l[k][j] * u[j][i];
                }

                l[k][i] = (matrix[k][i] - sum) / u[i][i];
            }
        }

        return new Double[][][] { l, u };
    }

    protected static void validateArrayLength(Double[] array, int length) {
        int arrayLength = array.length;

        if (arrayLength == length) return;

        throw new UnacceptableArrayLengthException(
                "Array length is %d (must be %d)".formatted(arrayLength, length));
    }

    protected static void validateRowLengths(Double[][] matrix) {
        if (matrix.length == 0) return;

        int rowLength = matrix[0].length;

        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length == rowLength) continue;
            throw new UnevenRowLengthsException(
                    "Row %d is not the same length as all previous ones".formatted(i));
        }
    }

    protected static void validateRowsNumber(Matrix matrix, int rowsNumber) {
        int actualRowsNumber = matrix.getRowsNumber();

        if (actualRowsNumber == rowsNumber) return;

        throw new UnacceptableDimensionException(
                "Rows number is %d (must be %d)".formatted(actualRowsNumber, rowsNumber));
    }

    protected static void validateColumnsNumber(Matrix matrix, int columnsNumber) {
        int actualColumnsNumber = matrix.getColumnsNumber();

        if (actualColumnsNumber == columnsNumber) return;

        throw new UnacceptableDimensionException(
                "Columns number is %d (must be %d)".formatted(actualColumnsNumber, columnsNumber));
    }

    protected static void validateDimension(Matrix matrix, int rowsNumber, int columnsNumber) {
        int actualRowsNumber = matrix.getRowsNumber();
        int actualColumnsNumber = matrix.getColumnsNumber();

        if (actualRowsNumber == rowsNumber && actualColumnsNumber == columnsNumber) return;

        throw new UnacceptableDimensionException(
                "Dimension is %dx%d (must be %dx%d)".formatted(
                        actualRowsNumber, actualColumnsNumber,
                        rowsNumber, columnsNumber));
    }

    protected static void validateDimension(Double[][] matrix, int rowsNumber, int columnsNumber) {
        validateRowLengths(matrix);

        int actualRowsNumber = matrix.length;
        int actualColumnsNumber = actualRowsNumber == 0 ? 0 : matrix[0].length;

        if (actualRowsNumber == rowsNumber && actualColumnsNumber == columnsNumber) return;

        throw new UnacceptableDimensionException(
                "Dimension is %dx%d (must be %dx%d)".formatted(
                        actualRowsNumber, actualColumnsNumber,
                        rowsNumber, columnsNumber));
    }

    protected static void validateDimensionSquare(Double[][] matrix) {
        validateRowLengths(matrix);

        int actualRowsNumber = matrix.length;
        int actualColumnsNumber = actualRowsNumber == 0 ? 0 : matrix[0].length;

        if (actualRowsNumber == actualColumnsNumber) return;

        throw new UnacceptableDimensionException(
                "Dimension is %dx%d (matrix must be square)".formatted(
                        actualRowsNumber, actualColumnsNumber));
    }

    private static Double determinant2x2(
            Double a00, Double a01,
            Double a10, Double a11) {
        return a00 * a11 - a01 * a10;
    }

    private static Double determinant3x3(
            Double a00, Double a01, Double a02,
            Double a10, Double a11, Double a12,
            Double a20, Double a21, Double a22) {
        Double a = a00 * a11 * a22 + a01 * a12 * a20 + a02 * a10 * a21;
        Double b = a02 * a11 * a20 + a01 * a10 * a22 + a00 * a12 * a21;
        return a - b;
    }

    private static Double[][] getMinor(Double[][] matrix, int row, int column) {
        int n = matrix.length;
        int m = matrix[0].length;

        Double[][] minor = new Double[n - 1][m - 1];

        int iShift;
        int jShift;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == row || j == column) continue;
                iShift = i > row ? -1 : 0;
                jShift = j > column ? -1 : 0;
                minor[i + iShift][j + jShift] = matrix[i][j];
            }
        }

        return minor;
    }
}
