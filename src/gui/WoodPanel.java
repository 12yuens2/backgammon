package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class WoodPanel extends JLabel {
	public WoodPanel(){
		super();
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
