package ai.random;

import game.Column;
import game.Move;
import game.PossibleMove;

import java.util.Random;

import ai.AI;

public class RandomAI implements AI {

	Random generator;
	
	public RandomAI(){
		generator = new Random();
	}
	
	public void makeMove() {
		int chosenMove = generator.nextInt(Move.possibleMoves.size());
		PossibleMove move = Move.possibleMoves.get(chosenMove);
		Move.executeMove(move,true);			
	}

}
