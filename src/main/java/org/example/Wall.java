package org.example;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Wall {
    private BufferedImage image;
    private Point pos;

    public Wall(int x, int y) {
        LoadImage();
        pos = new Point(x, y);
    }

    private void LoadImage() {
        try {
            image = ImageIO.read(new File("src/main/java/org/example/assets/Wall.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(
                image,
                pos.x * Board.TILE_SIZE,
                pos.y * Board.TILE_SIZE,
                observer
        );
    }

    public Point getPos() {
        return pos;
    }
}
