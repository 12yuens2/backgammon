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
		JMenuItem aiGame1 = new JMenuItem("Aoi vs Random");
		aiGame1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Game.startLocalAIGame(AIPanel.AoiIndex, AIPanel.RandomIndex);
			}
		});
		aiMenu.add(aiGame1);
	}
}
