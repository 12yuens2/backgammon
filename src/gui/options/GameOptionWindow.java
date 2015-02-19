package gui.options;

import game.Game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class GameOptionWindow extends JFrame {
	public static GameOptionWindow optionsMenu;
	static final String LOCAL = "Versus local player";
	static final String LOCAL_AI = "Versus local AI";
	static final String ONLINE = "Versus online player";

	public GameOptionWindow(){
		super("CS1006 Backgammon Options");
		optionsMenu = this;
		this.setPreferredSize(new Dimension(500,300));

		JPanel localPanel = new LocalPanel();
		JPanel AIPanel = new AIPanel();
		JPanel OnlinePanel = new NetworkPanel();

		JTabbedPane cards = new JTabbedPane();
		cards.add(localPanel,LOCAL);
		cards.add(AIPanel,LOCAL_AI);
		cards.add(OnlinePanel,ONLINE);

		this.add(cards);
		this.pack();
		this.setVisible(true);
	}
}
