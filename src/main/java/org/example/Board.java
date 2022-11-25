package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, KeyListener {
    private final int DELAY = 80;
    public static final int TILE_SIZE = 50;
    public static final int ROWS = 15;
    public static final int COLUMNS = 15;
    private static final long serialVersionUID = 490905409104883233L;
    private Timer timer;
    private int ENEMY_NUM = 1;

    private Player player;
    private ArrayList<Enemy> enemys;
    private ArrayList<Wall> walls;
    private Gate gate;
    private ArrayList<Point> wallsPos;

    public Board() {
        // set the game board size
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        // set the game board background color
        setBackground(new Color(232, 232, 232));

        // initialize the game state
        player = new Player();
        walls = CreateWalls();
        enemys = CreateEnemy();
        FindWallPosRand();
        CreateGate();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.tick();
        player.checkWall(walls); // Check wall

        // Check wall, bounded map and Random movement
        for(var enemy : enemys) {
            enemy.tick();
            enemy.RandomMovement();
            enemy.checkWall(walls);
        }

        CheckGameOver();
        CheckGameWin();

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawScore(g);


        for (var wall : walls) {
            wall.draw(g, this);
        }

        if (gate != null) {
            gate.draw(g, this);
        }

        for (var enemy : enemys) {
            enemy.draw(g, this);
        }

        player.draw(g, this);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // react to key down events
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position
                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE
                    );
                }
            }
        }
    }

    private void drawScore(Graphics g) {
        String text = "Score: " + player.getScore();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        g2d.setColor(new Color(30, 201, 139));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));

        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        Rectangle rect = new Rectangle(0, TILE_SIZE * (ROWS - 1), TILE_SIZE * COLUMNS, TILE_SIZE);

        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;

        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.drawString(text, x, y);
    }

    private ArrayList<Wall> CreateWalls() {
        var walls = new ArrayList<Wall>(){};

        for (int row = 2; row < ROWS - 1; row+=2) {
            for (int col = 0; col < COLUMNS; col+=1) {
                Random rand = new Random();
                int col_axis = rand.nextInt(14 - 0 + 1) + 0;
                walls.add(new Wall(col_axis, row));
            }
        }

        int idx = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                walls.add(new Wall(col, idx));
            }
            idx = ROWS-1;
        }

        idx = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                walls.add(new Wall(0, row));
                walls.add(new Wall(COLUMNS-1, row));
            }
            idx = ROWS-1;
        }

        return walls;
    }

    private void CreateGate() {
        Random rand = new Random();
        int x = rand.nextInt(13 - 1 + 1) + 1;

        gate = new Gate(x, 13);
    }

    private void FindWallPosRand() {
        var pos = new ArrayList<Point>(){};

        for (var wall : walls) {
            pos.add(wall.getPos());
        }

        wallsPos = pos;
    }

    private ArrayList<Enemy> CreateEnemy() {
        var enemy = new ArrayList<Enemy>(){};

        // CHANGE ENEMY SKIN WITH VARIABLE
        String enemy1 = "Enemy1"; // Name file
        String enemy2 = "Enemy2";
        String enemy3 = "Enemy3";


        // ROW1
        enemy.add(new Enemy(1, 3, enemy1));
        enemy.add(new Enemy(13, 3, enemy2));

        // ROW2
        enemy.add(new Enemy(1, 5, enemy2));
        enemy.add(new Enemy(3, 5, enemy3));

        // ROW3
        enemy.add(new Enemy(2, 7, enemy1));
        enemy.add(new Enemy(8, 7, enemy3));

        // ROW4
        enemy.add(new Enemy(5, 9, enemy1));
        enemy.add(new Enemy(10, 9, enemy2));

        // ROW5
        enemy.add(new Enemy(5, 11, enemy3));
        enemy.add(new Enemy(8, 11, enemy2));

        // ROW6
        enemy.add(new Enemy(1, 13, enemy1));

        return enemy;
    }

    private void CheckGameOver() {
        for (var enemy : enemys) {
            if (player.getPos().equals(enemy.getPos())) {
                JOptionPane.showMessageDialog(null, "Game Over");
                this.setVisible(false);
                System.exit(0);
                break;
            }
        }
    }

    private void CheckGameWin() {
        if (player.getPos().equals(gate.getPos())) {
            JOptionPane.showMessageDialog(null, "Game Win");
            this.setVisible(false);
            System.exit(0);
        }
    }
}