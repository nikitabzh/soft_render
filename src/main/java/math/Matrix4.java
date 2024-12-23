package math;

import java.util.Arrays;

public class Matrix4 {
    private double[][] matrix;

    public Matrix4() {
        matrix = new double[4][4];
        for (int i = 0; i < 4; i++) {
            matrix[i][i] = 1.0;
        }
    }

    public Matrix4(double[][] matrix) {
        if (matrix.length != 4 || matrix[0].length != 4) {
            throw new IllegalArgumentException("Matrix must be 4x4");
        }
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }
    public double get(int row, int col) {
      return matrix[row][col];
    }

    public void set(int row, int col, double value) {
      matrix[row][col] = value;
    }
    public void setMatrix(double[][] matrix) {
      this.matrix = matrix;
    }
    public static Matrix4 identity() {
        return new Matrix4();
    }

    public static Matrix4 translation(double x, double y, double z) {
      Matrix4 m = new Matrix4();
      m.set(0, 3, x);
      m.set(1, 3, y);
      m.set(2, 3, z);
      return m;
    }

    public static Matrix4 scaling(double x, double y, double z) {
        Matrix4 m = new Matrix4();
        m.set(0, 0, x);
        m.set(1, 1, y);
        m.set(2, 2, z);
        return m;
    }

    public static Matrix4 rotationX(double angle) {
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        Matrix4 m = new Matrix4();
        m.set(1, 1, cos);
        m.set(1, 2, -sin);
        m.set(2, 1, sin);
        m.set(2, 2, cos);
        return m;
    }

    public static Matrix4 rotationY(double angle) {
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        Matrix4 m = new Matrix4();
        m.set(0, 0, cos);
        m.set(0, 2, sin);
        m.set(2, 0, -sin);
        m.set(2, 2, cos);
        return m;
    }


    public static Matrix4 rotationZ(double angle) {
      double cos = Math.cos(Math.toRadians(angle));
      double sin = Math.sin(Math.toRadians(angle));
        Matrix4 m = new Matrix4();
      m.set(0, 0, cos);
      m.set(0, 1, -sin);
      m.set(1, 0, sin);
      m.set(1, 1, cos);
      return m;
    }


    public Matrix4 multiply(Matrix4 other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result[i][j] += matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return new Matrix4(result);
    }

    public Vector3 transform(Vector3 vector) {
      double x = vector.getX() * matrix[0][0] + vector.getY() * matrix[0][1] + vector.getZ() * matrix[0][2] + 1 * matrix[0][3];
      double y = vector.getX() * matrix[1][0] + vector.getY() * matrix[1][1] + vector.getZ() * matrix[1][2] + 1 * matrix[1][3];
      double z = vector.getX() * matrix[2][0] + vector.getY() * matrix[2][1] + vector.getZ() * matrix[2][2] + 1 * matrix[2][3];
        return new Vector3(x, y, z);
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Matrix4 matrix4 = (Matrix4) o;
    return Arrays.deepEquals(matrix, matrix4.matrix);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(matrix);
  }

  @Override
  public String toString() {
    return "Matrix4{" +
            "matrix=" + Arrays.deepToString(matrix) +
            '}';
  }
}