package ai.homura;

import java.io.Serializable;
import java.util.ArrayList;

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
				if (move.getTurn() == savedMove.getTurn() && move.getFrom() == savedMove.getFrom() && move.getTo() == savedMove.getTo()){
					moveExists = true;
					savedMove.setWins(savedMove.getWins() + 1);
				}
			}
			if (!moveExists){
				move.setWins(1);
				timelineMoves.add(move);
			}
		}
	}

	public void pushLoseData(ArrayList<TimelineMove> gameMoves) {
		for (TimelineMove move : gameMoves){
			boolean moveExists = false;
			for (TimelineMove savedMove : timelineMoves){
				if (move.getTurn() == savedMove.getTurn() && move.getFrom() == savedMove.getFrom() && move.getTo() == savedMove.getTo()){
					moveExists = true;
					savedMove.setLoses(savedMove.getLoses() + 1);
				}
			}
			if (!moveExists){
				move.setLoses(1);
				timelineMoves.add(move);
			}
		}		
	}
}
