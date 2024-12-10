package org.example.soft_render;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;

public class MainApp extends Application {

    private static final double CAMERA_INITIAL_DISTANCE = -1000;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;

    private PerspectiveCamera camera;
    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("3D Model Viewer");

        BorderPane root = new BorderPane();
        ModelManager modelManager = new ModelManager();
        FileManager fileManager = new FileManager(modelManager);
        UIManager uiManager = new UIManager(fileManager, modelManager);

        root.setTop(uiManager.createMenuBar());
        root.setLeft(uiManager.createSidePanel());

        SubScene subScene = new SubScene(modelManager.getSceneManager().getSceneRoot(), 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTGRAY);
        root.setCenter(subScene);

        camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                new Rotate(-30, Rotate.Y_AXIS),
                new Rotate(-30, Rotate.X_AXIS),
                new Translate(0, 0, CAMERA_INITIAL_DISTANCE)
        );
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setFieldOfView(20);
        subScene.setCamera(camera);

        subScene.setOnMousePressed(this::handleMousePressed);
        subScene.setOnMouseDragged(this::handleMouseDragged);
        subScene.setOnScroll(this::handleMouseScroll);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Устанавливаем окно на максимальный размер экрана
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        primaryStage.show();
    }

    private void handleMousePressed(MouseEvent event) {
        mousePosX = event.getSceneX();
        mousePosY = event.getSceneY();
        mouseOldX = event.getSceneX();
        mouseOldY = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        mouseOldX = mousePosX;
        mouseOldY = mousePosY;
        mousePosX = event.getSceneX();
        mousePosY = event.getSceneY();
        double mouseDeltaX = (mousePosX - mouseOldX);
        double mouseDeltaY = (mousePosY - mouseOldY);

        double modifier = 1.0;
        double modifierFactor = 0.1;

        if (event.isControlDown()) {
            modifier = 0.1;
        }
        if (event.isShiftDown()) {
            modifier = 10.0;
        }
        if (event.isPrimaryButtonDown()) {
            camera.getTransforms().add(new Rotate(
                    -modifier * modifierFactor * mouseDeltaX * ROTATION_SPEED,
                    Rotate.Y_AXIS
            ));
            camera.getTransforms().add(new Rotate(
                    modifier * modifierFactor * mouseDeltaY * ROTATION_SPEED,
                    Rotate.X_AXIS
            ));
        } else if (event.isSecondaryButtonDown()) {
            double z = camera.getTranslateZ();
            double newZ = z + modifier * modifierFactor * mouseDeltaX * TRACK_SPEED;
            camera.setTranslateZ(newZ);
        }
    }

    private void handleMouseScroll(ScrollEvent event) {
        double delta = event.getDeltaY();
        camera.setTranslateZ(camera.getTranslateZ() + delta);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
