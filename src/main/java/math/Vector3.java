package math;

import java.util.Objects;

public class Vector3 {
    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }


    public Vector3 multiply(double scalar) {
      return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    public double dotProduct(Vector3 other) {
      return x * other.x + y * other.y + z * other.z;
    }

    public Vector3 crossProduct(Vector3 other) {
      double newX = y * other.z - z * other.y;
      double newY = z * other.x - x * other.z;
      double newZ = x * other.y - y * other.x;
      return new Vector3(newX, newY, newZ);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3 normalize() {
        double mag = magnitude();
        if (mag == 0) return new Vector3(0, 0, 0);
        return new Vector3(x / mag, y / mag, z / mag);
    }
    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Double.compare(vector3.x, x) == 0 && Double.compare(vector3.y, y) == 0 && Double.compare(vector3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}