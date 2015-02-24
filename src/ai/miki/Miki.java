package ai.miki;

import java.util.ArrayList;

import game.Column;
import game.Game;

public class Miki {

	private FutureBoard currentboard;
	private int[] boardState;
	
	public void setBoardState() {
		boardState = new int[Game.gameBoard.getAll().length +2];
		for (Column c: Game.gameBoard.getAll()) {
			boardState[c.getNumber()] = c.getPieces().size()*c.getColor();
		}
	}
	
	
	
	
		
}
