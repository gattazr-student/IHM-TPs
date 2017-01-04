package ihm.homefinder;

/**
 * Home
 */
public class Home {

	/** Location of Home */
	private Location pLocation;
	/** Number of rooms */
	private int pNbRooms;
	/** Price of the room */
	private int pPrice;

	/**
	 * Create a Home with the given information
	 *
	 * @param aLocation
	 *            location of the Home
	 * @param aNbRooms
	 *            number of rooms in the Home
	 * @param aPrice
	 *            price of the Home
	 */
	public Home(Location aLocation, int aNbRooms, int aPrice) {
		this.pLocation = aLocation;
		this.pNbRooms = aNbRooms;
		this.pPrice = aPrice;
	}

	/**
	 * @return Home location
	 */
	public Location getLocation() {
		return this.pLocation;
	}

	/**
	 * @return number of rooms in the Home
	 */
	public int getNbRooms() {
		return this.pNbRooms;
	}

	/**
	 * @return price of the Home
	 */
	public int getPrice() {
		return this.pPrice;
	}

}
