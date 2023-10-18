package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MatrixMutable implements Matrix {
    private final ArrayList<ArrayList<Double>> elements;

    public MatrixMutable() {
        elements = new ArrayList<>();
    }

    public MatrixMutable(int rows, int columns) {
        elements = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            elements.add(new ArrayList<>(Collections.nCopies(columns, 0d)));
        }
    }

    public MatrixMutable(Matrix matrix) {
        elements = new ArrayList<>();

        for (int i = 0; i < matrix.getRowsNumber(); i++) {
            elements.add(new ArrayList<>(List.of(matrix.getRowElements(i))));
        }
    }

    public MatrixMutable(Double[][] matrix) {
        elements = new ArrayList<>();

        for (Double[] row : matrix) {
            elements.add(new ArrayList<>(List.of(row)));
        }
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

    public MatrixMutable add(Matrix matrix) {
        MatrixMutable result = new MatrixMutable(this);
        for (int i = 0; i < getRowsNumber(); i++) {
            for (int j = 0; j < getColumnsNumber(); j++) {
                result.setElement(i, j, result.getElement(i, j) + matrix.getElement(i, j));
            }
        }
        return result;
    }

    public MatrixMutable multiply(Double number) {
        MatrixMutable result = new MatrixMutable(this);
        for (int i = 0; i < getRowsNumber(); i++) {
            for (int j = 0; j < getColumnsNumber(); j++) {
                result.setElement(i, j, result.getElement(i, j) * number);
            }
        }
        return result;
    }

    public MatrixMutable multiply(Matrix matrix) {
        int m = getRowsNumber();
        int n = getColumnsNumber();
        int p = matrix.getColumnsNumber();
        MatrixMutable result = new MatrixMutable(m, p);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < n; k++) {
                    result.setElement(i, j, result.getElement(i, j) + getElement(i, k) * matrix.getElement(k, j));
                }
            }
        }
        return result;
    }

    public MatrixMutable transpose() {
        int m = getRowsNumber();
        int n = getColumnsNumber();
        MatrixMutable result = new MatrixMutable(n, m);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result.setElement(j, i, getElement(i, j));
            }
        }
        return result;
    }

    public static MatrixMutable diagonalMatrix(Double[] diagonalElements) {
        MatrixMutable result = new MatrixMutable(diagonalElements.length, diagonalElements.length);
        for (int i = 0; i < diagonalElements.length; i++) {
            result.setElement(i, i, diagonalElements[i]);
        }
        return result;
    }

    public static MatrixMutable identityMatrix(int size) {
        MatrixMutable result = new MatrixMutable(size, size);
        for (int i = 0; i < size; i++) {
            result.setElement(i, i, 1d);
        }
        return result;
    }

    public static MatrixMutable rowMatrix(Double[] rowElements) {
        MatrixMutable result = new MatrixMutable(1, rowElements.length);
        result.setRowElements(0, rowElements);
        return result;
    }

    public static MatrixMutable columnMatrix(Double[] columnElements) {
        MatrixMutable result = new MatrixMutable(columnElements.length, 1);
        result.setColumnElements(0, columnElements);
        return result;
    }

    public void setElement(int row, int column, Double value) {
        elements.get(row).set(column, value);
    }

    public void setRowElements(int row, Double[] values) {
        for (int j = 0; j < getColumnsNumber(); j++) {
            elements.get(row).set(j, values[j]);
        }
    }

    public void setColumnElements(int column, Double[] values) {
        for (int i = 0; i < getRowsNumber(); i++) {
            elements.get(i).set(column, values[i]);
        }
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
        return elements.hashCode();
    }

    public void changeDimension(int rows, int columns) {
        // Change rows number
        if (rows < getRowsNumber()) {
            elements.subList(rows, getRowsNumber()).clear();
        } else {
            for (int i = 0; i < rows - getRowsNumber(); i++) {
                elements.add(new ArrayList<>(Collections.nCopies(columns, 0d)));
            }
        }

        // Change columns number
        if (columns < getColumnsNumber()) {
            for (int i = 0; i < getRowsNumber(); i++) {
                elements.get(i).subList(columns, getColumnsNumber()).clear();
            }
        } else {
            for (int i = 0; i < getRowsNumber(); i++) {
                elements.get(i).addAll(Collections.nCopies(columns - getColumnsNumber(), 0d));
            }
        }
    }
}
