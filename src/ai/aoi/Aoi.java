package ai.aoi;

import java.util.ArrayList;

import game.Column;
import game.Game;
import game.Move;
import ai.AI;

public class Aoi extends AI{
	
	public static final int SINGLE_SAME_PIECE_VALUE = -10;
	public static final int SINGLE_DIFF_PIECE_VALUE = -100;
	public static final int PAIR_VALUE = 50;
	public static final int GREATER_THAN_PAIR_VALUE = 25;
	public static final int END_VALUE = 75;
	public static final int WOOD_VALUE = -50;
	
	private ArrayList<PossibleBoard> possibleBoards = new ArrayList<>();
	private int[] currentBoard;
	
	public Aoi(){
		
	}
	
	public int[] getBoardState() {
		int[] boardState = new int[Column.getAll().length + 2];
		for (Column c: Column.getAll()) {
			
			boardState[c.getNumber()] = c.getNumber()*c.getColor();
			
			if (c.getPieces().size() == 1 && c.getColor() == Game.turn) {
				boardState[c.getNumber()] += SINGLE_SAME_PIECE_VALUE; 
			}
			if (c.getPieces().size() == 1 && c.getColor() != Game.turn) {
				boardState[c.getNumber()] += SINGLE_DIFF_PIECE_VALUE;
			}
			if (c.getPieces().size() == 2 && c.getColor() == Game.turn) {
				boardState[c.getNumber()] += PAIR_VALUE;
			}
			if (c.getPieces().size() > 2 && c.getColor() == Game.turn) {
				boardState[c.getNumber()] += GREATER_THAN_PAIR_VALUE;
			}
			if (c.getNumber() == 0 || c.getNumber() == 25) {
				boardState[c.getNumber()] += END_VALUE;
			}
		}
		for (int i = 0; i <=1; i++){
			if (Column.woodColumns[i].hasPieces() && Column.woodColumns[i].getColor() == Game.turn){
				boardState[Column.getAll().length + i] += WOOD_VALUE*Column.woodColumns[i].getPieces().size();
			}
		}
		return boardState;
	}

	public void evaluateBoard() {
		
		currentBoard = getBoardState();
		possibleBoards = new ArrayList<>();
		
		for (Integer[] move: Move.getValidMoves()) {
			int[] board = getNewBoardState(move);
			possibleBoards.add(new PossibleBoard(board, move));
		}
		
		PossibleBoard bestBoard = null;
		int bestBoardValue = Integer.MIN_VALUE;
		for (PossibleBoard board: possibleBoards) {
			if (board.getValue() > bestBoardValue) {
				bestBoardValue = board.getValue();
				bestBoard = board;
			}
		}
		//System.out.println("" + bestBoard.getMoves()[0] + " , " + bestBoard.getMoves()[1]);
		Move.executeMove(bestBoard.getMoves());
	}

	private int[] getNewBoardState(Integer[] move) {
		int[] newBoard = currentBoard.clone();
		
		Column.selectedColumn = Column.find(move[1]);
		
		if (Column.selectedColumn.isWoodColumn()){
			newBoard[move[1]] -= WOOD_VALUE;
		} else {
			//change FROM column value
			if (Column.selectedColumn.getPieces().size() == 1){
				//lone piece moving somewhere
				newBoard[move[1]] -= SINGLE_SAME_PIECE_VALUE;				
			}
			if (Column.selectedColumn.getPieces().size() == 2){
				newBoard[move[1]] -= PAIR_VALUE;
				newBoard[move[1]] += SINGLE_SAME_PIECE_VALUE;
			}
			if (Column.selectedColumn.getPieces().size() == 3){
				newBoard[move[1]] -= GREATER_THAN_PAIR_VALUE;
				newBoard[move[1]] += PAIR_VALUE;
			}			
		}		
		
		//change TO column value
		if (Column.find(move[0]).getPieces().size() == 0){
			//lone piece moving somewhere
			newBoard[move[0]] += SINGLE_SAME_PIECE_VALUE;				
		}
		if (Column.find(move[0]).getPieces().size() == 1){
			if (Column.find(move[0]).getColor() != Game.turn){
				newBoard[move[0]] -= SINGLE_DIFF_PIECE_VALUE;
			} else {
				newBoard[move[0]] += PAIR_VALUE;
				newBoard[move[0]] -= SINGLE_SAME_PIECE_VALUE;				
			}
		}
		if (Column.find(move[0]).getPieces().size() == 2){
			newBoard[move[0]] += GREATER_THAN_PAIR_VALUE;
			newBoard[move[0]] -= PAIR_VALUE;
		}			
		
		Column.selectedColumn = null;
		return newBoard;
	}

}
