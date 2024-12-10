package org.example.soft_render;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

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

    // Метод для создания модели на основе данных из Model
    public Node createModelNode(Model model) {
        TriangleMesh mesh = new TriangleMesh();

        for (Vertex vertex : model.vertices) {
            mesh.getPoints().addAll(
                    (float) vertex.x, (float) vertex.y, (float) vertex.z
            );
        }

        for (Face face : model.faces) {
            for (int i = 0; i < face.vertexIndices.size() - 2; i++) {
                mesh.getFaces().addAll(
                        face.vertexIndices.get(0), face.textureIndices.get(0),
                        face.vertexIndices.get(i + 1), face.textureIndices.get(i + 1),
                        face.vertexIndices.get(i + 2), face.textureIndices.get(i + 2)
                );
            }
        }

        MeshView meshView = new MeshView(mesh);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLUE);
        meshView.setMaterial(material);
        return meshView;
    }
}
