package ai.miki;

import game.Board;
import game.Column;
import game.Game;
import game.Move;
import game.PossibleMove;

import java.util.ArrayList;

public class FutureBoard extends Board {

	private int pips, opponentPips;
	ArrayList<FutureBoard> futureBoards;	
	private int playerColor;
	public static final int MAX_DEPTH = 4;

	public FutureBoard(Board board, int depth, int playerColor) {
		super(board.getTurn());
		this.init();
		this.playerColor = playerColor;
		for (int i = 0; i < columns.length; i++){
			for (int j = 0; j < board.getAll()[i].getPieces().size() - 1; j++){
				columns[i].addPiece(board.getAll()[i].getColor());
			}
		}

		for (int i = 0; i < woodColumns.length; i++){
			for (int j = 0; j < board.getAll()[i].getPieces().size(); j++){
				woodColumns[i].addPiece(board.getAll()[i].getPieces().get(0).getColor());
			}
		}

		ArrayList<PossibleMove> moves = new ArrayList<>();
		
		calculatePips();
		if (depth == 1){
			Move.setPossibleMoves(this);
			for (PossibleMove move : Move.possibleMoves){
				moves.add(move.clone());
			}
			for (PossibleMove move : moves){
				FutureBoard possibleBoard = new FutureBoard(this, depth + 1, playerColor);
				possibleBoard.calculatePips();
				System.out.println("EXECUTING IMAGINARY MOVE");	
				Move.executeMove(possibleBoard, move, false);
				System.out.println("EXECUTED IMAGINARY MOVE");
				Game.gameWindow.repaint();
			}
		} else if (depth < MAX_DEPTH) {
			for (int i = 1; i <= 6; i++){
				for (int j = 1; j <= 6; j++){
					int[] tempDice = {i,j};
					Move.setDice(this, tempDice);
					Move.setPossibleMoves(this);
					for (PossibleMove move : moves){
						FutureBoard possibleBoard = new FutureBoard(this, depth + 1, playerColor);
						possibleBoard.calculatePips();
						System.out.println("EXECUTING IMAGINARY MOVE");	
						Move.executeMove(possibleBoard, move, false);
						System.out.println("EXECUTED IMAGINARY MOVE");
						Game.gameWindow.repaint();

					}
				}
			}
		}
	}

	public void calculatePips(){
		for (int i = 1; i < columns.length -1; i++){
			if (columns[i].getColor() == Board.BLACK){
				if (playerColor == Board.BLACK){
					pips += columns[i].getNumber()*columns[i].getPieces().size();
				} else {
					opponentPips += columns[i].getNumber()*columns[i].getPieces().size();
				}
			} else if (columns[i].getColor() == Board.WHITE){
				if (playerColor == Board.WHITE){
					pips += (25 - columns[i].getNumber())*columns[i].getPieces().size();
				} else {
					opponentPips += (25 - columns[i].getNumber())*columns[i].getPieces().size();
				}
			}
		}
	}

}
