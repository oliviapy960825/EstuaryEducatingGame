
/**
 * 
 * @author Erin O'Connor, Aashaka Desai, Bree McCausland, Eric Nahe, Peiyu Wang
 *
 */
public class TileMap {
	// class which builds a 2d array of tiles based on a string passed in.
	Tile[][] tilemap;
	Background background;
	String layout;

	/**
	 * This is the TileMap constructor, and it sets the tile map of the
	 * background based on the background enum
	 * 
	 * @param currentLayout
	 * @return null
	 */
  
	public TileMap(String currentLayout) {
		layout = currentLayout;
		switch (currentLayout) {
		case "ground":
			this.background = Background.HOUSE;
			tilemap = new Tile[View.rows][View.columns];
			for (int i = 0; i < View.rows; i++) {
				for (int j = View.columns - 13; j < View.columns - 11; j++) {
					tilemap[i][j] = Tile.GROUND;
				}
			}
			;
			break;

		case "emptytest":
			this.background = Background.SHED;
			tilemap = new Tile[View.rows][View.columns];
			break;
		case "beachwave":
			this.background = Background.BEACH;

			tilemap = new Tile[View.rows][View.columns];
			for (int i = 0; i < 5; i++)
				for (int j = 0; j < 10; j++)
					if (j % 3 == 0)
						tilemap[i][j] = Tile.WAVE1;
					else if (j % 3 == 1)
						tilemap[i][j] = Tile.WAVE2;
					else
						tilemap[i][j] = Tile.WAVE3;
			break;
			
		case "citybackground":
			this.background=Background.CITY;
			tilemap = new Tile[View.rows][View.columns];
			break;
		case "estuarybackground":
			this.background=Background.ESTUARY;
			tilemap = new Tile[View.rows][View.columns];
			break;
		default:
			this.background = Background.HOUSE;
			tilemap = new Tile[View.columns][View.rows];
			break;

		}

	}

	/**
	 * This method is used to get the background
	 * 
	 * @param null
	 * @return background
	 */
  
	public Background getBackground() {
		return background;
	}

	/**
	 * This method is used to set the background
	 * 
	 * @param background
	 * @return null
	 */
  
	public void setBackground(Background background) {
		this.background = background;
	}

	/**
	 * This method is used to set the tile map of a certain background
	 * 
	 * @param tilemap
	 * @return null
	 */
  
	public void setTilemap(Tile[][] tilemap) {
		this.tilemap = tilemap;
	}

	/**
	 * This method is used to get the tile map of a certain background
	 * 
	 * @param null
	 * @return tilemap
	 */
  
	public Tile[][] getTilemap() {
		return tilemap;
	}
}
