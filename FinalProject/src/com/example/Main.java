package com.example;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {	
	public static void main (String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("Run or Die");
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new GamePanel());
				frame.pack();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
			}
			
			
		});
	}

}
