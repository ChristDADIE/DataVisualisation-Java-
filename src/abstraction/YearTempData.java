package abstraction;

import java.util.HashMap;
import java.util.Map;

/**
 * YearTempData stores the temperature anomalies for a given year
 * 
 * @author Cédric Fleury
 * Date : 01/10/2021
 */
public class YearTempData {
	
	public int year;
	
	private Map<Integer, LatTempData> yearData;

	/**
	 * Constructor 
	 * @param year the year of the temperature anomalies
	 */
	public YearTempData(int year) {
		this.year = year;
		yearData = new HashMap<Integer, LatTempData>();
	}

	/**
	 * Add a new data sample in the temperature anomalies
	 * @param tempData the data sample
	 */
	public void addDataSample(TempData tempData) {
		if(!yearData.containsKey(tempData.lat)) {
			yearData.put(tempData.lat, new LatTempData(tempData.year, tempData.lat));
		}
		yearData.get(tempData.lat).addDataSample(tempData);
	}
	
	/**
	 * Get a specific data sample
	 * @param lat the latitude of the temperature anomaly
	 * @param lon the longitude of the temperature anomaly
	 * @return the data sample
	 */
	public TempData getDataSample(int lat, int lon) {
		return yearData.get(lat).getDataSample(lon);
	}
	
	/**
	 * Get the temperature anomalies at a specific latitude
	 * for a given year
	 * @param lat
	 * @return the corresponding LatTempData
	 */
	public LatTempData getLatitudeData(int lat) {
		return yearData.get(lat);
	}
	
	/**
	 * Get all the data for a year
	 * @return a map with years as keys and LatTempData as values
	 */
	public Map<Integer, LatTempData> getAllData() {
		return yearData;
	}
}
