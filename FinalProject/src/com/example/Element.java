package com.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Element {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	private Position pos;
	private Velocity vel;
	private CharacterType charType;
	private boolean isWin;
	private boolean isDeath;
	private Image aliveImage;
	private Image deathImage;
	
	public Element() {
		this.pos = new Position();
		this.vel = new Velocity();
		this.charType = new CharacterType(ShapeType.RECTANGLE);
		this.isWin = false;
	}
	
	public void loadImage() {
		if(charType.getShape() == ShapeType.RECTANGLE) {
			aliveImage = charType.getSquarePlayerImage();
		}else if(charType.getShape() == ShapeType.TRIANGLE) {
			aliveImage = charType.getTrianglePlayerImage();
		}else {
			aliveImage = charType.getDiamondPlayerImage();
		}
		ImageIcon ii;
		ii = new ImageIcon("src/com/example/blood.png");
		deathImage = ii.getImage();
	}
	
	protected Element(Position pos, Velocity vel, CharacterType charType) {
		super();
		this.pos = pos;
		this.vel = vel;
		this.charType = charType;
		loadImage();
		
	}

	public void draw(Graphics g) {
		
		int radius = 25;
		if(!isDeath()) {
			g.setColor(Color.GREEN);
			g.drawImage(aliveImage, (int) (pos.x), (int)(pos.y), null);
				
		}else {
			g.drawImage(deathImage, (int) pos.x, (int)pos.y, null);
		}
	}

	public abstract void move(char ch);
	
	public abstract void move();

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public Velocity getVel() {
		return vel;
	}

	public void setVel(Velocity vel) {
		this.vel = vel;
	}

	public CharacterType getCharType() {
		return charType;
	}

	public void setCharType(CharacterType charType) {
		this.charType = charType;
	}

	public boolean isWin() {
		return isWin;
	}

	public void setWin() {
		if(pos.y < 70) {
			this.isWin = true;
		}
	}
	
	public boolean isDeath() {
		return isDeath;
	}
	
	public void setDeath() {
		isDeath = true;
	}
	
	public void setNotMovingByChance() {
		Random rand = new Random();
		boolean desireToMove = rand.nextBoolean();
		if(!desireToMove) {
			setVel(new Velocity(0, 0));
		}
	}
	
	public abstract boolean isMoving();
}
