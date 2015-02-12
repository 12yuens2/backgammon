package game;

import gui.Window;

public class Game {
	
	public static int turn = Column.BLACK;
	
	public static void main(String[] args) {
		
		boolean gameOver = false;
		
		Column.init();
		
		Window gameWindow = new Window();
		
		while (!gameOver){
			gameWindow.render();
		}

	}

	public static void changeTurn() {
		Game.turn = (Game.turn == Column.BLACK) ? Column.WHITE : Column.BLACK;
	}
}
