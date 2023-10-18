package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixImmutableTest {
    @Test
    public void createMatrixByCopying() {
        MatrixImmutable m1 = instantiateRandomMatrix(3, 4);
        MatrixImmutable m2 = new MatrixImmutable(m1);
        assertEquals(m1, m2);
    }

    @Test
    public void createMatrixFromArray() {
        Double[][] m_data = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        MatrixImmutable m = new MatrixImmutable(m_data);
        for (int i = 0; i < m_data.length; i++) {
            for (int j = 0; j < m_data[0].length; j++) {
                assertEquals(m.getElement(i, j), m_data[i][j]);
            }
        }
    }

    @Test
    public void getMatrixDimension() {
        int a = 3;
        int b = 4;
        MatrixImmutable m = instantiateRandomMatrix(a, b);
        int[] dim = m.getDimension();
        assertEquals(2, dim.length);
        assertEquals(a, dim[0]);
        assertEquals(b, dim[1]);
    }

    @Test
    public void addMatrices() {
        Double[][] m1Data = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] m2Data = {{0d, 3d, 0d}, {2d, 0d, 1d}};
        Double[][] rData = {{1d, 5d, 3d}, {6d, 5d, 7d}};
        MatrixImmutable m1 = new MatrixImmutable(m1Data);
        MatrixImmutable m2 = new MatrixImmutable(m2Data);
        MatrixImmutable r = new MatrixImmutable(rData);
        assertEquals(r, m1.add(m2));
    }

    @Test
    public void multiplyMatrixByNumber() {
        Double[][] mData = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] rData = {{2d, 4d, 6d}, {8d, 10d, 12d}};
        MatrixImmutable m = new MatrixImmutable(mData);
        MatrixImmutable r = new MatrixImmutable(rData);
        assertEquals(r, m.multiply(2d));
    }

    @Test
    public void multiplyMatrices() {
        Double[][] m1Data = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] m2Data = {{1d, 2d, 3d}, {4d, 5d, 6d}, {7d, 8d, 9d}};
        Double[][] rData = {{30d, 36d, 42d}, {66d, 81d, 96d}};
        MatrixImmutable m1 = new MatrixImmutable(m1Data);
        MatrixImmutable m2 = new MatrixImmutable(m2Data);
        MatrixImmutable r = new MatrixImmutable(rData);
        assertEquals(r, m1.multiply(m2));
    }

    @Test
    public void transposeMatrix() {
        Double[][] mData = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] rData = {{1d, 4d}, {2d, 5d}, {3d, 6d}};
        MatrixImmutable m1 = new MatrixImmutable(mData);
        MatrixImmutable r = new MatrixImmutable(rData);
        assertEquals(r, m1.transpose());
    }

    @Test
    public void diagonalMatrix() {
        Double[] dData = {1d, 2d, 3d};
        Double[][] rData = {{1d, 0d, 0d}, {0d, 2d, 0d}, {0d, 0d, 3d}};

        MatrixImmutable m = MatrixImmutable.diagonalMatrix(dData);
        MatrixImmutable r = new MatrixImmutable(rData);

        assertEquals(r, m);
    }

    @Test
    public void identityMatrix() {
        Double[][] rData = {{1d, 0d, 0d}, {0d, 1d, 0d}, {0d, 0d, 1d}};

        MatrixImmutable m = MatrixImmutable.identityMatrix(3);
        MatrixImmutable r = new MatrixImmutable(rData);

        assertEquals(r, m);
    }

    @Test
    public void rowMatrix() {
        Double[] vData = {1d, 2d, 3d};
        Double[][] rData = {{1d, 2d, 3d}};

        MatrixImmutable m = MatrixImmutable.rowMatrix(vData);
        MatrixImmutable r = new MatrixImmutable(rData);

        assertEquals(r, m);
    }

    @Test
    public void columnMatrix() {
        Double[] vData = {1d, 2d, 3d};
        Double[][] rData = {{1d}, {2d}, {3d}};

        MatrixImmutable m = MatrixImmutable.columnMatrix(vData);
        MatrixImmutable r = new MatrixImmutable(rData);

        assertEquals(r, m);
    }

    @Test
    public void matrixEqual() {
        Double[][] m1Data = {{0d, 1d, 0d}, {0d, 0d, 0d}};
        Double[][] m2Data = {{0d, 1d, 0d}, {0d, 0d, 0d}, {0d, 0d, 0d}};
        Double[][] m3Data = {{0d, 1d, 0d}, {0d, 1d, 0d}};

        MatrixImmutable m1 = new MatrixImmutable(m1Data);
        MatrixImmutable m2 = new MatrixImmutable(m2Data);
        MatrixImmutable m3 = new MatrixImmutable(m3Data);
        MatrixImmutable m4 = new MatrixImmutable(m1Data);

        assertEquals(m1, m1);  // Same object
        assertNotEquals(m1, new Object());  // Object of other type
        assertNotEquals(m1, m2);  // Different dimensions
        assertNotEquals(m1, m3);  // Different elements
        assertEquals(m1, m4);  // Same elements
    }

    @Test
    public void matrixHashCode() {
        Double[][] m1Data = {{0d, 1d, 0d}, {0d, 0d, 0d}};
        Double[][] m2Data = {{0d, 1d, 0d}, {0d, 0d, 0d}, {0d, 0d, 0d}};
        Double[][] m3Data = {{0d, 1d, 0d}, {0d, 1d, 0d}};

        MatrixImmutable m1 = new MatrixImmutable(m1Data);
        MatrixImmutable m2 = new MatrixImmutable(m2Data);
        MatrixImmutable m3 = new MatrixImmutable(m3Data);
        MatrixImmutable m4 = new MatrixImmutable(m1Data);

        assertNotEquals(m1.hashCode(), m2.hashCode());  // Different dimensions
        assertNotEquals(m1.hashCode(), m3.hashCode());  // Different elements
        assertEquals(m1.hashCode(), m4.hashCode());  // Same elements
    }

    MatrixImmutable instantiateRandomMatrix(int rows, int columns) {
        Double[][] result = new Double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = Math.random();
            }
        }
        return new MatrixImmutable(result);
    }
}
