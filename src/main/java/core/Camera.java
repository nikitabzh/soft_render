package core;

import math.Matrix4;
import math.Vector3;

public class Camera {
    private Vector3 position;
    private Vector3 target;
    private Vector3 up;
    private Matrix4 viewMatrix;

    public Camera(Vector3 position, Vector3 target, Vector3 up) {
        this.position = position;
        this.target = target;
        this.up = up;
        updateViewMatrix();
    }

    public Camera() {
        this.position = new Vector3(0, 0, 5);
        this.target = new Vector3(0, 0, 0);
        this.up = new Vector3(0, 1, 0);
        updateViewMatrix();
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
        updateViewMatrix();
    }

    public Vector3 getTarget() {
        return target;
    }

    public void setTarget(Vector3 target) {
        this.target = target;
        updateViewMatrix();
    }

    public Vector3 getUp() {
        return up;
    }

    public void setUp(Vector3 up) {
        this.up = up;
        updateViewMatrix();
    }

    public Matrix4 getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4 viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    private void updateViewMatrix() {
        Vector3 zAxis = position.subtract(target).normalize();
        Vector3 xAxis = up.crossProduct(zAxis).normalize();
        Vector3 yAxis = zAxis.crossProduct(xAxis);

        Matrix4 view = new Matrix4();
        view.set(0, 0, xAxis.getX());
        view.set(1, 0, xAxis.getY());
        view.set(2, 0, xAxis.getZ());
        view.set(0, 1, yAxis.getX());
        view.set(1, 1, yAxis.getY());
        view.set(2, 1, yAxis.getZ());
        view.set(0, 2, zAxis.getX());
        view.set(1, 2, zAxis.getY());
        view.set(2, 2, zAxis.getZ());
        view.set(0, 3, -xAxis.dotProduct(position));
        view.set(1, 3, -yAxis.dotProduct(position));
        view.set(2, 3, -zAxis.dotProduct(position));

        this.viewMatrix = view;
    }
}