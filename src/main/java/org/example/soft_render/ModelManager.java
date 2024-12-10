package org.example.soft_render;

import javafx.scene.Node;

public class ModelManager {

    private Model model;
    private final SceneManager sceneManager;

    public ModelManager() {
        this.sceneManager = new SceneManager();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void addModelToScene(Node modelNode) {
        sceneManager.addModel(modelNode);
    }

    public void removeModelFromScene(Node modelNode) {
        sceneManager.removeModel(modelNode);
    }

    public void transformModel(Node modelNode, double translateX, double translateY, double translateZ) {
        modelNode.setTranslateX(translateX);
        modelNode.setTranslateY(translateY);
        modelNode.setTranslateZ(translateZ);
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
