package com.cgvsu.render_engine;

import com.cgvsu.math.Vector3f;

public class Triangle {
    public Vector3f v1, v2, v3;

    public Triangle(Vector3f v1, Vector3f v2, Vector3f v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }
}