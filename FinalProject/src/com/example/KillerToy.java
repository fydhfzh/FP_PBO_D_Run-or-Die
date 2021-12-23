package com.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class KillerToy {
	public static int WIDTH = 1000;
	public static int HEIGHT = 1000;
	public Image toyImage;
	private ToyState toyState;
	public Position pos;
	
	public KillerToy() {
		this.toyState = ToyState.COUNTING;
		this.pos = new Position(WIDTH / 2, 75);
		loadImage();
	}
	
	public boolean checkMoving(Element movingChar) {
		if(!movingChar.isWin()) {
			if(movingChar.isMoving()) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	public void loadImage() {
		ImageIcon ii = new ImageIcon("src/com/example/KillerToy.png");
		toyImage = ii.getImage();
	}
	
	public void draw(Graphics g) {
		g.drawImage(toyImage, (int) pos.x + 5, (int) pos.y + 20, null);
	}
	
	public ToyState getState() {
		return toyState;
	}
	
	public void setState(ToyState state) {
		toyState = state;
	}
}
