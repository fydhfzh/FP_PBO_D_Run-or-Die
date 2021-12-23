package com.example;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends Element{
	private boolean isMoving = false;
	private Image selection;
	public Player() {
		super();
	}

	public Player(Position pos, Velocity vel, CharacterType charType) {
		super(pos, vel, charType);
		ImageIcon ii = new ImageIcon("src/com/example/PlayerSelection.png");
		selection = ii.getImage();
	}
	
	@Override
	public void move(char ch) {
		if(!isDeath() && !isWin()) {
			float vel = 1;
			if(ch == 'w') {
				setVel(new Velocity(0, -vel));
			}else if(ch == 'a') {
				setVel(new Velocity(-vel, 0));
			}else if(ch == 'd') {
				setVel(new Velocity(vel, 0));
			}else if(ch == 's') {
				setVel(new Velocity(0, vel));
			}
			update();
		}
	}
	
	public void update() {
		if(!(getPos().x + getVel().x > WIDTH && getPos().x +getVel().x < 0)) {
			setPos(new Position(getPos().x + getVel().x, getPos().y + getVel().y));
		}
		setWin();
	}
	
	public void drawSelection(Graphics g) {
		if(!isDeath()) {
			g.drawImage(selection, (int) (getPos().x + 2), (int)(getPos().y - 15), null);
		}
	}
	
	@Override
	public boolean isMoving() {
		return isMoving;
	}
	
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	@Override
	public void move() {
		//Do nothing
	}
	
	
}
