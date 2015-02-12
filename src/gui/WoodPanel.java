package gui;

import game.Piece;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class WoodPanel extends ColumnPanel {
	public WoodPanel(int i, boolean faceDown){
		super(i, faceDown);
	}
	
	public void paintComponent(Graphics g){
		if (this.isSelected()){
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.ORANGE);			
		}

		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (int i = 0; i < column.getPieces().size(); i++){
			if (column.getPieces().get(i).getColor() == Piece.WHITE){
				g.setColor(Color.white);
			} else {
				g.setColor(Color.BLACK);
			}
			int y = 0;
			if (this.faceDown){
				y = this.getHeight()-(i+1)*size;
			} else {
				y = i*size;
			}
			g.fillOval(0, y, size, size);
		} 
	}
}
