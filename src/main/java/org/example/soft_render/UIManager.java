package org.example.soft_render;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.Node;

public class UIManager {

    private final FileManager fileManager;
    private final ModelManager modelManager;

    public UIManager(FileManager fileManager, ModelManager modelManager) {
        this.fileManager = fileManager;
        this.modelManager = modelManager;
    }

    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem loadModelItem = new MenuItem("Load Model");
        MenuItem saveModelItem = new MenuItem("Save Model");
        MenuItem exitItem = new MenuItem("Exit");

        loadModelItem.setOnAction(e -> fileManager.loadModel());
        saveModelItem.setOnAction(e -> fileManager.saveModel());
        exitItem.setOnAction(e -> System.exit(0));

        fileMenu.getItems().addAll(loadModelItem, saveModelItem, exitItem);
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    public VBox createSidePanel() {
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

    private void addModel() {
        // Убрать добавление тестовой модели
    }

    private void removeModel() {
        // Пример удаления последней добавленной модели
        if (!modelManager.getSceneManager().getSceneRoot().getChildren().isEmpty()) {
            Node modelNode = modelManager.getSceneManager().getSceneRoot().getChildren().get(0);
            modelManager.removeModelFromScene(modelNode);
        }
    }

    private void transformModel() {
        // Пример трансформации последней добавленной модели
        if (!modelManager.getSceneManager().getSceneRoot().getChildren().isEmpty()) {
            Node modelNode = modelManager.getSceneManager().getSceneRoot().getChildren().get(0);
            modelManager.transformModel(modelNode, 50, 50, 50);
        }
    }
}
