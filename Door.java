import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 *
 */
public class Door {

	/*
	 * Doors have an x and y location, a door id (the location of the column of
	 * this door) and the location of the next and previous rows of doors
	 */
	int doorXloc;
	int doorYloc;
	int currDoorID;
	int prevDoorIDs;
	int nextDoorIDs;

	/**
	 * This is the constructor for the door, and it sets the x location, y
	 * location, the currentDoor ID, the previousDoor ID and nextDoor ID
	 *
	 * @param xLocation
	 * @param yLocation
	 * @param currDoorID
	 * @param prevDoorID
	 * @param nextDoorID
	 * @return null
	 */

	public Door(int xLocation, int yLocation, int currDoorID, int prevDoorID, int nextDoorID) {
		doorXloc = xLocation;
		doorYloc = yLocation;
		this.currDoorID = currDoorID;
		this.prevDoorIDs = prevDoorID;
		this.nextDoorIDs = nextDoorID;
	}

	/**
	 * This is the method used to get the current door ID
	 *
	 * @param null
	 * @return currDoorID, the number of the scene that the door should be
	 *         displayed in
	 */

	public int getCurrDoorID() {
		return currDoorID;
	}

	/**
	 * This is the method used to set the current door ID, which sets the number
	 * of the scene where the door should be displayed in
	 *
	 * @param currDoorID
	 * @return null
	 */

	public void setCurrDoorID(int currDoorID) {
		this.currDoorID = currDoorID;
	}

	/**
	 * This is the method used to get the previous door ID
	 *
	 * @param null
	 * @return prevDoorIDs, the number of the scene prior to the scene that the
	 *         door should be displayed in
	 */

	public int getPrevDoorIDs() {
		return prevDoorIDs;
	}

	/**
	 * This is the method used to set the previous door ID, which sets the
	 * number of the scene prior to the scene where the door should be displayed
	 * in
	 *
	 * @param prevDoorIDs
	 * @return null
	 */

	public void setPrevDoorIDs(int prevDoorIDs) {
		this.prevDoorIDs = prevDoorIDs;
	}

	/**
	 * This is the method used to get the next door ID
	 *
	 * @param null
	 * @return nextDoorIDs, the number of the scene after the scene that the
	 *         door should be displayed in, which indicates which scene the view
	 *         should switch to after the player enters the door
	 */

	public int getNextDoorIDs() {
		return nextDoorIDs;
	}

	/**
	 * This is the method used to set the next door ID- which sets the number of
	 * the scene after the scene where the door should be displayed in
	 *
	 * @param nextDoorIDs, indicates which scene the view should switch to after the
	 *        player enters the door
	 * @return null
	 */

	public void setNextDoorIDs(int nextDoorIDs) {
		this.nextDoorIDs = nextDoorIDs;
	}

	/**
	 * This is the method used to get the x location of the door
	 *
	 * @param null
	 * @return doorXloc, indicates the x location of the door in a certain scene
	 */

	public int getDoorXloc() {
		return doorXloc;
	}

	/**
	 * This is the method used to set the x location of the door
	 *
	 * @param doorXloc
	 * @return null
	 */

	public void setDoorXloc(int doorXloc) {
		this.doorXloc = doorXloc;
	}

	/**
	 * This is the method used to get the y location of the door
	 *
	 * @param null
	 * @return doorYloc, indicates the y location of the door in a certain scene
	 */

	public int getDoorYloc() {
		return doorYloc;
	}

	/**
	 * This is the method used to set the y location of the door
	 *
	 * @param doorYloc
	 * @return null
	 */
  
	public void setDoorYloc(int doorYloc) {
		this.doorYloc = doorYloc;
	}
}
