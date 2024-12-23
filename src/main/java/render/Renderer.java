package render;

import core.Camera;
import core.Model;
import core.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Matrix4;
import math.Vector2;
import math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final double[][] zBuffer;
    private final int width;
    private final int height;
    private final TriangleRasterizer triangleRasterizer;
    private boolean lightingEnabled;
    private boolean textureEnabled;

    public Renderer(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.width = (int) canvas.getWidth();
        this.height = (int) canvas.getHeight();
        this.zBuffer = new double[width][height];
        triangleRasterizer = new TriangleRasterizer(gc, width, height);
        this.lightingEnabled = false;
      this.textureEnabled = false;
    }


    public void setLightingEnabled(boolean lightingEnabled) {
        this.lightingEnabled = lightingEnabled;
    }
    public void setTextureEnabled(boolean textureEnabled) {
      this.textureEnabled = textureEnabled;
    }

    public void render(Scene scene, Camera camera) {
        clearCanvas();
        resetZBuffer();

        Matrix4 viewMatrix = camera.getViewMatrix();

        for (int modelIndex = 0; modelIndex < scene.getModels().size(); modelIndex++) {
            Model model = scene.getModels().get(modelIndex);
            Matrix4 modelMatrix = model.getTransform().getModelMatrix();

            boolean isSelected = scene.isSelected(modelIndex);
            Color modelColor = isSelected ? Color.RED : Color.GRAY;
            Texture texture = model.getTexture();

            for (List<Integer> polygon : model.getPolygons()) {
                List<Vector3> transformedVertices = new ArrayList<>();
                 List<Vector3> originalVertices = new ArrayList<>();
                List<Vector2> originalTextureVertices = new ArrayList<>();

                for(int vertexIndex = 0; vertexIndex < polygon.size(); vertexIndex++){
                   if(vertexIndex % 2 == 0) {
                        Vector3 vertex = model.getVertices().get(polygon.get(vertexIndex));
                         Vector3 transformedVertex = viewMatrix.transform(modelMatrix.transform(new Vector3(vertex.getX(), -vertex.getY(), vertex.getZ())));
                      transformedVertices.add(transformedVertex);
                        originalVertices.add(vertex);
                     } else {
                        Vector2 textureVertex = model.getTextureVertices().get(polygon.get(vertexIndex));
                       originalTextureVertices.add(textureVertex);
                    }
                }


                if(transformedVertices.size() == 3){
                  drawTriangle(transformedVertices.get(0), transformedVertices.get(1), transformedVertices.get(2), originalVertices.get(0), originalVertices.get(1), originalVertices.get(2), originalTextureVertices.get(0), originalTextureVertices.get(1), originalTextureVertices.get(2), modelColor, texture);
                }else if(transformedVertices.size() > 3){
                    for (int i = 1; i < transformedVertices.size() - 1; i++) {
                       drawTriangle(transformedVertices.get(0), transformedVertices.get(i), transformedVertices.get(i + 1), originalVertices.get(0), originalVertices.get(i), originalVertices.get(i+1), originalTextureVertices.get(0), originalTextureVertices.get(i), originalTextureVertices.get(i+1), modelColor, texture);
                   }
                }
            }
        }
    }
    private void drawTriangle(Vector3 v1, Vector3 v2, Vector3 v3, Vector3 originalV1, Vector3 originalV2, Vector3 originalV3, Vector2 originalTextureV1, Vector2 originalTextureV2, Vector2 originalTextureV3, Color color, Texture texture) {
        Vector3 screenV1 = transformToScreenCoordinates(v1);
        Vector3 screenV2 = transformToScreenCoordinates(v2);
        Vector3 screenV3 = transformToScreenCoordinates(v3);

        if(lightingEnabled){
            Color lightColor = calculateLighting(screenV1, screenV2, screenV3, originalV1, originalV2, originalV3, color);
            if (textureEnabled && texture != null){
                triangleRasterizer.drawTriangle(screenV1, screenV2, screenV3, lightColor, zBuffer, texture, originalTextureV1, originalTextureV2, originalTextureV3);
            } else {
                triangleRasterizer.drawTriangle(screenV1, screenV2, screenV3, lightColor, zBuffer);
            }
        }
        else {
            if (textureEnabled && texture != null){
                triangleRasterizer.drawTriangle(screenV1, screenV2, screenV3, color, zBuffer, texture, originalTextureV1, originalTextureV2, originalTextureV3);
            } else {
                triangleRasterizer.drawTriangle(screenV1, screenV2, screenV3, color, zBuffer);
          }
        }
    }
  private Color calculateLighting(Vector3 screenV1, Vector3 screenV2, Vector3 screenV3, Vector3 originalV1, Vector3 originalV2, Vector3 originalV3, Color baseColor){
      Vector3 normal = originalV2.subtract(originalV1).crossProduct(originalV3.subtract(originalV1)).normalize();

       Vector3 lightDir = new Vector3(0,0, 1).normalize();
        double intensity = Math.max(0, lightDir.dotProduct(normal));
        double ambientIntensity = 0.4;
        intensity = ambientIntensity + (1 - ambientIntensity) * intensity;

        return baseColor.deriveColor(0, 1, intensity, 1);
    }


    private void clearCanvas() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void resetZBuffer() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                zBuffer[x][y] = Double.NEGATIVE_INFINITY;
            }
        }
    }
  private Vector3 transformToScreenCoordinates(Vector3 vertex) {
      double fov = 60;
      double aspectRatio = (double) width / height;
      double near = 0.1;
      double far = 1000;

      double fovScale = 1 / Math.tan(Math.toRadians(fov / 2));
      double projX = vertex.getX() * fovScale * aspectRatio;
      double projY = vertex.getY() * fovScale;
      double projZ = (vertex.getZ() * (far + near)) / (far - near) - (2 * far * near) / (far - near);

        double screenX = (projX / (-vertex.getZ()) + 1) * width / 2;
        double screenY = (projY / (-vertex.getZ()) + 1) * height / 2;
        return new Vector3(screenX, screenY, projZ);
    }
}