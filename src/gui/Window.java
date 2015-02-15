package gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Window extends JFrame {
	public Window(){
		super("CS 1006 Backgammon");
		GamePanel panel = new GamePanel();
		this.add(panel);
		this.setPreferredSize(new Dimension(800,800) );
				
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(false);

		
	}

}

class PaintMouseListener implements MouseListener{

	JComponent component;
	
	public PaintMouseListener(JComponent component) {
		this.component = component;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		component.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		component.repaint();		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		component.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		component.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		component.repaint();
	}
	
}

class PaintMouseMotionListener implements MouseMotionListener{

	JComponent component;
	
	public PaintMouseMotionListener(JComponent component) {
		this.component = component;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		component.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		component.repaint();		
	}
	
}