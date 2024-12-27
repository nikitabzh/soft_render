package core;

import math.Matrix4;
import math.Vector3;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CameraTest {

    @Test
    public void testDefaultCamera() {
        Camera camera = new Camera();
        assertEquals(new Vector3(0, 0, 5), camera.getPosition());
        assertEquals(new Vector3(0, 0, 0), camera.getTarget());
        assertEquals(new Vector3(0, 1, 0), camera.getUp());

        Matrix4 expectedViewMatrix = new Matrix4();
        expectedViewMatrix.set(0, 0, 1);
        expectedViewMatrix.set(1, 0, 0);
        expectedViewMatrix.set(2, 0, 0);
        expectedViewMatrix.set(0, 1, 0);
        expectedViewMatrix.set(1, 1, 1);
        expectedViewMatrix.set(2, 1, 0);
        expectedViewMatrix.set(0, 2, 0);
        expectedViewMatrix.set(1, 2, 0);
        expectedViewMatrix.set(2, 2, 1);
        expectedViewMatrix.set(0, 3, 0);
        expectedViewMatrix.set(1, 3, 0);
        expectedViewMatrix.set(2, 3, -5);

        assertEquals(expectedViewMatrix, camera.getViewMatrix());
    }

    @Test
    public void testCustomCamera() {
        Vector3 position = new Vector3(1, 2, 3);
        Vector3 target = new Vector3(4, 5, 6);
        Vector3 up = new Vector3(0, 1, 0);
        Camera camera = new Camera(position, target, up);
        assertEquals(position, camera.getPosition());
        assertEquals(target, camera.getTarget());
        assertEquals(up, camera.getUp());

        Vector3 zAxis = position.subtract(target).normalize();
        Vector3 xAxis = up.crossProduct(zAxis).normalize();
        Vector3 yAxis = zAxis.crossProduct(xAxis);

        Matrix4 expectedViewMatrix = new Matrix4();
        expectedViewMatrix.set(0, 0, xAxis.getX());
        expectedViewMatrix.set(1, 0, xAxis.getY());
        expectedViewMatrix.set(2, 0, xAxis.getZ());
        expectedViewMatrix.set(0, 1, yAxis.getX());
        expectedViewMatrix.set(1, 1, yAxis.getY());
        expectedViewMatrix.set(2, 1, yAxis.getZ());
        expectedViewMatrix.set(0, 2, zAxis.getX());
        expectedViewMatrix.set(1, 2, zAxis.getY());
        expectedViewMatrix.set(2, 2, zAxis.getZ());
        expectedViewMatrix.set(0, 3, -xAxis.dotProduct(position));
        expectedViewMatrix.set(1, 3, -yAxis.dotProduct(position));
        expectedViewMatrix.set(2, 3, -zAxis.dotProduct(position));

        assertEquals(expectedViewMatrix, camera.getViewMatrix());
    }

    @Test
    public void testSetPosition() {
        Camera camera = new Camera();
        Vector3 newPosition = new Vector3(1, 2, 3);
        camera.setPosition(newPosition);
        assertEquals(newPosition, camera.getPosition());

        // Проверка обновления матрицы вида
        Vector3 zAxis = newPosition.subtract(camera.getTarget()).normalize();
        Vector3 xAxis = camera.getUp().crossProduct(zAxis).normalize();
        Vector3 yAxis = zAxis.crossProduct(xAxis);

        Matrix4 expectedViewMatrix = new Matrix4();
        expectedViewMatrix.set(0, 0, xAxis.getX());
        expectedViewMatrix.set(1, 0, xAxis.getY());
        expectedViewMatrix.set(2, 0, xAxis.getZ());
        expectedViewMatrix.set(0, 1, yAxis.getX());
        expectedViewMatrix.set(1, 1, yAxis.getY());
        expectedViewMatrix.set(2, 1, yAxis.getZ());
        expectedViewMatrix.set(0, 2, zAxis.getX());
        expectedViewMatrix.set(1, 2, zAxis.getY());
        expectedViewMatrix.set(2, 2, zAxis.getZ());
        expectedViewMatrix.set(0, 3, -xAxis.dotProduct(newPosition));
        expectedViewMatrix.set(1, 3, -yAxis.dotProduct(newPosition));
        expectedViewMatrix.set(2, 3, -zAxis.dotProduct(newPosition));

        assertEquals(expectedViewMatrix, camera.getViewMatrix());
    }

    @Test
    public void testSetTarget() {
        Camera camera = new Camera();
        Vector3 newTarget = new Vector3(1, 2, 3);
        camera.setTarget(newTarget);
        assertEquals(newTarget, camera.getTarget());

        // Проверка обновления матрицы вида
        Vector3 zAxis = camera.getPosition().subtract(newTarget).normalize();
        Vector3 xAxis = camera.getUp().crossProduct(zAxis).normalize();
        Vector3 yAxis = zAxis.crossProduct(xAxis);

        Matrix4 expectedViewMatrix = new Matrix4();
        expectedViewMatrix.set(0, 0, xAxis.getX());
        expectedViewMatrix.set(1, 0, xAxis.getY());
        expectedViewMatrix.set(2, 0, xAxis.getZ());
        expectedViewMatrix.set(0, 1, yAxis.getX());
        expectedViewMatrix.set(1, 1, yAxis.getY());
        expectedViewMatrix.set(2, 1, yAxis.getZ());
        expectedViewMatrix.set(0, 2, zAxis.getX());
        expectedViewMatrix.set(1, 2, zAxis.getY());
        expectedViewMatrix.set(2, 2, zAxis.getZ());
        expectedViewMatrix.set(0, 3, -xAxis.dotProduct(camera.getPosition()));
        expectedViewMatrix.set(1, 3, -yAxis.dotProduct(camera.getPosition()));
        expectedViewMatrix.set(2, 3, -zAxis.dotProduct(camera.getPosition()));

        assertEquals(expectedViewMatrix, camera.getViewMatrix());
    }

    @Test
    public void testSetUp() {
        Camera camera = new Camera();
        Vector3 newUp = new Vector3(0, 0, 1);
        camera.setUp(newUp);
        assertEquals(newUp, camera.getUp());

        // Проверка обновления матрицы вида
        Vector3 zAxis = camera.getPosition().subtract(camera.getTarget()).normalize();
        Vector3 xAxis = newUp.crossProduct(zAxis).normalize();
        Vector3 yAxis = zAxis.crossProduct(xAxis);

        Matrix4 expectedViewMatrix = new Matrix4();
        expectedViewMatrix.set(0, 0, xAxis.getX());
        expectedViewMatrix.set(1, 0, xAxis.getY());
        expectedViewMatrix.set(2, 0, xAxis.getZ());
        expectedViewMatrix.set(0, 1, yAxis.getX());
        expectedViewMatrix.set(1, 1, yAxis.getY());
        expectedViewMatrix.set(2, 1, yAxis.getZ());
        expectedViewMatrix.set(0, 2, zAxis.getX());
        expectedViewMatrix.set(1, 2, zAxis.getY());
        expectedViewMatrix.set(2, 2, zAxis.getZ());
        expectedViewMatrix.set(0, 3, -xAxis.dotProduct(camera.getPosition()));
        expectedViewMatrix.set(1, 3, -yAxis.dotProduct(camera.getPosition()));
        expectedViewMatrix.set(2, 3, -zAxis.dotProduct(camera.getPosition()));

        assertEquals(expectedViewMatrix, camera.getViewMatrix());
    }
}
