package gui.sprites;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	static BufferedImage[][] sprites;
	static int size;
	
	public static void init(BufferedImage sheet, int x, int y){
		size = Math.min(sheet.getWidth()/x, sheet.getHeight()/y);
		sprites = new BufferedImage[x][y];
		for (int j = 0; j < y; j++){
			for (int i = 0; i < x; i++){
				sprites[i][j] = sheet.getSubimage(x, y, size, size);
			}
		}
	}
	
	public static BufferedImage getSprite(int x, int y){
		if (x >= 0 && x < sprites.length && y >= 0 && y < sprites[0].length){
			return sprites[x][y];			
		} else {
			return null;
		}
	}

}
