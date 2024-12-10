package com.cgvsu.render_engine;

import com.cgvsu.model.Model;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class RenderEngine {
    public enum RenderMode {
        WIREFRAME, TEXTURE, LIGHTING, STATIC_COLOR
    }

    public static void render(GraphicsContext gc, Camera camera, Model model, RenderMode mode) {
        // Логика для переключения режимов
        switch (mode) {
            case WIREFRAME:
                drawWireframe(model, gc);
                break;
            case TEXTURE:
                drawTexturedTriangles(model, gc);
                break;
            case LIGHTING:
                drawLitTriangles(model, gc);
                break;
            case STATIC_COLOR:
                drawStaticColor(model, gc, Color.GRAY);
                break;
        }
    }

    private static void drawWireframe(Model model, GraphicsContext gc) {
        // Отрисовка каркаса
    }

    private static void drawTexturedTriangles(Model model, GraphicsContext gc) {
        // Отрисовка с текстурами
    }

    private static void drawLitTriangles(Model model, GraphicsContext gc) {
        // Отрисовка с освещением
    }

    private static void drawStaticColor(Model model, GraphicsContext gc, Color color) {
        // Отрисовка с одним цветом
    }
}