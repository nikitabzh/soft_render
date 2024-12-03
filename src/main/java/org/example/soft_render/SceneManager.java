package org.example.soft_rend;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

public class SceneManager {

    private final Group sceneRoot;

    public SceneManager() {
        sceneRoot = new Group();
    }

    public void addModel(Node model) {
        sceneRoot.getChildren().add(model);
    }

    public void removeModel(Node model) {
        sceneRoot.getChildren().remove(model);
    }

    public Group getSceneRoot() {
        return sceneRoot;
    }

    // пример метода для создания простой модели
    public Node createSampleModel() {
        Box box = new Box(100, 100, 100);
        box.setMaterial(new javafx.scene.paint.PhongMaterial(Color.BLUE));
        return box;
    }
}
