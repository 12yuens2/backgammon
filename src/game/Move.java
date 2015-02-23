package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Move {
	public static String message = " ";

	public static int[] dice = {0,0};
	public static int[] doubles = {0,0};
	private static Random random = new Random();

	public static ArrayList<PossibleMove> possibleMoves = new ArrayList<>();

	public static void setDice(int[] dices){
		dice = dices;

		if (dice[0] == dice[1]){
			doubles[0] = dice[0];
			doubles[1] = dice[1];
		} else {
			doubles[0] = 0;
			doubles[1] = 0;
		}
	}

	public static void rollDice() {

		doubles[0] = 0;
		doubles[1] = 0;

		dice[0] = random.nextInt(5)+ 1;
		dice[1] = random.nextInt(5)+ 1;
		message = " " + dice[0] + "-" + dice[1] + ":";
		//		System.out.print("Dice 1:");
		//		dice[0] = input.nextInt();
		//		System.out.print("Dice 2:");
		//		dice[1] = input.nextInt();

		if (dice[0] == dice[1]){
			doubles[0] = dice[0];
			doubles[1] = dice[1];
		}
		Move.setPossibleMoves();
		//		System.out.println(dice[0] + " " + dice[1] + " (" + doubles[0] + " " + doubles[1] + ")");
	}

	public static void consumeMove(int start, int end){
		consumeMove(start,end,true);
	}

	public static void consumeMove(int start, int end, boolean share) {
		//		System.out.println(end);
		//		System.out.println(start);
		int moveUsed = Math.abs(end - start);
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
		if (share){
			message = message + "(" + start + "|" + end + "),";
			//			System.out.println(message);
		}		
		if (!Move.checkMoves()){
			Game.changeTurn();
		}
		//		System.out.println(dice[0] + " " + dice[1] + " (" + doubles[0] + " " + doubles[1] + ")");
		if (dice[0] == 0 && dice[1] == 0){
			Game.changeTurn();
		}

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
		//		System.out.println(dice[0] + " " + dice[1] + " (" + doubles[0] + " " + doubles[1] + ")");
		if (dice[0] == 0 && dice[1] == 0){
			Game.changeTurn();
		}
	}

	public static boolean checkMoves(){
		Column temp = Column.selectedColumn;
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
		if (temp != null){
			Column.selectedColumn = temp;
		}
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
					if( Column.canBearOff()){
						if (c.getColor() == Column.BLACK){
							if (c.getNumber() < move){
								return true;
							}
						} else if (c.getColor() == Column.WHITE){
							if (c.getNumber() > 25 - move){
								return true;
							}
						}
					}
				}
			}
			Column.selectedColumn = null;
		}
		return false;
	}


	public static void setPossibleMoves(){
		possibleMoves.clear();
		for (Column c : Column.woodColumns){
			if (hasValidMoves(c)){
				for (int move : Move.dice){
					int x = c.getNumber() + move*c.getColor();
					if (x >= 0 && x < Column.getAll().length){
						if (Column.find(c.getNumber() + move*c.getColor()).isValidMove()){
							possibleMoves.add(new PossibleMove(x,c.getNumber(),move));
						}						
					}
				}
			}
		}
		if (!possibleMoves.isEmpty()) { return; }
		
		for (Column c: Column.getAll()){
			if (hasValidMoves(c)){
				for (int move : Move.dice){
					int x = c.getNumber() + move*c.getColor();
					if (x >= 0 && x < Column.getAll().length){
						if (Column.find(c.getNumber() + move*c.getColor()).isValidMove() ){
							possibleMoves.add(new PossibleMove(x,c.getNumber(),move));
						}
					}

					if (c.getColor() == Column.BLACK){
						if (x < 0 && Column.canBearOff() && c.hasToBearOff() ){
							possibleMoves.add(new PossibleMove(0,c.getNumber(),move));
						}
					}  else {
						if (x > Column.getAll().length && Column.canBearOff() && c.hasToBearOff() ){
							possibleMoves.add(new PossibleMove(25, c.getNumber(), move));
						}								
					}

				}
			}
		}

		System.out.println("Possible moves:");
		for (PossibleMove move : possibleMoves){
			System.out.println("" + move.getFrom() + " >> " + move.getTo() + " using " + move.getDiceUsed() + ".");
		}
	}

	public static ArrayList<Integer[]> getValidMoves(){
		ArrayList<Integer[]> moves = new ArrayList<>();

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

		if (moves.isEmpty()){

			for (Column c: Column.getAll()){
				if (hasValidMoves(c)){
					for (int move : Move.dice){
						int x = c.getNumber() + move*c.getColor();
						if (x >= 0 && x < Column.getAll().length){
							if (Column.find(c.getNumber() + move*c.getColor()).isValidMove() ){
								Integer[] tempMove = {c.getNumber(),x};
								moves.add(tempMove);
							}
						}

						if (c.getColor() == Column.BLACK){
							if (x < 0 && Column.canBearOff() ){
								if (c.hasToBearOff()){
									Integer[] tempMove = {c.getNumber(),0};
									moves.add(tempMove);																			
								}
							}
						}  else {
							if (x > Column.getAll().length ){
								if (c.hasToBearOff()){
									Integer[] tempMove = {c.getNumber(),25};
									moves.add(tempMove);																			
								}
							}								
						}

					}
				}
			}			
		}

		return moves;
	}

	public static void executeMove(Integer[] move){
		executeMove(move, true);
	}

	public static void executeMove(Integer[] move, boolean share) {
		//assume valid move is passed here
		if (isCaptureMove(move)){
			if (Column.find(move[1]).getColor() == Column.WHITE){
				Column.find(Column.WOOD_WHITE).addPiece(Column.find(move[1]).RemovePiece());
			} else {
				Column.find(Column.WOOD_BLACK).addPiece(Column.find(move[1]).RemovePiece());
			}
		}

		Column.find(move[1]).addPiece(Column.find(move[0]).RemovePiece());

		Move.consumeMove(move[0],move[1],share);

	}

	public static boolean isCaptureMove(Integer[] move){
		//		System.out.println("__________________________________________");
		//		System.out.println(" " + move[0] + " , " + move[1]);
		return ((!(Column.find(move[0]).getColor() == Column.find(move[1]).getColor()) && Column.find(move[1]).getPieces().size() == 1));
	}

	public static void executeMove(int[] moves) {
		Integer[] newMoves = new Integer[moves.length];
		int i = 0;
		for (int m : moves) {
			newMoves[i++] = Integer.valueOf(m);
		}
		executeMove(newMoves,false);
	}

	public static void executeMove(PossibleMove move, boolean shareToNetwork) {
		//assume valid move is passed here
		if (isCaptureMove(move)){
			if (Column.find(move.getTo()).getColor() == Column.WHITE){
				Column.find(Column.WOOD_WHITE).addPiece(Column.find(move.getTo()).RemovePiece());
			} else {
				Column.find(Column.WOOD_BLACK).addPiece(Column.find(move.getTo()).RemovePiece());
			}
		}

		Column.find(move.getTo()).addPiece(Column.find(move.getFrom()).RemovePiece());

		Move.consumeMove(move,shareToNetwork);		
	}

	private static void consumeMove(PossibleMove move, boolean shareToNetwork) {
		for (int i = 0; i < dice.length; i++){
			if (dice[i] == move.getDiceUsed()){
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
		if (shareToNetwork){
			message = message + "(" + move.getFrom() + "|" + move.getTo() + "),";
		}
		Move.setPossibleMoves();
		
		if (possibleMoves.isEmpty() || (dice[0] == 0 && dice[1] == 0 )){
			Game.changeTurn();
		}
	}

	private static boolean isCaptureMove(PossibleMove move) {
		return ((!(Column.find(move.getFrom()).getColor() == Column.find(move.getTo()).getColor()) && Column.find(move.getTo()).getPieces().size() == 1));
	}	
}
