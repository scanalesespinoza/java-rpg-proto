package org.scanales.rpg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
    private List<Enemy> enemies = new ArrayList<>();
    private ScorePanel scorePanel;
	private JLayeredPane layeredPane;

    public GamePanel() {
    	    	
        setPreferredSize(new Dimension(gridSize * numCols, gridSize * numRows));
        setBackground(Color.WHITE);

        player = new Player(numCols / 2, numRows / 2);
        sword = new Sword(player);

        // Create some initial enemies
        spawnEnemies(5); // Spawn 5 enemies initially

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

        // Create a layered pane and set it as the layout manager
        layeredPane = new JLayeredPane();
        setLayout(new BorderLayout());
        add(layeredPane);

        // Create and add the ScorePanel to the layered pane
        scorePanel = new ScorePanel();
        layeredPane.add(scorePanel, JLayeredPane.PALETTE_LAYER);
        scorePanel.setBounds(10, 10, 100, 30); // Adjust position and size as needed
 
    }

    // Add a method to increase the score when an enemy is killed
    public void increaseScore() {
        scorePanel.increaseScore();
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

    private void spawnEnemies(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int enemyX = random.nextInt(numCols * gridSize);
            int enemyY = random.nextInt(numRows * gridSize);
            int enemySize = 20; // Adjust the size as needed
            enemies.add(new Enemy(this, enemyX, enemyY, enemySize));
        }
    }

    private void handleSwordCollision() {
        Rectangle swordRect = sword.getBounds(gridSize);
        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && swordRect.intersects(new Rectangle(enemy.getX(), enemy.getY(), enemy.getSize(), enemy.getSize()))) {
                enemy.kill(); // Kill the enemy on sword collision
                increaseScore(); // Increase the score
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle animation or game logic here (if needed)
        handleSwordCollision(); // Check for sword collision with enemies
        // Repaint the panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the field grid
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                g.setColor(Color.WHITE);
                g.fillRect(col * gridSize, row * gridSize, gridSize, gridSize);
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * gridSize, row * gridSize, gridSize, gridSize);
            }
        }

        // Draw the player
        player.draw(g, gridSize);

        // Draw the sword
        if (sword != null) {
            g.setColor(Color.RED);

            int playerCenterX = player.getX() * gridSize + gridSize / 2;
            int playerCenterY = player.getY() * gridSize + gridSize / 2;

            int deltaX = mouseX - playerCenterX;
            int deltaY = mouseY - playerCenterY;
            double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            int swordStartX = (int) (playerCenterX + (deltaX / length) * (gridSize / 2));
            int swordStartY = (int) (playerCenterY + (deltaY / length) * (gridSize / 2));

            int swordEndX = (int) (swordStartX + (deltaX / length) * (gridSize * 30)); // Three times longer
            int swordEndY = (int) (swordStartY + (deltaY / length) * (gridSize * 30)); // Three times longer

            g.drawLine(swordStartX, swordStartY, swordEndX, swordEndY);
        }

        // Draw the enemies
        for (Enemy enemy : enemies) {
            enemy.draw(g, gridSize);
        }
    }
}
