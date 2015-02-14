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
		this.value = (this.wins/(1.0* this.loses) - 50.0);
	}
	
	public double getValue(){
		return this.value;
	}
}
