package org.example.soft_render;

import java.io.IOException;

public class ModelManager {

    public void loadModel(String filePath) throws IOException {
        ObjReader.loadModel(filePath);
    }

    public void saveModel(String filePath) throws IOException {
        ObjWriter.saveModel(filePath);
    }
}
