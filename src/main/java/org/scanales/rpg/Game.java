package org.scanales.rpg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.io.File;

public class Game extends JPanel implements ActionListener, KeyListener, MouseMotionListener, ComponentListener {
    private int gridSize = 10; // Size of each grid cell (changed to 10)
    private int numRows = 90;  // Number of rows in the field
    private int numCols = 160; // Number of columns in the field

    private int playerX = numCols / 2;   // Initial player X position (centered)
    private int playerY = numRows / 2;   // Initial player Y position (centered)

    private int mouseX = -1;   // Mouse X position (initialized to -1)
    private int mouseY = -1;   // Mouse Y position (initialized to -1)

    private Timer timer;

    private int playerSpeed = 1; // Player speed set to 1

    private ImageIcon playerIcon;

    public Game() {
        setPreferredSize(new Dimension(gridSize * numCols, gridSize * numRows));
        setBackground(Color.WHITE);

        // Set up a timer for animation (if needed)
        timer = new Timer(16, this); // 16ms delay for ~60 FPS
        timer.start();

        addKeyListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addComponentListener(this); // Register a component listener for window resizing

        // Load the player icon from the "images" folder using getResource
        String imagePath = getClass().getClassLoader().getResource("images/player.gif").getPath();
        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            // Resize the player icon to 50x50 pixels
            playerIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } else {
            System.err.println("Player image not found: " + imagePath);
        }
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

        // Draw the player (adjusted for centering)
        if (playerIcon != null) {
            playerIcon.paintIcon(this, g, playerX * gridSize - (50 - gridSize) / 2, playerY * gridSize - (50 - gridSize) / 2);
        } else {
            // Fallback to drawing a blue rectangle if the image is not found
            g.setColor(Color.BLUE);
            g.fillRect(playerX * gridSize, playerY * gridSize, gridSize, gridSize);
        }

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

        boolean moveHorizontal = false;
        boolean moveVertical = false;

        switch (keyCode) {
            case KeyEvent.VK_A:
                if (playerX > 0) {
                    playerX -= playerSpeed;
                    moveHorizontal = true;
                }
                break;
            case KeyEvent.VK_D:
                if (playerX < numCols - 1) {
                    playerX += playerSpeed;
                    moveHorizontal = true;
                }
                break;
            case KeyEvent.VK_W:
                if (playerY > 0) {
                    playerY -= playerSpeed;
                    moveVertical = true;
                }
                break;
            case KeyEvent.VK_S:
                if (playerY < numRows - 1) {
                    playerY += playerSpeed;
                    moveVertical = true;
                }
                break;
        }

        // Handle diagonal movement
        if (moveHorizontal && moveVertical) {
            // You can adjust playerSpeedDiagonal to control diagonal movement speed
            int playerSpeedDiagonal = playerSpeed / 2;

            if (keyCode == KeyEvent.VK_A) {
                if (playerX > 0) {
                    playerX -= playerSpeedDiagonal;
                }
            } else if (keyCode == KeyEvent.VK_D) {
                if (playerX < numCols - 1) {
                    playerX += playerSpeedDiagonal;
                }
            } else if (keyCode == KeyEvent.VK_W) {
                if (playerY > 0) {
                    playerY -= playerSpeedDiagonal;
                }
            } else if (keyCode == KeyEvent.VK_S) {
                if (playerY < numRows - 1) {
                    playerY += playerSpeedDiagonal;
                }
            }
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

    @Override
    public void componentResized(ComponentEvent e) {
        // Adjust the number of rows and columns based on the panel size
        int newWidth = getWidth();
        int newHeight = getHeight();

        numCols = newWidth / gridSize;
        numRows = newHeight / gridSize;

        repaint(); // Repaint the panel to update the grid
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        // Handle componentMoved events (if needed)
    }

    @Override
    public void componentShown(ComponentEvent e) {
        // Handle componentShown events (if needed)
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // Handle componentHidden events (if needed)
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
