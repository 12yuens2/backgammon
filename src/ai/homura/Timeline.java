package ai.homura;

import game.Column;
import game.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Timeline implements Serializable {
	ArrayList<TimelineMove> timelineMoves;
	
	public Timeline(){
		timelineMoves = new ArrayList<TimelineMove>();
	}
	
	public void getMoveValue(){
		
	}

	public void pushWinData(ArrayList<TimelineMove> gameMoves) {
		for (TimelineMove move : gameMoves){
			boolean moveExists = false;
			for (TimelineMove savedMove : timelineMoves){
				if (Arrays.equals(move.getBoardState(), savedMove.getBoardState()) && move.getFrom() == savedMove.getFrom() && move.getTo() == savedMove.getTo()){
					moveExists = true;
					savedMove.addWin();
				}
			}
			if (!moveExists){
				move.setWins(1);
				timelineMoves.add(move);
				//System.out.println("Homura chan learned a new move.");
			}
		}
	}

	public void pushLoseData(ArrayList<TimelineMove> gameMoves) {
		for (TimelineMove move : gameMoves){
			boolean moveExists = false;
			for (TimelineMove savedMove : timelineMoves){
				if (Arrays.equals(move.getBoardState(), savedMove.getBoardState()) && move.getFrom() == savedMove.getFrom() && move.getTo() == savedMove.getTo()){
					moveExists = true;
					savedMove.addLoss();
				}
			}
			if (!moveExists){
				move.setLoses(1);
				timelineMoves.add(move);
				//System.out.println("Homura chan learned a new move.");
			}
		}		
	}
	
}
