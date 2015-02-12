package game;

import java.util.Random;

public class Move {
	private String message;

	public static int[] dice = {0,0};
	public static int[] doubles = {0,0};
	private static Random random = new Random();
	
	public static void rollDice() {
		dice[0] = random.nextInt(5)+ 1;
		dice[1] = random.nextInt(5)+ 1;
		
		if (dice[0] == dice[1]){
			doubles[0] = dice[0];
			doubles[1] = dice[1];
		}
		
		System.out.println(dice[0] + " " + dice[1]);
	}
}
