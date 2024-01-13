package org.scanales.rpg;

import java.awt.*;
import javax.swing.ImageIcon;

import org.scanales.support.SoundManager;

public class Enemy {
    private int x;
    private int y;
    private int size;
    private boolean alive;
	private ImageIcon enemyIcon;
	private GamePanel gamePanel;
	private SoundManager soundManager;

    public Enemy(GamePanel gamePanel, int x, int y, int size) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.size = size;
        this.alive = true;
        // Load the player icon from the "images" folder using getResource
        enemyIcon = new ImageIcon(getClass().getResource("/images/zombie.gif"));
        
        // Resize the player icon to 50x50 pixels
        enemyIcon = new ImageIcon(enemyIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        
        soundManager = new SoundManager();
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public boolean isAlive() {
        return alive;
    }

    public void draw(Graphics g, int gridSize) {
        if (alive) {
            // Draw the enemy using the loaded image
            if (enemyIcon != null) {
                enemyIcon.paintIcon(null, g, x, y);
            }
        }
    }

    public void kill() {
        alive = false;
    }

    public boolean isHitBySword(Rectangle swordBounds) {
        Rectangle enemyBounds = new Rectangle(x, y, enemyIcon.getImage().getWidth(null), enemyIcon.getImage().getHeight(null));
        return enemyBounds.intersects(swordBounds);
    }
    
    public void playRandomSound() {
        if (isAlive()) {
            String[] soundFiles = {
                "resources/sounds/enemy_sound1.wav",
                "resources/sounds/enemy_sound2.wav",
                // Add more sound file paths as needed
            };
			soundManager.playRandomSound(soundFiles);
        }
    }
}
