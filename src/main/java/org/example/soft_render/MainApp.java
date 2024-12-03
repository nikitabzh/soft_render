package org.example.soft_render;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("3D Model Viewer");

        BorderPane root = new BorderPane();
        ModelManager modelManager = new ModelManager();
        FileManager fileManager = new FileManager(modelManager);
        UIManager uiManager = new UIManager(fileManager);

        root.setTop(uiManager.createMenuBar());
        root.setLeft(uiManager.createSidePanel());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
