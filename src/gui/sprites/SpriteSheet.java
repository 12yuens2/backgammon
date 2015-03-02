package gui.sprites;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	static BufferedImage spriteSheet;
	static int size = 256;
	static String filename = "src/gui/sprites/SpriteSheet.png";
	static BufferedImage pieceSpriteBlack = null;
	static BufferedImage pieceSpriteWhite = null;
	static BufferedImage pieceEndWhite = null;
	static BufferedImage pieceEndBlack = null;
	static BufferedImage boardSprite = null;
	static BufferedImage woodSprite = null;
	static BufferedImage endSprite = null;
	static BufferedImage redColSprite = null;
	static BufferedImage redColSpriteFlipped = null;
	static BufferedImage greenColSprite = null;
	static BufferedImage greenColSpriteFlipped = null;
	static BufferedImage[] diceSprites = new BufferedImage[7];
	
	public static void init(){
		try {
			spriteSheet = ImageIO.read(new File(filename));
			pieceSpriteBlack = getSprite(0,0);
			pieceSpriteWhite = getSprite(1,0);
			greenColSprite = getSprite(1,1,1,7);
			greenColSpriteFlipped = getSprite(3,1,1,7);
			redColSprite = getSprite(0,1,1,7);
			redColSpriteFlipped = getSprite(2,1,1,7);
			boardSprite = getSprite(4,1);
			woodSprite = getSprite(4,0);
			for (int i = 0; i < diceSprites.length; i++ ){
				diceSprites[i] = getSprite(5,i);
			}
			pieceEndWhite = spriteSheet.getSubimage(3*size, size/4, size, size/4);
			pieceEndBlack = spriteSheet.getSubimage(3*size, 0, size, size/4);
			endSprite = getSprite(6,1,1,7);
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
		return pieceSpriteBlack;
	}

	public static BufferedImage getWhite(){
		return pieceSpriteWhite;
	}
	
	public static BufferedImage getGreenColFlipped(){
		return greenColSpriteFlipped;
	}
	
	public static BufferedImage getRedColFlipped(){
		return redColSpriteFlipped;
	}
	
	public static BufferedImage getRedCol(){
		return redColSprite;
	}
	
	public static BufferedImage getGreenCol(){
		return greenColSprite;
	}
	
	public static BufferedImage getBoard(){
		return boardSprite;
	}

	public static BufferedImage getWood() {
		return woodSprite;
	}

	public static BufferedImage getDice(int value){
		return diceSprites[value];
	}
	
	public static BufferedImage getWhiteEnd() {
		return pieceEndWhite;
	}
	
	public static BufferedImage getBlackEnd() {
		return pieceEndBlack;
	}

	public static Image getEnd() {
		return endSprite;
	}
}
