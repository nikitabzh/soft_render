package math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Matrix4Test {

    @Test
    public void testIdentityMatrix() {
        Matrix4 identity = Matrix4.identity();
        double[][] expected = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        assertTrue(matricesEqual(identity.getMatrix(), expected));
    }

    @Test
    public void testTranslation() {
        Matrix4 translation = Matrix4.translation(2, 3, 4);
        double[][] expected = {
                {1, 0, 0, 2},
                {0, 1, 0, 3},
                {0, 0, 1, 4},
                {0, 0, 0, 1}
        };
        assertTrue(matricesEqual(translation.getMatrix(), expected));
    }

    @Test
    public void testScaling() {
        Matrix4 scaling = Matrix4.scaling(2, 3, 4);
        double[][] expected = {
                {2, 0, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 4, 0},
                {0, 0, 0, 1}
        };
        assertTrue(matricesEqual(scaling.getMatrix(), expected));
    }

    @Test
    public void testRotationX() {
        Matrix4 rotationX = Matrix4.rotationX(90);
        double[][] expected = {
                {1, 0, 0, 0},
                {0, 0, -1, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 1}
        };
        assertTrue(matricesEqual(rotationX.getMatrix(), expected));
    }

    @Test
    public void testRotationY() {
        Matrix4 rotationY = Matrix4.rotationY(90);
        double[][] expected = {
                {0, 0, 1, 0},
                {0, 1, 0, 0},
                {-1, 0, 0, 0},
                {0, 0, 0, 1}
        };
        assertTrue(matricesEqual(rotationY.getMatrix(), expected));
    }

    @Test
    public void testRotationZ() {
        Matrix4 rotationZ = Matrix4.rotationZ(90);
        double[][] expected = {
                {0, -1, 0, 0},
                {1, 0, 0, 0},
                {0, 0, 0, 1},
                {0, 0, 1, 0}
        };
        assertTrue(matricesEqual(rotationZ.getMatrix(), expected));
    }

    @Test
    public void testMatrixMultiplication() {
        Matrix4 m1 = Matrix4.translation(1, 2, 3);
        Matrix4 m2 = Matrix4.scaling(2, 2, 2);
        Matrix4 result = m1.multiply(m2);

        double[][] expected = {
                {2, 0, 0, 2},
                {0, 2, 0, 4},
                {0, 0, 2, 6},
                {0, 0, 0, 1}
        };

        assertTrue(matricesEqual(result.getMatrix(), expected));
    }

    @Test
    public void testTransformation() {
        Vector3 vector = new Vector3(1, 2, 3);
        Matrix4 transformation = Matrix4.translation(1, -1, 2).multiply(Matrix4.scaling(2, 2, 2));
        Vector3 transformedVector = transformation.transform(vector);

        Vector3 expectedVector = new Vector3(3.0, -1.0, 8.0); // (1*2 + (1), (2*2 + (-1)), (3*2 + (2)))
        assertEquals(expectedVector, transformedVector);
    }

    private boolean matricesEqual(double[][] matrix1, double[][] matrix2) {
        if (matrix1.length != matrix2.length) {
            return false;
        }
        for (int i = 0; i < matrix1.length; i++) {
            if (matrix1[i].length != matrix2[i].length) {
                return false;
            }
            for (int j = 0; j < matrix1[i].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
