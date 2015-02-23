package game;

public class PossibleMove {
	private int to,from,diceUsed;

	public PossibleMove(int to, int from, int diceUsed) {
		this.to = to;
		this.from = from;
		this.diceUsed = diceUsed;
	}

	public int getTo() {
		return to;
	}

	public int getFrom() {
		return from;
	}

	public int getDiceUsed() {
		return diceUsed;
	}
	
}
