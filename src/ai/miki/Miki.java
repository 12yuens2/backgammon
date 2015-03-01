package ai.miki;

import java.util.ArrayList;
import java.util.Random;

import ai.AI;
import ai.random.RandomAI;
import game.Column;
import game.Game;
import game.Move;
import game.PossibleMove;

public class Miki implements AI {
	
	Random generator;
	ArrayList<FutureBoard> futureBoards;
	
	public Miki(){
		generator = new Random();		
	}

	public void makeMove() {
		
		futureBoards = new ArrayList<>();
		Move.setPossibleMoves(Game.gameBoard);
		ArrayList<PossibleMove> boardMoves = new ArrayList<>();
		
		for (PossibleMove move : Move.possibleMoves){
			boardMoves.add(move.clone());
		}
	
		for (PossibleMove move : boardMoves){
			FutureBoard newBoard = new FutureBoard(move, Game.gameBoard, 1, Game.gameBoard.getTurn());
			futureBoards.add(newBoard);
		}

		PossibleMove bestMove = evaluteMove();
//		System.out.println("From " + FutureBoard.NUMBEROFBOARDS + " boards, the best move is " + bestMove.getFrom() + " > " + bestMove.getTo() + " using " + bestMove.getDiceUsed() + ".");
		
		Move.setPossibleMoves(Game.gameBoard);

		Move.executeMove(Game.gameBoard,bestMove,true);
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FutureBoard.NUMBEROFBOARDS = 0;
	}

	private PossibleMove evaluteMove() {
		
		PossibleMove bestMove = null;
		double bestValue = Integer.MIN_VALUE;
		for (FutureBoard board : this.futureBoards){
			if (board.evaluate() > bestValue){
				bestValue = board.evaluate();
				bestMove = board.getMove();
			}
		}
		
		return bestMove;
	}
}
