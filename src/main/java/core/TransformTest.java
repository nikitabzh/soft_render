package core;

import math.Matrix4;
import math.Vector3;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransformTest {

    @Test
    public void testDefaultTransform() {
        Transform transform = new Transform();
        assertEquals(new Vector3(0, 0, 0), transform.getPosition(), "Default position should be (0, 0, 0)");
        assertEquals(new Vector3(1, 1, 1), transform.getScale(), "Default scale should be (1, 1, 1)");
        assertEquals(new Vector3(0, 0, 0), transform.getRotation(), "Default rotation should be (0, 0, 0)");
        assertNotNull(transform.getModelMatrix(), "Model matrix should not be null");
        System.out.println("testDefaultTransform passed");
    }

    @Test
    public void testSetPosition() {
        Transform transform = new Transform();
        transform.setPosition(new Vector3(1, 2, 3));
        assertEquals(new Vector3(1, 2, 3), transform.getPosition(), "Position should be updated to (1, 2, 3)");
        System.out.println("testSetPosition passed");
    }

    @Test
    public void testSetScale() {
        Transform transform = new Transform();
        transform.setScale(new Vector3(2, 2, 2));
        assertEquals(new Vector3(2, 2, 2), transform.getScale(), "Scale should be updated to (2, 2, 2)");
        System.out.println("testSetScale passed");
    }

    @Test
    public void testSetRotation() {
        Transform transform = new Transform();
        transform.setRotation(new Vector3(90, 0, 0));
        assertEquals(new Vector3(90, 0, 0), transform.getRotation(), "Rotation should be updated to (90, 0, 0)");
        System.out.println("testSetRotation passed");
    }

    @Test
    public void testModelMatrixUpdate() {
        Transform transform = new Transform();
        transform.setPosition(new Vector3(1, 2, 3));
        transform.setScale(new Vector3(2, 2, 2));
        transform.setRotation(new Vector3(90, 0, 0));

        Matrix4 expectedModelMatrix = Matrix4.translation(1, 2, 3)
                .multiply(Matrix4.rotationX(90))
                .multiply(Matrix4.scaling(2, 2, 2));

        assertEquals(expectedModelMatrix, transform.getModelMatrix(), "Model matrix should be updated correctly after transformations");
        System.out.println("testModelMatrixUpdate passed");
    }
}
