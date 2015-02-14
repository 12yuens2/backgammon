package ai.homura;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import game.Game;
import game.Move;

public class Homura {
	
	private final String timelinePath = "src/ai/homura/timeline.bgdata";
	
	Random generator;
	Timeline timeline;
	ArrayList<TimelineMove> gameMoves;
	
	public Homura(){
		generator = new Random();
		gameMoves = new ArrayList<TimelineMove>();
		try{
			FileInputStream fin = new FileInputStream(timelinePath);
			ObjectInputStream in = new ObjectInputStream(fin);
			timeline = (Timeline) in.readObject();
			in.close();
			fin.close();
		} catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (timeline == null){
				timeline = new Timeline();
			}
		}
		
		for (TimelineMove t : timeline.timelineMoves){
			System.out.println(t.getFrom() + " > " + t.getTo() + " T:" + t.getTurn() + " W: " + t.getWins() + " L: " + t.getLoses() );
		}
//		System.exit(0);
	}
	
	public void makeRandomMove(){
		ArrayList<Integer[]> moves = Move.getValidMoves();
//		System.out.println(moves.size());
		
		ArrayList<TimelineMove> knownMoves = new ArrayList<>();
		for (TimelineMove m : this.timeline.timelineMoves){
			for (Integer[] move : moves){
				if (m.getTurn() == Game.turn && m.getFrom() == move[1] && m.getTo() == move[0]){
					knownMoves.add(m);
				}
			}
		}
		
		if (!knownMoves.isEmpty()){

			TimelineMove bestMove = null;
			double bestValue = -10000000.0;
			for (TimelineMove m : knownMoves){

				if (m.getValue() > bestValue){
					System.out.println(m.getValue());
					bestValue = m.getValue();
					bestMove = m;
				}
			}
			
			System.out.println(bestMove.getTurn());
			Integer[] chosenKnownMove = {bestMove.getTo(),bestMove.getFrom()};
			Move.executeMove(chosenKnownMove);
		} else {
			int chosenMove = generator.nextInt(moves.size());
			gameMoves.add(new TimelineMove(Game.turnNumber,moves.get(chosenMove)[0],moves.get(chosenMove)[1]));
			Move.executeMove(moves.get(chosenMove));			
		}
	}

	public void addWinData() {
		timeline.pushWinData(gameMoves);
		this.saveTimeline();
	}

	public void addLoseData() {
		timeline.pushLoseData(gameMoves);
		this.saveTimeline();		
	}

	private void saveTimeline() {
		try {
			FileOutputStream fout = new FileOutputStream(timelinePath);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(timeline);
			out.close();
			fout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.gameMoves = new ArrayList<>();
		}
	}
}
