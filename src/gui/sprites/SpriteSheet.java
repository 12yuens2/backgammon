package gui.sprites;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	static BufferedImage spriteSheet;
	static int size = 256;
	static String filename = "src/gui/sprites/SpriteSheet.jpeg";
	
	public static void init(){
		try {
			spriteSheet = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage getSprite(int x, int y){
		return getSprite(x, y,1,1);
	}
	
	public static BufferedImage getSprite(int x, int y, int width, int height){
		return spriteSheet.getSubimage(x*size, y*size, width*size, height*size);
	}

	public static BufferedImage getBlack(){
		return getSprite(0,0);
	}

	public static BufferedImage getWhite(){
		return getSprite(1,0);
	}
	
	public static BufferedImage getGreenColFlipped(){
		return getSprite(3,1,1,7);
	}
	
	public static BufferedImage getRedColFlipped(){
		return getSprite(2,1,1,7);
	}
	
	public static BufferedImage getRedCol(){
		return getSprite(0,1,1,7);
	}
	
	public static BufferedImage getGreenCol(){
		return getSprite(1,1,1,7);
	}
	
	public static BufferedImage getBoard(){
		return getSprite(4,1);
	}

	public static BufferedImage getWood() {
		return getSprite(4,0);
	}

	public static BufferedImage getDice(int value){
		return getSprite(5,value);
	}
	
	public static BufferedImage getWhiteEnd() {
		return spriteSheet.getSubimage(5*size, size/4, size, size/4);
	}
	
	public static BufferedImage getBlackEnd() {
		return spriteSheet.getSubimage(5*size, 0, size, size/4);
	}

	public static Image getEnd() {
		return getSprite(6,1,1,7);
	}
}
