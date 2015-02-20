package game;

import gui.game.ColumnPanel;

import java.awt.Color;
import java.util.ArrayList;

public class Column {

	public static final int EMPTY = 0;
	public static final int BLACK = -1;
	public static final int WHITE = 1;

	public static final int WOOD_WHITE = 26;
	public static final int WOOD_BLACK = 27;

	private static Column[] columns = new Column[26];
	public static Column[] woodColumns = new Column[2]; // 0 - black, 1 - white

	public static Column selectedColumn;

	ArrayList<Piece> pieces;
	int number;
	boolean isSelected;
	public boolean isHighlighted;
	public ColumnPanel panel;

	public Column(int i) {
		pieces = new ArrayList<>();		
		this.number = i;
	}

	public static Column[] getAll(){
		return columns;
	}

	public static void init(){
		for (int i = 0; i < columns.length; i++){
			columns[i] = new Column(i);
		}

		for (int i = 0; i < woodColumns.length; i++){
			woodColumns[0] = new Column(Column.WOOD_BLACK);
			woodColumns[1] = new Column(Column.WOOD_WHITE);
		}
	}

	public static void addPieces(){
		//testing

		columns[1].addPiece(Piece.WHITE);
		columns[1].addPiece(Piece.WHITE);

		columns[12].addPiece(Piece.WHITE);
		columns[12].addPiece(Piece.WHITE);
		columns[12].addPiece(Piece.WHITE);
		columns[12].addPiece(Piece.WHITE);
		columns[12].addPiece(Piece.WHITE);

		columns[17].addPiece(Piece.WHITE);
		columns[17].addPiece(Piece.WHITE);
		columns[17].addPiece(Piece.WHITE);

		columns[19].addPiece(Piece.WHITE);
		columns[19].addPiece(Piece.WHITE);
		columns[19].addPiece(Piece.WHITE);
		columns[19].addPiece(Piece.WHITE);
		columns[19].addPiece(Piece.WHITE);

		columns[6].addPiece(Piece.BLACK);
		columns[6].addPiece(Piece.BLACK);
		columns[6].addPiece(Piece.BLACK);
		columns[6].addPiece(Piece.BLACK);
		columns[6].addPiece(Piece.BLACK);
		
		columns[8].addPiece(Piece.BLACK);
		columns[8].addPiece(Piece.BLACK);
		columns[8].addPiece(Piece.BLACK);

		columns[13].addPiece(Piece.BLACK);
		columns[13].addPiece(Piece.BLACK);
		columns[13].addPiece(Piece.BLACK);
		columns[13].addPiece(Piece.BLACK);
		columns[13].addPiece(Piece.BLACK);

		columns[24].addPiece(Piece.BLACK);
		columns[24].addPiece(Piece.BLACK);

	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}

	public void addPiece(boolean b){
		this.pieces.add(new Piece(b));
	}

	public boolean RemovePiece(){
		if (this.pieces.size() > 0){
			boolean b = this.pieces.get(0).getColor();
			this.pieces.remove(this.pieces.get(0));
			return b;			
		}
		return false;
	}

	public static Column find(int i) {
		if (i >= 0 && i < columns.length){
			return columns[i];			
		} else if (i == Column.WOOD_BLACK){
			return woodColumns[0];
		} else if (i == Column.WOOD_WHITE){
			return woodColumns[1];
		} else {
			return null;
		}

	}

	public int getNumber() {
		if (this.number == Column.WOOD_BLACK){
			return 25;
		} else if (this.number == Column.WOOD_WHITE){
			return 0;
		} else {
			return number;			
		}
	}

	public boolean hasPieces(){
		return !pieces.isEmpty();
	}

	/*
	 * -1 is the error
	 * rest is boolean
	 */
	public int getColor(){
		if (this.pieces.isEmpty()){
			return EMPTY;
		} else {
			if (this.pieces.get(0).getColor() == Piece.BLACK){
				return BLACK;
			} else {
				return WHITE;
			}
		}
	}

	public boolean matchesColor(){
		return Column.selectedColumn.getColor() == this.getColor();
	}
	
	public boolean isEmpty(){
		return (this.getColor() == Column.EMPTY);
	}
	
	public boolean isCapturable(){
		return (this.getPieces().size() == 1);
	}
	
	public boolean isForwardMove(){
		
		
		return (
				((this.getNumber() == 25 || this.getNumber() == 0) && this.canBearOff() || (this.getNumber() != 25 && this.getNumber() != 0)) && (					
					(Column.selectedColumn.getColor() == WHITE && this.number > Column.selectedColumn.number) ||
					(Column.selectedColumn.getColor() == BLACK && this.number < Column.selectedColumn.number) ||
					(Column.selectedColumn.isWoodColumn())
				)
		);
	}
		
	public boolean isLegalMove(){
		for (int move : Move.dice){
			if (move == this.getMoveNumber()){
				return true;
			}
		}
		return false;
	}
	
	private boolean hasToBearOff() {
		System.out.println(this.getNumber());
		if (this.getNumber() == 0){
			if (Column.selectedColumn.getNumber() > Math.max(Move.dice[0], Move.dice[1])){
				for (int i = 6; i < Column.selectedColumn.getNumber();i--){
					return true;
				}
			}
		} else if (this.getNumber() == 25){
			return true;
		} else {
			return false;
		}
		return false;
	}

	public boolean canBearOff(){
		boolean canBearOff = true;
		if (Column.selectedColumn.getColor() == Column.BLACK){
			for (Column c : Column.getAll()){
				if (c.getNumber() >= 7 && c.getColor() == Column.BLACK){
					canBearOff = false;
				}
			}
			for (Column w : Column.woodColumns){
				if (w.getColor() == Column.BLACK){
					canBearOff = false;
				}
			}
		} else {
			for (Column c : Column.getAll()){
				if (c.getNumber() <= 18 && c.getColor() == Column.WHITE){
					canBearOff = false;
				}
			}
			for (Column w : Column.woodColumns){
				if (w.getColor() == Column.WHITE){
					canBearOff = false;
				}
			}
		}
		return canBearOff;
	}
	
	public boolean isValidMove(){
		return ( this.isForwardMove() && !this.isWoodColumn() && this.isLegalMove() && (		
				this.isEmpty() || this.matchesColor() || (!this.matchesColor() && this.isCapturable())
				)
		);
	}
	
	public int getMoveNumber(){
		if (Column.selectedColumn == Column.find(WOOD_BLACK)){
			return 25 - this.getNumber();
		} else if (Column.selectedColumn == Column.find(WOOD_WHITE)){
			return this.getNumber();
		} else {
			return Math.abs(Column.selectedColumn.getNumber() - this.getNumber());			
		}
	}
	
	public void select(){
		Game.gameWindow.repaint();
			if (Column.selectedColumn != this && Column.selectedColumn != null && Game.turn == Column.selectedColumn.getColor() ){
				if (this.isValidMove()){
					if (this.isCapturable() && !this.matchesColor()){
						if (this.getColor() == Column.BLACK){
							Column.find(Column.WOOD_BLACK).addPiece(this.RemovePiece());
						} else {
							Column.find(Column.WOOD_WHITE).addPiece(this.RemovePiece());
						}
					}	

					int moveUsed = this.getMoveNumber();
					this.addPiece(Column.selectedColumn.RemovePiece());

					Move.consumeMove(Column.selectedColumn.getNumber(),this.getNumber());
					Column.selectedColumn.unSelect();

				}

			} else if (Column.selectedColumn == null && this.hasPieces() && Game.turn == this.getColor()){
				if (
						(this.getColor() == Column.BLACK && Game.blackIsHuman) ||
						(this.getColor() == Column.WHITE && Game.whiteIsHuman)
						){
					Column.selectedColumn = this;
					this.isSelected = true;
					Column.setHighlighted();					
				}

			}			
	}

	private static void setHighlighted() {
		for (Column c: Column.getAll()){
			if (c.isValidMove()){
				c.isHighlighted = true;
			}
		}
	}

	public boolean isWoodColumn() {
		for (Column c: Column.woodColumns){
			if (this.equals(c)){
				return true;
			}
		}
		return false;
	}

	public boolean isSelected(){
		return isSelected;
	}

	public void unSelect() {
		panel.repaint();
		Column.setUnHighlighted();
		this.isSelected = false;
		Column.selectedColumn = null;
	}

	private static void setUnHighlighted() {
		for (Column c: Column.getAll()){
			c.isHighlighted = false;
			c.panel.repaint();
		}

	}
}
