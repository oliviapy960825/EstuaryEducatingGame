import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 *
 */
public class Objects{

	ObjectSprites objectSprite;
	int xloc;
	int xRange;
	int yloc;
	boolean interacted;
	boolean solid;
	int healthPts;
	BufferedImage currentSprite;
	String type;
	boolean sorted;
	boolean grayed;

	/**
	 * This is the object constructor, it instantiated the sorted boolean value to false and the grayed boolean value to true
	 *
	 * @param objectSprite
	 * @param xloc
	 * @param yloc
	 * @param interacted
	 * @param solid
	 * @param healthPts
	 * @param type
	 * @return null
	 */

	public Objects(ObjectSprites objectSprite, int xloc, int yloc, boolean interacted, boolean solid, int healthPts, String type) {
		this.objectSprite = objectSprite;
		this.xRange = 0;
		this.xloc = xloc;
		this.yloc = yloc;
		this.interacted = interacted;
		currentSprite = objectSprite.getFirstState();
		this.solid = solid;
		this.healthPts = healthPts;
		this.type = type;
		this.sorted = false;
		this.grayed = true;
	}

	/**
	 * This method changes the sprite of the object. It is called after the
	 * player character interacts with the objects, and it sets the solid
	 * boolean value to false if it used to be true (which means that now the
	 * player character can pass through it) and it changes the interacted
	 * boolean value to true after interaction
	 *
	 * @param null
	 * @return null
	 */

	public void changeSprites() {
		currentSprite = this.objectSprite.getSecondState();
		if (this.solid) {
			this.solid = false;
		}
		interacted = true;
	}

	/**
	 * This method checks the collision between the player character and the
	 * objects,
	 *
	 * @param p
	 * @param xloc
	 * @return boolean value indicating if there's collision between the player
	 *         and the objects
	 */

	public boolean checkCollision(Player p, int xloc) {
		Rectangle playerBounds = new Rectangle(xloc, p.getYloc(), 128, 128);
		Rectangle itemBounds = new Rectangle(this.xloc, this.yloc, 16, 16 );

		if (playerBounds.intersects(itemBounds) && this.solid == true){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * This is the method used to get the solid boolean value of the object
	 *
	 * @param null
	 * @return soild, the boolean value indicating if the player character can
	 *         go pass the object or not
	 */

	public boolean isSolid() {
		return solid;
	}

	/**
	 * This is the method used to set the boolean value of the object
	 *
	 * @param solid
	 * @return null
	 */

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	/**
	 * This method is used to check if the image of the object is grayed out (not interactable)
	 *
	 * @param null
	 * @return grayed
	 */

	public boolean isGray() {
		return grayed;
	}

	/**
	 * This method is used to set the image to be grayed out
	 *
	 * @param grayed
	 * @return null
	 */


	public void setGray(boolean grayed) {
		this.grayed = grayed;
	}


	/**
	 * This is the method used to get the x location range of the object, which
	 * is used to detect the collision
	 *
	 * @param null
	 * @return xRange
	 */

	public int getxRange() {
		return xRange;
	}

	/**
	 * This is the method used to set the x location range of the object
	 *
	 * @param xRange
	 * @return null
	 */

	public void setxRange(int xRange) {
		this.xRange = xRange;
	}

	/**
	 * This is the method used to get the current sprite
	 *
	 * @param null
	 * @return currentSprite
	 */

	public BufferedImage getCurrentSprite() {
		return currentSprite;
	}

	/**
	 * This is the method used to set the current sprite
	 *
	 * @param currentSprite
	 * @return null
	 */

	public void setCurrentSprite(BufferedImage currentSprite) {
		this.currentSprite = currentSprite;
	}

	/**
	 * This is the method used to get the object sprite
	 *
	 * @param null
	 * @return objectSprite
	 */

	public ObjectSprites getObjectSprite() {
		return objectSprite;
	}

	/**
	 * This is the method used to set the object sprite
	 *
	 * @param objectSprite
	 * @return null
	 */

	public void setObjectSprite(ObjectSprites objectSprite) {
		this.objectSprite = objectSprite;
	}

	/**
	 * This method gets the health points of an object
	 *
	 * @param null
	 * @return healthPts
	 */

	public int getHealthPts() {
		return healthPts;
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
	 * This is the method used to set the x location
	 *
	 * @param xloc
	 * @return null
	 */

	public void setXloc(int xloc) {
		this.xloc = xloc;
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
	 * This is the method used to set the y location
	 *
	 * @param yloc
	 * @return null
	 */

	public void setYloc(int yloc) {
		this.yloc = yloc;
	}

	/**
	 * This is the method used to get the interacted boolean value
	 *
	 * @param null
	 * @return interacted
	 */

	public boolean isInteracted() {
		return interacted;
	}

	/**
	 * This is the method used to set the interacted boolean value
	 *
	 * @param interacted
	 * @return null
	 */

	public void setInteracted(boolean interacted) {
		this.interacted = interacted;
	}

	/**
	 *
	 * This method sets the sorted parameter to true if it was sorted in the BinPanel, false if not
	 *
	 * @param s
	 * @return null
	 */

	public void setSorted(boolean s){
		sorted = s;
	}

	/**
	 * This method checks if the object was sorted into their correct bin
	 *
	 * @param null
	 * @return sorted
	 */
	 
	public boolean isSorted(){
		return sorted;
	}
}
