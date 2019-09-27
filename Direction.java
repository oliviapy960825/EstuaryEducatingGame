/**
 * This is the enum file for direction
 *
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 */

public enum Direction {

	EAST("east", 0), WEST("west", 1);

	private String name = null;
	private int position;

	/**
	 * This is the constructor for direction
	 *
	 * @param s, the name text of the direction
	 * @param i, the position/number of the direction when it is used
	 * @return null
	 */

	private Direction(String s, int i) {
		name = s;
		position = i;
	}

	/**
	 * This method is used to get the name string of a direction
	 *
	 * @param null
	 * @return name, the name text of the direction
	 */

	public String getName() {
		return name;
	}

	/**
	 * This method is used to get the position/number of the direction
	 *
	 * @param null
	 * @return position, the position or the number of the direction
	 */

	public int getPosition() {
		return position;
	}
}
