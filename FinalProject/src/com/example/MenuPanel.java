package com.example;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	ShapeType shapeState;
	public int buttonWidth = 400;
	private Image background;
	public Color originalColor = Color.DARK_GRAY;
	boolean state1 = false;
	boolean state2 = false;
	boolean state3 = false;
	private Image previewSquareImage;
	private Image previewTriangleImage;
	private Image previewDiamondImage;

	
	public Rectangle playButton = new Rectangle(WIDTH/2 - buttonWidth/2 , HEIGHT/2 - 200, buttonWidth, 65);
	public Rectangle changeButton = new Rectangle(WIDTH/2 - buttonWidth/2 , HEIGHT/2 - 50, buttonWidth, 65);
	public Rectangle quitButton = new Rectangle(WIDTH/2 - buttonWidth/2 , HEIGHT/2 + 100, buttonWidth, 65);
	
	public MenuPanel() {
		shapeState = ShapeType.RECTANGLE;
		loadImage();
	}
	
	public void loadImage() {
		ImageIcon ii = new ImageIcon("src/com/example/menubackground.png");
		background = ii.getImage();
		ii = new ImageIcon("src/com/example/PlayerSquare_0.png");
		previewSquareImage = ii.getImage();
		ii = new ImageIcon("src/com/example/PlayerTriangle_0.png");
		previewTriangleImage = ii.getImage();
		ii = new ImageIcon("src/com/example/PlayerDiamond_0.png");
		previewDiamondImage = ii.getImage();
	}
	
	public void render(Graphics g, String playStatus) {
		Graphics2D g2d = (Graphics2D) g;

		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.drawImage(background, 0, 0, null);
		g.setFont(fnt0);
		g.setColor(Color.WHITE);
		g.drawString("Run Or Die", WIDTH / 2 - 135, 60);
		g.setColor(Color.GREEN);
		g.drawString("Run", WIDTH/2 - 135, 60);
		g.setColor(Color.RED);
		g.drawString("Die", WIDTH / 2 + 47, 60);
		
		if(state1 == false) {
			g2d.setColor(originalColor);
		}else {
			g2d.setColor(Color.LIGHT_GRAY);
		}
		g2d.fill(playButton);
		
		if(state2 == false) {
			g2d.setColor(originalColor);
		}else {
			g2d.setColor(Color.LIGHT_GRAY);
		}
		g2d.fill(changeButton);
		
		if(state3 == false) {
			g2d.setColor(originalColor);
		}else {
			g2d.setColor(Color.LIGHT_GRAY);
		}
		g2d.fill(quitButton);
		
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setColor(Color.WHITE);
		g.setFont(fnt1);
		if(playStatus == "Play") {
			g.drawString(playStatus, (int)(playButton.x + buttonWidth/2.5 + 5), playButton.y + 40);
		}else {
			g.drawString(playStatus, (int)(playButton.x + buttonWidth/3 + 5), playButton.y + 40);
		}
		g.drawString("Change Character Shape", changeButton.x + 19, changeButton.y + 40);
		g.setColor(Color.WHITE);
		g.drawString("Quit", (int)(quitButton.x + buttonWidth/2.5 + 5), quitButton.y + 40);
		
		
		if(getShapeState() == ShapeType.TRIANGLE) {
			g.drawImage(previewTriangleImage, WIDTH/2 - 40, HEIGHT/6, null);
		}else if(getShapeState() == ShapeType.RECTANGLE) {
			g.drawImage(previewSquareImage, WIDTH/2 - 40, HEIGHT/6, null);
		}else{
			g.drawImage(previewDiamondImage, WIDTH/2 - 40, HEIGHT/6, null);
		}
		
	}
	
	public void changeShape() {
		if(shapeState == ShapeType.TRIANGLE) {
			shapeState = ShapeType.RECTANGLE;
		}else if(shapeState == ShapeType.RECTANGLE) {
			shapeState = ShapeType.DIAMOND;
		}else if(shapeState == ShapeType.DIAMOND) {
			shapeState = ShapeType.TRIANGLE;
		}
	}
	
	public ShapeType getShapeState() {
		return shapeState;
	}
}

