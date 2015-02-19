package game;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JTextField;

import networking.Client;
import networking.Server;
import ai.AI;
import ai.aoi.Aoi;
import ai.homura.Homura;
import gui.game.Window;
import gui.options.AIPanel;

public class Game {

	public static int turn = 0;
	public static int turnNumber = 0;
	public static int blackwins = 0;
	public static int whitewins = 0;
	public static Window gameWindow;
	public static final int PIECE_NUMBER = 15;
	public static final int LOCAL = 0;
	public static int winner;
	public static boolean gameOver;
	public static boolean hasStarted;

	public static boolean whiteIsHuman;
	public static boolean blackIsHuman;
	public static boolean isServer;
	public static boolean whiteIsNetwork;
	public static boolean blackIsNetwork;
	
	public static AI whiteAI, blackAI;
	
	
	public static void main(String[] args) throws InterruptedException {

		Homura homuraChanWhite = new Homura();
		AI randomChan = new AI();
		Aoi aoiChan = new Aoi();
		Game.gameOver = false;

		Column.init();
		gameWindow = new Window();
		
		while (true){
			while (!hasStarted){
				Thread.sleep(100);
			}
			System.out.println("starting game...");
			while (!gameOver){
				while (Game.turn == Column.WHITE && !Game.gameOver){
					if (whiteIsNetwork){
						
					} else {
						if (whiteIsHuman){
							Thread.sleep(40);
						} else {
							whiteAI.makeRandomMove();
							Thread.sleep(120);
						}						
					}
					gameWindow.repaint();
				}
				while (Game.turn == Column.BLACK && !Game.gameOver){
					if (blackIsNetwork){
						
					} else {
						if (blackIsHuman){
							Thread.sleep(40);
						} else {
							blackAI.makeRandomMove();
							Thread.sleep(120);
						}
					}
					gameWindow.repaint();
				}
			}
			hasStarted = false;
		}
		/*
		Game.changeTurn();
		int gamesPlayed = 0;
		while (gamesPlayed < 100){
			while (!Game.gameOver){
				while (Game.turn == Column.WHITE && !Game.gameOver){
			//		randomChan.makeRandomMove();
					gameWindow.repaint();
					Thread.sleep(40);
				}



				while (Game.turn == Column.BLACK && !Game.gameOver){
			//		aoiChan.evaluateBoard();
					gameWindow.repaint();
					Thread.sleep(40);
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
*/
	}

	public static void changeTurn() {
		System.out.println("Changing turn...");
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

	public static void startGame(){
		Column.addPieces();
		Game.hasStarted = true;
		Game.turn = Column.WHITE;
		Move.rollDice();
		gameWindow.repaint();
	}

	public static void startLocalGame() {
		whiteIsHuman = true;
		blackIsHuman = true;
		startGame();
		
	}

	public static void startAIGame(int AIType, boolean playAsWhite) {
		if (playAsWhite){
			whiteIsHuman = true;
			blackIsHuman = false;
			
			switch(AIType){
				case AIPanel.AoiIndex :
					blackAI = new Aoi();
					break;
				case AIPanel.HomuraIndex :
					blackAI = new Homura();
					break;
				case AIPanel.RandomIndex :
					blackAI = new AI();
					break;
			}
			
		} else {
			whiteIsHuman = false;
			blackIsHuman = true;
			
			switch(AIType){
				case AIPanel.AoiIndex :
					whiteAI = new Aoi();
					break;
				case AIPanel.HomuraIndex :
					whiteAI = new Homura();
					break;
				case AIPanel.RandomIndex :
					whiteAI = new AI();
					break;
			}
		}
		startGame();
	}

	public static void startNetworkGame(int aiType, int port) {
		//stuff here
		
		try {
			Server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void joinNetworkGame(int aiType, int port, JTextField hostName) {
		//stuff here
		
		try {
			Client.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void startLocalAIGame(int AIType1, int AIType2) {
		whiteIsHuman = false;
		blackIsHuman = false;
		
		switch(AIType1){
			case AIPanel.AoiIndex :
				whiteAI = new Aoi();
				break;
			case AIPanel.HomuraIndex :
				whiteAI = new Homura();
				break;
			case AIPanel.RandomIndex :
				whiteAI = new AI();
				break;
		}

		switch(AIType2){
			case AIPanel.AoiIndex :
				blackAI = new Aoi();
				break;
			case AIPanel.HomuraIndex :
				blackAI = new Homura();
				break;
			case AIPanel.RandomIndex :
				blackAI = new AI();
				break;		
		}
		startGame();
	}

}
