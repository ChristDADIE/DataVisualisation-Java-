package abstraction;

import java.util.HashMap;
import java.util.Map;

/**
 * LatTempData stores the temperature anomalies at 
 * a specific latitude for a given year
 * 
 * @author Cédric Fleury
 * Date : 01/10/2021
 */
public class LatTempData {
	
	public int year;
	public int lat;
	
	private Map<Integer, TempData> lonData;
	
	/**
	 * Constructor 
	 * @param year the year of the temperature anomalies
	 * @param lat the latitude of the temperature anomalies
	 */
	public LatTempData(int year, int lat) {
		this.year = year;
		this.lat = lat;
		lonData = new HashMap<Integer, TempData>();
	}
	
	/**
	 * Add a new data sample in the temperature anomalies
	 * @param tempData the data sample
	 */
	public void addDataSample(TempData tempData) {
		lonData.put(tempData.lon, tempData);
	}
	
	/**
	 * Get a specific data sample
	 * @param lon the longitude of the temperature anomaly
	 * @return the data sample
	 */
	public TempData getDataSample(int lon) {
		return lonData.get(lon);
	}
	
	/**
	 * Get all the data for this latitude (and year)
	 * @return a map with the longitude as keys and the temperature anomaly as values
	 */
	public Map<Integer, TempData> getAllData() {
		return lonData;
	}
	
}
