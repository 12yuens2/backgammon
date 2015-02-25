package game;

import java.util.ArrayList;

public class Board {
	private Column[] columns;
	public Column[] woodColumns;
	
	public static final int EMPTY = 0;
	public static final int BLACK = -1;
	public static final int WHITE = 1;

	public static final int WOOD_WHITE = 26;
	public static final int WOOD_BLACK = 27;

	private Column selectedColumn;
	
	public Board(){
		columns = new Column[26];
		woodColumns = new Column[2];
	}
	
	public Column getSelected(){
		return selectedColumn;
	}

	public Column[] getAll(){
		return columns;
	}

	public void init(){
		for (int i = 0; i < columns.length; i++){
			columns[i] = new Column(i,this);
		}

		for (int i = 0; i < woodColumns.length; i++){
			woodColumns[0] = new Column(WOOD_BLACK,this);
			woodColumns[1] = new Column(WOOD_WHITE,this);
		}
	}

	public void addPieces(){

		columns[1].addPiece(Piece.WHITE); columns[1].addPiece(Piece.WHITE);

		columns[12].addPiece(Piece.WHITE); columns[12].addPiece(Piece.WHITE);
		columns[12].addPiece(Piece.WHITE); columns[12].addPiece(Piece.WHITE);
		columns[12].addPiece(Piece.WHITE);

		columns[17].addPiece(Piece.WHITE); columns[17].addPiece(Piece.WHITE);
		columns[17].addPiece(Piece.WHITE);

		columns[19].addPiece(Piece.WHITE); columns[19].addPiece(Piece.WHITE);
		columns[19].addPiece(Piece.WHITE); columns[19].addPiece(Piece.WHITE);
		columns[19].addPiece(Piece.WHITE);
		
		columns[6].addPiece(Piece.BLACK); columns[6].addPiece(Piece.BLACK);
		columns[6].addPiece(Piece.BLACK); columns[6].addPiece(Piece.BLACK);
		columns[6].addPiece(Piece.BLACK);
		
		columns[8].addPiece(Piece.BLACK); columns[8].addPiece(Piece.BLACK);
		columns[8].addPiece(Piece.BLACK);

		columns[13].addPiece(Piece.BLACK); columns[13].addPiece(Piece.BLACK);
		columns[13].addPiece(Piece.BLACK); columns[13].addPiece(Piece.BLACK);
		columns[13].addPiece(Piece.BLACK);

		columns[24].addPiece(Piece.BLACK); columns[24].addPiece(Piece.BLACK);

	}
	
	public Column findFrom(PossibleMove move){
		if (move.getFrom() == 0){
			return woodColumns[1];
		} else if (move.getFrom() == 25){
			return woodColumns[0];
		} else {
			return columns[move.getFrom()];
		}
	}
	
	public Column findTo(PossibleMove move){
		return columns[move.getTo()];
	}
	
	public Column find(int i) {
		if (i >= 0 && i < columns.length){
			return columns[i];			
		} else if (i == WOOD_BLACK){
			return woodColumns[0];
		} else if (i == WOOD_WHITE){
			return woodColumns[1];
		} else {
			return null;
		}

	}
				
	public boolean canBearOff(){
		boolean canBearOff = true;
		if (selectedColumn.getColor() == Column.BLACK){
			for (Column c : columns){
				if (c.getNumber() >= 7 && c.getColor() == Column.BLACK){
					canBearOff = false;
				}
			}
			for (Column w : woodColumns){
				if (w.getColor() == Column.BLACK){
					canBearOff = false;
				}
			}
		} else {
			for (Column c : columns){
				if (c.getNumber() <= 18 && c.getColor() == Column.WHITE){
					canBearOff = false;
				}
			}
			for (Column w : woodColumns){
				if (w.getColor() == Column.WHITE){
					canBearOff = false;
				}
			}
		}
		return canBearOff;
	}
	

	public void setHighlighted(int from) {
		for (Column c : columns){
			for (PossibleMove move : Move.possibleMoves){
				if (c.getNumber() == move.getTo() && move.getFrom() == from){
					c.isHighlighted = true;
				}
			}
		}
	}
	
	public boolean hasWoodMoves() {
		for (Column c: woodColumns) {
			if (Move.hasValidMoves(this, c)) {
				return true;
			}
		}
		return false;
	}

	public void unSelect() {
		for (Column c : columns){
			c.isSelected = false;
			c.isHighlighted = false;
		}
		for (Column c : woodColumns){
			c.isSelected = false;
			c.isHighlighted = false;
		}
		selectedColumn = null;
		Game.gameWindow.repaint();
	}

	public void setSelected(Column column) {
		this.selectedColumn = column;
	}
		
}
