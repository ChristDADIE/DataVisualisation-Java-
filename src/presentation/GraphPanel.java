package presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

/**
 * GraphPanel create a JPanel to draw a graph
 *  
 * @author Cédric Fleury
 * Date : 01/10/2021
 */
@SuppressWarnings("serial")
public class GraphPanel extends JPanel {

	private int minX, maxX, numValue;
	private float minY, maxY;
	
	private int verticalLineValue;
	
	private int padding = 25;
	private int labelPadding = 25;
	private Color lineColor = new Color(44, 102, 230, 180);
	private Color pointColor = new Color(100, 100, 100, 180);
	private Color gridColor = new Color(200, 200, 200, 200);
	private Color vertLineColor = new Color(0, 0, 0, 255);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private static final Stroke LINE_STROKE = new BasicStroke(3f);
	private int pointWidth = 4;
	private int numberYDivisions = 10;
	private List<Float> data;

	/**
	 * Constructor of GraphPanel
	 * @param minX minimum value on the graph X axis
	 * @param maxX maximum value on the graph X axis
	 * @param minY minimum value on the graph Y axis
	 * @param maxY maximum value on the graph Y axis
	 */
	public GraphPanel(int minX, int maxX, float minY, float maxY) {
		data = null;

		
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.numValue = maxX - minX + 1;
		verticalLineValue = -1;
	}
	
	/**
	 * Get the X value corresponding to a 2D position on the GraphPanel
	 * @param p the 2D position in the panel
	 * @return the corresponding X value
	 */
	public int getXValueOnGraph(Point p) {
		int year = -1;
		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (maxX - minX);
		if(p.y > padding && p.y < getHeight() - padding - labelPadding) {
			year = Math.min(Math.max(minX + (int) ((p.x - padding - labelPadding) / xScale), minX), maxX);
		}
		return year;
	}
	
	/**
	 * Draw a vertical at a specific X value
	 * @param val the X value where to draw the line
	 */
	public void drawVerticalLine(int val) {
		verticalLineValue = val;
		this.repaint();
	}

	/**
	 * Update the data of the graph 
	 * @param list the new data
	 */
	public void updateData(List<Float> list) {
		data = list;
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (maxX - minX);
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxY - minY);

		// draw white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);

		// create hatch marks and grid lines for y axis.
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
			int y1 = y0;
			if (numValue > 0) {
				g2.setColor(gridColor);
				g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
				g2.setColor(Color.BLACK);
				String yLabel = ((int) ((minY + (maxY - minY) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}

		// and for x axis
		for (int i = minX; i <= maxX; i++) {
			if (numValue > 1) {
				int x0 = (i - minX) * (getWidth() - padding * 2 - labelPadding) / (numValue - 1) + padding + labelPadding;
				int x1 = x0;
				int y0 = getHeight() - padding - labelPadding;
				int y1 = y0 - pointWidth;
				if (((i - minX) % 20) == 0) {
					g2.setColor(gridColor);
					g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
					g2.setColor(Color.BLACK);
					String xLabel = i + "";
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
				}
				g2.drawLine(x0, y0, x1, y1);
			}
		}

		// create x and y axes 
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);
		
		
		if (data != null) {
			
			List<Point> graphPoints = new ArrayList<>();
			for (int i = 0; i < numValue; i++) {
				int x1 = (int) (i * xScale + padding + labelPadding);
				int y1 = getHeight() - padding - labelPadding;
				if(!Float.isNaN(data.get(i))) {
					y1 = (int) ((maxY - data.get(i)) * yScale + padding);
				}
				graphPoints.add(new Point(x1, y1));
			}
	
			Stroke oldStroke = g2.getStroke();
			g2.setColor(lineColor);
			g2.setStroke(GRAPH_STROKE);
			for (int i = 0; i < graphPoints.size() - 1; i++) {
				int x1 = graphPoints.get(i).x;
				int y1 = graphPoints.get(i).y;
				int x2 = graphPoints.get(i + 1).x;
				int y2 = graphPoints.get(i + 1).y;
				g2.drawLine(x1, y1, x2, y2);
			}
	
			g2.setStroke(oldStroke);
			g2.setColor(pointColor);
			for (int i = 0; i < graphPoints.size(); i++) {
				int x = graphPoints.get(i).x - pointWidth / 2;
				int y = graphPoints.get(i).y - pointWidth / 2;
				int ovalW = pointWidth;
				int ovalH = pointWidth;
				g2.fillOval(x, y, ovalW, ovalH);
			}
			
			g2.setStroke(LINE_STROKE);
			g2.setColor(vertLineColor);
			int x1 = (int) ((verticalLineValue-minX) * xScale + padding + labelPadding);
			int y1 = getHeight() - padding - labelPadding;
			int x2 = (int) ((verticalLineValue-minX) * xScale + padding + labelPadding);
			int y2 = padding;
			g2.drawLine(x1, y1, x2, y2);
			
			
			g2.setStroke(oldStroke);
		}
	}
	

}
