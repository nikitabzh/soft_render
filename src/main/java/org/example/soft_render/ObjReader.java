package org.example.soft_render;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ObjReader {

    public static void loadModel(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // парсить OBJ файл и загружать модель
                System.out.println(line);
            }
        }
    }
}
