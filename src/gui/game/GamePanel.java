package gui.game;

import game.Board;
import game.Column;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GamePanel(Board board){
		
		int colNum = 0;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx=0.8;
		c.weighty=0.8;
				
		c.gridy=2;
		//bottom
		c.gridx=13;
//		c.weightx=1.6;
		this.add(new EndPanel(colNum, false, board),c);
		c.weightx=0.8;
		colNum++;
		
		for (int i = 12; i > 6; i--){
			c.gridx=i;
			this.add(new ColumnPanel(colNum, true, board),c);
			colNum++;
		}
		c.gridx = 6;
		this.add(new WoodPanel(Board.WOOD_BLACK,false, board),c);
		for (int i = 5; i >= 0; i--){
			c.gridx=i;
			this.add(new ColumnPanel(colNum, true, board),c);
			colNum++;
		}
		c.gridy=0;
		//top
		for (int i = 0; i < 6; i++){
			c.gridx=i;
			this.add(new ColumnPanel(colNum, false, board),c);
			colNum++;
		}
		c.gridx = 6;
		this.add(new WoodPanel(Board.WOOD_WHITE,true, board),c);
		for (int i = 7; i <= 12; i++){
			c.gridx=i;
			this.add(new ColumnPanel(colNum, false, board),c);
			colNum++;
		}
		c.gridx=13;
//		c.weightx=1.6;
		this.add(new EndPanel(colNum, true, board),c);
		c.weightx=0.8;
		
		//middle
		c.gridy=1;
		c.gridx=2;
		c.gridwidth=2;
		c.weighty=0.1;
		this.add(new DicePanel(board,Column.BLACK),c);

		c.gridx=9;
		this.add(new DicePanel(board,Column.WHITE),c);
		
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLUE);

		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
}
