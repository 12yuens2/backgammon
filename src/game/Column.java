package game;

import java.util.ArrayList;

public class Column {
	
	public static final int EMPTY = -1;
	public static final int BLACK = 1;
	public static final int WHITE = 0;
	
	private static Column[] columns = new Column[24];

	private static Column selectedColumn;
	
	ArrayList<Piece> pieces;
	int number;
	boolean isSelected;
	
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
		
		columns[0].AddPiece(Piece.WHITE);
		columns[0].AddPiece(Piece.WHITE);
		
		columns[11].AddPiece(Piece.WHITE);
		columns[11].AddPiece(Piece.WHITE);
		columns[11].AddPiece(Piece.WHITE);
		columns[11].AddPiece(Piece.WHITE);
		columns[11].AddPiece(Piece.WHITE);
		
		columns[16].AddPiece(Piece.WHITE);
		columns[16].AddPiece(Piece.WHITE);
		columns[16].AddPiece(Piece.WHITE);
		
		columns[18].AddPiece(Piece.WHITE);
		columns[18].AddPiece(Piece.WHITE);
		columns[18].AddPiece(Piece.WHITE);
		columns[18].AddPiece(Piece.WHITE);
		columns[18].AddPiece(Piece.WHITE);
		
		columns[5].AddPiece(Piece.BLACK);
		columns[5].AddPiece(Piece.BLACK);
		columns[5].AddPiece(Piece.BLACK);
		columns[5].AddPiece(Piece.BLACK);
		columns[5].AddPiece(Piece.BLACK);
		
		columns[7].AddPiece(Piece.BLACK);
		columns[7].AddPiece(Piece.BLACK);
		columns[7].AddPiece(Piece.BLACK);
		
		columns[12].AddPiece(Piece.BLACK);
		columns[12].AddPiece(Piece.BLACK);
		columns[12].AddPiece(Piece.BLACK);
		columns[12].AddPiece(Piece.BLACK);
		columns[12].AddPiece(Piece.BLACK);
		
		columns[23].AddPiece(Piece.BLACK);
		columns[23].AddPiece(Piece.BLACK);
		
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
	
	public void AddPiece(boolean b){
		this.pieces.add(new Piece(b));
	}
	
	public boolean RemovePiece(){
		boolean b = this.pieces.get(0).getColor();
		this.pieces.remove(this.pieces.get(0));
		return b;
	}
	
	public static Column find(int i) {
		return columns[i - 1];
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
		if (Column.selectedColumn != this && Column.selectedColumn != null && Game.turn == Column.selectedColumn.getColor()){
			if ( (Column.selectedColumn.getColor() == WHITE && this.number > Column.selectedColumn.number) ||
			(Column.selectedColumn.getColor() == BLACK && this.number < Column.selectedColumn.number) ){
			
				if (this.getColor() == Column.EMPTY || Column.selectedColumn.getColor() == this.getColor() ){
					System.out.println("moving piece");
					this.AddPiece(Column.selectedColumn.RemovePiece());
					Column.selectedColumn.unSelect();
					Game.changeTurn();
				} else if (Column.selectedColumn.getColor() != this.getColor() && this.pieces.size() == 1){
					this.RemovePiece(); //send to wood
					this.AddPiece(Column.selectedColumn.RemovePiece());
					Column.selectedColumn.unSelect();	
					Game.changeTurn();
				}	
			}
		} else if (Column.selectedColumn == null && this.hasPieces() && Game.turn == this.getColor()){
			Column.selectedColumn = this;			
			this.isSelected = true;
		}
	}
	
	public boolean isSelected(){
		return isSelected;
	}

	public void unSelect() {
		this.isSelected = false;
		Column.selectedColumn = null;
	}
}
