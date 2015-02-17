package game;

import ai.AI;
import ai.aoi.Aoi;
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

	public static void main(String[] args) throws InterruptedException {

		Homura homuraChanWhite = new Homura();
		AI randomChan = new AI();
		Aoi aoiChan = new Aoi();
		Game.gameOver = false;

		Column.init();
		gameWindow = new Window();
		Game.changeTurn();
		int gamesPlayed = 0;
		while (gamesPlayed < 100){
			while (!Game.gameOver){
				while (Game.turn == Column.WHITE && !Game.gameOver){
					randomChan.makeRandomMove();
					gameWindow.repaint();
					Thread.sleep(400);
				}



				while (Game.turn == Column.BLACK && !Game.gameOver){
					aoiChan.evaluateBoard();
					gameWindow.repaint();
					Thread.sleep(500);
				}
			}

			if (Game.winner == Column.BLACK){

				homuraChanWhite.addLoseData();
				Game.blackwins++;
			} else {

				homuraChanWhite.addWinData();
				Game.whitewins++;
			}

			gamesPlayed++;
			System.out.println("Game number: " + gamesPlayed);
			Game.reset();
		}

		System.out.println("Black won: " + blackwins);
		System.out.println("White won: " + whitewins);
		System.exit(0);

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
