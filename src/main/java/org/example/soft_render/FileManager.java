package org.example.soft_render;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

public class FileManager {

    private final ModelManager modelManager;

    public FileManager(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public void loadModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Model File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OBJ Files", "*.obj")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Model model = ObjReader.loadModel(selectedFile.getPath());
                modelManager.setModel(model);
            } catch (IOException e) {
                showErrorDialog("Error loading model", e.getMessage());
            }
        }
    }

    public void saveModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Model File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OBJ Files", "*.obj")
        );
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                ObjWriter.saveModel(modelManager.getModel(), selectedFile.getPath());
            } catch (IOException e) {
                showErrorDialog("Error saving model", e.getMessage());
            }
        }
    }

    private void showErrorDialog(String title, String message) {
        InterfaceManager interfaceManager = new InterfaceManager();
        interfaceManager.showErrorDialog(title, message);
    }
}
