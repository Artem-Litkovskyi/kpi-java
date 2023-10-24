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
    public void createMatrixFromArray() {
        Double[][] mData = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        MatrixMutable m = new MatrixMutable(mData);
        for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData[0].length; j++) {
                assertEquals(mData[i][j], m.getElement(i, j));
            }
        }
    }

    @Test
    public void createMatrixByCopying() {
        MatrixMutable m1 = MatrixMutable.randomMatrix(3, 4);
        MatrixMutable m2 = new MatrixMutable(m1);
        assertEquals(m1, m2);
    }

    @Test
    public void createMatrixOfSize() {
        MatrixMutable m = new MatrixMutable(3, 4);
        assertEquals(3, m.getRowsNumber());
        assertEquals(4, m.getColumnsNumber());
    }

    @Test
    public void getMatrixDimension() {
        int a = 3;
        int b = 4;
        MatrixMutable m = MatrixMutable.randomMatrix(a, b);
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
        MatrixMutable m1 = new MatrixMutable(m1Data);
        MatrixMutable m2 = new MatrixMutable(m2Data);
        MatrixMutable r = new MatrixMutable(rData);
        assertEquals(r, m1.add(m2));
    }

    @Test
    public void multiplyMatrixByNumber() {
        Double[][] mData = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] rData = {{2d, 4d, 6d}, {8d, 10d, 12d}};
        MatrixMutable m = new MatrixMutable(mData);
        MatrixMutable r = new MatrixMutable(rData);
        assertEquals(r, m.multiply(2d));
    }

    @Test
    public void multiplyMatrices() {
        Double[][] m1Data = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] m2Data = {{1d, 2d, 3d}, {4d, 5d, 6d}, {7d, 8d, 9d}};
        Double[][] rData = {{30d, 36d, 42d}, {66d, 81d, 96d}};
        MatrixMutable m1 = new MatrixMutable(m1Data);
        MatrixMutable m2 = new MatrixMutable(m2Data);
        MatrixMutable r = new MatrixMutable(rData);
        assertEquals(r, m1.multiply(m2));
    }

    @Test
    public void transposeMatrix() {
        Double[][] mData = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        Double[][] rData = {{1d, 4d}, {2d, 5d}, {3d, 6d}};
        MatrixMutable m1 = new MatrixMutable(mData);
        MatrixMutable r = new MatrixMutable(rData);
        assertEquals(r, m1.transpose());
    }

    @Test
    public void determinant() {
        Double[][] m1Data = {{9d}};
        Double[][] m2Data = {{9d, 7d}, {5d, -4d}};
        Double[][] m3Data = {{9d, 7d, 5d}, {5d, -4d, 51d}, {-10d, 6d, 13d}};
        Double[][] m4Data = {
                {9d, 7d, 5d, 5d, -3d},
                {5d, -4d, 51d, -31d, 31d},
                {-10d, 6d, 13d, 19d, 1d},
                {2d, 8d, -1d, -1d, 7d},
                {17d, 6d, 10d, 13d, 9d},
        };

        Double m1Det = 9d;
        Double m2Det = -71d;
        Double m3Det = -7297d;
        Double m4Det = 3103998d;

        MatrixMutable m1 = new MatrixMutable(m1Data);
        MatrixMutable m2 = new MatrixMutable(m2Data);
        MatrixMutable m3 = new MatrixMutable(m3Data);
        MatrixMutable m4 = new MatrixMutable(m4Data);

        assertEquals(m1Det, m1.determinant());
        assertEquals(m2Det, m2.determinant());
        assertEquals(m3Det, m3.determinant());
        assertEquals(m4Det, m4.determinant());
    }

    @Test
    public void luDecomposition() {
        double precision = 0.001d;

        Double[][] m1Data = {{9d, 7d}, {5d, -4d}};
        Double[][] m2Data = {{9d, 7d, 5d}, {5d, -4d, 51d}, {-10d, 6d, 13d}};

        Double[][] l1Data = {{1d, 0d}, {5d/9d, 1d}};
        Double[][] u1Data = {{9d, 7d}, {0d, -71d/9d}};

        Double[][] l2Data = {{1d, 0d, 0d}, {5d/9d, 1d, 0d}, {-10d/9d, -124d/71d, 1d}};
        Double[][] u2Data = {{9d, 7d, 5d}, {0d, -71d/9d, 434d/9d}, {0d, 0d, 7297d/71d}};

        MatrixMutable m1 = new MatrixMutable(m1Data);
        MatrixMutable m2 = new MatrixMutable(m2Data);

        MatrixMutable l1 = new MatrixMutable(l1Data);
        MatrixMutable u1 = new MatrixMutable(u1Data);

        MatrixMutable l2 = new MatrixMutable(l2Data);
        MatrixMutable u2 = new MatrixMutable(u2Data);

        MatrixMutable[] lu1 = m1.luDecomposition();
        MatrixMutable[] lu2 = m2.luDecomposition();

        assertTrue(compareWithPrecision(l1, lu1[0], precision));
        assertTrue(compareWithPrecision(u1, lu1[1], precision));
        assertTrue(compareWithPrecision(l2, lu2[0], precision));
        assertTrue(compareWithPrecision(u2, lu2[1], precision));
    }

    @Test
    public void diagonalMatrix() {
        Double[] dData = {1d, 2d, 3d};
        Double[][] rData = {{1d, 0d, 0d}, {0d, 2d, 0d}, {0d, 0d, 3d}};

        MatrixMutable m = MatrixMutable.diagonalMatrix(dData);
        MatrixMutable r = new MatrixMutable(rData);

        assertEquals(r, m);
    }

    @Test
    public void identityMatrix() {
        Double[][] rData = {{1d, 0d, 0d}, {0d, 1d, 0d}, {0d, 0d, 1d}};

        MatrixMutable m = MatrixMutable.identityMatrix(3);
        MatrixMutable r = new MatrixMutable(rData);

        assertEquals(r, m);
    }

    @Test
    public void rowMatrix() {
        Double[] vData = {1d, 2d, 3d};
        Double[][] rData = {{1d, 2d, 3d}};

        MatrixMutable m = MatrixMutable.rowMatrix(vData);
        MatrixMutable r = new MatrixMutable(rData);

        assertEquals(r, m);
    }

    @Test
    public void columnMatrix() {
        Double[] vData = {1d, 2d, 3d};
        Double[][] rData = {{1d}, {2d}, {3d}};

        MatrixMutable m = MatrixMutable.columnMatrix(vData);
        MatrixMutable r = new MatrixMutable(rData);

        assertEquals(r, m);
    }

    @Test
    public void matrixEqual() {
        Double[][] m1Data = {{0d, 1d, 0d}, {0d, 0d, 0d}};
        Double[][] m2Data = {{0d, 1d, 0d}, {0d, 0d, 0d}, {0d, 0d, 0d}};
        Double[][] m3Data = {{0d, 1d, 0d}, {0d, 1d, 0d}};

        MatrixMutable m1 = new MatrixMutable(m1Data);
        MatrixImmutable m2 = new MatrixImmutable(m1Data);
        MatrixMutable m3 = new MatrixMutable(m2Data);
        MatrixMutable m4 = new MatrixMutable(m3Data);
        MatrixMutable m5 = new MatrixMutable(m1Data);

        assertEquals(m1, m1);  // Same object
        assertNotEquals(m1, new Object());  // Object of other type
        assertNotEquals(m1, m2);  // Immutable matrix
        assertNotEquals(m1, m3);  // Different dimensions
        assertNotEquals(m1, m4);  // Different elements
        assertEquals(m1, m5);  // Same elements
    }

    @Test
    public void matrixHashCode() {
        Double[][] m1Data = {{0d, 1d, 0d}, {0d, 0d, 0d}};
        Double[][] m2Data = {{0d, 1d, 0d}, {0d, 0d, 0d}, {0d, 0d, 0d}};
        Double[][] m3Data = {{0d, 1d, 0d}, {0d, 1d, 0d}};

        MatrixMutable m1 = new MatrixMutable(m1Data);
        MatrixImmutable m2 = new MatrixImmutable(m1Data);
        MatrixMutable m3 = new MatrixMutable(m2Data);
        MatrixMutable m4 = new MatrixMutable(m3Data);
        MatrixMutable m5 = new MatrixMutable(m1Data);

        assertNotEquals(m1.hashCode(), m2.hashCode());  // Immutable matrix
        assertNotEquals(m1.hashCode(), m3.hashCode());  // Different dimensions
        assertNotEquals(m1.hashCode(), m4.hashCode());  // Different elements
        assertEquals(m1.hashCode(), m5.hashCode());  // Same elements
    }

    @Test
    public void changeMatrixDimension() {
        int n = 5;
        MatrixMutable m1 = MatrixMutable.randomMatrix(n, n);
        MatrixMutable m2 = new MatrixMutable(m1);
        MatrixMutable m3 = new MatrixMutable(m1);
        m2.changeDimension(n-1, n+1);
        m3.changeDimension(n+1, n-1);
        assertEquals(m2.getRowsNumber(), n-1);
        assertEquals(m2.getColumnsNumber(), n+1);
        assertEquals(m3.getRowsNumber(), n+1);
        assertEquals(m3.getColumnsNumber(), n-1);
    }

    boolean compareWithPrecision(Matrix a, Matrix b, double precision) {
        if (a.getRowsNumber() != b.getRowsNumber()) return false;
        if (a.getColumnsNumber() != b.getColumnsNumber()) return false;

        for (int i = 0; i < a.getRowsNumber(); i++) {
            for (int j = 0; j < a.getColumnsNumber(); j++) {
                if (Math.abs(a.getElement(i, j) - b.getElement(i, j)) > precision) return false;
            }
        }

        return true;
    }
}
