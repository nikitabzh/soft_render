package render;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Texture {
    private final Image image;

    public Texture(Image image) {
        this.image = image;
    }

    public Color getPixelColor(double u, double v) {
        if (image == null) return Color.WHITE;
      
        int x = (int) (u * image.getWidth());
        int y = (int) (v * image.getHeight());

        if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
            return Color.WHITE;
        }
        return image.getPixelReader().getColor(x, y);
    }
}