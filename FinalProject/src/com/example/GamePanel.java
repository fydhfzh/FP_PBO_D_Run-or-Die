package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GamePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GameState state = GameState.GAMEMENU;
	MenuPanel menuPanel = new MenuPanel();
	GameField gameField = new GameField(menuPanel.getShapeState());
	
	
	public GamePanel() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(1000, 1000));
		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseCensor());
	}

	public enum GameState{
		GAMEFIELD,
		GAMEMENU;
	}
	
	
	
	class MouseHandler implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(state == GameState.GAMEMENU) {
				if(e.getX() >= menuPanel.playButton.x && e.getX() <= menuPanel.playButton.x + menuPanel.playButton.getWidth()
				&& e.getY() >= menuPanel.playButton.getY() && e.getY() <= menuPanel.playButton.getY() + menuPanel.playButton.getHeight()) {
					state = GameState.GAMEFIELD;
					setPanel(gameField);
					if(gameField.isRunning && !gameField.checkGameOver()) {
						gameField.thread.resume();
					}
					gameField.requestFocus();
				}else if(e.getX() >= menuPanel.changeButton.x && e.getX() <= menuPanel.changeButton.x + menuPanel.changeButton.getWidth()
				&& e.getY() >= menuPanel.changeButton.getY() && e.getY() <= menuPanel.changeButton.getY() + menuPanel.changeButton.getHeight()) {
					menuPanel.changeShape();
					gameField.player1.setCharType(new CharacterType(menuPanel.getShapeState()));
					repaint();
				}else if(e.getX() >= menuPanel.quitButton.x && e.getX() <= menuPanel.quitButton.x + menuPanel.quitButton.getWidth()
				&& e.getY() >= menuPanel.quitButton.getY() && e.getY() <= menuPanel.quitButton.getY() + menuPanel.quitButton.getHeight()) {
					System.exit(0);
				}
			}else if(state == GameState.GAMEFIELD) {
				if(e.getX() >= gameField.menuButton.x && e.getX() <= gameField.menuButton.x + gameField.menuButton.getWidth()
				&& e.getY() >= gameField.menuButton.getY() && e.getY() <= gameField.menuButton.getY() + gameField.menuButton.getHeight()) {
					state = GameState.GAMEMENU;
					setPanel(menuPanel);
					if(gameField.isRunning && !gameField.checkGameOver()) {
						gameField.thread.suspend();
					}else if(gameField.checkGameOver()){
						gameField = new GameField(menuPanel.getShapeState());
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
	
	class MouseCensor implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			if(state == GameState.GAMEMENU) {
				if(e.getX() >= menuPanel.playButton.x && e.getX() <= menuPanel.playButton.x + menuPanel.playButton.getWidth()
				&& e.getY() >= menuPanel.playButton.getY() && e.getY() <= menuPanel.playButton.getY() + menuPanel.playButton.getHeight()) {
					menuPanel.state1 = true;
				}else {
					menuPanel.state1 = false;
				}
				
				
				if(e.getX() >= menuPanel.changeButton.x && e.getX() <= menuPanel.changeButton.x + menuPanel.changeButton.getWidth()
				&& e.getY() >= menuPanel.changeButton.getY() && e.getY() <= menuPanel.changeButton.getY() + menuPanel.changeButton.getHeight()) {
					menuPanel.state2 = true;
				}else {
					menuPanel.state2 = false;
				}
				
				
				if(e.getX() >= menuPanel.quitButton.x && e.getX() <= menuPanel.quitButton.x + menuPanel.quitButton.getWidth()
				&& e.getY() >= menuPanel.quitButton.getY() && e.getY() <= menuPanel.quitButton.getY() + menuPanel.quitButton.getHeight()) {
					menuPanel.state3 = true;
				}else {
					menuPanel.state3 = false;
				}
			}else if(state == GameState.GAMEFIELD) {
				if(e.getX() >= gameField.menuButton.x && e.getX() <= gameField.menuButton.x + gameField.menuButton.getWidth()
				&& e.getY() >= gameField.menuButton.getY() && e.getY() <= gameField.menuButton.getY() + gameField.menuButton.getHeight()) {
					gameField.buttonState = true;
				}else {
					gameField.buttonState = false;
				}
			}
			repaint();
		}
		
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(state == GameState.GAMEMENU) {
			String playStatus = gameField.isRunning() == true ? "Resume" : "Play";
			menuPanel.render(g, playStatus);
		}
	}

	public void setPanel(JPanel panel) {
		removeAll();
		add(panel);
		revalidate();
		repaint();
	}
	
}
