package game;

import java.util.ArrayList;
import java.util.Random;

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
		
		Move.setPossibleMoves();
	}

	public static void rollDice() {

		doubles[0] = 0;
		doubles[1] = 0;

		dice[0] = random.nextInt(5)+ 1;
		dice[1] = random.nextInt(5)+ 1;
		message = " " + dice[0] + "-" + dice[1] + ":";

		if (dice[0] == dice[1]){
			doubles[0] = dice[0];
			doubles[1] = dice[1];
		}
		Move.setPossibleMoves();
		//		System.out.println(dice[0] + " " + dice[1] + " (" + doubles[0] + " " + doubles[1] + ")");
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
		if (possibleMoves.isEmpty()) {
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
								if (c.getNumber() != 0){
									possibleMoves.add(new PossibleMove(0,c.getNumber(),move));									
								}
							}
						}
						
						if (c.getColor() == Column.WHITE) {
							if (x > 25 && Column.canBearOff() && c.hasToBearOff() ){
								if (c.getNumber() != 25) {
									possibleMoves.add(new PossibleMove(25, c.getNumber(), move));									
								}
							}								
						}

					}
				}
			}			
		}

	/*	if (!possibleMoves.isEmpty()){
			System.out.println("Possible moves:");
			for (PossibleMove move : possibleMoves){
				System.out.println("" + move.getFrom() + " >> " + move.getTo() + " using " + move.getDiceUsed() + ".");
			}			
		} */
	}
	
	public static void executeMove(PossibleMove move, boolean shareToNetwork) {
		//assume valid move is passed here
		Column from = Column.findFrom(move);
		Column to = Column.findTo(move);
		if (to.getPieces().size() == 1){
			if (from.getColor() == Column.WHITE && to.getColor() == Column.BLACK){
				Column.find(Column.WOOD_BLACK).addPiece(to.RemovePiece());
			} else if (from.getColor() == Column.BLACK && to.getColor() == Column.WHITE) {
				Column.find(Column.WOOD_WHITE).addPiece(to.RemovePiece());
			}
		}

		to.addPiece(from.RemovePiece());

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

	public static PossibleMove find(int from, int to) {
		for (PossibleMove move : possibleMoves){
			if (move.getFrom() == from && move.getTo() == to){
				return move;
			}
		}
		return null;
	}
}
