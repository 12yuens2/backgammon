package game;

import gui.Window;

public class Game {
	
	public static int turn = Column.BLACK;
	public static Window gameWindow;
	
	public static void main(String[] args) {
		
		boolean gameOver = false;
		
		Column.init();
		gameWindow = new Window();
		Game.changeTurn();
		
		while (!gameOver){

		}

	}

	public static void changeTurn() {
		Game.turn = (Game.turn == Column.BLACK) ? Column.WHITE : Column.BLACK;
		gameWindow.repaint();
		Move.rollDice();
	}
}
