package ai.homura;

import java.io.Serializable;

public class TimelineMove implements Serializable {
	private int turn;
	private int from;
	private int to;
	private int wins;
	private int loses;
	private double value;
	public TimelineMove(int turn, int from, int to) {
		this.turn = turn;
		this.from = from;
		this.to = to;
		this.wins = 0;
		this.loses = 0;
		this.value = 0.0;
	}
	
	private void addWin(){
		this.wins++;
		this.calculateValue();
	}
	
	private void addLoss(){
		this.loses++;
		this.calculateValue();
	}
	
	private void calculateValue(){
		this.value = 100.0*(this.wins/(1.0* this.loses)) - 50.0;
	}
	
	public double getValue(){
		calculateValue();
		return this.value;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLoses() {
		return loses;
	}

	public void setLoses(int loses) {
		this.loses = loses;
	}
	
	
}
