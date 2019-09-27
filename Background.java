import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This is the enum file of the background image, with methods that will allow us
 * to switch among the background images based on the health score calculated
 *
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 *
 */
public enum Background {

	HOUSE(createImage(new File("images/objects/house_background_100.png"))), BEACH(
					createImage(new File("images/objects/sand_background.png"))), SHED(
							createImage(new File("images/objects/shed_background.png"))),CITY(createImage(new File("images/objects/city_background.png"))),
	ESTUARY(createImage(new File("images/objects/estuary_100.png")),
			createImage(new File("images/objects/estuary_75.png")),
			createImage(new File("images/objects/estuary_50.png")),
			createImage(new File("images/objects/estuary_25.png")),
			createImage(new File("images/objects/estuary_0.png"))),
	HEALTH_SMILEY(createImage(new File("images/objects/health_smiley_100.png")),
			createImage(new File("images/objects/health_smiley_75.png")),
			createImage(new File("images/objects/health_smiley_50.png")),
			createImage(new File("images/objects/health_smiley_25.png")),
			createImage(new File("images/objects/health_smiley_0.png")));


	/**
	 * This method gets the image of the best health score
	 *
	 * @param null
	 * @return BufferedImage
	 */

	public BufferedImage getOneHundred() {
		return oneHundred;
	}

	/**
	 * This method sets the image as if the health was 100
	 *
	 * @param BufferedImage
	 * @return null
	 */

	public void setOneHundred(BufferedImage oneHundred) {
		this.oneHundred = oneHundred;
	}

	/**
	 * This method gets the the second to the best background image according to the health score
	 *
	 * @param null
	 * @return BufferedImage
	 */

	public BufferedImage getSeventyFive() {
		return seventyFive;
	}

	/**
	 * This method sets the image as if the health was 75
	 *
	 * @param BufferedImage
	 * @return null
	 */

	public void setSeventyFive(BufferedImage seventyFive) {
		this.seventyFive = seventyFive;
	}

	/**
	 * This method gets the third to the best background image depending on the health
	 *
	 * @param null
	 * @return BufferedImage
	 */

	public BufferedImage getFifty() {
		return fifty;
	}

	/**
		 * This method sets the image as if the health was 50
		 *
		 * @param BufferedImage
		 * @return null
		 */

	public void setFifty(BufferedImage fifty) {
		this.fifty = fifty;
	}

	/**
		 * This method gets the fourth to the best background image depending on the health
		 *
		 * @param null
		 * @return BufferedImage
		 */

	public BufferedImage getTwentyFive() {
		return twentyFive;
	}

	/**
		 * This method sets the image as if the health was 25
		 *
		 * @param BufferedImage
		 * @return null
		 */

	public void setTwentyFive(BufferedImage twentyFive) {
		this.twentyFive = twentyFive;
	}

	/**
		 * This method gets the worst background image because health score is very low
		 *
		 * @param null
		 * @return BufferedImage
		 */

	public BufferedImage getZero() {
		return zero;
	}

	/**
	 * This method sets the image as if the health was 0
	 *
	 * @param BufferedImage
	 * @return null
	 */

	public void setZero(BufferedImage zero) {
		this.zero = zero;
	}

	/**
	 *
	 * This is a constructor that has all of the images that depend on the health counter (from 0 to 100)
	 *
	 * @param oneHundred
	 * @param seventyFive
	 * @param fifty
	 * @param twentyFive
	 * @param zero
	  * @return null
	 */

	private Background(BufferedImage oneHundred, BufferedImage seventyFive, BufferedImage fifty,
			BufferedImage twentyFive, BufferedImage zero) {
		this.oneHundred = oneHundred;
		this.seventyFive = seventyFive;
		this.fifty = fifty;
		this.twentyFive = twentyFive;
		this.zero = zero;
	}

	/**
	 *
	 * This is a constructor that takes in the image as if the health was at 100, and sets the other images to null
	 *
	 * @param oneHundred
	 * @return null
	 */

	private Background(BufferedImage oneHundred) {
		this.oneHundred = oneHundred;
		this.seventyFive = null;
		this.fifty = null;
		this.twentyFive = null;
		this.zero = null;
	}

	/**
	 *
	 * This returns the correct background depending on the health counter
	 *
	 * @param health
	 * @return BufferedImage
	 */

	public BufferedImage getBackgroundHealth(int health) {

		if (health >= 25 && health < 50) {
			return twentyFive;
		}

		else if (health >= 50 && health < 75) {
			return fifty;
		}

		else if (health >= 75 && health < 100) {
			return seventyFive;
		}

		else if (health == 100) {
			return oneHundred;
		}
		else
		{
			return zero;
		}
	}

	private BufferedImage oneHundred;
	private BufferedImage seventyFive;
	private BufferedImage fifty;
	private BufferedImage twentyFive;
	private BufferedImage zero;

	/**
	 *
	 * This method reads the images from a file and makes them a BufferedImage
	 *
	 * @param f
	 * @return BufferedImage
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
