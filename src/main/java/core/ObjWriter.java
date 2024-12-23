package core;

import math.Vector3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ObjWriter {

    public static void write(String filePath, Model model, boolean withTransformations) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writeVertices(writer, model.getVertices());
            writePolygons(writer, model.getPolygons());
            System.out.println("Model saved successfully to " + filePath);
        }
        catch (IOException e){
            throw new IOException("Error writing to file " + filePath, e);
        }
    }

    private static void writeVertices(BufferedWriter writer, List<Vector3> vertices) throws IOException {
        for (Vector3 vertex : vertices) {
            writer.write(String.format("v %.6f %.6f %.6f\n", vertex.getX(), vertex.getY(), vertex.getZ()));
        }
    }

    private static void writePolygons(BufferedWriter writer, List<List<Integer>> polygons) throws IOException {
        for (List<Integer> polygon : polygons) {
            writer.write("f");
            for (int vertexIndex : polygon) {
                writer.write(" " + (vertexIndex + 1));
            }
            writer.write("\n");
        }
    }
}