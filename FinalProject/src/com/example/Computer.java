package com.example;

import java.util.Random;


public class Computer extends Element{
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	public boolean desireToMove;
	private int latestDir = 0;
	
	public Computer() {
		super();
	}

	protected Computer(Position pos, Velocity vel, CharacterType charType) {
		super(pos, vel, charType);
	}
	
	@Override
	public void move(char ch) {
		//Do nothing
	}
	
	@Override
	public void move() {
		if(!isWin() && !isDeath()) {
			Random rand = new Random();
			setVel(new Velocity(0, 0));
			if(desireToMove) {
				int dir = 0;
				dir = rand.nextInt(5) + 1;
				float vel = 2;
				if(dir == 1) {
					this.setVel(new Velocity(-vel, 0));
				}else if(dir == 2) {
					this.setVel(new Velocity(vel, 0));
				}else if(dir == 3) {
					this.setVel(new Velocity(0, -vel));
				}else if(dir == 4) {
					this.setVel(new Velocity(vel, -vel));
				}else if(dir == 5) {
					this.setVel(new Velocity(-vel, -vel));
				}
			}
			if(getPos().x + getVel().x < WIDTH && getPos().x + getVel().x > 0) {
				this.setPos(new Position(getPos().x + getVel().x, getPos().y + getVel().y));
			}
			
			setWin();
		}
	}
	
	public void setDesireToMoveByChance() {
		desireToMove = new Random().nextBoolean();
	}
	
	@Override
	public boolean isMoving() {
		return getVel().x != 0 && getVel().y != 0;
	}
}
