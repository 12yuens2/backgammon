package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
	public Window(){
		super("CS 1006 Backgammon");
		GamePanel panel = new GamePanel();
		this.add(panel);
		this.setPreferredSize(new Dimension(800,800) );
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

		
	}
	
	public void render(){
		this.repaint();
	}
}
