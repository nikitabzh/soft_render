package com.cgvsu.render_engine;

import com.cgvsu.math.Vector3f;

public class Light {
    public Vector3f position;
    public float intensity;

    public Light(Vector3f position, float intensity) {
        this.position = position;
        this.intensity = intensity;
    }
}