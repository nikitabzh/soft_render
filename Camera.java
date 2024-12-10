package com.cgvsu.render_engine;

import javax.vecmath.Matrix4f;
import com.cgvsu.math.Vector3f;

public class Camera {
    private Vector3f position, target;
    private float fov, aspectRatio, nearPlane, farPlane;

    public Camera(Vector3f position, Vector3f target, float fov, float aspectRatio, float nearPlane, float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public Matrix4f getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public Matrix4f getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }
}
