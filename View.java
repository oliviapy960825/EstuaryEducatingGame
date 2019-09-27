import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

@SuppressWarnings("serial")
/**
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 */

public class View extends JFrame {
	Player p = Player.getInstance();
	//static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//final static int frameWidth=(int) screenSize.getWidth();
	//final static int frameHeight=(int) screenSize.getHeight();
	final static int frameWidth = 1366;
	final static int frameHeight = 768;
	final static int imgWidth = 165;
	final static int imgHeight = 165;
	final int drawDelay = 30; // msec
	DrawPanel drawPanel = new DrawPanel();
	MenuPanel menuPanel = new MenuPanel();
	InventoryPanel inventoryPanel = new InventoryPanel();

	OverPanel overPanel = new OverPanel();
	BinPanel binPanel = new BinPanel();
	Action drawAction;
	Direction d = Direction.EAST;
	Player character = new Player();
	long startTime;

	long beachStart;
	long bt;
	long inventStart;
	long est;


	static int counte =0;
	static int countb=0;
	static int countt=0;

	static int counti = 0;
	TileMap[] layout;
	Door[][] doorMap;
	Objects[][] items;
	static int rows = frameWidth / 32;
	static int columns = frameHeight / 32;
	boolean mowerEquip = false;
	int health = 100; // delete this
	CardLayout c1 = new CardLayout();
	JPanel cards = new JPanel(new CardLayout());
	CardLayout cardLayout = (CardLayout) cards.getLayout();
	boolean sunny;
	boolean beach = false;
	boolean flag = false;
	boolean startCalculations = false;
	BufferedImage estuaryBG;
	BufferedImage smileyHealth;

	Objects imower = new Objects(ObjectSprites.LAWNMOWER, 1036, 620, false, true, 0, null);
	Objects igrass = new Objects(ObjectSprites.GRASSCLIPPINGS, 50, 650, false, true, 8, "compost");
	Objects iapple = new Objects(ObjectSprites.APPLE, 300, 630, false, true, 8, "compost");
	Objects ibottle = new Objects(ObjectSprites.BOTTLE, 525, 620, false, true, 8, "recycle");
	Objects itrash = new Objects(ObjectSprites.TRASH, 990, 620, false, true, 6, "trash");
	Objects isoda = new Objects(ObjectSprites.SODA, 1125, 625, false, true, 8, "recycle");
	Objects ipizza = new Objects(ObjectSprites.PIZZA, 1050, 625, false, true, 6, "trash");
	Objects iplate = new Objects(ObjectSprites.PLATE, 800, 625, false, true, 6, "trash");
	Objects irecycle=new Objects(ObjectSprites.RECYCLEBIN,1036, 620, false, true, 0, null);
	Objects itrashbin=new Objects(ObjectSprites.TRASHBIN, 840, 605, false, true, 0, null);
	Objects icompost=new Objects(ObjectSprites.COMPOSTBIN, 300, 620, false, true, 0, null);
	Objects lastGrass = new Objects(ObjectSprites.GRASS, 288, 640, false, true, 0, null);
	Objects iperson = new Objects(ObjectSprites.PERSON, 300, 570, false, true, 0, null);
	Objects ishirt = new Objects(ObjectSprites.SHIRT, 280, 590, false, true, 0, null);

	/**
	 * This is the view constructor, and there are many objects we instantiated
	 * in the constructor. The layout is the array of the background in each
	 * scene. The doorMap is the 2D array of the doors that should go into each
	 * scene (with the first parameter being the x location of that door, the
	 * second parameter being the y location of that door, the third parameter
	 * being the number of the scene it should be displayed in, the fourth
	 * parameter being unused right now and the fifth parameter indicating the
	 * number of the next scene the view should switch to after entering this
	 * door). The items is the 2D array of all the objects that should be
	 * displayed in the view(with the first parameter being the object image,
	 * the second parameter being the x location of the object where it should
	 * be displayed, the third parameter being the y location of the object
	 * where it should be displayed, the fourth parameter being the interact
	 * boolean value of that object indicating if the object is interacting with
	 * the player and the fifth parameter being the solid boolean value which
	 * indicates if the player can go pass the object(with the boolean value
	 * true) or not (with the boolean value false) then it will add all the
	 * panels to the card layout so that the panels can be switched accordingly
	 * It also will make the game full-screen
	 *
	 * @param null
	 * @return null
	 */

	public View() {

		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		layout = new TileMap[] {new TileMap("ground"), new TileMap("emptytest"), new TileMap("beachwave"), new TileMap("citybackground"),new TileMap("estuarybackground")};
		doorMap = new Door[][] {{new Door(1248, 640, 0, -1, 1), new Door (200, 640, 0, -1, 2)}, //0 for house
			{new Door(1248, 640, 1, -1, 0)},//1 for shed
			{new Door (100, 640, 2, -1, 0), new Door (1248, 640, 2 ,-1, 3)},//2 for beach
			{new Door (100, 640, 3, -1, 4), new Door (1248, 640, 3 ,-1, 2)},//3 for city
			{new Door (100, 640, 4, -1, 3)}//4 for estuary
			};

		items = new Objects [][] {{ // adds the objects onto the scene

			igrass, lastGrass,
			new Objects(ObjectSprites.GRASS, 320, 640, false, true, 0, null),
			new Objects(ObjectSprites.GRASS, 352, 640, false, true, 0, null),
			new Objects(ObjectSprites.GRASS, 384, 640, false, true, 0, null),
			new Objects(ObjectSprites.GRASS, 416, 640, false, true, 0, null)},
			{imower},{iapple, ibottle, itrash, isoda, ipizza, iplate},{itrashbin, iperson, ishirt},{}};

		drawAction = new AbstractAction(){
    		public void actionPerformed(ActionEvent e){
    			drawPanel.repaint();
    			try {
    				Thread.sleep(50);
    			} catch (InterruptedException ie) {
    				ie.printStackTrace();
    			}
    		}
    	};
    	// adds different scenes using cardLayout design
    	cards.add(menuPanel, "1");
        cards.add(drawPanel, "2");
        cards.add(inventoryPanel, "3");
        cards.add(overPanel, "4");
        cards.add(binPanel,"5");
        add(cards);

       GraphicsEnvironment gfxEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gfxDev = gfxEnv.getDefaultScreenDevice();
        gfxDev.setFullScreenWindow(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.gray);
		setSize(frameWidth, frameHeight);
		setVisible(true);
		pack();
	}

	/**
	 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu
	 *         Wang
	 */

	private class OverPanel extends JPanel {
		/**
		 * This method is used to paint/display everything that should be
		 * displayed on the screen in a certain panel specifically, this method
		 * will paint the game over scene and add buttons to it so that the
		 * player can exit the game and it saves the health of the game in a text file
		 *
		 *
		 * @param g, used to draw images on the screen
		 * @return null
		 */

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			BufferedImage gameover = null;

			try {
				gameover = ImageIO.read(new File("images/objects/game_over_100.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}


			g.drawImage(gameover, 0, 0, null);

			g.drawImage(smileyHealth, 620, 400, null);
			JButton button2 = new JButton("Exit");
			this.setLayout(null);
			button2.setBounds(670, 200, 100, 50);
			overPanel.add(button2);



			button2.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					FileOutputStream fileOut;
					ObjectOutputStream out;
					try {
						fileOut = new FileOutputStream("Player.txt");
						out = new ObjectOutputStream(fileOut);
						out.writeObject(p.getHealth());
				        out.close();
				        fileOut.close();
				        System.out.print("Serialized health is saved in Player.txt");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					System.exit(0);
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}
			});
		}

		/**
		 * This method is used to make the size of the panel the same as the
		 * screen size
		 *
		 * @param null
		 * @return Dimension, which is the same as the screen dimension
		 */

		public Dimension getPreferredSize() {
			return new Dimension(frameWidth, frameHeight);
		}
	}

	/**
	 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu
	 *         Wang
	 */

	private class InventoryPanel extends JPanel {
		/**
			 * This method is used to paint/display everything that should be
			 * displayed on the screen in a certain panel specifically, this method
			 * will paint the inventory scene and add buttons to it so that the
			 * player can choose which inventory he/she wants to use
			 *
			 * @param g, used to draw images on the screen
			 * @return null
			 */

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			BufferedImage inventory = null;
			BufferedImage gray_lawnmower = null;
			BufferedImage gray_grass = null;
			BufferedImage gray_bottle = null;
			BufferedImage gray_can = null;
			BufferedImage gray_trash = null;
			BufferedImage gray_apple = null;
			BufferedImage gray_plate = null;
			BufferedImage gray_pizza = null;
			BufferedImage gray_shirt = null;
			BufferedImage hand = null;
			BufferedImage lawnmower = null;
			BufferedImage grass = null;
			BufferedImage bottle = null;
			BufferedImage can = null;
			BufferedImage trash = null;
			BufferedImage apple = null;
			BufferedImage plate = null;
			BufferedImage pizza = null;
			BufferedImage shirt = null;

			if (counti == 0) {
				counti = 1;
				inventStart =  System.currentTimeMillis();
			}

			try {
				inventory = ImageIO.read(new File("images/objects/inventory_background.png"));
				hand = ImageIO.read(new File("images/objects/hand.png"));
				gray_lawnmower = ImageIO.read(new File("images/objects/gray_lawnmower.png"));
				lawnmower = ImageIO.read(new File("images/objects/lawnmower.png"));
				gray_grass = ImageIO.read(new File("images/objects/gray_grass_clippings.png"));
				gray_bottle = ImageIO.read(new File("images/objects/gray_bottle.png"));
				gray_can = ImageIO.read(new File("images/objects/gray_soda.png"));
				gray_trash = ImageIO.read(new File("images/objects/gray_trash.png"));
				gray_apple = ImageIO.read(new File("images/objects/gray_apple.png"));
				gray_plate = ImageIO.read(new File("images/objects/gray_plate.png"));
				gray_pizza = ImageIO.read(new File("images/objects/gray_pizza.png"));
				gray_shirt = ImageIO.read(new File("images/objects/gray_shirt.png"));
				grass = ImageIO.read(new File("images/objects/grass_clippings.png"));
				bottle = ImageIO.read(new File("images/objects/bottle.png"));
				can = ImageIO.read(new File("images/objects/soda.png"));
				trash = ImageIO.read(new File("images/objects/trash.png"));
				apple = ImageIO.read(new File("images/objects/apple.png"));
				plate = ImageIO.read(new File("images/objects/plate.png"));
				pizza = ImageIO.read(new File("images/objects/pizza.png"));
				shirt = ImageIO.read(new File("images/objects/shirt.png"));

			} catch (IOException e) {
				e.printStackTrace();
			}

			g.drawImage(inventory, 0, 0, null);
			g.drawImage(hand, 300, 250, null);

			if (imower.interacted) {
				g.drawImage(lawnmower, 410, 230, null);
			} else {
				g.drawImage(gray_lawnmower, 410, 230, null);
			}
			if (igrass.interacted) {
				g.drawImage(grass, 555, 250, null);
			} else {
				g.drawImage(gray_grass, 555, 250, null);
			}
			if (ibottle.interacted) {
				g.drawImage(bottle, 680, 250, null);
			} else {
				g.drawImage(gray_bottle, 680, 250, null);
			}
			if (ishirt.interacted) {
				g.drawImage(shirt, 805, 250, null);
			} else {
				g.drawImage(gray_shirt, 805, 250, null);
			}
			if (isoda.interacted) {
				g.drawImage(can, 300, 450, null);
			} else {
				g.drawImage(gray_can, 300, 450, null);
			}
			if (itrash.interacted) {
				g.drawImage(trash, 410, 450, null);
			} else {
				g.drawImage(gray_trash, 410, 450, null);
			}
			if (iapple.interacted) {
				g.drawImage(apple, 555, 450, null);
			} else {
				g.drawImage(gray_apple, 555, 450, null);
			}
			if (iplate.interacted) {
				g.drawImage(plate, 660, 450, null);
			} else {
				g.drawImage(gray_plate, 660, 450, null);
			}
			if (ipizza.interacted) {
				g.drawImage(pizza, 805, 450, null);
			} else {
				g.drawImage(gray_pizza, 805, 450, null);
			}

			JButton button1 = new JButton("cursor");
			JButton button2 = new JButton("mower");
			JButton button3 = new JButton("grass");
			JButton button4 = new JButton("bottle");
			JButton button5 = new JButton("can");
			JButton button6 = new JButton("trash");
			JButton button7 = new JButton("apple");
			JButton button8 = new JButton("plate");
			JButton button9 = new JButton("pizza");
			JButton button10 = new JButton("shirt");

			this.setLayout(null);
			button1.setBounds(280, 300, 100, 50);
			button2.setBounds(400, 300, 100, 50);
			button3.setBounds(520, 300, 100, 50);
			button4.setBounds(640, 300, 100, 50);
			button5.setBounds(280, 500, 100, 50);
			button6.setBounds(400, 500, 100, 50);
			button7.setBounds(520, 500, 100, 50);
			button8.setBounds(640, 500, 100, 50);
			button9.setBounds(780, 500, 100, 50);
			button10.setBounds(780, 300, 100, 50);

			inventoryPanel.add(button1);
			inventoryPanel.add(button2);
			inventoryPanel.add(button3);
			inventoryPanel.add(button4);
			inventoryPanel.add(button5);
			inventoryPanel.add(button6);
			inventoryPanel.add(button7);
			inventoryPanel.add(button8);
			inventoryPanel.add(button9);
			inventoryPanel.add(button10);

			button1.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					p.changeEquip(1);
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});

			button2.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (imower.interacted) {
						p.changeEquip(2);
					}
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override

				public void mouseExited(MouseEvent e) {
				}

			});
			button3.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// p.changeEquip(2);
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					button3.setToolTipText( "Grass clippings can be placed in a compost bin." );
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button4.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// p.changeEquip(3);
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					button4.setToolTipText( "Plastic bottles should be recycled." );
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button5.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// p.changeEquip(4);
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					button5.setToolTipText( "Soda cans are recyclables." );
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button6.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// p.changeEquip(5);
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					button6.setToolTipText( "This should be put in a trash bin." );
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button7.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// p.changeEquip(6);
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					button7.setToolTipText( "An apple can be put in a compost bin." );
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button8.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// p.changeEquip(2);
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					button8.setToolTipText( "Ceramic plates can be thrown in the trash." );
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button9.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// p.changeEquip(3);
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					button9.setToolTipText( "Pizza should be thrown in the trash." );
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}
			});

			button10.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout.show(cards, "2");
					}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					if(ishirt.interacted)
						button10.setToolTipText( "Souvenir received!" );
					}

				@Override
				public void mouseExited(MouseEvent e) {

				}
			});
		}
	}

	/**
	 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu
	 *         Wang
	 */
	private class BinPanel extends JPanel {
		/**
		 * This method is used to paint/display everything that should be
		 * displayed on the screen in a certain panel specifically, this method
		 * will paint the bin panel in the city scene when the player interacts
		 * with the bin and start the sorting game. It will require the player to
		 * choose a bin before choosing an item using the buttons below them. It will
		 * also display a check or cross indicating if the player makes the right choice
		 * sorting, if the player makes the wrong choice, it will also display text telling
		 * the player which bin the item is supposed to go, which is educational
		 *
		 * @param g, used to draw images on the screen
		 * @return null
		 */
    private boolean compostGlow = false;
    private boolean recycleGlow = false;
    private boolean trashGlow = false;
    private boolean anyGlow = false;
    private boolean isGood = false;
    private boolean started = false;
    Objects itemToBeSorted=null;
		/**
     * This method gets the boolean value of compostGlow, which is basically indicating if the compost bin is selected
     *
     * @param null
     * @return compostGlow
     */
    private boolean getCompostGlow(){
    	return compostGlow;
    }
		/**
		* This method sets the boolean value of compostGlow, which is basically indicating if the compost bin is selected
		*
		* @param compostGlow
		* @return null
		*/
    private void setCompostGlow(boolean compostGlow){
    	this.compostGlow=compostGlow;
    }

		/**
		* This method gets the boolean value of recycleGlow, which is basically indicating if the recycle bin is selected
		*
		* @param null
		* @return recycleGlow
		*/
    private boolean getRecycleGlow(){
    	return recycleGlow;
    }
		/**
     * This method sets the boolean value of recycleGlow, which is basically indicating if the recycle bin is selected
     *
     * @param recycleGlow
     * @return null
     */
    private void setRecycleGlow(boolean recycleGlow){
    	this.recycleGlow=recycleGlow;
    }
    /**
     * This method gets the boolean value of trashGlow, which is basically indicating if the trash bin is selected
     * @param null
     * @return trashGlow
     */
    private boolean getTrashGlow(){
    	return trashGlow;
    }
		/**
     * This method sets the boolean value of trashGlow, which is basically indicating if the trash bin is selected
     *
     * @param trashGlow
     * @return null
     */
    private void setTrashGlow(boolean trashGlow){
    	this.trashGlow=trashGlow;
    }
		/**
		* This method gets the boolean value of anyGlow, which is basically indicating if any of the bins is selected
		*
		* @param null
		* @return anyGlow
		*/
    private boolean getAnyGlow(){
    	return anyGlow;
    }

		/**
     * This method sets the boolean value of anyGlow, which is basically indicating if any of the bins is selected
     *
     * @param null
     * @return null
     */
    private void setAnyGlow(){
    	this.anyGlow= trashGlow || recycleGlow || compostGlow;
    }
		/**
     * This method gets the boolean value of isGood, which is basically indicating if the player puts the item in the right bin
     *
     * @param null
     * @return isGood
     */
    private boolean getIsGood(){
    	return isGood;
    }
		/**
     * This method sets the boolean value of isGood, which is basically indicating if the player puts the item in the right bin
     *
     * @param isGood
     * @return null
     */
    private void setIsGood(boolean isGood){
    	this.isGood=isGood;
    }
		/**
     * This method gets the object of the itemToBeSorted object, which is basically indicating which item is selected and waiting to be put into the bin
     *
     * @param null
     * @return itemToBeSorted
     */
    private Objects getItemToBeSorted(){
    	return itemToBeSorted;
    }
		/**
     * This method sets the object of the itemToBeSorted object, which is basically indicating which item is selected and waiting to be put into the bin
     *
     * @param item
     * @return null
     */
    private void setItemToBeSorted(Objects item){
    	this.itemToBeSorted=item;
    }
		/**
     * This method paints all the images
     *
     * @param g
     * @return null
     */

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			BufferedImage trashBin=null;
			BufferedImage glowingTrashBin=null;
			BufferedImage compostBin=null;
			BufferedImage glowingCompostBin=null;
			BufferedImage recycleBin=null;
			BufferedImage glowingRecycleBin=null;
			BufferedImage inventory = null;
			BufferedImage gray_grass = null;
			BufferedImage gray_bottle = null;
			BufferedImage gray_can = null;
			BufferedImage gray_trash = null;
			BufferedImage gray_apple = null;
			BufferedImage gray_plate = null;
			BufferedImage gray_pizza = null;
			BufferedImage grass = null;
			BufferedImage bottle = null;
			BufferedImage can = null;
			BufferedImage trash = null;
			BufferedImage apple = null;
			BufferedImage plate = null;
			BufferedImage pizza = null;
			BufferedImage arrow = null;
			BufferedImage bad_outcome = null;
			BufferedImage good_outcome = null;

			try {
				inventory = ImageIO.read(new File("images/objects/sorting_background.png"));
				gray_grass = ImageIO.read(new File("images/objects/gray_grass_clippings.png"));
				gray_bottle = ImageIO.read(new File("images/objects/gray_bottle.png"));
				gray_can = ImageIO.read(new File("images/objects/gray_soda.png"));
				gray_trash = ImageIO.read(new File("images/objects/gray_trash.png"));
				gray_apple = ImageIO.read(new File("images/objects/gray_apple.png"));
				gray_plate = ImageIO.read(new File("images/objects/gray_plate.png"));
				gray_pizza = ImageIO.read(new File("images/objects/gray_pizza.png"));
				grass = ImageIO.read(new File("images/objects/grass_clippings.png"));
				bottle = ImageIO.read(new File("images/objects/bottle.png"));
				can = ImageIO.read(new File("images/objects/soda.png"));
				trash = ImageIO.read(new File("images/objects/trash.png"));
				apple = ImageIO.read(new File("images/objects/apple.png"));
				plate = ImageIO.read(new File("images/objects/plate.png"));
				pizza = ImageIO.read(new File("images/objects/pizza.png"));
				recycleBin=ImageIO.read(new File("images/objects/recycle_bin.png"));
				glowingRecycleBin=ImageIO.read(new File("images/objects/glowing_recycle_bin.png"));
				trashBin=ImageIO.read(new File("images/objects/trash_bin.png"));
				glowingTrashBin=ImageIO.read(new File("images/objects/glowing_trash_bin.png"));
				compostBin=ImageIO.read(new File("images/objects/compost_bin.png"));
				glowingCompostBin=ImageIO.read(new File("images/objects/glowing_compost_bin.png"));
				arrow=ImageIO.read(new File("images/objects/arrow.png"));
				bad_outcome=ImageIO.read(new File("images/objects/bad_outcome.png"));
				good_outcome=ImageIO.read(new File("images/objects/good_outcome.png"));

			} catch (IOException e) {
				e.printStackTrace();
			}

			g.drawImage(inventory, 0, 0, null);


			if (igrass.interacted) {
				g.drawImage(grass, 500, 250, null);
				igrass.setGray(false);
			} else {
				g.drawImage(gray_grass, 500, 250, null);
				igrass.setGray(true);
			}
			if (ibottle.interacted) {
				g.drawImage(bottle, 650, 250, null);
				ibottle.setGray(false);
			} else {
				g.drawImage(gray_bottle, 650, 250, null);
				ibottle.setGray(true);
			}
			if (isoda.interacted) {
				g.drawImage(can, 800, 250, null);
				isoda.setGray(false);
			} else {
				g.drawImage(gray_can, 800, 250, null);
				isoda.setGray(true);
			}
			if (itrash.interacted) {
				g.drawImage(trash, 950, 250, null);
				itrash.setGray(false);
			} else {
				g.drawImage(gray_trash, 950, 250, null);
				itrash.setGray(true);
			}
			if (iapple.interacted) {
				g.drawImage(apple, 570, 450, null);
				iapple.setGray(false);
			} else {
				g.drawImage(gray_apple, 570, 450, null);
				iapple.setGray(true);
			}
			if (iplate.interacted) {
				g.drawImage(plate, 700, 450, null);
				iplate.setGray(false);
			} else {
				g.drawImage(gray_plate, 700, 450, null);
				iplate.setGray(true);
			}
			if (ipizza.interacted) {
				g.drawImage(pizza, 890, 450, null);
				ipizza.setGray(false);
			} else {
				g.drawImage(gray_pizza, 890, 450, null);
				ipizza.setGray(true);
			}
			if(trashGlow){
				g.drawImage(glowingTrashBin, 550, 600, null);
			}
			else{
				g.drawImage(trashBin, 550, 600, null);
			}
			if(compostGlow){
				g.drawImage(glowingCompostBin, 700, 600, null);
			}
			else{
				g.drawImage(compostBin, 700, 600, null);
			}
			if(recycleGlow){
				g.drawImage(glowingRecycleBin, 880, 600, null);
			}
			else{
				g.drawImage(recycleBin, 880, 600, null);
			}
			//add in drawImage functions for bin based on the interaction

			if(!anyGlow)
				g.drawImage(arrow, 350, 650, this);


			if(anyGlow && started){
				if(isGood){
					g.drawImage(good_outcome, 250, 300, null);
				}
				else{
					g.drawImage(bad_outcome, 250, 300, null);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString("This item should go into "+getItemToBeSorted().type+" bin!", 170, 600);
				}
			}

			JButton button1 = new JButton("grass");
			JButton button2 = new JButton("bottle");
			JButton button3 = new JButton("can");
			JButton button4 = new JButton("trash");
			JButton button5 = new JButton("apple");
			JButton button6 = new JButton("plate");
			JButton button7 = new JButton("pizza");
			JButton button8 = new JButton("Trash Bin");
			JButton button9 = new JButton("Compost Bin");
			JButton button10 = new JButton("Recycle Bin");
			JButton button11 = new JButton("Exit");
			this.setLayout(null);
			button1.setBounds(460, 300, 100, 50);
			button2.setBounds(610, 300, 100, 50);
			button3.setBounds(760, 300, 100, 50);
			button4.setBounds(910, 300, 100, 50);

			button5.setBounds(530, 500, 100, 50);
			button6.setBounds(690, 500, 100, 50);
			button7.setBounds(870, 500, 100, 50);

			button8.setBounds(530, 680, 100, 50);
			button9.setBounds(690, 680, 100, 50);
			button10.setBounds(870, 680, 100, 50);
			button11.setBounds(1200, 680, 100, 50);

			binPanel.add(button1);
			binPanel.add(button2);
			binPanel.add(button3);
			binPanel.add(button4);
			binPanel.add(button5);
			binPanel.add(button6);
			binPanel.add(button7);
			binPanel.add(button8);
			binPanel.add(button9);
			binPanel.add(button10);
			binPanel.add(button11);

			button1.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (igrass.isGray()) {
						// do nothing
					}
					else if(igrass.interacted&&anyGlow){
						started = true;

						igrass.setInteracted(false);
						setItemToBeSorted(igrass);
						if(compostGlow){
							setIsGood(true);

							igrass.setSorted(true);
						}

						else{
							setIsGood(false);
							igrass.setSorted(false);
						}
						igrass.setGray(true);
					}
				}
				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});

			button2.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (ibottle.isGray()) {
						// do nothing
					}

					else if(ibottle.interacted&&anyGlow){
						started = true;

						ibottle.setInteracted(false);
						setItemToBeSorted(ibottle);
						if(recycleGlow){
							setIsGood(true);
							ibottle.setSorted(true);
						}
						else{
							setIsGood(false);
							ibottle.setSorted(false);
						}
						ibottle.setGray(true);
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override

				public void mouseExited(MouseEvent e) {
				}

			});
			button3.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (isoda.isGray()) {
						// do nothing
					}

					else if(isoda.interacted&&anyGlow){
						started = true;

						isoda.setInteracted(false);
						setItemToBeSorted(isoda);
						if(recycleGlow){
							setIsGood(true);
							isoda.setSorted(true);
						}
						else{
							setIsGood(false);
							isoda.setSorted(false);
						}

						isoda.setGray(true);
					}

				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button4.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (itrash.isGray()) {
						// do nothing
					}

					else if(itrash.interacted&&anyGlow){
						started = true;

						itrash.setInteracted(false);
						setItemToBeSorted(itrash);
						if(trashGlow){
							setIsGood(true);
							itrash.setSorted(true);
						}
						else{
							setIsGood(false);
							itrash.setSorted(false);
						}

						itrash.setGray(true);
					}

				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button5.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (iapple.isGray()) {
						// do nothing
					}

					else if(iapple.interacted&&anyGlow){
						started = true;

						iapple.setInteracted(false);
						setItemToBeSorted(iapple);
						if(compostGlow){
							setIsGood(true);
							iapple.setSorted(true);
						}
						else{
							setIsGood(false);
							iapple.setSorted(false);
						}

						iapple.setGray(true);
					}

				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button6.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {

					if (iplate.isGray()) {
						// do nothing
					}

					else if(iplate.interacted&&anyGlow){
						started = true;

						iplate.setInteracted(false);
						setItemToBeSorted(iplate);
						if(trashGlow){
							setIsGood(true);
							iplate.setSorted(true);
						}
						else{
							setIsGood(false);
							iplate.setSorted(false);
						}

						iplate.setGray(true);
					}

				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button7.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (ipizza.isGray()) {
						// do nothing
					}

					else if(ipizza.interacted&&anyGlow){
						started = true;
						ipizza.setInteracted(false);
						setItemToBeSorted(ipizza);
						if(trashGlow){
							setIsGood(true);
							ipizza.setSorted(true);
						}
						else{
							setIsGood(false);
							ipizza.setSorted(false);
						}
					}

				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button8.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					started=false;
					setTrashGlow(!trashGlow);
					if(trashGlow){
						setRecycleGlow(false);
						setCompostGlow(false);
					}
					setAnyGlow();
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button9.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					started=false;
					setCompostGlow(!compostGlow);
					if(compostGlow){
						setRecycleGlow(false);
						setTrashGlow(false);
					}
					setAnyGlow();
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button10.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					started=false;
					setRecycleGlow(!recycleGlow);
					if(recycleGlow){
						setCompostGlow(false);
						setTrashGlow(false);
					}
					setAnyGlow();
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			button11.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout.show(cards, "2");
					startCalculations = true;
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
		}

		/**
		 * This method is used to make the size of the panel the same as the
		 * screen size
		 *
		 * @param null
		 * @return Dimension, which is the same as the screen dimension
		 */

		public Dimension getPreferredSize() {
			return new Dimension(frameWidth, frameHeight);
		}
	}

	/**
	 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu
	 *         Wang
	 */

	private class MenuPanel extends JPanel {
		/**
		 * This method is used to paint/display everything that should be
		 * displayed on the screen in a certain panel specifically, this method
		 * will paint the menu scene and add buttons to it so that the actual
		 * player can choose which player character he/she wants to use to play
		 * the game
		 *
		 * @param g, used to draw images on the screen
		 * @return null
		 */

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			BufferedImage menu = null;
			try {
				menu = ImageIO.read(new File("images/objects/menu.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(menu, 0, 0, null);
			JButton button1 = new JButton("choose");
			JButton button2 = new JButton("choose");
			this.setLayout(null);
			button1.setBounds(250, 200, 100, 50);
			button2.setBounds(1000, 200, 100, 50);
			menuPanel.add(button1);
			menuPanel.add(button2);
			button1.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					p.setSonny(false);
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});

			button2.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					p.setSonny(true);
					cardLayout.show(cards, "2");
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});

		}

		/**
		 * This method is used to make the size of the panel the same as the
		 * screen size
		 *
		 * @param null
		 * @return Dimension, which is the same as the screen dimension
		 */

		public Dimension getPreferredSize() {
			return new Dimension(frameWidth, frameHeight);
		}
	}

	/**
	 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu
	 *         Wang
	 */

	private class DrawPanel extends JPanel {
		/**
		 * This method is used to paint/display everything that should be
		 * displayed on the screen in a certain panel specifically, this method
		 * will check the playerRoom attribute of the player and switch scenes
		 * according to different values of the playerRoom, and paint the view and
		 * items/characters accordingly
		 *
		 * @param g, used to draw images on the screen
		 * @return null
		 */

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			BufferedImage backgroundImage;

			if(p.getPlayerRoom()!=4){
				backgroundImage = layout[p.getPlayerRoom()].getBackground().getBackgroundHealth(health);}

			else {
				if (counte == 0){
					counte++;
					est = System.currentTimeMillis();
				}

				backgroundImage = estuaryBG;

				if (System.currentTimeMillis()- est >= 5000) {
					cardLayout.show(cards, "4");
				}
			}

			g.drawImage(backgroundImage, 0, 0, null);
			int currentLength = items[p.getPlayerRoom()].length;

			for (int i = 0; i < currentLength; i++) {
				g.drawImage(items[p.getPlayerRoom()][i].getCurrentSprite(), items[p.getPlayerRoom()][i].getXloc(),
						items[p.getPlayerRoom()][i].getYloc(), null);
			}
			BufferedImage lawnmower_txt = null;
			BufferedImage grass_txt = null;
			BufferedImage glowing_grass = null;
			BufferedImage arrow = null;
			BufferedImage beach_txt = null;
			BufferedImage instructions = null;
			BufferedImage person = null;
			BufferedImage person_txt = null;
			BufferedImage trashbin = null;

    		try {
    			lawnmower_txt=ImageIO.read(new File("images/objects/lawnmower_txt.png"));
    			glowing_grass=ImageIO.read(new File("images/objects/glowing_grass_clippings.png"));
    			grass_txt = ImageIO.read(new File("images/objects/grass_txt.png"));
    			arrow = ImageIO.read(new File("images/objects/arrow.png"));
    			instructions = ImageIO.read(new File("images/objects/instructions.png"));
    			beach_txt = ImageIO.read(new File("images/objects/beach_txt.png"));
    			person = ImageIO.read(new File("images/characters/collector.png"));
    			trashbin = ImageIO.read(new File("images/objects/trash_bin.png"));
    			person_txt = ImageIO.read(new File("images/objects/person_txt.png"));
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		}

    		if(!(imower.interacted) && p.xloc <= 500) {
    			g.drawImage(lawnmower_txt, 200, 230, null);
    			g.drawImage(arrow, 750, 300, null);
    		}

    		if(lastGrass.isSolid() == false && p.getPlayerRoom() == 0 && igrass.interacted == false) {
				g.drawImage(grass_txt, 100, 230, null);
				g.drawImage(glowing_grass, 50, 650, null);
			}

    		if (p.getPlayerRoom() == 0) {
				g.drawImage(instructions, 770, 250, null);
			}

			if (p.getPlayerRoom() == 2) {
				g.drawImage(beach_txt, 300, 180, null);
			}

			int xloc = 0, yloc = 0;

			if (p.getPlayerRoom() != 2) {
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						Tile[][] tilemap = layout[p.getPlayerRoom()].getTilemap();
						if (tilemap[i][j] != null) {
							BufferedImage temp = tilemap[i][j].getImage();
							xloc = i * temp.getWidth();
							yloc = 320 + j * temp.getHeight();
							g.drawImage(temp, xloc, yloc, this);
						}
					}
				}
			}
			else {
				for (int i = 0; i < rows ; i ++) {
					for (int j = 0; j < columns; j++) {
						Tile[][] tilemap = layout[p.getPlayerRoom()].getTilemap();
						if ((tilemap[i][j] != null) && (bt - j >2)) {
							BufferedImage temp = tilemap[i][j].getImage();
							xloc = 2 + i * temp.getWidth();
							yloc = 270 + j * (temp.getHeight() - 33);
							if (beach && (bt + j > 19))
								temp = null;
							g.drawImage(temp, xloc, yloc, this);
						}
					}
				}
			}
			if(iperson.interacted && p.getPlayerRoom() == 3) {
				g.drawImage(person, 300, 570, null);
			}

			if(itrashbin.interacted && p.getPlayerRoom() == 3 &&!flag) {
				g.drawImage(trashbin, 840, 605, null);
				flag=true;
				cardLayout.show(cards, "5");
			}

			if (countt == 0) {
				countt = 1;
				startTime = System.currentTimeMillis();
			}
			if (p.getPlayerRoom() == 2 && countb == 0) {
				beachStart = System.currentTimeMillis();
				countb++;
			}

			if (counti == 1) {
				long since = System.currentTimeMillis() - inventStart;
				startTime = startTime + since;
				counti = 0;
			}




			bt = (System.currentTimeMillis() - beachStart) / 1000;

			if(p.getPlayerRoom()==2) {

				// checks to see if water is at player's feet to begin retreat
				// and wash away trash
				if ((yloc - 50 > p.getYloc()) && bt != 0) {
					beach = true;
					for (int i = 0; i < currentLength; i++) {
						items[p.getPlayerRoom()][i].setYloc(-50);
					}
				}

				// resets beachtimer for wave
				if (beach && bt == 20)
					setBT(true);
			}

			if(iperson.interacted && p.getPlayerRoom() == 3) {
				g.drawImage(person, 300, 570, null);
				if(!ishirt.interacted) {
					g.drawImage(person_txt, 250, 200, null);
					g.drawString(("$" + (100 - p.getHealth())), 400, 290);
					}
				}

			if(itrashbin.interacted && p.getPlayerRoom() == 3 &&!flag) {
				g.drawImage(trashbin, 840, 605, null);
				flag=true;
				cardLayout.show(cards, "5");
				}

			// draws the smiley face health indicator
			g.drawImage(smileyHealth, 1150, 30, null);


			for (int i = 0; i < doorMap[p.getPlayerRoom()].length; i++) {
				int xDoor = doorMap[p.getPlayerRoom()][i].getDoorXloc();
				int yDoor = doorMap[p.getPlayerRoom()][i].getDoorYloc();
				g.drawImage(Tile.DOOR.getImage(), xDoor, yDoor, null);
			}

			g.drawImage(p.getImage(), p.getXloc(), p.getYloc(), null);

		}

		/**
		 * This method is used to make the size of the panel the same as the
		 * screen size
		 *
		 * @param null
		 * @return Dimension, which is the same as the screen dimension
		 */

		public Dimension getPreferredSize() {
			return new Dimension(frameWidth, frameHeight);
		}

		/**
		 * This method will reset the value beachStart according to the boolean value
		 * reset, which means that when the player enters the beach scene again, the
		 * beachStart will be reset and the movements of waves will start again
		 *
		 * @param reset
		 * @return null
		 */

		public void setBT(boolean reset) {
			if (reset)
				beachStart = System.currentTimeMillis();
		}
	}





	/**
	 * This method is used to update the current state of the view, so that if
	 * the player's location or state or the object he/she is interacting with
	 * has changes, it will be reflected on the screen. The health situation of
	 * the estuary will also be reflected with both the smiley face on the screen
	 * and the background image in the estuary scene
	 *
	 * @param xloc, the lateral location of the player
	 * @param yloc, the vertical location of the player
	 * @param d, the direction of the player moving towards
	 * @param move, the state that indicates if the player is moving or not
	 * @param sprite, the object that the player is interacting with
	 * @param health, the health counter indicating the health situation of estuary
	 * @return null
	 */

	public void update(int xloc, int yloc, Direction d, boolean move, int sprite, int health) {

		p.changeEquip(sprite);
		p.setXloc(xloc);
		p.setYloc(yloc);
		p.setDirect(d);
		p.setAction(move);
		p.setHealth(health);

		estuaryBG = Background.ESTUARY.getBackgroundHealth(health);
		smileyHealth = Background.HEALTH_SMILEY.getBackgroundHealth(health);

		repaint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the instance of the player object that will be displayed on the
	 * screen
	 *
	 * @param null
	 * @return the player instance
	 */

	public Player getP() {
		return p;
	}

	/**
	 * This method is used to set the player object in the view
	 *
	 * @param p, the instance of the player object in the view
	 * @return null
	 */

	public void setP(Player p) {
		this.p = p;
	}

	/**
	 * This method is used to get the frame width of the screen that the game is
	 * going to be displayed on
	 *
	 * @param null
	 * @return framwWidth, the width of the displaying screen
	 */

	public int getWidth() {
		return frameWidth;
	}

	/**
	 * This method is used to get the frame height of the screen that the game
	 * is going to be displayed on
	 *
	 * @param null
	 * @return framwHeight, the height of the displaying screen
	 */

	public int getHeight() {
		return frameHeight;
	}

	/**
	 * This method is used to get the width of the image that is going to be
	 * displayed on the screen
	 *
	 * @param null
	 * @return imgWidth, the width of the image displayed
	 */

	public int getImageWidth() {
		return imgWidth;
	}

	/**
		 * This method is used to get the width of the image that is going to be
		 * displayed on the screen
		 *
		 * @param null
		 * @return imgHeight, the height of the image displayed
		 */

	public int getImageHeight() {
		return imgHeight;
	}

	/**
		 * This method is used to get the draw delay so that the player can actually
		 * see the items showing up
		 *
		 * @param null
		 * @return drawDelay, the delaying time that is between different states
		 */

	public int getdrawDelay() {
		return drawDelay;
	}

	/**
	 * This method is used to get the draw action
	 *
	 * @param null
	 * @return drawAction
	 */

	public Action getdrawAction() {
		return drawAction;
	}

	/**
	 * This method is used to get the door map displayed on the screen
	 *
	 * @param null
	 * @return doorMap, a 2D array of doors that are displayed on the screen
	 */

	public Door[][] getDoorMap() {
		return doorMap;
	}

	/**
	 * This method is used to set the door map, which is in a 2D array, that
	 * will be displayed on the screen
	 *
	 * @param doorMap, the 2D array of doors that will be displayed on the screen
	 * @return null
	 */

	public void setDoorMap(Door[][] doorMap) {
		this.doorMap = doorMap;
	}

	/**
	 * This method is used to get the objects in the view, they will be stored
	 * in a 2D array with the type of object
	 *
	 * @param null
	 * @return items, the 2D array of objects that will be displayed on the
	 *         screen
	 */
	public Objects[][] getItems() {
		return items;
	}
}
