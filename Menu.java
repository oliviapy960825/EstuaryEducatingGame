import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
/**
 * 
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 *
 */
public class Menu extends JPanel {

	boolean startGame = false;

	/**
	 * This is the constructor of the menu and it has a mouselistener
	 * instantiated so when the actual player clicks on the button, the game
	 * starts
	 * 
	 * @param null
	 * @return null
	 */
  
	public Menu() {
		setFocusable(true);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// when certain player is clicked, chooses it
				startGame = true;
			}
		});
	}

	/**
	 * This is the method that used to paint the graphic
	 * 
	 * @param g
	 * @return null
	 */
  
	public void paint(Graphics g) {
		super.paint(g);
	}
}
