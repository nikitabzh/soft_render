package com.cgvsu.render_engine;

import com.cgvsu.math.Vector3f;
import java.awt.Color;
import java.awt.Graphics;

public class Rasterizer {
    private int width, height;
    private float[][] zBuffer;

    public Rasterizer(int width, int height) {
        this.width = width;
        this.height = height;
        zBuffer = new float[width][height];
        clearZBuffer();
    }

    public void clearZBuffer() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                zBuffer[i][j] = Float.MAX_VALUE;
            }
        }
    }

    public void drawTriangle(Triangle tri, Color color, Graphics g) {
        // Получаем вершины треугольника
        Vector3f v1 = tri.v1;
        Vector3f v2 = tri.v2;
        Vector3f v3 = tri.v3;

        // Сортируем вершины по y (высоте)
        Vector3f[] vertices = {v1, v2, v3};
        sortVerticesByY(vertices);

        // Временные переменные для интерполяции
        Vector3f top = vertices[0];
        Vector3f mid = vertices[1];
        Vector3f bottom = vertices[2];

        // Растеризация треугольника — используем метод "сканирующей строки"
        for (int y = (int) top.y; y <= bottom.y; y++) {
            if (y < mid.y) {
                drawScanLine(g, y, top, mid, top, bottom, color);
            } else {
                drawScanLine(g, y, mid, bottom, top, bottom, color);
            }
        }
    }

    private void sortVerticesByY(Vector3f[] vertices) {
        // Сортировка по Y-координате
        java.util.Arrays.sort(vertices, (a, b) -> Float.compare(a.y, b.y));
    }

    private void drawScanLine(Graphics g, int y, Vector3f a, Vector3f b, Vector3f c, Vector3f d, Color color) {
        // Линейная интерполяция X-координат
        float x1 = interpolate(a.y, a.x, b.y, b.x, y);
        float x2 = interpolate(c.y, c.x, d.y, d.x, y);

        if (x1 > x2) {
            float temp = x1;
            x1 = x2;
            x2 = temp;
        }

        // Рисуем горизонтальную линию
        for (int x = (int) x1; x <= x2; x++) {
            g.setColor(color);
            g.drawLine(x, y, x, y);
        }
    }

    private float interpolate(float y1, float x1, float y2, float x2, float y) {
        // Линейная интерполяция X по Y
        if (y1 == y2) return x1; // Защита от деления на 0
        return x1 + (y - y1) * (x2 - x1) / (y2 - y1);
    }
}
