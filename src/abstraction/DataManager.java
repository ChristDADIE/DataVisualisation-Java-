package abstraction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * DataManager stores the temperature anomalies in a data
 * structure which allows to quickly access to the data
 * 
 * @author Cédric Fleury
 * Date : 01/10/2021
 */
public class DataManager {
	
	private Map<Integer, YearTempData> data;
	private int firstYear; 
	
	
	
	/*données pour les couleurs températures*/
	
	private float MAX_TEMP = -1000;
	private float MIN_TEMP = 1000;
	private int REDUCE_VALUE = 3;
	private int ALPHA_VALUE = 128;
	
	
	/*à revoir*/
	private float MAX_LON = -100;
	private float MIN_LON = 1000;
	
	
	

	/**
	 * Create a Data Manager
	 */
	public DataManager() {
		
		data = new HashMap<Integer, YearTempData>();
		try {
			loadData(this.getClass().getResource("tempanomaly_4x4grid.csv").toURI().getPath());
			System.out.println(data.get(1980).getAllData());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Instantiate the data value for an additional year
	 * @param year the value of the year
	 */
	public void addYear(int year) {
		data.put(year, new YearTempData(year));
	}
	
	/**
	 * Add a data sample in the data structure
	 * @param tempData the new data sample
	 */
	public void addDataSample(TempData tempData) {
		data.get(tempData.year).addDataSample(tempData);
	}
	
	/**
	 * Define which year is the first year
	 * @param year the value of the year
	 */
	public void setFirstYear(int year) {
		firstYear = year;
	}
	
	/**
	 * Get the first year of the data
	 * @return the first year of the data 
	 */
	public int getFirstYear() {
		return firstYear;
	}
	
	/**
	 * Get the number of years available in data
	 * @return The number of years
	 */
	public int getNumberOfYear() {
		return data.size();
	}
	
	/**
	 *
temperature anomaly
	 * @param lat the latitude of the temperature anomaly
	 * @param lon the longitude of the temperature anomaly
	 * @return the temperature anomaly in degree
	 */
	/*1 echantillon donnees precis*/
	public float getDataSample(int year, int lat, int lon) {
		return data.get(year).getDataSample(lat, lon).getValue();
	}
	
	/**
	 * Get all the temperature anomalies for a given latitude (at a specific year).
	 * @param year the year of the temperature anomalies
	 * @param lat the latitude of the temperature anomalies
	 * @return a map with the longitude as keys and temperature anomalies as values
	 */
	public Map<Integer, Float> getDataByLatitude(int year, int lat) {
		Map<Integer, Float> map = new HashMap<Integer, Float>();
		Map<Integer, TempData> LatData = data.get(year).getLatitudeData(lat).getAllData();
		LatData.keySet().forEach(k -> map.put(k, LatData.get(k).getValue()));
		return map;
	}
	
	/**
	 * Get all the temperature anomalies for a given year
	 * @param year the year of the temperature anomalies
	 * @return a map of maps with the latitude and the longitude as keys and temperature anomalies as values
	 */
	public Map<Integer, Map<Integer, Float>> getDataByYear(int year) {
		Map<Integer, Map<Integer, Float>> map = new HashMap<Integer, Map<Integer, Float>>();
		Map<Integer, LatTempData> yearData = data.get(year).getAllData();
		yearData.keySet().forEach(lat -> map.put(lat, getDataByLatitude(year, lat)));
		return map;
	}
	
	/**
	 * Get the data for all years for a specific area
	 * @param lat the latitude of the area
	 * @param lon the longitude of the area
	 * @return a map with the years as keys and temperature anomalies as values
	 */
	public Map<Integer, Float> getDataForAllYears(int lat, int lon) {
		Map<Integer, Float> map = new HashMap<Integer, Float>();
		data.keySet().forEach(y -> map.put(y, data.get(y).getDataSample(lat, lon).getValue()));
		return map;
	}
	
	public float getMaxTempValue() {
		return MAX_TEMP;
	}
	
	public float getMinTempValue() {
		return MIN_TEMP;
	}
	
	public int getReduceDelta() {
		return REDUCE_VALUE;
	}
	
	public int getAlphaValue() {
		return ALPHA_VALUE;
	}
	
	/*à revoir*/
	public float getMaxLongitude() {
		return MAX_LON;
	}
	
	/*à revoir*/
	public float getMinLongitude() {
		return MIN_LON;
	}
	
	 
	public void loadData(String filePath) {
		try {
			FileReader file = new FileReader(filePath);
			BufferedReader bufRead = new BufferedReader(file);
			String line = bufRead.readLine();
			
			// Parse the first line
			String[] array = line.split(",");
			int firstYear = Integer.parseInt(array[2]);
			
			setFirstYear(firstYear);
			
			int year;
			boolean def;
			float tempValue;
			
			for(int i = 2; i <array.length;i++ ) {
				
				addYear(firstYear+ (i-2));
				
			}
			
			// Move to the second line
			line = bufRead.readLine();
			while (line != null) {
				// Parse the line
				String[] array1 = line.split(",");
				int val = Integer.parseInt(array1[0]);
				int temp = Integer.parseInt(array1[1]);
				
				/*Pour les min et les max de la longitude */
				if (MAX_LON <  Float.parseFloat(array1[1])) {
					System.out.println();
					MAX_LON = Float.parseFloat(array1[1]);
				}
				
				
				if (MIN_LON >  Float.parseFloat(array1[1])) {
					MIN_LON = Float.parseFloat(array1[1]);
				}
				
				
				for(int i = 2; i <array1.length;i++ ) {
					
					year = firstYear +(i-2);
					if(array1[i].equals("NA")) {
						
						 tempValue = Float.NaN;
						 def = false;	
					}
					else {
						 tempValue = Float.parseFloat(array1[i]);
						 def = true; 
						 
							if (MAX_TEMP <  Float.parseFloat(array1[i])) {
								System.out.println();
								MAX_TEMP = Float.parseFloat(array1[i]);
							}
							
							
							if (MIN_TEMP >  Float.parseFloat(array1[i])) {
								MIN_TEMP = Float.parseFloat(array1[i]);
							}
						 
					}

					TempData tempDate = new TempData(val,temp,year,def,tempValue);
					addDataSample(tempDate);	
				}
				
				line = bufRead.readLine();
			}
			bufRead.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	

	
}
