import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 *
 */

public class Model {


	Direction d;
	int xloc = 552;
	int yloc = 530;
	final int xIncr = 32;
	final int yIncr = 32;
	int frameWidth = 1366;
	int frameHeight = 768;
	int imgWidth = 165;
	int imgHeight = 165;
	Player currentPlayer;
	Objects[][] currentItems;
	ObjectSprites item;
	int health = 0; // health increases as player collects more items and puts the items in the correct bin

	/**
	 * This is the constructor of the model, it sets the frame width, frame
	 * height, image width, image height and direction of the player character
	 *
	 * @param fWidth
	 * @param fHeight
	 * @param iWidth
	 * @param iHeight
	 * @param d
	 * @return null
	 */

	public Model(int fWidth, int fHeight, int iWidth, int iHeight, Direction d) {
		frameWidth = fWidth;
		frameHeight = fHeight;
		imgWidth = iWidth;
		imgHeight = iHeight;
		this.d = d;
	}

	/**
	 * This method is used to update the location and direction of the player
	 * character in the game. It also checks collisions and updates the
	 * inventory status of the player and the health counter when the player does things
	 * like picks up the trash
	 *
	 * @param p
	 * @param items
	 * @param interact
	 * @return null
	 */

	public void updateLocationAndDirection(Player p, Objects[][] items, boolean interact) {

		switch(d) {
		case EAST:
			xloc+=xIncr;
			if(xloc>1300){
				xloc-=xIncr;
			}
			break;

		case WEST:
			xloc = xloc- xIncr;
			if(xloc<-100){
				xloc+=xIncr;
			}
			break;
		}

		int length = items[p.getPlayerRoom()].length;

		for (int i = 0; i < length; i++) {
			if (items[p.getPlayerRoom()][i].checkCollision(p, xloc)) {
				switch(d) {
				case EAST:
					xloc-=xIncr;
					break;

				case WEST:
					xloc+=xIncr;
					break;
				}
				item = items[p.getPlayerRoom()][i].getObjectSprite();

				if (interact) {
					if (item != ObjectSprites.GRASS) {
						p.addInventory(items[p.getPlayerRoom()][i]);
		    			items[p.getPlayerRoom()][i].changeSprites();
		    			health += items[p.getPlayerRoom()][i].getHealthPts();
					}
				}

				if (item == ObjectSprites.GRASS && p.isMow()) {
						p.addInventory(items[p.getPlayerRoom()][i]);
						items[p.getPlayerRoom()][i].changeSprites();
						health += items[p.getPlayerRoom()][i].getHealthPts();
				}
			}
		}
	}

	/**
	 * This method checks the door and switch the scene if the player character
	 * interacts with the door
	 *
	 * @param p
	 * @param doorMap
	 * @param doorInteract
	 * @return null
	 */

	public void checkDoors(Player p, Door[][] doorMap, boolean doorInteract) {
		Rectangle playerBounds = new Rectangle(p.getXloc(), p.getYloc(), 128, 128);
		for (int i = 0; i < doorMap[p.getPlayerRoom()].length; i++) {
			Rectangle doorBounds = new Rectangle(doorMap[p.getPlayerRoom()][i].getDoorXloc(),
					doorMap[p.getPlayerRoom()][i].getDoorYloc(), 16, 16);
			if (playerBounds.intersects(doorBounds) && doorInteract) {
				p.operateDoor(doorMap[p.getPlayerRoom()][i].getNextDoorIDs());
			}
		}
	}

	/**
	 * This method calculates the health after sorting in the city scene
	 *
	 * @param items
	 * @return null
	 */

	public void calculateHealth(Objects[][] items) {
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items[i].length; j++) {
					if (items[i][j].isSorted()) {
						health += items[i][j].getHealthPts();
						items[i][j].setSorted(false);
					}
				}
			}
		}

	/**
	 * This method gets and returns the health of the estuary
	 *
	 * @param null
	 * @return health
	 */

	public int getHealth() {
		return health;
	}

	/**
	 * This method gets and returns the x location
	 *
	 * @param null
	 * @return xloc
	 */

	public int getX() {
		return xloc;
	}

	/**
	 * This method gets and returns the y location
	 *
	 * @param null
	 * @return yloc
	 */

	public int getY() {
		return yloc;
	}

	/**
	 * This method gets and returns the direction
	 *
	 * @param null
	 * @return d
	 */

	public Direction getDirect() {
		return d;
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
		 * This method sets the x location
		 * @param i
		 * @return null
		 */
		 
	public void setXLoc(int i) {
		xloc = i;
	}

}
