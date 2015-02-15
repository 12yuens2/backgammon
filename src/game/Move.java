package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Move {
	private String message;

	public static int[] dice = {0,0};
	public static int[] doubles = {0,0};
	private static Random random = new Random();
	private static Scanner input = new Scanner(System.in);
	
	
	public static void rollDice() {
		
		doubles[0] = 0;
		doubles[1] = 0;
		
		dice[0] = random.nextInt(5)+ 1;
		dice[1] = random.nextInt(5)+ 1;
		
//		System.out.print("Dice 1:");
//		dice[0] = input.nextInt();
//		System.out.print("Dice 2:");
//		dice[1] = input.nextInt();
		
		if (dice[0] == dice[1]){
			doubles[0] = dice[0];
			doubles[1] = dice[1];
		}
		
//		System.out.println(dice[0] + " " + dice[1] + " (" + doubles[0] + " " + doubles[1] + ")");
	}

	public static void consumeMove(int moveUsed) {
		for (int i = 0; i < dice.length; i++){
			if (dice[i] == moveUsed){
				dice[i] = 0;
				for (int j = 0; j < doubles.length; j++){
					if (doubles[i] != 0){
						dice[i] = doubles[i];
						doubles[i] = 0;
						break;
					}
				}
				break;
			}
		}
				
		if (!Move.checkMoves()){
			Game.changeTurn();
		}
		//System.out.println(dice[0] + " " + dice[1] + " (" + doubles[0] + " " + doubles[1] + ")");
		if (dice[0] == 0 && dice[1] == 0){
			Game.changeTurn();
		}
	}
	
	public static boolean checkMoves(){
		boolean hasMoves = false;
		for (Column c: Column.getAll()){
			if (hasValidMoves(c)){
				hasMoves = true;
			}
		}
		for (Column c: Column.woodColumns){
			if (hasValidMoves(c)){
				hasMoves = true;
			}
		}
		Column.selectedColumn = null;
//		System.out.println(hasMoves);
		return hasMoves;
	}
	
	public static boolean hasValidMoves(Column c){
		if (c.getColor() == Game.turn){
			Column.selectedColumn = c;
			for (int move : Move.dice){
				int x = c.getNumber() + move*c.getColor();
				if (x >= 0 && x < Column.getAll().length){
					if (Column.find(c.getNumber() + move*c.getColor()).isValidMove()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static ArrayList<Integer[]> getValidMoves(){
		ArrayList<Integer[]> moves = new ArrayList<>();
		for (Column c: Column.getAll()){
			if (hasValidMoves(c)){
				for (int move : Move.dice){
					int x = c.getNumber() + move*c.getColor();
					if (x >= 0 && x < Column.getAll().length){
						if (Column.find(c.getNumber() + move*c.getColor()).isValidMove()){
							Integer[] tempMove = {c.getNumber(),x};
							moves.add(tempMove);
						}						
					}
				}
			}
		}
		for (Column c : Column.woodColumns){
			if (hasValidMoves(c)){
				for (int move : Move.dice){
					int x = c.getNumber() + move*c.getColor();
					if (x >= 0 && x < Column.getAll().length){
						if (Column.find(c.getNumber() + move*c.getColor()).isValidMove()){
							Integer[] tempMove = {c.number,x};
							moves.add(tempMove);
						}						
					}
				}
			}
		}
		
		for (Integer[] move : moves){
//			System.out.println(" " + move[0] + " , " + move[1]);
		}
		
		return moves;
	}

	public static void executeMove(Integer[] move) {
		//assume valid move is passed here
		if (isCaptureMove(move)){
			if (Column.find(move[1]).getColor() == Column.WHITE){
				Column.find(Column.WOOD_WHITE).addPiece(Column.find(move[1]).RemovePiece());
			} else {
				Column.find(Column.WOOD_BLACK).addPiece(Column.find(move[1]).RemovePiece());
			}
		}

		Column.find(move[1]).addPiece(Column.find(move[0]).RemovePiece());
		Move.consumeMove(Math.abs(move[1] - move[0]));
	}
	
	public static boolean isCaptureMove(Integer[] move){
		return ((!(Column.find(move[0]).getColor() == Column.find(move[1]).getColor()) && Column.find(move[1]).getPieces().size() == 1));
	}
	
	
}
