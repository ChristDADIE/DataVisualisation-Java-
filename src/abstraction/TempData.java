package abstraction;

/**
 * TempData stores a specific data sample
 * 
 * @author Cédric Fleury
 * Date : 01/10/2021
 */
public class TempData {
	
	/**
	 * The year of the data sample
	 */
	public int year;
	
	/**
	 * The latitude of the data sample
	 */
	public int lat;
	
	/**
	 * The longitude of the data sample
	 */
	public int lon;
	
	private boolean isDefined;
	private float tempValue;
	
	/**
	 * Constructor
	 * @param lat the latitude of the data sample
	 * @param lon the latitude of the data sample
	 * @param year the year of the data sample
	 * @param isDefined true if the value of the data sample is defined
	 * @param tempValue the value of the temperature anomaly
	 */
	public TempData(int lat, int lon, int year, boolean isDefined, float tempValue) {
		this.lat = lat;
		this.lon = lon;
		this.year = year;
		this.isDefined = isDefined;
		if(isDefined)
			this.tempValue = tempValue;
		else
			this.tempValue = Float.NaN;
	}
	
	/**
	 * Is value of the temperature anomaly of this data sample defined ?
	 * @return true if the value is defined
	 */
	public boolean isValueDefined() {
		return isDefined;
	}
	
	/**
	 * Get the value of the temperature anomaly of this data sample 
	 * @return th value in degree
	 */
	public float getValue() {
		return tempValue;
	}
}
