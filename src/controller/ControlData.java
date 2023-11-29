package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import abstraction.DataManager;

import presentation.MainInterface;


public class ControlData {
	
	private DataManager myData;
	private MainInterface theInterface;
	private int firstYear;
	private int lastYear;

	
	
	private int yearSelected;
	Map<Integer, Map<Integer, Float>> dataYear;
	Map<Integer, Float> dataYearLatitude;
	

	 
	
	/*graph panel / graph panel view variables*/
	private int latitudeGraph;
	private int longitudeGraph;
	
	/* histo panel / histo panel view variables*/
	private int latitudeLineView;
	
	public ControlData(MainInterface theInterface) {
		DataManager myData = new DataManager();
		this.myData = myData;
		this.theInterface = theInterface;
		
	}
	
	
	public float getFirstYear() {
		return firstYear;
	}
	
	public float getLastYear() {
		return lastYear;
	}
	

	public void setLatitudeGraph(int latitudeGraph) {
		this.latitudeGraph= latitudeGraph;
	}
	
	public void setLongitudeGraph(int longitudeGraph) {
		this.longitudeGraph= longitudeGraph;
	}
	
	public void initVariable() {
		firstYear = myData.getFirstYear();
		lastYear = myData.getFirstYear()+ myData.getNumberOfYear()-1;
		yearSelected = theInterface.getYearSelected();
		
	}
	public void initYearMain() {
		
		/*years for the slider of the map panel*/
		initVariable();
		theInterface.setVFirstYear(firstYear);
		theInterface.setVLastYear(lastYear);
		
		/*years for the slider of the graph panel*/
		
	}
	
	public void initMapPanel() {
		initVariable();
		
		 
		Map<Integer, Map<Integer, Float>> dataYear	= myData.getDataByYear(yearSelected);
		for(Integer key: dataYear.keySet()) {
			dataYearLatitude = myData.getDataByLatitude(yearSelected, key);
			for(Integer key1: dataYearLatitude.keySet()) {
				
				float tempVal = myData.getDataSample(yearSelected, key, key1);
				
				theInterface.getMapPanel().changTileColor(key, key1, temp2Color(tempVal));
				theInterface.getMapPanel().repaint();
				
			}
			
		}

	}



	private Color temp2Color (float temp) {
		int r = 0, g = 0, b = 0;
		Color newColor;
		if (Float.isNaN(temp)) {
			newColor = new Color(0, 0, 0, 0);
		} else {
			
			float delta = myData.getMaxTempValue() - myData.getMinTempValue() -myData.getReduceDelta();
			float percentage = Math.min((temp - myData.getMinTempValue()) * 100 / delta, 100);
			
			if(percentage < 33) {
				r = 0;
				g = (int) (7.72 * percentage);
				b = 255;
			} else if (percentage < 66) {
				r = (int) (7.72 * (percentage-33));
				g = 255;
				b = (int) (750 - 7.5 * (percentage+33));
			} else {
				r = 255;
				g = (int) (750 - 7.5 * percentage);
				b = 0;
			}
				newColor = new Color(r, g, b, myData.getAlphaValue());
		}
		return newColor;
	}
	
/*-----------------------------------------*/

	/*graph panel*/
	public List<Float> dataGraph() {
		
		latitudeGraph = theInterface.getLatSelected();
		longitudeGraph = theInterface.getLonSelected();
		Map<Integer, Float> dataForGraph = myData.getDataForAllYears(latitudeGraph, longitudeGraph);
		List<Float> tempValByYear = new ArrayList<>();
		for (Integer key: dataForGraph.keySet()) {
			tempValByYear.add(dataForGraph.get(key));
		}
		return tempValByYear;	
	}
	
/* MISE A JOUR GRAPHE*/
	public void paintGraph() {
		List<Float> tempData = this.dataGraph();

		//GraphPanel graph = theInterface.getGraphPanel().getGraph();
		theInterface.getGraphPanel().getGraph().updateData(tempData);

		
	}
/*--------------------------------------------------*/
	
	/*histo panel*/
	
	public List<Float> dataHisto() {
		initVariable();

		latitudeLineView = theInterface.getLatitudeLine();

		Map<Integer, Float> dataForHisto = myData.getDataByLatitude(yearSelected, latitudeLineView);
		SortedSet<Integer> longitudesSort = new TreeSet<>(dataForHisto.keySet());
		List<Float> tempValByYear = new ArrayList<>();
		for (Integer key: longitudesSort) {
			tempValByYear.add(dataForHisto.get(key));
		}
		
		return tempValByYear;
		
	}

	/* MISE A JOUR HISTOGRAMME*/
	public void painthisto() {
		List<Float> dataHis = this.dataHisto();
		
		theInterface.getHistoPanel().getHisto().updateData(dataHis);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
