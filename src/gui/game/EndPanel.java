package gui.game;

import game.Board;
import game.Piece;

import java.awt.Color;
import java.awt.Graphics;

public class EndPanel extends ColumnPanel {

	public EndPanel(int i, boolean faceDown, Board board) {
		super(i, faceDown, board);
	}

	public void paintComponent(Graphics g){
		g.setColor(Color.GRAY);
		
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if (this.column.isHighlighted){
			g.setColor(new Color(0.8f,0.8f,0.0f,0.5f));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		for (int i = 0; i < column.getPieces().size(); i++){
			if (column.getPieces().get(i).getColor() == Piece.WHITE){
				g.setColor(Color.white);
			} else {
				g.setColor(Color.BLACK);
			}
			int y = 0;
			if (this.faceDown){
				y = this.getHeight()-(i+1)*size/5 - i;
			} else {
				y = i*size/5 + i;
			}
			g.fillRect(0, y, size, size/5);
		} 
	}
	
}
