package org.example.soft_render;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjReader {
    public static Model loadModel(String filepath) throws IOException {
        Model model = new Model();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        try {
                            double x = Double.parseDouble(parts[1]);
                            double y = Double.parseDouble(parts[2]);
                            double z = Double.parseDouble(parts[3]);
                            model.addVertex(new Vertex(x, y, z));
                        } catch (NumberFormatException e) {
                            System.err.println("Ошибка при чтении вершины: " + line);
                        }
                    }
                } else if (line.startsWith("vt ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3) {
                        try {
                            double u = Double.parseDouble(parts[1]);
                            double v = Double.parseDouble(parts[2]);
                            model.addTextureCoordinate(new TextureCoordinate(u, v));
                        } catch (NumberFormatException e) {
                            System.err.println("Ошибка при чтении текстурной координаты: " + line);
                        }
                    }
                } else if (line.startsWith("f ")) {
                    String[] parts = line.split("\\s+");
                    List<Integer> vertexIndices = new ArrayList<>();
                    List<Integer> textureIndices = new ArrayList<>();
                    for (int i = 1; i < parts.length; i++) {
                        String[] vertexParts = parts[i].split("/");
                        if (vertexParts.length > 0 && !vertexParts[0].isEmpty()) {
                            vertexIndices.add(Integer.parseInt(vertexParts[0]) - 1);
                        }
                        if (vertexParts.length > 1 && !vertexParts[1].isEmpty()) {
                            textureIndices.add(Integer.parseInt(vertexParts[1]) - 1);
                        }
                    }
                    model.addFace(new Face(vertexIndices, textureIndices));
                }
            }
        }
        return model;
    }
}
