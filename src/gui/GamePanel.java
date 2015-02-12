package gui;

import game.Column;
import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	public GamePanel(){
		
		int colNum = 1;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx=0.8;
		c.weighty=0.8;
		
		// 
		//
		//
		//
		
		c.gridy=1;
		//bottom
		for (int i = 12; i > 6; i--){
			c.gridx=i;
			this.add(new ColumnPanel(colNum, true),c);
			colNum++;
		}
		c.gridx = 6;
		this.add(new WoodPanel(),c);
		for (int i = 5; i >= 0; i--){
			c.gridx=i;
			this.add(new ColumnPanel(colNum, true),c);
			colNum++;
		}
		c.gridy=0;
		//top
		for (int i = 0; i < 6; i++){
			c.gridx=i;
			this.add(new ColumnPanel(colNum, false),c);
			colNum++;
		}
		c.gridx = 6;
		this.add(new WoodPanel(),c);
		for (int i = 7; i <= 12; i++){
			c.gridx=i;
			this.add(new ColumnPanel(colNum, false),c);
			colNum++;
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLUE);

		g.fillRect(0, 0, this.getWidth(), this.getHeight());
/*		for (Column c : Column.getAll()){
			if (c.isSelected()){
				g.setColor(Color.white);
				Point p = this.getMousePosition();
				if (p != null){
					g.fillOval(p.x-25 ,p.y-25, 50, 50);					
				}
			}
		} */
	}
	
	public void render(){
		//render background
		//render columns
		//render active
	}
	
}
