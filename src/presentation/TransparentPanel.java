package presentation;

import java.util.HashMap;
import java.util.Map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.AlphaComposite;

import javax.swing.JPanel;

/**
 * TransparentPanel create a JPanel to draw transparent colored areas
 * on top of the another panel
 *  
 * @author Cédric Fleury
 * Date : 01/10/2021
 */
@SuppressWarnings("serial")
public class TransparentPanel extends JPanel {
	
	private static final int TILE_SIZE = 4;
	
	private Map<Integer, Map<Integer, Tile>> items;
	private int imageWidth;
	private int imageHeight;
	private Tile selectedTile;
	private Shape line;
	
	/**
	 * Constructor of the TransparentPanel
	 * @param width is the width of panel under the TransparentPanel
	 * @param height is the height of panel under the TransparentPanel
	 */
	public TransparentPanel(int width, int height) {
		imageWidth = width;
		imageHeight = height;
		selectedTile = null;
		line = null;
		items = new HashMap<Integer, Map<Integer, Tile>>();
		
		for (int lat=-90+TILE_SIZE/2; lat<=90-TILE_SIZE/2; lat+=TILE_SIZE) {
			if(!items.containsKey(lat))
				items.put(lat, new HashMap<Integer, Tile>());
			
			for (int lon=-180+TILE_SIZE/2; lon<=180-TILE_SIZE/2; lon+=TILE_SIZE) {
				Point p1 = convertCoordinate(lat-TILE_SIZE/2, lon-TILE_SIZE/2);
				Point p2 = convertCoordinate(lat+TILE_SIZE/2, lon+TILE_SIZE/2);
				Tile tile = new Tile(lat, lon, p1.x, p2.y, p2.x-p1.x, p1.y-p2.y);
				items.get(lat).put(lon, tile);
			}
		}
	}
	
	/**
	 * Change the color of a specific area on the panel
	 * @param lat the latitude of the area
	 * @param lon the longitude of the area
	 * @param color the new color of the area
	 */
	public void changTileColor(int lat, int lon, Color color) {
		items.get(lat).get(lon).changeColor(color);
	}
	
	/**
	 * Get the latitude corresponding to a 2D position on the panel
	 * @param p the 2D position in the panel
	 * @return the corresponding latitude
	 */
	public int getLatitude(Point p) {
		int lat = 0;
		Tile tile = null;
		line = null;
		for(Map<Integer, Tile> l : items.values()) {
			for(Tile t : l.values()) {
				if(t.contains(p))
					tile = t;
			}
		}
		if(tile != null) {
			lat = tile.getLat();
		}
		return lat;
	}
	
	/**
	 * Select an area on the map and draw a feedback 
	 * (Red outline around the area)
	 * @param lat the latitude of the area
	 * @param lon the longitude of the area
	 */
	public void selectArea(int lat, int lon) {
		selectedTile = items.get(lat).get(lon);
	}
	
	/**
	 * Draw a line on the panel at a given latitude
	 * @param lat the latitude of the line
	 */
	public void drawLine(int lat) {
		Point p1 = convertCoordinate(lat, -180);
		Point p2 = convertCoordinate(lat, 180);
		line = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);
	}
	
	/**
	 * Get the coordinate of the area corresponding to a 2D position on the MapPanel
	 * @param p the 2D position in the panel 
	 * @return the coordinate as a Point where x is the latitude and y is the longitude
	 */
	public Point getTile(Point p) {
		Point coord = new Point();
		Tile tile = null;
		for(Map<Integer, Tile> l : items.values()) {
			for(Tile t : l.values()) {
				if(t.contains(p))
					tile = t;
			}
		}
		if (tile!=null) {
			coord.x = tile.getLat();
			coord.y = tile.getLon();
		}
		return coord;
	}
	
    private Point convertCoordinate(int lat, int lon) {
    	Point p = new Point();
    	p.x = (int) ((imageWidth/360.0) * (180 + lon));
    	p.y = (int) ((imageHeight/180.0) * (90 - lat));
    	return p;
    }
	
	 // This is Swing, so override Paint*Component* - not Paint
    protected void paintComponent(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // call super.paintComponent to get default Swing 
        // painting behavior (opaque honored, etc.)
        super.paintComponent(g2d);
        
        items.values().forEach(l -> l.values().forEach(i -> i.drawShape(g2d)));
        
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1.0f));
        if(selectedTile != null) {
        	g2d.setColor(Color.RED);
        	g2d.setStroke(new BasicStroke(2));
        	selectedTile.drawBorder(g2d);
        }
        
        if (line != null) {
        	g2d.setColor(Color.BLACK);
        	g2d.setStroke(new BasicStroke(2));
        	g2d.draw(line);
        }
    }

}


// Get hints from :
// https://www.it-swarm-fr.com/fr/java/comment-definir-un-fond-transparent-de-jpanel/968244910/