package org.example.soft_render;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public List<Vertex> vertices;
    public List<TextureCoordinate> textureCoordinates;
    public List<Face> faces;

    public Model() {
        vertices = new ArrayList<>();
        textureCoordinates = new ArrayList<>();
        faces = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addTextureCoordinate(TextureCoordinate textureCoordinate) {
        textureCoordinates.add(textureCoordinate);
    }

    public void addFace(Face face) {
        faces.add(face);
    }
}
