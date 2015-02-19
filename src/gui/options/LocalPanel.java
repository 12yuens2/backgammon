package gui.options;

import game.Game;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LocalPanel extends JPanel {
	public LocalPanel(){
		JButton localPlayerStartBtn = new JButton("Start Game");
		localPlayerStartBtn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				Game.startLocalGame();
			}
		});
		this.add(localPlayerStartBtn);
	}
}
