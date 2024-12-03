package org.example.soft_render;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    private ModelManager modelManager;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("3D Model Viewer");

        BorderPane root = new BorderPane();
        MenuBar menuBar = createMenuBar();
        VBox sidePanel = createSidePanel();
        modelManager = new ModelManager();

        root.setTop(menuBar);
        root.setLeft(sidePanel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem loadModelItem = new MenuItem("Load Model");
        MenuItem saveModelItem = new MenuItem("Save Model");
        MenuItem exitItem = new MenuItem("Exit");

        loadModelItem.setOnAction(e -> loadModel());
        saveModelItem.setOnAction(e -> saveModel());
        exitItem.setOnAction(e -> System.exit(0));

        fileMenu.getItems().addAll(loadModelItem, saveModelItem, exitItem);
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    private VBox createSidePanel() {
        VBox sidePanel = new VBox();
        sidePanel.setSpacing(10);

        Button addModelButton = new Button("Add Model");
        Button removeModelButton = new Button("Remove Model");
        Button transformModelButton = new Button("Transform Model");

        addModelButton.setOnAction(e -> addModel());
        removeModelButton.setOnAction(e -> removeModel());
        transformModelButton.setOnAction(e -> transformModel());

        sidePanel.getChildren().addAll(addModelButton, removeModelButton, transformModelButton);

        return sidePanel;
    }

    private void loadModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Model File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OBJ Files", "*.obj")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                modelManager.loadModel(selectedFile.getPath());
            } catch (IOException e) {
                showErrorDialog("Error loading model", e.getMessage());
            }
        }
    }

    private void saveModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Model File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OBJ Files", "*.obj")
        );
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                modelManager.saveModel(selectedFile.getPath());
            } catch (IOException e) {
                showErrorDialog("Error saving model", e.getMessage());
            }
        }
    }

    private void addModel() {
        // Реализовать добавление модели к сцене
    }

    private void removeModel() {
        // Реализовать удаление модели со сцены
    }

    private void transformModel() {
        // Внедрить трансформирующую модель
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}