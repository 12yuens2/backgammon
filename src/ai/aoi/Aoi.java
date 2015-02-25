package ai.aoi;

import java.util.ArrayList;

import game.Column;
import game.Game;
import game.Move;
import game.PossibleMove;
import ai.AI;

public class Aoi implements AI{
	
	public static final int SINGLE_SAME_PIECE_VALUE = -10;
	public static final int SINGLE_DIFF_PIECE_VALUE = -100;
	public static final int PAIR_VALUE = 25;
	public static final int GREATER_THAN_PAIR_VALUE = 10;
	public static final int END_VALUE = 75;
	public static final int WOOD_VALUE = -50;
	
	private ArrayList<PossibleBoard> possibleBoards = new ArrayList<>();
	private int[] currentBoard;
	
	public Aoi(){
		
	}

	public void makeMove() {
		
		currentBoard = getBoardState();
		possibleBoards = new ArrayList<>();
		
		for (PossibleMove move : Move.possibleMoves) {
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
		Move.executeMove(Game.gameBoard,bestBoard.getMove(),true);
	}
	
	public int[] getBoardState() {
		int[] boardState = new int[Game.gameBoard.getAll().length + 2];
		for (Column c: Game.gameBoard.getAll()) {
			
			boardState[c.getNumber()] = c.getPieces().size()*c.getNumber()*c.getColor()*25;
			
			if (c.getPieces().size() == 1 && c.getColor() == Game.gameBoard.getTurn()) {
				boardState[c.getNumber()] += SINGLE_SAME_PIECE_VALUE; 
			}
			if (c.getPieces().size() == 1 && c.getColor() != Game.gameBoard.getTurn()) {
				boardState[c.getNumber()] += SINGLE_DIFF_PIECE_VALUE;
			}
			if (c.getPieces().size() == 2 && c.getColor() == Game.gameBoard.getTurn()) {
				boardState[c.getNumber()] += PAIR_VALUE;
			}
			if (c.getPieces().size() > 2 && c.getColor() == Game.gameBoard.getTurn()) {
				boardState[c.getNumber()] += GREATER_THAN_PAIR_VALUE;
			}
			if (c.getNumber() == 0 || c.getNumber() == 25) {
				boardState[c.getNumber()] += END_VALUE;
			}
		}
		for (int i = 0; i <=1; i++){
			if (Game.gameBoard.woodColumns[i].hasPieces() && Game.gameBoard.woodColumns[i].getColor() == Game.gameBoard.getTurn()){
				boardState[Game.gameBoard.getAll().length + i] += WOOD_VALUE*Game.gameBoard.woodColumns[i].getPieces().size();
			}
		}
		return boardState;
	}

	private int[] getNewBoardState(PossibleMove move) {
		int[] newBoard = currentBoard.clone();
		
		int fromIndex = move.getFrom();
		int toIndex = move.getTo();
		
		Game.gameBoard.setSelected(Game.gameBoard.find(fromIndex));
		
		if (Game.gameBoard.getSelected().isWoodColumn()){
			newBoard[fromIndex] -= WOOD_VALUE;
		} else {
			//change FROM column value
			if (Game.gameBoard.getSelected().getPieces().size() == 1){
				//lone piece moving somewhere
				newBoard[fromIndex] -= SINGLE_SAME_PIECE_VALUE;				
			}
			if (Game.gameBoard.getSelected().getPieces().size() == 2){
				newBoard[fromIndex] -= PAIR_VALUE;
				newBoard[fromIndex] += SINGLE_SAME_PIECE_VALUE;
			}
			if (Game.gameBoard.getSelected().getPieces().size() == 3){
				newBoard[fromIndex] -= GREATER_THAN_PAIR_VALUE;
				newBoard[fromIndex] += PAIR_VALUE;
			}			
		}		
		
		//change TO column value
		if (Game.gameBoard.find(toIndex).getPieces().size() == 0){
			//lone piece moving somewhere
			newBoard[toIndex] += SINGLE_SAME_PIECE_VALUE;				
		}
		if (Game.gameBoard.find(toIndex).getPieces().size() == 1){
			if (Game.gameBoard.find(toIndex).getColor() != Game.gameBoard.getTurn()){
				newBoard[toIndex] -= SINGLE_DIFF_PIECE_VALUE;
			} else {
				newBoard[toIndex] += PAIR_VALUE;
				newBoard[toIndex] -= SINGLE_SAME_PIECE_VALUE;				
			}
		}
		if (Game.gameBoard.find(toIndex).getPieces().size() == 2){
			newBoard[toIndex] += GREATER_THAN_PAIR_VALUE;
			newBoard[toIndex] -= PAIR_VALUE;
		}			
		
		Game.gameBoard.setSelected(null);
		return newBoard;
	}
}
