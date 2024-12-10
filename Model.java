package com.cgvsu.model;

import java.util.ArrayList;
import com.cgvsu.math.Vector3f;
import com.cgvsu.render_engine.Triangle;

public class Model {
    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();
    public ArrayList<Triangle> triangles = new ArrayList<>();

    public void triangulate() {
        for (Polygon polygon : polygons) {
            for (int i = 1; i < polygon.getVertexIndices().size() - 1; i++) {
                Triangle triangle = new Triangle(
                        vertices.get(polygon.getVertexIndices().get(0)),
                        vertices.get(polygon.getVertexIndices().get(i)),
                        vertices.get(polygon.getVertexIndices().get(i + 1))
                );
                triangles.add(triangle);
            }
        }
    }

    public Vector3f calculateNormal(Vector3f v1, Vector3f v2, Vector3f v3) {
        Vector3f edge1 = new Vector3f(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z);
        Vector3f edge2 = new Vector3f(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z);

        Vector3f normal = new Vector3f(
                edge1.y * edge2.z - edge1.z * edge2.y,
                edge1.z * edge2.x - edge1.x * edge2.z,
                edge1.x * edge2.y - edge1.y * edge2.x
        );
        normal.normalize();
        return normal;
    }


}