package org.example.soft_render;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ObjWriter {

    public static void saveModel(String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Записывать данные модели в OBJ-файл
            bw.write("Sample model data");
        }
    }
}
