package gui.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import game.Column;
import game.Piece;

import javax.swing.JLabel;

public class ColumnPanel extends JLabel {
	Column column;
	boolean faceDown;
	protected static final int size = 50;

	public ColumnPanel(int i, boolean faceDown){
		super();
		this.column = Column.find(i);
		this.column.panel = this;
		this.faceDown = faceDown;
		this.addMouseListener(new BackGammonListener(this));
		this.addMouseMotionListener(new PaintMouseMotionListener(this));
		this.addMouseListener(new PaintMouseListener(this));
	}

	public void paintComponent(Graphics g){
		if (column.getNumber() % 2 == 0){
			g.setColor(Color.green);
		} else {
			g.setColor(Color.red);
		}
		if (column.isSelected()){
			g.setColor(Color.LIGHT_GRAY);
		}
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (column.isHighlighted){
			g.setColor(new Color(0.1f, 0.1f, 1.0f, 0.9f));
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
				y = this.getHeight()-(i+1)*size;
				if ( y < 0){
					if (y - size < 0){
						y = (int) ( this.getHeight() - size*((i+1)%(this.getHeight()/(size))) - size*Math.pow(0.5, (int) (i/(this.getHeight()/size))) );
					}
				}
			} else {
				y = i*size;
				if (y + size >  this.getHeight()){
					y = (int) ( size*(i%(this.getHeight()/(size))) + size*Math.pow(0.5, (int) (i/(this.getHeight()/size))) );
				}
			}
			g.fillOval(0, y, size, size);
			g.setColor(Color.gray);
			g.drawOval(0, y, size, size);
		} 
	}

	public boolean isSelected() {
		return column.isSelected();
	}

	public void unSelect() {
		column.unSelect();
		
	}

	public void select() {
		column.select();
	}	
}

class BackGammonListener implements MouseListener{

	ColumnPanel panel;
	
	public BackGammonListener(ColumnPanel columnPanel) {
		panel = columnPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (panel.isSelected()){
			panel.unSelect();
		} else {
			panel.select();
		}
		panel.repaint();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		panel.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		panel.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		panel.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		panel.repaint();
	}
}
