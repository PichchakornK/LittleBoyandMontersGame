package org.example;

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
     private BufferedImage image;
     private Point pos;
     private int score;
     private int key_val;

     public Player() {
          LoadImage();

          pos = new Point(1,1);
          score = 0;
     }

     private void LoadImage() {
          try {
               image = ImageIO.read(new File("src/main/java/org/example/assets/Player.png"));
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

     public void keyPressed(KeyEvent e) {
          int key = e.getKeyCode();
          key_val = key;

          if (key == KeyEvent.VK_UP) {
               pos.translate(0, -1);
          }
          if (key == KeyEvent.VK_RIGHT) {
               pos.translate(1, 0);
          }
          if (key == KeyEvent.VK_DOWN) {
               pos.translate(0, 1);
          }
          if (key == KeyEvent.VK_LEFT) {
               pos.translate(-1, 0);
          }
     }

     public void tick() {
          if (pos.x < 0) {
               pos.x = 0;
          } else if (pos.x >= Board.COLUMNS) {
               pos.x = Board.COLUMNS - 1;
          }

          if (pos.y < 0) {
               pos.y = 0;
          } else if (pos.y >= Board.ROWS) {
               pos.y = Board.ROWS - 1;
          }
     }

     public void checkWall(ArrayList<Wall> walls) {
          for (var wall : walls) {
               if (pos.x == wall.getPos().x && pos.y == wall.getPos().y) {
                    if (key_val == 39) pos.x -= 1; // Right
                    if (key_val == 37) pos.x += 1; // Left
                    if (key_val == 38) pos.y += 1; // Up
                    if (key_val == 40) pos.y -= 1; // Down
               }
          }
     }

     public String getScore() {
          return String.valueOf(score);
     }

     public void addScore(int amount) {
          score += amount;
     }

     public Point getPos() {
          return pos;
     }
}