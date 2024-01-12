package org.scanales.rpg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class GamePanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private int gridSize = 10;
    private int numRows = 90;
    private int numCols = 160;
    private int playerSpeed = 1;

    private Player player;
    private Sword sword;
    private int mouseX = -1;
    private int mouseY = -1;
    private Set<Integer> pressedKeys = new HashSet<>();
    private Timer keyRepeatTimer;

    public GamePanel() {
        setPreferredSize(new Dimension(gridSize * numCols, gridSize * numRows));
        setBackground(Color.WHITE);

        player = new Player(numCols / 2, numRows / 2);
        sword = new Sword(player);

        // Add key press and release listeners
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyRelease(e);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePress(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMotion(e);
            }
        });

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Add a component listener to handle window resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResize();
            }
        });

        // Set up a timer for animation (if needed)
        Timer timer = new Timer(16, this); // 16ms delay for ~60 FPS
        timer.start();

        // Set up a timer for key repeats
        keyRepeatTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleKeyRepeat();
            }
        });
        keyRepeatTimer.setInitialDelay(100);
        keyRepeatTimer.setRepeats(true);
        keyRepeatTimer.start();
    }

    private void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        pressedKeys.add(keyCode);
        handleKeyChange();
    }

    private void handleKeyRelease(KeyEvent e) {
        int keyCode = e.getKeyCode();
        pressedKeys.remove(keyCode);
        handleKeyChange();
    }

    private void handleKeyChange() {
        int dx = 0;
        int dy = 0;

        if (pressedKeys.contains(KeyEvent.VK_A)) {
            dx = -playerSpeed;
        } else if (pressedKeys.contains(KeyEvent.VK_D)) {
            dx = playerSpeed;
        }

        if (pressedKeys.contains(KeyEvent.VK_W)) {
            dy = -playerSpeed;
        } else if (pressedKeys.contains(KeyEvent.VK_S)) {
            dy = playerSpeed;
        }

        player.move(dx, dy);
        sword.updateMousePosition(mouseX, mouseY);
        repaint();
    }

    private void handleKeyRepeat() {
        if (!pressedKeys.isEmpty()) {
            handleKeyChange();
        }
    }

    private void handleMousePress(MouseEvent e) {
        // Handle mouse click events (if needed)
    }

    private void handleMouseMotion(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        sword.updateMousePosition(mouseX, mouseY);
        repaint();
    }

    private void handleResize() {
        // Adjust the number of rows and columns based on the panel size
        int newWidth = getWidth();
        int newHeight = getHeight();

        numCols = newWidth / gridSize;
        numRows = newHeight / gridSize;

        repaint(); // Repaint the panel to update the grid
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle animation or game logic here (if needed)
        // Repaint the panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the field grid
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * gridSize, row * gridSize, gridSize, gridSize);
            }
        }

        // Draw the player
        player.draw(g, gridSize);

        // Draw the sword
        sword.draw(g, gridSize);
    }
}
