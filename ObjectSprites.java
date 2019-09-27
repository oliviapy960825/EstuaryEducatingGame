import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 *
 */

public enum ObjectSprites {

	LAWNMOWER(createImage(new File("images/objects/glowing_lawnmower.png"))),
	LAWNMOWERTXT(createImage (new File("images/objects/lawnmower_txt.png"))),
	GRASSCLIPPINGS(createImage(new File("images/objects/grass_clippings.png"))),
	EMPTY(createImage(new File("images/objects/empty.png"))),
	GRASS(createImage(new File("images/objects/tall_grass_100.png")), createImage(new File("images/objects/short_grass_100.png"))),
	APPLE(createImage(new File("images/objects/apple.png"))),
	BOTTLE(createImage(new File("images/objects/bottle.png"))),
	SODA(createImage(new File("images/objects/soda.png"))),
	TRASH(createImage(new File("images/objects/trash.png"))),
	PIZZA(createImage(new File("images/objects/pizza.png"))),
	PLATE(createImage(new File("images/objects/plate.png"))),
	RECYCLEBIN(createImage(new File("images/objects/glowing_recycle_bin.png")),createImage(new File("images/objects/recycle_bin.png"))),
	TRASHBIN(createImage(new File("images/objects/glowing_trash_bin.png")),createImage(new File("images/objects/trash_bin.png"))),
	COMPOSTBIN(createImage(new File("images/objects/glowing_compost_bin.png")),createImage(new File("images/objects/compost_bin.png"))),
	PERSON(createImage(new File("images/characters/collector.png"))),
	SHIRT(createImage(new File("images/objects/shirt.png")));

	private BufferedImage firstState, secondState;

	/**
	 * This is the constructor for the ObjectSprites enum file
	 *
	 * @param firstState
	 * @return null
	 */

	private ObjectSprites(BufferedImage firstState) {
		this.firstState = firstState;
		this.secondState = null;
	}

	/**
	 * This is the constructor for the ObjectSprites enum file
	 *
	 * @param firstState
	 * @param secondState
	 * @return null
	 */
	private ObjectSprites(BufferedImage firstState, BufferedImage secondState) {
		this.firstState = firstState;
		this.secondState = secondState;
	}

	/**
	 * This is the method used to get the first sprite state, the state before
	 * interaction
	 *
	 * @param null
	 * @return firstState
	 */
	public BufferedImage getFirstState() {
		return firstState;
	}


	/**
	 * This is the method used to set the first sprite state, the state before
	 * interaction
	 *
	 * @param firstState
	 * @return null
	 */

	public void setFirstState(BufferedImage firstState) {
		this.firstState = firstState;
	}


	/**
	 * This is the method used to get the second sprite state, the state after
	 * interaction
	 *
	 * @param null
	 * @return secondState
	 */
	public BufferedImage getSecondState() {
		return secondState;
	}

	/**
	 * This is the method used to set the second sprite state, the state after
	 * interaction
	 *
	 * @param secondState
	 * @return null
	 */

	public void setSecondState(BufferedImage secondState) {
		this.secondState = secondState;
	}

	/**
	 * This method is used to read the address of the image and create the image
	 *
	 * @param f
	 * @return null
	 */

	private static BufferedImage createImage(File f) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(f);
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
