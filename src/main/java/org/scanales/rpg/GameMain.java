package org.scanales.rpg;

import javax.swing.*;

public class GameMain {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Game");
                GamePanel gamePanel = new GamePanel();

                frame.add(gamePanel);
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}