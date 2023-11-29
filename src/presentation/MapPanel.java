package presentation;

import java.awt.Color;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

/**
 * MapPanel create a JPanel to draw a map with 
 * transparent colored areas on top of it
 *  
 * @author Cédric Fleury
 * Date : 01/10/2021
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel {
	
	private TransparentPanel top;
	private JPanel bottom;

	/**
	 * Constructor of the Map Panel
	 * @param imagePath the path of the map image
	 */
	public MapPanel(String imagePath) {
		
		ImageIcon image = new ImageIcon(imagePath);
		
		// setting layout to null so we can make panels overlap
		this.setLayout(null);
		
        // add top Panel - components Paint in order added, so add topPanel first
		top = new TransparentPanel(image.getIconWidth(), image.getIconHeight());
		top.setOpaque(false);
		top.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		this.add(top);
		
		bottom = new JPanel();
	    bottom.setOpaque(true);
	    bottom.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
			
		JLabel labelImage = new JLabel(image);
		bottom.add(labelImage);
		this.add(bottom);

		this.setPreferredSize(new DimensionUIResource(image.getIconWidth(), image.getIconHeight()));
	}

	/**
	 * Change the color of a specific area on the map
	 * @param lat the latitude of the area
	 * @param lon the longitude of the area
	 * @param color the new color of the area
	 */
	public void changTileColor(int lat, int lon, Color color) {
		top.changTileColor(lat, lon, color);
	}
	
	/**
	 * Select an area on the map and draw a feedback 
	 * (Red outline around the area)
	 * @param lat the latitude of the area
	 * @param lon the longitude of the area
	 */
	public void selectArea(int lat, int lon) {
		top.selectArea(lat, lon);
	}
	
	/**
	 * Draw a line on the map at a given latitude
	 * @param lat the latitude of the line
	 */
	public void drawLine(int lat) {
		top.drawLine(lat);
	}
	
	/**
	 * Get the latitude corresponding to a 2D position on the MapPanel
	 * @param p the 2D position in the panel
	 * @return the corresponding latitude
	 */
	public int getLatitude(Point p) {
		return top.getLatitude(p);
	}
	
	/**
	 * Get the coordinate of the area corresponding to a 2D position on the MapPanel
	 * @param p the 2D position in the panel 
	 * @return the coordinate as a Point where x is the latitude and y is the longitude
	 */
	public Point getTile(Point p) {
		return top.getTile(p);
	}
}
