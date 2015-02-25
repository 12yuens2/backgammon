package game;

import java.util.ArrayList;
import java.util.Random;

public class Move {
	public static String message = " ";

	public static int[] dice = {0,0};
	public static int[] doubles = {0,0};
	private static Random random = new Random();

	public static ArrayList<PossibleMove> possibleMoves = new ArrayList<>();

	public static void setDice(Board board, int[] dices){
		dice = dices;

		if (dice[0] == dice[1]){
			doubles[0] = dice[0];
			doubles[1] = dice[1];
		} else {
			doubles[0] = 0;
			doubles[1] = 0;
		}
		
		Move.setPossibleMoves(board);
	}

	public static void rollDice(Board board) {

		doubles[0] = 0;
		doubles[1] = 0;

		dice[0] = random.nextInt(5)+ 1;
		dice[1] = random.nextInt(5)+ 1;
		message = " " + dice[0] + "-" + dice[1] + ":";

		if (dice[0] == dice[1]){
			doubles[0] = dice[0];
			doubles[1] = dice[1];
		}
		Move.setPossibleMoves(board);
		//		System.out.println(dice[0] + " " + dice[1] + " (" + doubles[0] + " " + doubles[1] + ")");
	}
	
	public static boolean hasValidMoves(Board board, Column c){
		if (c.getColor() == board.getTurn()){
			Column temp = null;
			if (board.getSelected() != null){
				temp = board.getSelected();
			}
			board.setSelected(c);
			for (int move : Move.dice){
				int x = c.getNumber() + move*c.getColor();
				if (x >= 0 && x < board.getAll().length){
					if (board.find(c.getNumber() + move*c.getColor()).isValidMove()){
						return true;
					}
				}
				
				if( board.canBearOff()){
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
			board.setSelected(null);	
			if (temp != null){
				board.setSelected(temp);
			}
		}
		return false;
	}


	public static ArrayList<PossibleMove> setPossibleMoves(Board board){
		possibleMoves.clear();
		for (Column c : board.woodColumns){
			if (hasValidMoves(board, c)){
				for (int move : Move.dice){
					int x = c.getNumber() + move*c.getColor();
					if (x >= 0 && x < board.getAll().length){
						if (board.find(c.getNumber() + move*c.getColor()).isValidMove()){
							possibleMoves.add(new PossibleMove(x,c.getNumber(),move));
						}						
					}
				}
			}
		}
		if (possibleMoves.isEmpty() && board.getWood(board.getTurn()).isEmpty() ) {
			for (Column c: board.getAll()){
				if (hasValidMoves(board, c)){
					for (int move : Move.dice){
						int x = c.getNumber() + move*c.getColor();
						if (x >= 0 && x < board.getAll().length){
							if (board.find(c.getNumber() + move*c.getColor()).isValidMove() ){
								possibleMoves.add(new PossibleMove(x,c.getNumber(),move));
							}
						}

						if (c.getColor() == Column.BLACK){
							if (x < 0 && board.canBearOff() && c.hasToBearOff() ){
								if (c.getNumber() != 0){
									possibleMoves.add(new PossibleMove(0,c.getNumber(),move));									
								}
							}
						}
						
						if (c.getColor() == Column.WHITE) {
							if (x > 25 && board.canBearOff() && c.hasToBearOff() ){
								if (c.getNumber() != 25) {
									possibleMoves.add(new PossibleMove(25, c.getNumber(), move));									
								}
							}								
						}

					}
				}
			}			
		}
/*
		if (!possibleMoves.isEmpty()){
			System.out.println("Possible moves:");
			for (PossibleMove move : possibleMoves){
				System.out.println("" + move.getFrom() + " >> " + move.getTo() + " using " + move.getDiceUsed() + ".");
			}			
		} */
		return possibleMoves;
	}
	
	public static void executeMove(Board board, PossibleMove move, boolean shareToNetwork) {
		System.out.println(board.equals(Game.gameBoard));
		//assume valid move is passed here
		Column from = board.findFrom(move);
		Column to = board.findTo(move);
		if (to.getPieces().size() == 1){
			if (from.getColor() == Column.WHITE && to.getColor() == Column.BLACK){
				board.find(Board.WOOD_BLACK).addPiece(to.RemovePiece());
			} else if (from.getColor() == Column.BLACK && to.getColor() == Column.WHITE) {
				board.find(Board.WOOD_WHITE).addPiece(to.RemovePiece());
			}
		}

		to.addPiece(from.RemovePiece());
		
		if (board.equals(Game.gameBoard)){
			Move.consumeMove(board, move,shareToNetwork);
		}
	}

	private static void consumeMove(Board board, PossibleMove move, boolean shareToNetwork) {
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
		Move.setPossibleMoves(board);
		
		if (possibleMoves.isEmpty() || (dice[0] == 0 && dice[1] == 0 )){
			if (board.equals(Game.gameBoard)){
				Game.changeTurn();				
			}
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
