package org.scanales.rpg;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1534597519627531633L;
	private int score;

    public ScorePanel() {
        score = 0;
    }

    public void increaseScore() {
        score++;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 30);
    }
}
