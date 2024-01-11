package org.scanales.rpg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements ActionListener, KeyListener, MouseMotionListener {
    private int gridSize = 30; // Size of each grid cell
    private int numRows = 10;  // Number of rows in the field
    private int numCols = 10;  // Number of columns in the field

    private int playerX = 0;   // Initial player X position
    private int playerY = 0;   // Initial player Y position

    private int mouseX = -1;   // Mouse X position (initialized to -1)
    private int mouseY = -1;   // Mouse Y position (initialized to -1)

    private Timer timer;

    public Game() {
        setPreferredSize(new Dimension(gridSize * numCols, gridSize * numRows));
        setBackground(Color.WHITE);

        // Set up a timer for animation (if needed)
        timer = new Timer(100, this);
        timer.start();

        addKeyListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle animation or game logic here (if needed)
        // Repaint the panel
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the field grid
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * gridSize, row * gridSize, gridSize, gridSize);
            }
        }

        // Draw the player
        g.setColor(Color.BLUE);
        g.fillRect(playerX * gridSize, playerY * gridSize, gridSize, gridSize);

        // Draw the sword (line) from player to mouse
        if (mouseX != -1 && mouseY != -1) {
            g.setColor(Color.RED);

            int playerCenterX = playerX * gridSize + gridSize / 2;
            int playerCenterY = playerY * gridSize + gridSize / 2;

            int deltaX = mouseX - playerCenterX;
            int deltaY = mouseY - playerCenterY;
            double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            int swordStartX = (int) (playerCenterX + (deltaX / length) * (gridSize / 2));
            int swordStartY = (int) (playerCenterY + (deltaY / length) * (gridSize / 2));

            int swordEndX = (int) (swordStartX + (deltaX / length) * (gridSize * 2));
            int swordEndY = (int) (swordStartY + (deltaY / length) * (gridSize * 2));

            g.drawLine(swordStartX, swordStartY, swordEndX, swordEndY);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle keyTyped events (if needed)
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle keyPressed events (e.g., player movement)
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (playerX > 0) {
                    playerX--;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (playerX < numCols - 1) {
                    playerX++;
                }
                break;
            case KeyEvent.VK_UP:
                if (playerY > 0) {
                    playerY--;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (playerY < numRows - 1) {
                    playerY++;
                }
                break;
        }

        repaint(); // Repaint the panel to show the updated player position
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle keyReleased events (if needed)
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Update the mouse coordinates
        mouseX = e.getX();
        mouseY = e.getY();
        repaint(); // Repaint the panel to update the sword position
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Handle mouseDragged events (if needed)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game");
            Game game = new Game();
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
