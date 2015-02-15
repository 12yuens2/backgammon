package ai.aoi;

public class PossibleBoard {

	private int[] boardState;
	private Integer[] moves;
	
	public int[] getBoardState() {
		return boardState;
	}
	public void setBoardState(int[] boardState) {
		this.boardState = boardState;
	}
	public Integer[] getMoves() {
		return moves;
	}
	public void setMoves(Integer[] moves) {
		this.moves = moves;
	}
	
	public PossibleBoard(int[] boardState, Integer[] move) {
		this.boardState = boardState;
		this.moves = move;
	}
	public int getValue() {
		int value = 0;
		for (int i : boardState){
			value += i;
		}
		return value;
	}
	
	

}
