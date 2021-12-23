package com.example;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.w3c.dom.ls.LSOutput;


public class GameField extends JPanel{
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	private ArrayList<Computer> computers;
	Player player1;
	KillerToy toy;
	Rectangle menuButton;
	Image menuImage;
	boolean buttonState = false;
	Color originalColor = Color.DARK_GRAY;
	ViewTime time;
	Thread thread;
	long tick = 0;
	boolean isRunning = false;
	Image background;
	
	public GameField(ShapeType shapeType) {
		Random rand = new Random();
		
		setPreferredSize(new Dimension(1000, 1000));
		time = new ViewTime();
		computers = new ArrayList<>();
		for(int i = 0; i < 20; i++) {
			ShapeType type;
			int objectId = rand.nextInt(3);
			if(objectId == 0) {
				type = ShapeType.TRIANGLE;
			}else if(objectId == 1) {
				type = ShapeType.RECTANGLE;
			}else{
				type = ShapeType.DIAMOND;
			}

			computers.add(new Computer(new Position(i*48 + 25, 1000 - (rand.nextInt(25) + 25)) , new Velocity(1, 1), new CharacterType(type)));
		}
		
		loadImage();
		player1 = new Player(new Position(250, 1000 - 50), new Velocity(0, 0), new CharacterType(shapeType));
		toy = new KillerToy();
		menuButton = new Rectangle(WIDTH - 100, 20, 60, 60);
		addKeyListener(new KeyHandlerPlayer1());

		
	}
	
	public void loadImage() {
		try {
			ImageIcon ii = new ImageIcon("src/com/example/background.jpg");
			background = ii.getImage();
			ii = new ImageIcon("src/com/example/Pause.png");
			menuImage = ii.getImage();
			
		} catch (Exception e) {
			System.out.println("Error mas gambar e nda ada");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g.drawImage(background, 0, 0, null);
		for(Element e: computers) {
			e.draw(g);
		}
		player1.draw(g);
		player1.drawSelection(g);
		toy.draw(g);
		
		if(toy.getState() == ToyState.CHECKING) {
			checkElement(g);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		if(!isRunning) {
			 g.drawString("Press W to start", WIDTH / 2 - 115, 50);
		}else if(!checkGameOver()) 
			g.drawString(time.getStringMinutes() + ":" + time.getStringSecond(), WIDTH / 2 - 10, 50);
		else g.drawString("Game Over", WIDTH / 2 - 75, 50);
		
		if(buttonState) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		}
		g2d.drawImage(menuImage,(int) menuButton.getX(), (int) menuButton.getY(), null);
	}
	
	public Thread gameThread() {
		thread = new Thread(new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				long delta = 0;
				long lastTime = System.currentTimeMillis();
				Random rand = new Random();
				int mod = rand.nextInt(6) + 5;
				int delay = 3;
				
				while(true) {
					if(checkGameOver() && (time.getSeconds() == 0 && time.getMinutes() == 0)) {
						thread.stop();
					}
					//waktu berubah per detik
					if(tick >= 1000L) {
						time.decrementSecond();
						if(toy.getState() == ToyState.CHECKING) {
							delay--;
						}
						tick = 0L;
					}
					
					//merubah state toy dari counting ke checking jika sesuai dengan syarat
					if(time.seconds % mod == 0 && toy.getState() == ToyState.COUNTING) {
						toy.setState(ToyState.CHECKING);
						for(Computer c: computers) {
							c.setDesireToMoveByChance();
						}
						mod = rand.nextInt(6) + 5;
					}
					
					
					//pergerakan elemen untuk setiap state berbeda
					if(toy.getState() == ToyState.COUNTING) {
						for(Computer c: computers) {
							c.setDesireToMoveByChance();
							c.move();
						}
					}else if(toy.getState() == ToyState.CHECKING) {
						for(Computer c: computers) {
							c.move();
						}
						if(delay == 0) {
							shoot();
							toy.setState(ToyState.COUNTING);
							delay = 3;
						}
					}
					
					repaint();
					try {
						Thread.sleep(1000/60);
						long now = System.currentTimeMillis();
						delta = now - lastTime;
						tick += delta;
						lastTime = now;
					} catch (InterruptedException e) {
					}
				}
			}
		});
		
		return thread;
	}
	
	
	class KeyHandlerPlayer1 extends KeyAdapter{
		@Override
		public void keyTyped(KeyEvent e) {
			if(!isRunning) {
				gameThread().start();
				isRunning = true;
				time.decrementSecond();
			}else{
			char ch = e.getKeyChar();
			player1.move(ch);
			player1.setMoving(true);
			}

		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			char ch = e.getKeyChar();
			player1.move(ch);
			player1.setMoving(true);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			player1.setVel(new Velocity(0, 0));
			player1.setMoving(false);
		}
		
	}
	
	public void drawLine(Graphics g, Element e) {
		if(!e.isDeath()) {
			g.setColor(Color.RED);
			g.drawLine((int) toy.pos.x + 10, (int) toy.pos.y + 50, (int) e.getPos().x + 25/2, (int) e.getPos().y + 25/2);
		}
	}
	
	public boolean checkGameOver() {
		int count = 0;
		for(Computer c: computers) {
			if(c.isDeath() || c.isWin()) {
				count++;
			}
		}
		
		if(player1.isDeath() || player1.isWin()) {
			count++;
		}
		
		if(time.getSeconds() == 0 && time.getMinutes() == 0) {
			for(Computer c: computers) {
				if(!c.isWin() && !c.isDeath()){
					c.setDeath();
				}
			}
			
			if(!player1.isDeath() || !player1.isWin()) player1.setDeath();
			
			return true;
		}
		
		
		return count == computers.size() + 1;
	}
	
	public void checkElement(Graphics g) {
		Random rand = new Random();
		int chance = rand.nextInt(computers.size() + 2);
		if(chance < computers.size()) {
			for(int i = 0; i < computers.size(); i++) {
				if(i == chance && !computers.get(i).isWin()) {
					drawLine(g, computers.get(i));
					break;
				}
			}
		}else if(chance == computers.size() && !player1.isWin()) {
			drawLine(g, player1);
		}
	}
	
	public void shoot() {
		for(Computer c: computers) {
			if(toy.checkMoving(c) && !c.isWin()){
				c.setDeath();
			}
		}
		if(toy.checkMoving(player1) && !player1.isWin()) {
			player1.setDeath();
		}
	
		repaint();
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
}
