package gui.options;

import game.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameMenu extends JMenuBar {
	public GameMenu(){
		JMenu menu = new JMenu("Game");
		this.add(menu);
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				new GameOptionWindow();
			}
			 
		});
		menu.add(newGame);
		JMenu aiMenu = new JMenu("AI Test");
		this.add(aiMenu);
		JMenuItem aiGame1 = new JMenuItem("Aoi(W) vs Random(B)");
		aiGame1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Game.startLocalAIGame(AIPanel.AoiIndex, AIPanel.RandomIndex);
			}
		});
		aiMenu.add(aiGame1);
		JMenuItem aiGame2 = new JMenuItem("Aoi(W) vs Miki(B)");
		aiGame2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Game.startLocalAIGame(AIPanel.AoiIndex, AIPanel.MikiIndex);
			}
		});
		aiMenu.add(aiGame2);
		JMenuItem aiGame3 = new JMenuItem("Random(W) vs Miki(B)");
		aiGame3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Game.startLocalAIGame(AIPanel.RandomIndex, AIPanel.MikiIndex);
			}
		});
		aiMenu.add(aiGame3);
	}
}
