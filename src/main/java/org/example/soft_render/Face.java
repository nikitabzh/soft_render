package org.example.soft_render;

import java.util.List;

public class Face {
    public List<Integer> vertexIndices;
    public List<Integer> textureIndices;

    public Face(List<Integer> vertexIndices, List<Integer> textureIndices) {
        this.vertexIndices = vertexIndices;
        this.textureIndices = textureIndices;
    }
}
