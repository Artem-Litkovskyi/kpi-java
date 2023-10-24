package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixImmutableTest {
    @Test
    public void createEmptyMatrix() {
        MatrixImmutable m = new MatrixImmutable();
        assertEquals(0, m.getRowsNumber());
        assertEquals(0, m.getColumnsNumber());
    }

    @Test
    public void createMatrixFromArray() {
        Double[][] mData = {{1d, 2d, 3d}, {4d, 5d, 6d}};
        MatrixImmutable m = new MatrixImmutable(mData);
        for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData[0].length; j++) {
                assertEquals(mData[i][j], m.getElement(i, j));
            }
        }
    }

    @Test
    public void createMatrixByCopying() {
        MatrixImmutable m1 = MatrixImmutable.randomMatrix(3, 4);
        MatrixImmutable m2 = new MatrixImmutable(m1);
        assertEquals(m1, m2);
    }

    @Test
    public void createMatrixOfSize() {
        MatrixImmutable m = new MatrixImmutable(3, 4);
        assertEquals(3, m.getRowsNumber());
        assertEquals(4, m.getColumnsNumber());
    }

    @Test
    public void getMatrixDimension() {
        int a = 3;
        int b = 4;
        MatrixImmutable m = MatrixImmutable.randomMatrix(a, b);
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

        MatrixImmutable m1 = new MatrixImmutable(m1Data);
        MatrixImmutable m2 = new MatrixImmutable(m2Data);
        MatrixImmutable m3 = new MatrixImmutable(m3Data);
        MatrixImmutable m4 = new MatrixImmutable(m4Data);

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

        MatrixImmutable m1 = new MatrixImmutable(m1Data);
        MatrixImmutable m2 = new MatrixImmutable(m2Data);

        MatrixImmutable l1 = new MatrixImmutable(l1Data);
        MatrixImmutable u1 = new MatrixImmutable(u1Data);

        MatrixImmutable l2 = new MatrixImmutable(l2Data);
        MatrixImmutable u2 = new MatrixImmutable(u2Data);

        MatrixImmutable[] lu1 = m1.luDecomposition();
        MatrixImmutable[] lu2 = m2.luDecomposition();

        assertTrue(compareWithPrecision(l1, lu1[0], precision));
        assertTrue(compareWithPrecision(u1, lu1[1], precision));
        assertTrue(compareWithPrecision(l2, lu2[0], precision));
        assertTrue(compareWithPrecision(u2, lu2[1], precision));
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
        MatrixMutable m2 = new MatrixMutable(m2Data);
        MatrixImmutable m3 = new MatrixImmutable(m2Data);
        MatrixImmutable m4 = new MatrixImmutable(m3Data);
        MatrixImmutable m5 = new MatrixImmutable(m1Data);

        assertEquals(m1, m1);  // Same object
        assertNotEquals(m1, new Object());  // Object of other type
        assertNotEquals(m1, m2);  // Mutable matrix
        assertNotEquals(m1, m3);  // Different dimensions
        assertNotEquals(m1, m4);  // Different elements
        assertEquals(m1, m5);  // Same elements
    }

    @Test
    public void matrixHashCode() {
        Double[][] m1Data = {{0d, 1d, 0d}, {0d, 0d, 0d}};
        Double[][] m2Data = {{0d, 1d, 0d}, {0d, 0d, 0d}, {0d, 0d, 0d}};
        Double[][] m3Data = {{0d, 1d, 0d}, {0d, 1d, 0d}};

        MatrixImmutable m1 = new MatrixImmutable(m1Data);
        MatrixMutable m2 = new MatrixMutable(m1Data);
        MatrixImmutable m3 = new MatrixImmutable(m2Data);
        MatrixImmutable m4 = new MatrixImmutable(m3Data);
        MatrixImmutable m5 = new MatrixImmutable(m1Data);

        assertNotEquals(m1.hashCode(), m2.hashCode());  // Mutable matrix
        assertNotEquals(m1.hashCode(), m3.hashCode());  // Different dimensions
        assertNotEquals(m1.hashCode(), m4.hashCode());  // Different elements
        assertEquals(m1.hashCode(), m5.hashCode());  // Same elements
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
