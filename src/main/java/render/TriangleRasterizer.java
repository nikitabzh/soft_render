package render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Vector2;
import math.Vector3;

public class TriangleRasterizer {
    private final GraphicsContext gc;
    private final int width;
    private final int height;

    public TriangleRasterizer(GraphicsContext gc, int width, int height) {
        this.gc = gc;
        this.width = width;
        this.height = height;
    }

    public void drawTriangle(Vector3 v1, Vector3 v2, Vector3 v3, Color color, double[][] zBuffer) {
      int x1 = (int) Math.round(v1.getX());
      int y1 = (int) Math.round(v1.getY());
      double z1 = v1.getZ();

      int x2 = (int) Math.round(v2.getX());
      int y2 = (int) Math.round(v2.getY());
      double z2 = v2.getZ();

      int x3 = (int) Math.round(v3.getX());
      int y3 = (int) Math.round(v3.getY());
      double z3 = v3.getZ();

        // Вычисляем ограничивающий прямоугольник
      int minX = Math.max(0, Math.min(Math.min(x1, x2), x3));
      int minY = Math.max(0, Math.min(Math.min(y1, y2), y3));
      int maxX = Math.min(width - 1, Math.max(Math.max(x1, x2), x3));
      int maxY = Math.min(height - 1, Math.max(Math.max(y1, y2), y3));

        // Вычисляем барицентрические координаты для каждого пикселя в ограничивающем прямоугольнике
      for (int y = minY; y <= maxY; y++) {
        for (int x = minX; x <= maxX; x++) {
          double[] barycentric = computeBarycentricCoordinates(x, y, x1, y1, x2, y2, x3, y3);
          double u = barycentric[0];
          double v = barycentric[1];
          double w = barycentric[2];

          if (u >= 0 && v >= 0 && w >= 0) {
            double depth = u * z1 + v * z2 + w * z3;
            if(depth > zBuffer[x][y]){
                zBuffer[x][y] = depth;
                gc.setFill(color);
                gc.fillRect(x, y, 1, 1);
            }
          }
        }
      }
    }
    public void drawTriangle(Vector3 v1, Vector3 v2, Vector3 v3, Color color, double[][] zBuffer, Texture texture, Vector2 originalTextureV1, Vector2 originalTextureV2, Vector2 originalTextureV3) {
        int x1 = (int) Math.round(v1.getX());
        int y1 = (int) Math.round(v1.getY());
        double z1 = v1.getZ();

        int x2 = (int) Math.round(v2.getX());
        int y2 = (int) Math.round(v2.getY());
        double z2 = v2.getZ();

        int x3 = (int) Math.round(v3.getX());
        int y3 = (int) Math.round(v3.getY());
        double z3 = v3.getZ();

        // Вычисляем ограничивающий прямоугольник
        int minX = Math.max(0, Math.min(Math.min(x1, x2), x3));
        int minY = Math.max(0, Math.min(Math.min(y1, y2), y3));
        int maxX = Math.min(width - 1, Math.max(Math.max(x1, x2), x3));
        int maxY = Math.min(height - 1, Math.max(Math.max(y1, y2), y3));

        // Вычисляем барицентрические координаты для каждого пикселя в ограничивающем прямоугольнике
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                double[] barycentric = computeBarycentricCoordinates(x, y, x1, y1, x2, y2, x3, y3);
                double u = barycentric[0];
                double v = barycentric[1];
                double w = barycentric[2];

                if (u >= 0 && v >= 0 && w >= 0) {
                  double depth = u * z1 + v * z2 + w * z3;
                  if (depth > zBuffer[x][y]) {
                      zBuffer[x][y] = depth;
                      // Интерполируем UV-координаты для пикселя
                     double textureU = u * originalTextureV1.getX() + v * originalTextureV2.getX() + w * originalTextureV3.getX();
                      double textureV = u * originalTextureV1.getY() + v * originalTextureV2.getY() + w * originalTextureV3.getY();

                    Color textureColor = texture.getPixelColor(textureU, 1- textureV); //Инвертируем текстуру
                      gc.setFill(textureColor);
                      gc.fillRect(x, y, 1, 1);
                  }
                }
            }
        }
    }

    private double[] computeBarycentricCoordinates(double px, double py, double x1, double y1, double x2, double y2, double x3, double y3) {
        double detT = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
        double l1 = ((y2 - y3) * (px - x3) + (x3 - x2) * (py - y3)) / detT;
        double l2 = ((y3 - y1) * (px - x3) + (x1 - x3) * (py - y3)) / detT;
        double l3 = 1 - l1 - l2;
        return new double[]{l1, l2, l3};
    }
}