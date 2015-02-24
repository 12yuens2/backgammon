package ai.miki;

import game.Column;
import game.Game;

import java.util.ArrayList;

public class FutureBoard {

	public static final int MAX_DEPTH = 4;
	
	private ArrayList<FutureBoard> boards = new ArrayList<>();
	private int depth;
	private int pips, opponentPips;
	private double ratioOfPips;
	
	/*RIP
	 * private int pips, opponentPips;
	private double ratioOfPips, strategicModifer;
	
	private static final double STRATEGIC_CONSTANT = 0.5;
	
	public int getPips() {
		return pips;
	}
	public void setPips(int pips) {
		this.pips = pips;
	}
	public int getOpponentPips() {
		return opponentPips;
	}
	public void setOpponentPips(int opponentPips) {
		this.opponentPips = opponentPips;
	}*/
	

	
	/*public void modifyStrategy() {
		ratioOfPips = pips/(opponentPips*1.0);
		strategicModifer = STRATEGIC_CONSTANT/(ratioOfPips*1.0);
	}*/
	
	public void getPipCount() {
		pips = 0;
		opponentPips = 0;
		if (Game.turn == Game.gameBoard.BLACK) {
			for (Column c: Game.gameBoard.getAll()) {
				if (c.getColor() == Game.gameBoard.BLACK) {
					pips+=c.getNumber()*c.getPieces().size();
				}
				if (c.getColor() == Game.gameBoard.WHITE){
					opponentPips += (25-c.getNumber())*c.getPieces().size();
				}
			}
			pips+=Game.gameBoard.woodColumns[1].getPieces().size()*25;
			opponentPips+= Game.gameBoard.woodColumns[0].getPieces().size()*25;
		} else {
			for (Column c: Game.gameBoard.getAll()) {
				if (c.getColor() == Game.gameBoard.WHITE) {
					pips+=(25-c.getNumber())*c.getPieces().size();
				}
				if (c.getColor() == Game.gameBoard.BLACK){
					opponentPips += c.getNumber()*c.getPieces().size();
				}
			}
			pips+=Game.gameBoard.woodColumns[0].getPieces().size()*25;
			opponentPips+= Game.gameBoard.woodColumns[1].getPieces().size()*25;
		}
		System.out.println(pips + " , " + opponentPips);
	}
	
	
}
