package ai.miki;

import game.PossibleMove;

public class FutureBoardData {
	private double strategyRatio;
	private double pipRatio;
	private double costRatio;
	
	public FutureBoardData(double pipRatio, double costRatio, double strategyratio){
		this.pipRatio = pipRatio;
		this.costRatio = costRatio;
		this.strategyRatio = strategyratio;
	}
	
	public double getStrategyRatio() {
		return strategyRatio;
	}

	public double getPipRatio() {
		return pipRatio;
	}

	public double getCostRatio() {
		return costRatio;
	}

	public double evaluate() {
		return Math.cbrt(costRatio+costRatio);
		//return pipRatio;
		//return Math.cbrt(strategyRatio*pipRatio*costRatio);
	}
}
