package org.example;

import java.util.Arrays;
import java.util.Objects;

public class MatrixImmutable implements Matrix {
    private final Double[][] elements;

    public MatrixImmutable(Double[][] matrix) {
        elements = matrix;
    }

    public MatrixImmutable(Matrix matrix) {
        elements = new Double[matrix.getRowsNumber()][matrix.getColumnsNumber()];

        for (int i = 0; i < matrix.getRowsNumber(); i++) {
            for (int j = 0; j < matrix.getColumnsNumber(); j++) {
                elements[i][j] = matrix.getElement(i, j);
            }
        }
    }

    public int getRowsNumber() {
        return elements.length;
    }

    public int getColumnsNumber() {
        return elements.length == 0 ? 0 : elements[0].length;
    }

    public int[] getDimension() {
        return new int[] { getRowsNumber(), getColumnsNumber() };
    }

    public Double getElement(int row, int column) {
        return elements[row][column];
    }

    public Double[] getRowElements(int row) {
        return elements[row];
    }

    public Double[] getColumnElements(int column) {
        Double[] columnElements = new Double[getRowsNumber()];
        for (int i = 0; i < columnElements.length; i++) {
            columnElements[i] = getElement(i, column);
        }
        return columnElements;
    }

    public MatrixImmutable add(Matrix matrix) {
        Double[][] result = new Double[getRowsNumber()][getColumnsNumber()];
        for (int i = 0; i < getRowsNumber(); i++) {
            for (int j = 0; j < getColumnsNumber(); j++) {
                result[i][j] = getElement(i, j) + matrix.getElement(i, j);
            }
        }
        return new MatrixImmutable(result);
    }

    public MatrixImmutable multiply(Double number) {
        Double[][] result = new Double[getRowsNumber()][getColumnsNumber()];
        for (int i = 0; i < getRowsNumber(); i++) {
            for (int j = 0; j < getColumnsNumber(); j++) {
                result[i][j] = getElement(i, j) * number;
            }
        }
        return new MatrixImmutable(result);
    }

    public MatrixImmutable multiply(Matrix matrix) {
        int m = getRowsNumber();
        int n = getColumnsNumber();
        int p = matrix.getColumnsNumber();
        Double[][] result = new Double[m][p];

        for (var row : result) {
            Arrays.fill(row, 0d);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] = result[i][j] + getElement(i, k) * matrix.getElement(k, j);
                }
            }
        }
        return new MatrixImmutable(result);
    }

    public MatrixImmutable transpose() {
        int m = getRowsNumber();
        int n = getColumnsNumber();
        Double[][] result = new Double[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[j][i] = getElement(i, j);
            }
        }
        return new MatrixImmutable(result);
    }

    public static MatrixImmutable diagonalMatrix(Double[] diagonalElements) {
        Double[][] result = new Double[diagonalElements.length][diagonalElements.length];

        for (var row : result) {
            Arrays.fill(row, 0d);
        }

        for (int i = 0; i < diagonalElements.length; i++) {
            result[i][i] = diagonalElements[i];
        }
        return new MatrixImmutable(result);
    }

    public static MatrixImmutable identityMatrix(int size) {
        Double[][] result = new Double[size][size];

        for (var row : result) {
            Arrays.fill(row, 0d);
        }

        for (int i = 0; i < size; i++) {
            result[i][i] = 1d;
        }
        return new MatrixImmutable(result);
    }

    public static MatrixImmutable rowMatrix(Double[] rowElements) {
        return new MatrixImmutable(new Double[][] { rowElements });
    }

    public static MatrixImmutable columnMatrix(Double[] columnElements) {
        Double[][] result = new Double[columnElements.length][1];
        for (int i = 0; i < columnElements.length; i++) {
            result[i][0] = columnElements[i];
        }
        return new MatrixImmutable(result);
    }

    public boolean equals(Object o) {
        // If comparing to object to itself
        if (o == this) return true;

        // If other object is not matrix
        if (!(o instanceof Matrix matrix)) return false;

        int rowsNumber = getRowsNumber();
        int columnsNumber = getColumnsNumber();

        // If other matrix has different dimension
        if (rowsNumber != matrix.getRowsNumber()) return false;
        if (columnsNumber != matrix.getColumnsNumber()) return false;

        // If other matrix has different elements
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                if (!Objects.equals(getElement(i, j), matrix.getElement(i, j))) return false;
            }
        }

        return true;
    }

    public int hashCode() {
        return Arrays.deepHashCode(elements);
    }
}
