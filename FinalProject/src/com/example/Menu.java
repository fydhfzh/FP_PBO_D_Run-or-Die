package com.ROD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
	
	public Rectangle playButton = new Rectangle(Game.WIDTH / 2+ 120, 150, 100, 50);
	public Rectangle changeButton = new Rectangle(Game.WIDTH / 2 +120, 250,100, 50);
	public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 +120, 350,100, 50);
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawing("RUN Or Die", Game.WIDTH / 2, 100);
		
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		g.drawingString("Play", playButton.x + 19, playButton.y + 30);
		g2d.draw(playButton);
		g.drawingString("Change Character Shape", helpButton.x + 19, helpButton.y + 30);
		g2d.draw(changeButton);
		g.drawingString("Quit", quitButton.x + 19, quitButton.y + 30);
		g2d.draw(quitButton);
		
		g2D.draw(playButton);
		g2D.draw(changeButton);
		g2D.draw(quitButton);
	}
}
