package org.example;

import javax.swing.*;

public class Main {
    private static void initWindow() {
        // TITLE BAR NAME
        String name = "Little Boy and Monsters GAME";

        JFrame window = new JFrame(name);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Board board = new Board();
        window.add(board);
        window.addKeyListener(board);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });
    }
}