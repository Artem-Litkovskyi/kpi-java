package org.example;

public interface Matrix {
    Double getElement(int row, int column);
    Double[] getRowElements(int row);
    Double[] getColumnElements(int column);
    Double[][] getElements();

    int getRowsNumber();
    int getColumnsNumber();
    int[] getDimension();

    Matrix add(Matrix matrix);
    Matrix multiply(Double number);
    Matrix multiply(Matrix matrix);
    Matrix transpose();

    Double determinant();
    Matrix[] luDecomposition();
}
