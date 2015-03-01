package gui.game;

import game.Board;
import game.Game;
import game.Move;
import gui.sprites.SpriteSheet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

public class DicePanel extends JLabel {
	private int color;
	private Board board;
	
	public DicePanel(Board board,int color){
		this.color = color;
		this.board = board;
	}
	
	public void paintComponent(Graphics g){
		if (board.getTurn() == this.color){
			if (board.getDice() == null){
				return;
			}
			g.setColor(Color.black);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			int w = this.getWidth();
			int h = this.getHeight();
			BufferedImage dice1 = SpriteSheet.getDice(board.getDice()[0]);
			BufferedImage dice2 = SpriteSheet.getDice(board.getDice()[1]);
			g.drawImage(dice1, 0, 0, w/2, h, null);
			g.drawImage(dice2, w/2, 0, w/2, h, null);
		}
	}
}
