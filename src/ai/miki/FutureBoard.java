package ai.miki;

import game.Board;
import game.Column;
import game.Game;
import game.Move;
import game.PossibleMove;

import java.util.ArrayList;

public class FutureBoard extends Board {

	public static final int SINGLE_COST = 15;
	public static final int PAIR_COST = 15;
	public static final int GREATER_THAN_COST = 1250000;
	public static final int WOOD_COST = 100;
	public static final int LENGTH_COST = 250000;
	
	public static final int SINGLE_SAME_PIECE_VALUE = -25;
	public static final int SINGLE_DIFF_PIECE_VALUE = 100;
	public static final int PAIR_VALUE = 50;
	public static final int GREATER_THAN_PAIR_VALUE = -100;
	public static final int END_VALUE = 75;
	public static final int WOOD_VALUE = -50;
	public static final int OTHER_WOOD_VALUE = 300;
	
	private int pips, opponentPips;
	private int cost, opponentCost;
	ArrayList<FutureBoard> futureBoards;
	private int playerColor;
	private PossibleMove boardMove;
	
	private int boardValue;
	
	public static final int MAX_DEPTH = 2;
	public static int NUMBEROFBOARDS;
	
	public FutureBoard(PossibleMove boardMove, Board board, int depth, int playerColor) {
		super(board.getTurn());
		this.init();
		this.boardMove = boardMove;
		this.addPieces(board);
		futureBoards = new ArrayList<>();
		Move.setDice(this, board.getDice());
		Move.executeMove(this, boardMove, false);
		NUMBEROFBOARDS++;
		this.playerColor = playerColor;



		calculateValue();
		calculatePips();
		calculateCost();
		makeFutureBoards(depth);

	}

	public void calculateValue() {
		boardValue = 0;
		for (Column c: this.getAll()) {
			
		//	System.out.println("Column " + c.getNumber() + "has " + c.getPieces().size() + " pieces.");
			
			boardValue += c.getPieces().size()*c.getNumber()*c.getColor()*20;
			
			if (c.getPieces().size() == 1 && c.getColor() == this.getTurn()) {
				boardValue += SINGLE_SAME_PIECE_VALUE; 
			}
			if (c.getPieces().size() == 1 && c.getColor() != this.getTurn()) {
				boardValue += SINGLE_DIFF_PIECE_VALUE;
			}
			if (c.getPieces().size() == 2 && c.getColor() == this.getTurn()) {
				boardValue += PAIR_VALUE;
			}
			if (c.getPieces().size() > 2 && c.getColor() == this.getTurn()) {
				boardValue += GREATER_THAN_PAIR_VALUE;
			}
			if (c.getNumber() == 0 || c.getNumber() == 25) {
				boardValue += END_VALUE;
			}
		}
		for (int i = 0; i <=1; i++){
			if (this.woodColumns[i].hasPieces() && this.woodColumns[i].getColor() == this.getTurn()){
				boardValue += WOOD_VALUE*this.woodColumns[i].getPieces().size();
			}
			if (this.woodColumns[i].hasPieces() && this.woodColumns[i].getColor() != this.getTurn()){
				boardValue += OTHER_WOOD_VALUE*this.woodColumns[i].getPieces().size();
			}
		}
	}
	
	private void makeFutureBoards(int depth) {
		ArrayList<PossibleMove> moves = new ArrayList<>();
		if (depth < MAX_DEPTH) {
			for (int i = 1; i <= 6; i++){
				for (int j = 1; j <= 6; j++){
					int[] tempDice = {i,j};
					Move.setDice(this, tempDice);
					Move.setPossibleMoves(this);
					for (PossibleMove move : Move.possibleMoves){
						moves.add(move.clone());
					}
					for (PossibleMove move : moves){
						FutureBoard possibleBoard = new FutureBoard(this.boardMove,this, depth + 1, playerColor);
						this.futureBoards.add(possibleBoard);
						Move.executeMove(possibleBoard, move, false);
						Game.gameWindow.repaint();

					}
				}
			}
		}
	}

	public void calculatePips(){
		for (int i = 1; i < columns.length - 1; i++){
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
	
	public void calculateCost(){
		for (Column c : columns){
			if (c.getPieces().size() > 0){
				if (this.playerColor == BLACK){
					addCost(c.getColor(),c.getPieces().size()*(25-c.getNumber())*LENGTH_COST);					
				} else {
					addCost(c.getColor(),c.getPieces().size()*(c.getNumber())*LENGTH_COST);
				}

			}
			if (c.getPieces().size()  == 1){
				addCost(c.getColor(),SINGLE_COST);
			}
			if (c.getPieces().size() == 2){
				addCost(c.getColor(),PAIR_COST);
			}
			if (c.getPieces().size() > 2){
				addCost(c.getColor(),GREATER_THAN_COST);
			}
		}
		for (Column c : woodColumns){
			if (c.getPieces().size() > 0){
				addCost(c.getColor(),WOOD_COST*c.getPieces().size());
			}
		}
	}

	public void addCost(int color, int cost){
		if (color == this.playerColor){
			this.cost += cost;
		} else {
			this.opponentCost += cost;
		}
	}
	
	public PossibleMove getMove() {
		return boardMove;
	}

	public double evaluate() {
		double childValue = 0;
		if(!futureBoards.isEmpty()){
			System.out.println("\tEvaluating children of "+ this);
			System.out.println(futureBoards.size());
			for (FutureBoard board : futureBoards){
				childValue += board.evaluate();
			}
			childValue = (childValue/(1.0*futureBoards.size()));
			boardValue += childValue;
		}
		return boardValue;
		/*(ArrayList<FutureBoardData> data = new ArrayList<>();
		if (!futureBoards.isEmpty()){
			for (FutureBoard board: futureBoards){
				data.add(board.evaluate());
			}			
		}

		double pipRatio = opponentPips/(1.0*pips);
		double costRatio = opponentCost/(1.0*cost);
		
		if (!data.isEmpty()){
			double futurePips = 0;
			double futureCosts = 0;
			for (FutureBoardData fBoardData : data){
				futurePips += fBoardData.getPipRatio();
				futureCosts += fBoardData.getCostRatio();
			}
			
			futurePips = futurePips/(1.0*data.size());
			futureCosts = futureCosts/(1.0*data.size());
			
			pipRatio = Math.sqrt(pipRatio*futurePips);
			costRatio = Math.sqrt(costRatio*futureCosts);			
		}
		
		return new FutureBoardData(pipRatio,costRatio,1);*/
	}
}
