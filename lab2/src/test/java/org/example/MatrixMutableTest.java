package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixMutableTest {
    @Test
    public void createEmptyMatrix() {
        MatrixMutable m = new MatrixMutable();
        assertEquals(0, m.getRowsNumber());
        assertEquals(0, m.getColumnsNumber());
    }

    @Test
    public void createMatrixOfSize() {
        MatrixMutable m = new MatrixMutable(3, 4);
        assertEquals(3, m.getRowsNumber());
        assertEquals(4, m.getColumnsNumber());
    }

    @Test
    public void createMatrixByCopying() {
        MatrixMutable m1 = instantiateRandomMatrix(3, 4);
        MatrixMutable m2 = new MatrixMutable(m1);
        assertEquals(m1, m2);
    }

    @Test
    public void createMatrixFromArray() {
        Double[][] m_data = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        MatrixMutable m = new MatrixMutable(m_data);
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
        MatrixMutable m = instantiateRandomMatrix(a, b);
        int[] dim = m.getDimension();
        assertEquals(2, dim.length);
        assertEquals(a, dim[0]);
        assertEquals(b, dim[1]);
    }

    @Test
    public void addMatrices() {
        Double[][] m1_data = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] m2_data = {{0d, 3d, 0d}, {2d, 0d, 1d}};
        Double[][] r_data = {{1d, 5d, 3d}, {6d, 5d, 7d}};
        MatrixMutable m1 = new MatrixMutable(m1_data);
        MatrixMutable m2 = new MatrixMutable(m2_data);
        MatrixMutable r = new MatrixMutable(r_data);
        assertEquals(r, m1.add(m2));
    }

    @Test
    public void multiplyMatrixByNumber() {
        Double[][] m_data = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] r_data = {{2d, 4d, 6d}, {8d, 10d, 12d}};
        MatrixMutable m = new MatrixMutable(m_data);
        MatrixMutable r = new MatrixMutable(r_data);
        assertEquals(r, m.multiply(2d));
    }

    @Test
    public void multiplyMatrices() {
        Double[][] m1_data = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] m2_data = {{1d, 2d, 3d}, {4d, 5d, 6d}, {7d, 8d, 9d}};
        Double[][] r_data = {{30d, 36d, 42d}, {66d, 81d, 96d}};
        MatrixMutable m1 = new MatrixMutable(m1_data);
        MatrixMutable m2 = new MatrixMutable(m2_data);
        MatrixMutable r = new MatrixMutable(r_data);
        assertEquals(r, m1.multiply(m2));
    }

    @Test
    public void transposeMatrix() {
        Double[][] m_data = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] r_data = {{1d, 4d}, {2d, 5d}, {3d, 6d}};
        MatrixMutable m1 = new MatrixMutable(m_data);
        MatrixMutable r = new MatrixMutable(r_data);
        assertEquals(r, m1.transpose());
    }

    @Test
    public void diagonalMatrix() {
        Double[] d_data = {1d, 2d, 3d};
        Double[][] r_data = {{1d, 0d, 0d}, {0d, 2d, 0d}, {0d, 0d, 3d}};

        MatrixMutable m = MatrixMutable.diagonalMatrix(d_data);
        MatrixMutable r = new MatrixMutable(r_data);

        assertEquals(r, m);
    }

    @Test
    public void identityMatrix() {
        Double[][] r_data = {{1d, 0d, 0d}, {0d, 1d, 0d}, {0d, 0d, 1d}};

        MatrixMutable m = MatrixMutable.identityMatrix(3);
        MatrixMutable r = new MatrixMutable(r_data);

        assertEquals(r, m);
    }

    @Test
    public void rowMatrix() {
        Double[] v_data = {1d, 2d, 3d};
        Double[][] r_data = {{1d, 2d, 3d}};

        MatrixMutable m = MatrixMutable.rowMatrix(v_data);
        MatrixMutable r = new MatrixMutable(r_data);

        assertEquals(r, m);
    }

    @Test
    public void columnMatrix() {
        Double[] v_data = {1d, 2d, 3d};
        Double[][] r_data = {{1d}, {2d}, {3d}};

        MatrixMutable m = MatrixMutable.columnMatrix(v_data);
        MatrixMutable r = new MatrixMutable(r_data);

        assertEquals(r, m);
    }

    @Test
    public void matrixEqual() {
        Double[][] m1_data = {{0d, 1d, 0d}, {0d, 0d, 0d}};
        Double[][] m2_data = {{0d, 1d, 0d}, {0d, 0d, 0d}, {0d, 0d, 0d}};
        Double[][] m3_data = {{0d, 1d, 0d}, {0d, 1d, 0d}};

        MatrixMutable m1 = new MatrixMutable(m1_data);
        MatrixMutable m2 = new MatrixMutable(m2_data);
        MatrixMutable m3 = new MatrixMutable(m3_data);
        MatrixMutable m4 = new MatrixMutable(m1_data);

        assertEquals(m1, m1);  // Same object
        assertNotEquals(m1, new Object());  // Object of other type
        assertNotEquals(m1, m2);  // Different dimensions
        assertNotEquals(m1, m3);  // Different elements
        assertEquals(m1, m4);  // Same elements
    }

    @Test
    public void matrixHashCode() {
        Double[][] m1_data = {{0d, 1d, 0d}, {0d, 0d, 0d}};
        Double[][] m2_data = {{0d, 1d, 0d}, {0d, 0d, 0d}, {0d, 0d, 0d}};
        Double[][] m3_data = {{0d, 1d, 0d}, {0d, 1d, 0d}};

        MatrixMutable m1 = new MatrixMutable(m1_data);
        MatrixMutable m2 = new MatrixMutable(m2_data);
        MatrixMutable m3 = new MatrixMutable(m3_data);
        MatrixMutable m4 = new MatrixMutable(m1_data);

        assertNotEquals(m1.hashCode(), m2.hashCode());  // Different dimensions
        assertNotEquals(m1.hashCode(), m3.hashCode());  // Different elements
        assertEquals(m1.hashCode(), m4.hashCode());  // Same elements
    }

    @Test
    public void changeMatrixDimension() {
        int n = 5;
        MatrixMutable m1 = instantiateRandomMatrix(n, n);
        MatrixMutable m2 = new MatrixMutable(m1);
        MatrixMutable m3 = new MatrixMutable(m1);
        m2.changeDimension(n-1, n+1);
        m3.changeDimension(n+1, n-1);
        assertEquals(m2.getRowsNumber(), n-1);
        assertEquals(m2.getColumnsNumber(), n+1);
        assertEquals(m3.getRowsNumber(), n+1);
        assertEquals(m3.getColumnsNumber(), n-1);
    }

    MatrixMutable instantiateRandomMatrix(int rows, int columns) {
        MatrixMutable m = new MatrixMutable(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                m.setElement(i, j, Math.random());
            }
        }
        return m;
    }
}
