package ihm.homefinder;

/**
 * Representation of a Location
 *
 * This class was created so that it would be possible to easily replace how a
 * Location is represented.
 */
public class Location {

	private int pLatitude;
	private int pLongitude;

	public Location(int aLatitude, int aLongitude) {
		this.pLatitude = aLatitude;
		this.pLongitude = aLongitude;
	}

	public int getLatitude() {
		return this.pLatitude;
	}

	public int getLongitude() {
		return this.pLongitude;
	}

}
