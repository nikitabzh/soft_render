package com.cgvsu.render_engine;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Texture {
    private BufferedImage image;

    public Texture(String path) throws IOException {
        image = ImageIO.read(new File(path));
    }

    public Color getColor(float u, float v) {
        int x = Math.min((int) (u * image.getWidth()), image.getWidth() - 1);
        int y = Math.min((int) (v * image.getHeight()), image.getHeight() - 1);
        return new Color(image.getRGB(x, y));
    }
}