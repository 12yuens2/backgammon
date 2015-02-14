package game;

import ai.homura.Homura;
import gui.Window;

public class Game {

	public static int turn = Column.BLACK;
	public static int turnNumber = 0;
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
		while (gamesPlayed < 1000){
			while (!Game.gameOver){
				homuraChanWhite.makeRandomMove();
				if (!Game.gameOver){
					homuraChanBlack.makeRandomMove();					
				}
			}

			System.out.println("Homura wins.");
			
			if (Game.winner == Column.BLACK){
				homuraChanBlack.addWinData();
				homuraChanWhite.addLoseData();
			} else {
				homuraChanBlack.addLoseData();
				homuraChanWhite.addWinData();
			}
			
			gamesPlayed++;
			System.out.println(gamesPlayed);
			Game.reset();
		}
	}

	public static void changeTurn() {
		Game.turnNumber++;
		if (!Game.gameOver){
			if(Column.getAll()[0].getPieces().size() == Game.PIECE_NUMBER){
				Game.winner = Column.BLACK;
				Game.gameOver = true;

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
