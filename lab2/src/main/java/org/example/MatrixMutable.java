package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MatrixMutable extends AbstractMatrix {
    private final ArrayList<ArrayList<Double>> elements;

    public MatrixMutable() {
        elements = new ArrayList<>();
    }

    public MatrixMutable(Double[][] matrix) {
        validateRowLengths(matrix);
        elements = new ArrayList<>();
        changeDimension(matrix.length, matrix[0].length);
        setElements(matrix);
    }

    public MatrixMutable(Matrix matrix) {
        elements = new ArrayList<>();
        changeDimension(matrix.getRowsNumber(), matrix.getColumnsNumber());
        setElements(matrix.getElements());
    }

    public MatrixMutable(int rows, int columns) {
        elements = new ArrayList<>();
        changeDimension(rows, columns);
        setElements(zeroMatrixArray(rows, columns));
    }

    public int getRowsNumber() {
        return elements.size();
    }

    public int getColumnsNumber() {
        return elements.isEmpty() ? 0 : elements.get(0).size();
    }

    public int[] getDimension() {
        return new int[] { getRowsNumber(), getColumnsNumber() };
    }

    public Double getElement(int row, int column) {
        return elements.get(row).get(column);
    }

    public Double[] getRowElements(int row) {
        Double[] rowElements = new Double[getColumnsNumber()];

        for (int j = 0; j < rowElements.length; j++) {
            rowElements[j] = getElement(row, j);
        }

        return rowElements;
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

    public MatrixMutable add(Matrix matrix) {
        return new MatrixMutable(add(this, matrix));
    }

    public MatrixMutable multiply(Double number) {
        return new MatrixMutable(multiply(this, number));
    }

    public MatrixMutable multiply(Matrix matrix) {
        return new MatrixMutable(multiply(this, matrix));
    }

    public MatrixMutable transpose() {
        return new MatrixMutable(transpose(this));
    }

    public static MatrixMutable zeroMatrix(int rows, int columns) {
        return new MatrixMutable(zeroMatrixArray(rows, columns));
    }

    public static MatrixMutable diagonalMatrix(Double[] diagonalElements) {
        return new MatrixMutable(diagonalMatrixArray(diagonalElements));
    }

    public static MatrixMutable identityMatrix(int size) {
        return new MatrixMutable(identityMatrixArray(size));
    }

    public static MatrixMutable rowMatrix(Double[] rowElements) {
        return new MatrixMutable(rowMatrixArray(rowElements));
    }

    public static MatrixMutable columnMatrix(Double[] columnElements) {
        return new MatrixMutable(columnMatrixArray(columnElements));
    }

    public static MatrixMutable randomMatrix(int rows, int columns) {
        return new MatrixMutable(randomMatrixArray(rows, columns));
    }

    public Double determinant() {
        return determinant(getElements());
    }

    public MatrixMutable[] luDecomposition() {
        Double[][][] lu = luDecomposition(getElements());
        return new MatrixMutable[] { new MatrixMutable(lu[0]), new MatrixMutable(lu[1]) };
    }

    public boolean equals(Object o) {
        // If comparing to object to itself
        if (o == this) return true;

        // If other object is not matrix
        if (!(o instanceof MatrixMutable matrix)) return false;

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
        return Objects.hash(getClass(), elements);
    }

    public void setElement(int row, int column, Double value) {
        elements.get(row).set(column, value);
    }

    public void setRowElements(int row, Double[] values) {
        int n = getColumnsNumber();

        validateArrayLength(values, n);

        for (int j = 0; j < n; j++) {
            setElement(row, j, values[j]);
        }
    }

    public void setColumnElements(int column, Double[] values) {
        int m = getRowsNumber();

        validateArrayLength(values, m);

        for (int i = 0; i < m; i++) {
            setElement(i, column, values[i]);
        }
    }

    public void setElements(Double[][] matrix) {
        int m = getRowsNumber();
        int n = getColumnsNumber();

        validateDimension(matrix, m, n);

        for (int i = 0; i < m; i++) {
            setRowElements(i, matrix[i]);
        }
    }

    public void changeDimension(int rows, int columns) {
        // Change rows number
        if (getRowsNumber() > rows) {
            elements.subList(rows, getRowsNumber()).clear();
        } else {
            while (getRowsNumber() != rows) {
                elements.add(new ArrayList<>(Collections.nCopies(columns, 0d)));
            }
        }

        // Change columns number
        if (getColumnsNumber() > columns) {
            for (int i = 0; i < getRowsNumber(); i++) {
                elements.get(i).subList(columns, getColumnsNumber()).clear();
            }
        } else {
            for (int i = 0; i < getRowsNumber(); i++) {
                elements.get(i).addAll(Collections.nCopies(columns - elements.get(i).size(), 0d));
            }
        }
    }
}
