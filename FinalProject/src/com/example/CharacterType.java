package com.example;

import java.awt.Image;

import javax.swing.ImageIcon;

public class CharacterType {
	
	private ShapeType shape;
	private Image squarePlayer;
	private Image trianglePlayer;
	private Image diamondPlayer;
	
	public CharacterType(ShapeType shape) {
		this.shape = shape;
		ImageIcon ii;
		ii = new ImageIcon("src/com/example/PlayerSquare.png");
		squarePlayer = ii.getImage();
		ii = new ImageIcon("src/com/example/PlayerTriangle.png");
		trianglePlayer = ii.getImage();
		ii = new ImageIcon("src/com/example/PLayerDiamond.png");
		diamondPlayer = ii.getImage();
		
	}
	public ShapeType getShape() {
		return shape;
	}
	public void setShape(ShapeType shape) {
		this.shape = shape;
	}
	
	public Image getSquarePlayerImage() {
		return squarePlayer;
	}
	
	public Image getTrianglePlayerImage() {
		return trianglePlayer;
	}
	
	public Image getDiamondPlayerImage() {
		return diamondPlayer;
	}
	
	
	
}
