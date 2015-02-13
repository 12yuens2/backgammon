package game;

import gui.ColumnPanel;

import java.util.ArrayList;

public class Column {

	public static final int EMPTY = -1;
	public static final int BLACK = 1;
	public static final int WHITE = 0;

	public static final int WOOD_WHITE = -2;
	public static final int WOOD_BLACK = -3;

	private static Column[] columns = new Column[26];
	private static Column[] woodColumns = new Column[2]; // 0 - black, 1 - white

	private static Column selectedColumn;

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
		boolean b = this.pieces.get(0).getColor();
		this.pieces.remove(this.pieces.get(0));
		return b;
	}

	public static Column find(int i) {
		if (i >= 0){
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
				(Column.selectedColumn.getColor() == WHITE && this.number > Column.selectedColumn.number) ||
				(Column.selectedColumn.getColor() == BLACK && this.number < Column.selectedColumn.number) ||
				(Column.selectedColumn.isWoodColumn())
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
	
	public boolean isValidMove(){
		return ( this.isForwardMove() && !this.isWoodColumn() && this.isLegalMove() && (		
				this.isEmpty() || this.matchesColor() || (!this.matchesColor() && this.isCapturable())
				)
		);
	}
	
	public int getMoveNumber(){
			return Math.abs(Column.selectedColumn.getNumber() - this.getNumber());
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
				System.out.println("moving piece");
				this.addPiece(Column.selectedColumn.RemovePiece());
				Column.selectedColumn.unSelect();
				
				Move.consumeMove(moveUsed);
			}

		} else if (Column.selectedColumn == null && this.hasPieces() && Game.turn == this.getColor()){
			Column.selectedColumn = this;
			this.isSelected = true;
			Column.setHighlighted();
		}
	}

	private static void setHighlighted() {
		for (Column c: Column.getAll()){
			if (c.isValidMove()){
				c.isHighlighted = true;
			}
		}
	}

	private boolean isWoodColumn() {
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
