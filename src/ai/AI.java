package ai;

import game.Game;
import game.Move;

import java.util.ArrayList;
import java.util.Random;

public class AI {
	Random generator;
	
	public AI(){
		this.generator = new Random();
	}
	
	public void makeRandomMove(){
		ArrayList<Integer[]> moves = Move.getValidMoves();
		int chosenMove = generator.nextInt(moves.size());
		Move.executeMove(moves.get(chosenMove));
	}
}
