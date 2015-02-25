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
	
	public Miki(){
		generator = new Random();		
	}

	public void makeMove() {
		int[] tempDice = Move.dice.clone();
		
		FutureBoard boardTree = new FutureBoard(Game.gameBoard, 1, Game.gameBoard.getTurn());
		
		Move.setDice(Game.gameBoard, tempDice);
		
		Move.setPossibleMoves(Game.gameBoard);
		int chosenMove = generator.nextInt(Move.possibleMoves.size());
		PossibleMove move = Move.possibleMoves.get(chosenMove);
		System.out.println("Making real move");
		Move.executeMove(Game.gameBoard,move,true);
		

	}
}
