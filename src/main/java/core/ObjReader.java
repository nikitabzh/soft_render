// core/ObjReader.java
package core;

import math.Vector2;
import math.Vector3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjReader {
    public static Model read(String filePath) throws IOException {
        String modelName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
        Model model = new Model(modelName);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("v ")) {
                    addVertex(line, model);
                } else if (line.startsWith("vt ")) {
                    addTextureVertex(line, model);
                }
                else if (line.startsWith("f ")) {
                    addPolygon(line, model);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading OBJ file: " + e.getMessage());
        }
        return model;
    }
   private static void addTextureVertex(String line, Model model) {
        String[] parts = line.split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid texture vertex format in OBJ file");
        }
        try {
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            model.addTextureVertex(new Vector2(x, y));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid texture vertex coordinate format in OBJ file");
        }
    }

    private static void addVertex(String line, Model model) {
        String[] parts = line.split("\\s+");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid vertex format in OBJ file");
        }
        try {
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            model.addVertex(new Vector3(x, y, z));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid vertex coordinate format in OBJ file");
        }
    }


    private static void addPolygon(String line, Model model) {
        String[] parts = line.split("\\s+");
         if (parts.length < 4) {
                throw new IllegalArgumentException("Invalid polygon format in OBJ file");
         }
        List<Integer> polygon = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) {
             String[] vertexPart = parts[i].split("/");
            try {
                int vertexIndex = Integer.parseInt(vertexPart[0]) - 1;
                polygon.add(vertexIndex);
               if (vertexPart.length > 1){
                    int textureVertexIndex = Integer.parseInt(vertexPart[1]) - 1;
                    polygon.add(textureVertexIndex);
               }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid polygon vertex index in OBJ file");
            }
        }
        model.addPolygon(polygon);
    }
}