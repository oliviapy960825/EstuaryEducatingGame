import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 *
 */
public enum Tile {
	//Tiles have a 32*32 image alongside a solid variable, true if the tile cannot be 
	//walked through, false if the player can walk through it
	
	GROUND(createImage(new File("images/tiles/groundtile.png")), true),
	DOOR(createImage(new File("images/tiles/doortile.png")), false),
	WAVE1(createImage(new File("images/tiles/wave1.png")), true),
	WAVE2(createImage(new File("images/tiles/wave2.png")), true),
	WAVE3(createImage(new File("images/tiles/wave3.png")), true);

	private BufferedImage image;
	private boolean solid;

	/**
	 * This method is used to get the image
	 * 
	 * @param null
	 * @return image
	 */
  
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * This method is used to set the image
	 * 
	 * @param image
	 * @return null
	 */
  
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * This method is used to get the solid boolean value
	 * 
	 * @param null
	 * @return solid
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
	 * This is the Tile constructor
	 * 
	 * @param newImage
	 * @param newsolid
	 * @return null
	 */
  
	private Tile(BufferedImage newImage, boolean newsolid) {
		image = newImage;
		solid = newsolid;
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
