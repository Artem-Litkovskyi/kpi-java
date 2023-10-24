package org.example;

import java.util.Arrays;
import java.util.Objects;

public class MatrixImmutable extends AbstractMatrix {
    private final Double[][] elements;

    public MatrixImmutable() {
        elements = new Double[][] {};
    }

    public MatrixImmutable(Double[][] matrix) {
        validateRowLengths(matrix);
        elements = matrix;
    }

    public MatrixImmutable(Matrix matrix) {
        elements = matrix.getElements();
    }

    public MatrixImmutable(int rows, int columns) {
        elements = zeroMatrixArray(rows, columns);
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

    public Double[][] getElements() {
        int m = getRowsNumber();
        int n = getColumnsNumber();
        Double[][] elementsArray = new Double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                elementsArray[i][j] = getElement(i, j);
            }
        }

        return elementsArray;
    }

    public MatrixImmutable add(Matrix matrix) {
        return new MatrixImmutable(add(this, matrix));
    }

    public MatrixImmutable multiply(Double number) {
        return new MatrixImmutable(multiply(this, number));
    }

    public MatrixImmutable multiply(Matrix matrix) {
        return new MatrixImmutable(multiply(this, matrix));
    }

    public MatrixImmutable transpose() {
        return new MatrixImmutable(transpose(this));
    }

    public Double determinant() {
        return determinant(getElements());
    }

    public MatrixImmutable[] luDecomposition() {
        Double[][][] lu = luDecomposition(getElements());
        return new MatrixImmutable[] { new MatrixImmutable(lu[0]), new MatrixImmutable(lu[1]) };
    }

    public static MatrixImmutable zeroMatrix(int rows, int columns) {
        return new MatrixImmutable(zeroMatrixArray(rows, columns));
    }

    public static MatrixImmutable diagonalMatrix(Double[] diagonalElements) {
        return new MatrixImmutable(diagonalMatrixArray(diagonalElements));
    }

    public static MatrixImmutable identityMatrix(int size) {
        return new MatrixImmutable(identityMatrixArray(size));
    }

    public static MatrixImmutable rowMatrix(Double[] rowElements) {
        return new MatrixImmutable(rowMatrixArray(rowElements));
    }

    public static MatrixImmutable columnMatrix(Double[] columnElements) {
        return new MatrixImmutable(columnMatrixArray(columnElements));
    }

    public static MatrixImmutable randomMatrix(int rows, int columns) {
        return new MatrixImmutable(randomMatrixArray(rows, columns));
    }

    public boolean equals(Object o) {
        // If comparing to object to itself
        if (o == this) return true;

        // If other object is not matrix
        if (!(o instanceof MatrixImmutable matrix)) return false;

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
        return Objects.hash(getClass(), Arrays.deepHashCode(elements));
    }
}
