package presentation;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Rectangle;

/**
 * Tile create a colored area in a TransparentPanel
 *  
 * @author Cédric Fleury
 * Date : 01/10/2021
 */
public class Tile {

	private int lat;
	private int lon;
	private Shape shape;
	private Color color;
	
	/**
	 * Constructor of Tile
	 * @param lat the latitude of the area
	 * @param lon the longitude of the area
	 * @param x the x position of the area in the panel
	 * @param y the y position of the area in the panel
	 * @param w the width of the area in the panel
	 * @param h the height of the area in the panel
	 */
	public Tile(int lat, int lon, int x, int y, int w, int h) {
		this.lat = lat;
		this.lon = lon;
		this.shape = new Rectangle(x, y, w, h);
		this.color = Color.black;
	}
	
	/**
	 * Change the color of the area
	 * @param c the new color
	 */
	public void changeColor(Color c) {
		color = c;
	}
	
	/**
	 * Draw the area
	 * @param g the Graphics2D in which to draw the area
	 */
	public void drawShape(Graphics2D g) {
		g.setColor(color);
		g.fill(shape);
	}
	
	/**
	 * Draw the borders of area
	 * @param g the Graphics2D in which to draw the borders
	 */
	public void drawBorder(Graphics2D g) {
		g.draw(shape);
	}
	
	/**
	 * Test if the area contains the Point
	 * @param p the 2D Point
	 * @return true if the area contains p
	 */
	public boolean contains(Point p) {
		return shape.contains(p);
	}

	/**
	 * Get the corresponding latitude of the area
	 * @return the latitude
	 */
	public int getLat() {
		return lat;
	}

	/**
	 * Get the corresponding longitude of the area
	 * @return the longitude
	 */
	public int getLon() {
		return lon;
	}
	
	
	
}
