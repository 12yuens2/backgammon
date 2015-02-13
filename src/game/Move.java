package game;

import java.util.Random;
import java.util.Scanner;

public class Move {
	private String message;

	public static int[] dice = {0,0};
	public static int[] doubles = {0,0};
	private static Random random = new Random();
	private static Scanner input = new Scanner(System.in);
	
	
	public static void rollDice() {
		
		dice[0] = random.nextInt(5)+ 1;
		dice[1] = random.nextInt(5)+ 1;
		
		System.out.print("Dice 1:");
	//	dice[0] = input.nextInt();
		System.out.print("Dice 2:");
	//	dice[1] = input.nextInt();
		
		if (dice[0] == dice[1]){
			doubles[0] = dice[0];
			doubles[1] = dice[1];
		}
		
		System.out.println(dice[0] + " " + dice[1] + " (" + doubles[0] + " " + doubles[1] + ")");
	}

	public static void consumeMove(int moveUsed) {
		for (int i = 0; i < dice.length; i++){
			if (dice[i] == moveUsed){
				dice[i] = 0;
				for (int j = 0; j < doubles.length; j++){
					if (doubles[i] != 0){
						dice[i] = doubles[i];
						doubles[i] = 0;
						break;
					}
				}
				break;
			}
		}
		System.out.println(dice[0] + " " + dice[1] + " (" + doubles[0] + " " + doubles[1] + ")");
		if (dice[0] == 0 && dice[1] == 0){
			Game.changeTurn();
		}
	}
}
