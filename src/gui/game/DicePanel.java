package gui.game;

import game.Game;
import game.Move;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;

public class DicePanel extends JLabel {
	private int color;
	
	public DicePanel(int color){
		this.color = color;
	}
	
	public void paintComponent(Graphics g){
		if (Game.turn == this.color){
			g.setColor(Color.black);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.white);
			g.setFont(new Font(null, Font.PLAIN, 18));
			int w = this.getWidth();
			int h = this.getHeight();
			switch(Move.dice[0]){
				case 1:
					g.drawString("1", 0, h);
					break;
				case 2:
					g.drawString("2", 0, h);
					break;
				case 3:
					g.drawString("3", 0, h);
					break;
				case 4:
					g.drawString("4", 0, h);
					break;
				case 5:
					g.drawString("5", 0, h);
					break;
				case 6:
					g.drawString("6", 0, h);
					break;
			}
			switch(Move.dice[1]){
			case 1:
				g.drawString("1", w/2, h);
				break;
			case 2:
				g.drawString("2", w/2, h);
				break;
			case 3:
				g.drawString("3", w/2, h);
				break;
			case 4:
				g.drawString("4", w/2, h);
				break;
			case 5:
				g.drawString("5", w/2, h);
				break;
			case 6:
				g.drawString("6", w/2, h);
				break;
		}
		}
	}
}
