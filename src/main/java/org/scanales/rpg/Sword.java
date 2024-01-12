package org.scanales.rpg;

import java.awt.*;
import java.awt.geom.Line2D;

public class Sword {
    private Player player;
    private int mouseX;
    private int mouseY;

    public Sword(Player player) {
        this.player = player;
        this.mouseX = player.getX();
        this.mouseY = player.getY();
    }

    public void updateMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void draw(Graphics g, int gridSize) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);

        int playerCenterX = player.getX() * gridSize + gridSize / 2;
        int playerCenterY = player.getY() * gridSize + gridSize / 2;

        int deltaX = mouseX - playerCenterX;
        int deltaY = mouseY - playerCenterY;

        int swordEndX = playerCenterX + deltaX;
        int swordEndY = playerCenterY + deltaY;

        Line2D swordLine = new Line2D.Double(playerCenterX, playerCenterY, swordEndX, swordEndY);
        g2d.draw(swordLine);
    }
}
