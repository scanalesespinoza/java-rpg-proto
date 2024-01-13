package org.scanales.rpg;

import java.awt.*;

public class Sword {
    private Player player;
    private int mouseX;
    private int mouseY;

    public Sword(Player player) {
        this.player = player;
    }

    public void updateMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void draw(Graphics g, int gridSize) {
        int playerX = player.getX() * gridSize + gridSize / 2;
        int playerY = player.getY() * gridSize + gridSize / 2;

        g.setColor(Color.RED);

        int deltaX = mouseX - playerX;
        int deltaY = mouseY - playerY;
        double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        int swordStartX = (int) (playerX + (deltaX / length) * (gridSize / 2));
        int swordStartY = (int) (playerY + (deltaY / length) * (gridSize / 2));

        int swordEndX = (int) (swordStartX + (deltaX / length) * (gridSize * 2));
        int swordEndY = (int) (swordStartY + (deltaY / length) * (gridSize * 2));

        g.drawLine(swordStartX, swordStartY, swordEndX, swordEndY);
    }

    public Rectangle getBounds(int gridSize) {
        int playerX = player.getX() * gridSize + gridSize / 2;
        int playerY = player.getY() * gridSize + gridSize / 2;

        int deltaX = mouseX - playerX;
        int deltaY = mouseY - playerY;
        double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        int swordStartX = (int) (playerX + (deltaX / length) * (gridSize / 2));
        int swordStartY = (int) (playerY + (deltaY / length) * (gridSize / 2));

        int swordEndX = (int) (swordStartX + (deltaX / length) * (gridSize * 10)); // Three times longer
        int swordEndY = (int) (swordStartY + (deltaY / length) * (gridSize * 10)); // Three times longer

        int x = Math.min(swordStartX, swordEndX);
        int y = Math.min(swordStartY, swordEndY);
        int width = Math.abs(swordEndX - swordStartX);
        int height = Math.abs(swordEndY - swordStartY);

        return new Rectangle(x, y, width, height);
    }
}
