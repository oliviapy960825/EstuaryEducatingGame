import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
/**
 *
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 *
 */

public class Player implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	BufferedImage[] pics;
	BufferedImage img;
	int frameCount = 10;
	int picNum = 0;
	int xloc;
	int yloc;
	int health;
	int imgSize = 165;
	int imgWidth;
	int imgHeight;
	Direction d = Direction.EAST;
	Objects players;
	private static Player instance;
	boolean move;
	boolean mow;
	boolean grass;
	boolean bottle;
	boolean can;
	boolean trash;
	boolean apple;
	boolean sonny;
	int playerRoom = 0;
	Collection<Objects> inventory = new ArrayList<Objects>();

	/**
	 * This is the method used to get the player room, which is to get the scene
	 * the player character is in
	 *
	 * @param null
	 * @return playerRoom, the scene the player character is in
	 */

	public int getPlayerRoom() {
		return playerRoom;
	}

	/**
		 * This method returns the player instance
		 *
		 * @param null
		 * @return instance
		 */

	public static synchronized Player getInstance() {
		if (instance == null) {
			instance = new Player();
		}
		return instance;
	}

	/**
		 * This method is used to get the player character's moving status
		 *
		 * @param null
		 * @return move
		 */
	public boolean isMove() {
		return move;
	}
	/**
	 * This method is used to set the players move boolean, for testing purposes
	 *
	 * @param move
	 * @return null
	 */

	public void setMove(boolean move) {
		this.move = move;
	}
	/**
	 * This is the method used to set the player room, which is to set the scene
	 * the player character is in
	 *
	 * @param playerRoom
	 * @return null
	 */

	public void setPlayerRoom(int playerRoom) {
		this.playerRoom = playerRoom;
	}

	String[] adrr = new String[] {
			"images/characters/sonny_stand_east.png", "images/characters/sonny_stand_west.png",
			"images/characters/sonny_run_east.png", "images/characters/sonny_run_west.png",
			"images/characters/sonny_mow_east.png", "images/characters/sonny_mow_west.png",
			"images/characters/sunny_stand_east.png", "images/characters/sunny_stand_west.png",
			"images/characters/sunny_run_east.png", "images/characters/sunny_run_west.png",
			"images/characters/sunny_mow_east.png", "images/characters/sunny_mow_west.png" };
	ArrayList<BufferedImage[]> playerImages = new ArrayList<BufferedImage[]>();

	/**
	 * This is the play constructor, and it reads and creates all player images
	 *
	 * @param null
	 * @return null
	 */

	public Player() {
		for (int i = 0; i < adrr.length; i++) {
			img = createImage(new File(adrr[i]));
			frameCount = img.getWidth() / imgSize;
			imgWidth = img.getWidth();
			imgHeight = img.getHeight();
			pics = new BufferedImage[10];
			for (int j = 0; j < frameCount; j++) {
				pics[j] = img.getSubimage(imgSize * j, 0, imgSize, imgSize);
			}
			playerImages.add(pics);
		}
	}
	/**
	 *
	 * This method is used to set the health of the player so it can
	 *
	 * @param health
	 * @return null
	 */

	void setHealth(int health) {
		this.health = health;
		}

	/**
	 *
	 * This method gets the health of the player
	 * @param null
	 * @return health
	 */

	int getHealth() {
		return this.health;
		}

	/**
	 * This method is used to read the address of the image and create the image
	 *
	 * @param f
	 * @return null
	 */

	private BufferedImage createImage(File f) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(f);
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method is used to change the state of player character's mowing
	 * status
	 *
	 * @param i
	 * @return null
	 */

	public void changeEquip(int i) {
		switch (i) {
		case 1:
			mow = false;
			grass = false;
			can = false;
			bottle = false;
			trash = false;
			break;
		case 2:
			mow = true;
			break;
		case 3:
			grass = true;
			break;
		}
	}

	/**
	*This method gets and returns the boolean value grass
	* @param null
	* @return grass
	*/

	public boolean isGrass() {
		return grass;
	}

	/**
	*This method sets the boolean value grass
	* @param grass
	* @return null
	*/
	public void setGrass(boolean grass) {
		this.grass = grass;
	}


	/**
	 * This method gets and returns the x location
	 *
	 * @param null
	 * @return xloc
	 */

	public int getXloc() {
		return xloc;
	}

	/**
	 * This method gets and returns the y location
	 *
	 * @param null
	 * @return yloc
	 */

	public int getYloc() {
		return yloc;
	}

	/**
	 * This is the method used to set the x location
	 *
	 * @param x
	 * @return null
	 */

	public void setXloc(int x) {
		xloc = x;
	}

	/**
	 * This is the method used to set the y location
	 *
	 * @param y
	 * @return null
	 */

	public void setYloc(int y) {
		yloc = y;
	}

	/**
	 * This method sets the direction
	 *
	 * @param d
	 * @return null
	 */

	public void setDirect(Direction d) {
		this.d = d;
	}

	/**
	 * This method is used to add the objects to the player's inventory
	 *
	 * @param o
	 * @return null
	 */

	public void addInventory(Objects o) {
		inventory.add(o);
	}

	/**
	 * This method is used to get the player character's mowing status
	 *
	 * @param null
	 * @return mow
	 */

	public boolean isMow() {
		return mow;
	}

	/**
	 * This method is used to set the player character's moving status
	 *
	 * @param move
	 * @return null
	 */

	public void setAction(boolean move) {
		this.move = move;
	}

	/**
	 * This method is used to set the player character's mowing status
	 *
	 * @param mow
	 * @return null
	 */

	public void setMow(boolean mow) {
		this.mow = mow;
	}

	/**
	 * This method is used to get the player character image based on the move
	 * and mow boolean values
	 *
	 * @param null
	 * @return pics[picNum], the image of the player character with certain
	 *         boolean values of move and mow
	 */

	public BufferedImage getImage() {
	int i = 0;

	if (sonny) {
		if (move && !mow) {
			frameCount = 4;
			i = 2;
		}

		else if (mow) {
			frameCount = 1;
			i = 4;
		}
		else {
			frameCount = 1;
			i = 0;
		}
	}

	else {
		if (move && !mow) {
			frameCount = 4;
			i = 8;
		}
		else if (mow) {
			frameCount = 1;
			i = 10;
		}
		else
		{
			frameCount = 1;
			i = 6;
		}
	}
		pics = playerImages.get(d.getPosition() + i);
		picNum = (picNum + 1) % frameCount;


	return pics[picNum];
	}

	/**
	 * This method is used to get the boolean value sonny to see which player
	 * character the actual player chooses
	 *
	 * @param null
	 * @return sonny
	 */

	public boolean isSonny() {
		return sonny;
	}

	/**
	 * This method is used to set the boolean value sonny
	 *
	 * @param sonny
	 * @return null
	 */

	public void setSonny(boolean sonny) {
		this.sonny = sonny;
	}

	/**
	 * This method is used to check and set the player room the player character is in
	 *
	 * @param currentRoom
	 * @return null
	 */

	public void operateDoor(int currentRoom) {
		if (currentRoom != playerRoom) {
			playerRoom = currentRoom;
		}
	}
	/**
	 * Used to get frameCount for testing purposes
	 * @param null
	 * @return framecount
	 */
	public int getFrameCount() {
		return frameCount;
	}

}
