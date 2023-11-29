package presentation;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import java.util.List;

/**
 * HistoPanel create a JPanel to draw a histogram
 *  
 * @author Cédric Fleury
 * Date : 01/10/2021
 */
@SuppressWarnings("serial")
public class HistoPanel extends JPanel {

	private int minX, maxX, numValue;
	private float minY, maxY;
	private int selectedValue;
	private boolean isSelected;
	
	private int padding = 25;
	private int labelPadding = 25;
	private Color lineColor = new Color(44, 102, 230, 180);
	private Color selectedLineColor = new Color(44, 102, 230, 255);
	private Color gridColor = new Color(200, 200, 200, 200);
	private int pointWidth = 4;
	private int numberYDivisions = 10;
	private List<Float> data;

	/**
	 * Constructor of HistoPanel
	 * @param minX minimum value on the histogram X axis
	 * @param maxX maximum value on the histogram X axis
	 * @param minY minimum value on the histogram Y axis
	 * @param maxY maximum value on the histogram Y axis
	 */
	public HistoPanel(int minX, int maxX, float minY, float maxY) {
		data = null;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.numValue = (maxX - minX + 1);
		selectedValue = -1;
		isSelected = false;
	}
	
	/**
	 * Get the X value of the bar corresponding to a 2D position on the HistoPanel
	 * @param p the 2D position in the panel
	 * @return the corresponding X value
	 */
	public int getBarOnHisto(Point p) {
		int lon = -1;
		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (maxX - minX);
		lon = minX + (int) ((p.x - padding - labelPadding) / xScale);
		lon = lon<0?((int) ((lon+1)/4)) * 4 - 2 : ((int) ((lon-1)/4)) * 4 + 2;
		lon = Math.max(Math.min(lon, 178), -178);
		return lon;
	}

	/**
	 * Select the bar corresponding to a X value in the histogram
	 * @param val the X value
	 */
	public void selectBar(int val) {
		selectedValue = val;
		isSelected = true;
		repaint();
	}
	
	public int getSelectedValue() {
		return selectedValue;
	}

	/**
	 * De-select the current selected bar of the histogram
	 */
	public void deselectBar() {
		selectedValue = -1;
		isSelected = false;
		repaint();
	}
	
	/**
	 * Update the data of the histogram 
	 * @param list the new data
	 */
	public void updateData(List<Float> dat) {
		data = dat;
		repaint();
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
		for (int i = minX; i <= maxX; i+=4) {
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
			
			g2.setColor(lineColor);
			for (int i = 0; i < data.size(); i++) {
				int x1 = (int) (i * 4 * xScale + padding + labelPadding);
				int y1 = getHeight() - padding - labelPadding;
				if(!Float.isNaN(data.get(i))) {
					y1 = (int) ((maxY - data.get(i)) * yScale + padding);
				}
				int x2 = x1 + (int) (4 * xScale);
				int y2 = getHeight() - padding - labelPadding;
				g2.fillRect(x1+2, y1, x2-x1-3, y2-y1);
			}
			
			if (isSelected) {
				g2.setColor(selectedLineColor);
				int ind = (selectedValue - minX-2) / 4;
				int x1 = (int) (ind * 4 * xScale + padding + labelPadding);
				int y1 = getHeight() - padding - labelPadding;
				if(!Float.isNaN(data.get(ind))) {
					y1 = (int) ((maxY - data.get(ind)) * yScale + padding);
				}
				int x2 = x1 + (int) (4 * xScale);
				int y2 = getHeight() - padding - labelPadding;
				g2.fillRect(x1+2, y1, x2-x1-3, y2-y1);
			}
		}
	}
	
}
