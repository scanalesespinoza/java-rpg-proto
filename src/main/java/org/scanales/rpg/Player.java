package org.scanales.rpg;

import javax.swing.*;
import java.awt.*;

public class Player {
    private int x;
    private int y;
    private ImageIcon playerIcon;

    public Player(int initialX, int initialY) {
        x = initialX;
        y = initialY;
        // Load the player icon from the "images" folder using getResource
        String imagePath = getClass().getClassLoader().getResource("images/player.gif").getPath();
        playerIcon = new ImageIcon(imagePath);

        // Resize the player icon to 50x50 pixels
        playerIcon = new ImageIcon(playerIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g, int gridSize) {
        if (playerIcon != null) {
            playerIcon.paintIcon(null, g, x * gridSize - (50 - gridSize) / 2, y * gridSize - (50 - gridSize) / 2);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x * gridSize, y * gridSize, gridSize, gridSize);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
