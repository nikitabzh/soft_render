package com.cgvsu;

import com.cgvsu.render_engine.Scene;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.math.Vector3f;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.model.Model;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Создание сцены, камеры и модели
        Scene scene = new Scene();
        Camera camera = new Camera(
                new Vector3f(0, 0, 5),  // Позиция камеры
                new Vector3f(0, 0, 0),  // Точка, на которую смотрит камера
                45,                    // Угол обзора (FOV)
                16.0f / 9.0f,          // Соотношение сторон
                0.1f, 100.0f           // Ближняя и дальняя плоскости
        );

        scene.addCamera(camera);

        // Загрузка модели
        Model model = new Model();
        // Здесь можно вызвать загрузку модели из файла
        // Например: model.loadFromFile("path/to/your/model.obj");

        // Создание канвы для отрисовки
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Основная группа и сцена JavaFX
        Group root = new Group();
        root.getChildren().add(canvas);

        // Используем полное имя класса для JavaFX Scene
        javafx.scene.Scene fxScene = new javafx.scene.Scene(root, 800, 600);

        primaryStage.setTitle("Simple 3D Viewer");
        primaryStage.setScene(fxScene);
        primaryStage.show();

        // Рендеринг модели
        render(gc, scene, model);
    }

    private void render(GraphicsContext gc, Scene scene, Model model) {
        // Очистка экрана
        gc.clearRect(0, 0, 800, 600);

        // Получаем активную камеру
        Camera activeCamera = scene.getActiveCamera();

        // Рендерим модель с выбранной камерой и режимом отрисовки
        RenderEngine.render(gc, activeCamera, model, RenderEngine.RenderMode.WIREFRAME);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
