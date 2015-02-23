package gui.sprites;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	static BufferedImage spriteSheet;
	static int size;
	
	public static void init(BufferedImage sheet, int x, int y){
		size = Math.min(sheet.getWidth()/x, sheet.getHeight()/y);
		spriteSheet = sheet;
	}
	
	public static BufferedImage getSprite(int x, int y){
		return getSprite(x, y,1,1);
	}
	
	public static BufferedImage getSprite(int x, int y, int width, int height){
		return spriteSheet.getSubimage(x*size, y*size, width*size, height*size);
	}

}
