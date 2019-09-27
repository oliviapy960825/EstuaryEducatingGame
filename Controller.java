import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.Timer;

/**
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 */
public class Controller implements Serializable{
	private Model model;
	private View view;
	private KeyListener keyListener;
	Player p = Player.getInstance();
	boolean move;
	boolean mow;
	boolean interact;
	boolean doorInteract;
	int sprite = 0;

	/**
	 * This is the controller constructor, it instantiated the player, view,
	 * model and the boolean values
	 * 
	 * @param null
	 * @return null
	 */
	public Controller() {
		p = new Player();
		view = new View();
		model = new Model(view.getWidth(), view.getHeight(), view.getImageWidth(), view.getImageHeight(),
				Direction.EAST);
		move = false;
		mow = false;
		interact = false;
		doorInteract=false;
	}

	/**
	 * This is the start method, which has a keylistener to monitor every change
	 * so that the model and view can be updated accordingly due to the changes
	 * made by the actual player.
	 * 
	 * @param null
	 * @return null
	 */
	public void start() {
		keyListener = new KeyListener() {
			@Override
			
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();

				switch (keyCode) {
				case (KeyEvent.VK_LEFT):
					model.setDirect(Direction.WEST);
				    move = true;
				    break;
				    
				case (KeyEvent.VK_RIGHT) :
					model.setDirect(Direction.EAST);
				    move = true;
				    break;
				    
				case (KeyEvent.VK_SPACE) :
				    interact = true;
				    break;
				    
				case (KeyEvent.VK_1) :
					if (sprite == 1) {
						sprite = 0;
					} 
					else {
						sprite = 1;
					}
				    break;
				    
				case (KeyEvent.VK_I) :
					view.cardLayout.show(view.cards, "3");
				
				case (KeyEvent.VK_UP):
					doorInteract=true;
				break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				move=false;
				interact=false;
				doorInteract=false;
				}
		};

		view.addKeyListener(keyListener);
		
		for (int i = 0; i < 5000; i++) {

			if (move||interact) {
				model.updateLocationAndDirection(view.getP(), view.getItems(), interact);
			}
			model.checkDoors(view.getP(), view.getDoorMap(), doorInteract);
			if(doorInteract){//fix the bug that when the player switch to the lawnmower mode at the door it will automatically switch scenes
		        try {
		            Thread.sleep(500);
		            view.update(model.getX(), model.getY(), model.getDirect(), move, sprite, model.getHealth());
		        } catch (InterruptedException e) {
		        }
			}
			else{
				view.update(model.getX(), model.getY(), model.getDirect(), move, sprite, model.getHealth());
			}
			
			if (view.startCalculations == true) {
				model.calculateHealth(view.getItems());
			}
    }
    
		/**
		 * 
		 * This method runs the program
		 * 
		 * @param null
		 * @return null
		 */
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				
				Timer t = new Timer(120, view.getdrawAction());
				t.start(); 
			}
		});
	}

	/**
	 * This is the main method, and the game is started using it
	 * 
	 * @param args
	 * @return null
	 */
	public static void main(String[] args) {
		Controller c = new Controller();
		c.start();
		     
	}
}