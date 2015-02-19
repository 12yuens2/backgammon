package gui.options;

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
				GameOptionWindow popup = new GameOptionWindow();
			}
			 
		});
		menu.add(newGame);
	}
}
