package ai.homura;

import java.util.ArrayList;
import java.util.Random;

import game.Move;

public class Homura {
	
	Random generator;
	
	public Homura(){
		generator = new Random();
	}
	
	public void makeRandomMove(){
		ArrayList<Integer[]> moves = Move.getValidMoves();
		int chosenMove = generator.nextInt(moves.size());
		Move.executeMove(moves.get(chosenMove));
	}
}
