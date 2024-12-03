package org.example.soft_render;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class UIManager {

    private final FileManager fileManager;

    public UIManager(FileManager fileManager) {
        this.fileManager = fileManager;
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
        // реализовать добавление модели к сцене
    }

    private void removeModel() {
        // реализовать удаление модели со сцены
    }

    private void transformModel() {
        // реализовать трансформирующую модель
    }
}
