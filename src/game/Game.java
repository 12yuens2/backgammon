package game;

import ai.homura.Homura;
import gui.Window;

public class Game {

	public static int turn = Column.BLACK;
	public static int turnNumber = 0;
	public static int blackwins = 0;
	public static int whitewins = 0;
	public static Window gameWindow;
	public static final int PIECE_NUMBER = 15;
	public static int winner;
	public static boolean gameOver;

	public static void main(String[] args) {

		Homura homuraChanWhite = new Homura();
		Homura homuraChanBlack = new Homura();
		
		Game.gameOver = false;

		Column.init();
		gameWindow = new Window();
		Game.changeTurn();
		int gamesPlayed = 0;
		while (gamesPlayed < 1500){
			while (!Game.gameOver){
				while (Game.turn == Column.WHITE && !Game.gameOver){
					homuraChanWhite.makeRandomMove();					
				}
				while (Game.turn == Column.BLACK && !Game.gameOver){
					homuraChanBlack.makeRandomMove();					
				}
			}
			
			if (Game.winner == Column.BLACK){
				homuraChanBlack.addWinData();
				homuraChanWhite.addLoseData();
				Game.blackwins++;
			} else {
				homuraChanBlack.addLoseData();
				homuraChanWhite.addWinData();
				Game.whitewins++;
			}
			
			gamesPlayed++;
			System.out.println("Game number: " + gamesPlayed);
			Game.reset();
		}
		
		System.out.println("Black won: " + blackwins);
		System.out.println("White won: " + whitewins);
		
	}

	public static void changeTurn() {
		Game.turnNumber++;
		if (!Game.gameOver){
			if(Column.getAll()[0].getPieces().size() == Game.PIECE_NUMBER){
				Game.winner = Column.BLACK;
				Game.gameOver = true;
				System.out.println("Black wins");

			} else if (Column.getAll()[25].getPieces().size() == Game.PIECE_NUMBER) {
				Game.winner = Column.WHITE;
				Game.gameOver = true;
				System.out.println("White wins");
			}

			Game.turn = (Game.turn == Column.BLACK) ? Column.WHITE : Column.BLACK;
			gameWindow.repaint();
			Move.rollDice();
			Move.getValidMoves();
			if (!Move.checkMoves()){
				Game.changeTurn();
			}			
		}
	}
	
	public static void reset(){
		gameOver = false;
		Column.init();
		gameWindow.dispose();
		gameWindow = new Window();
		turnNumber = 0;
		changeTurn();
	}

}
