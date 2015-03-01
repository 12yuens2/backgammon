package gui.game;

import gui.sprites.SpriteSheet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

public class WoodPaddingPanel extends JLabel {
	public void paintComponent(Graphics g){
		if ( this.getWidth() > this.getHeight()){
			for (int i = 0; i <= 10; i++){
				g.drawImage(SpriteSheet.getWood(), (int) (this.getWidth()*(i/(1.0*10)) ),0,this.getWidth()/10,this.getHeight(),null);
			}
		} else {
			g.drawImage(SpriteSheet.getWood(), 0,0,this.getWidth(),this.getHeight(),null);			
		}

	}
}
