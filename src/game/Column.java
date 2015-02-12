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
//		this.isSelected = true;
	}

	public static Column[] getAll(){
		return columns;
	}
	
	public static void init(){
		for (int i = 0; i < columns.length; i++){
			columns[i] = new Column(i+1);
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
			return columns[i - 1];			
		} else if (i == Column.WOOD_BLACK){
			return woodColumns[0];
		} else if (i == Column.WOOD_WHITE){
			return woodColumns[1];
		} else {
			return null;
		}

	}

	public int getNumber() {
		return number;
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
	
	public void select(){
		Game.gameWindow.repaint();
		if (Column.selectedColumn != this && Column.selectedColumn != null && Game.turn == Column.selectedColumn.getColor() ){
			if ( 	(Column.selectedColumn.getColor() == WHITE && this.number > Column.selectedColumn.number) ||
					(Column.selectedColumn.getColor() == BLACK && this.number < Column.selectedColumn.number) || 
					(Column.selectedColumn.isWoodColumn())
			){
			
				if (!this.isWoodColumn() && (this.getColor() == Column.EMPTY || Column.selectedColumn.getColor() == this.getColor() ) ){
					System.out.println("moving piece");
					this.addPiece(Column.selectedColumn.RemovePiece());
					Column.selectedColumn.unSelect();
					Game.changeTurn();
				} else if (Column.selectedColumn.getColor() != this.getColor() && this.pieces.size() == 1){
					if (this.getColor() == Column.BLACK){
						Column.find(Column.WOOD_BLACK).addPiece(this.RemovePiece());
					} else {
						Column.find(Column.WOOD_WHITE).addPiece(this.RemovePiece());
					}
					
					this.addPiece(Column.selectedColumn.RemovePiece());
					Column.selectedColumn.unSelect();	
					Game.changeTurn();
				}	
			}
		} else if (Column.selectedColumn == null && this.hasPieces() && Game.turn == this.getColor()){
			Column.selectedColumn = this;
			this.isSelected = true;
			Column.setHighlighted();
		}
	}
	
	private static void setHighlighted() {
		if (Column.selectedColumn.getColor() == Column.WHITE){
			for (Column c : Column.getAll()){
				for (int dice : Move.dice){
					if (Column.selectedColumn.getNumber() + dice == c.number){
						c.isHighlighted = true;
					} else {
						c.isHighlighted = false;
					}
				}
			}
		} else if (Column.selectedColumn.getColor() == Column.BLACK){
			for (Column c : Column.getAll()){
				for (int dice : Move.dice){
					if (Column.selectedColumn.getNumber() - dice == c.number){
						c.isHighlighted = true;
					} else {
						c.isHighlighted = false;
					}
				}
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
		for (Column c: woodColumns){
			c.panel.repaint();
		}
		this.isSelected = false;
		Column.selectedColumn = null;
	}
}
