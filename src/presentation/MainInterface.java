package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import abstraction.DataManager;
import controller.ControlData;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URISyntaxException;
import java.util.TreeSet;



public class MainInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/*mon chemin vers la map*/
	
	private String path; 
	private MapPanel mapPanel;
	private JTextField textField;
	
	/*annee pour Slider Vue*/
	private int vFirstYear;
	private int vLastYear;
	private GraphPanelView dialog = new GraphPanelView();
	
	private int yearSelected;
	
/* latitude and longitude selected*/
	private int latSelected ;
	private int lonSelected;
	
	/*Le controleur*/
	
	private ControlData controler = new ControlData(this);
	
	
	
	/*Histo view*/
	private HistoPanelView myHisto = new HistoPanelView();
	private int latitudeLine;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					MainInterface frame = new MainInterface();
					

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public MapPanel getMapPanel() {
		return  mapPanel;
	}
	
	public int getYearSelected() {
		return  yearSelected;
	}
	public void setYearSelected(int year) {
		yearSelected = year;
	}
	
	public void setVFirstYear(int year) {
		vFirstYear = year;
	}
	
	public int getLatSelected() {
		return  latSelected;
	}
	
	public int getLonSelected() {
		return  lonSelected;
	}
	
	public void setLatSelected(int lat) {
		  latSelected=lat;
	}
	
	public void setLonSelected(int lon) {
		lonSelected=lon;
	}

	
	public GraphPanelView getGraphPanel() {
		return  dialog;
	}
	
	public HistoPanelView getHistoPanel() {
		return  myHisto;
	}
	/*----------------------------------------*/
	public void setVLastYear(int year) {
		vLastYear = year;
	}
	
	public int getLatitudeLine() {
		return latitudeLine;
	}
	
	public void setLatitudeLine(int lati) {
		latitudeLine= lati;
	}
	
	
	/**
	 * @throws URISyntaxException
	 */
	public MainInterface() throws URISyntaxException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 960, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("exemple");
		//setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// init contol
		
		controler.initYearMain();
	
		
/* Panel mode*/	
		path = this.getClass().getResource("earth.png").toURI().getPath();
		mapPanel = new MapPanel(path);

		
		JPanel panelForMode = new JPanel();
		panelForMode.setLayout(new BoxLayout(panelForMode, BoxLayout.Y_AXIS));
		
		JLabel labelMode = new JLabel("Selection Mode:");
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton selectArea = new JRadioButton("select area");
		

		
		JRadioButton selectLatitude= new JRadioButton("select latitude");

		JLabel labelDisplayed = new JLabel("Displayed Windows:");
		JCheckBox  graphByear = new JCheckBox("Graph by year");
		
		

		
		JCheckBox  latitudeHisto = new JCheckBox("latitude histo");
		
		buttonGroup.add(selectArea);
		buttonGroup.add(selectLatitude);
		panelForMode.add(labelMode);
		panelForMode.add(selectArea);
		panelForMode.add(selectLatitude);
		
		panelForMode.add(labelDisplayed);
		panelForMode.add(graphByear);
		panelForMode.add(latitudeHisto);
		

		
		
		contentPane.add(panelForMode, BorderLayout.EAST);
		
/*--------------------*/	
		
		/*panel for slider*/
		JPanel panelForSlider = new JPanel();
		panelForSlider.setLayout(new BoxLayout(panelForSlider, BoxLayout.Y_AXIS));
		
		yearSelected =1880;
		
		
		JPanel panel = new JPanel();
		panelForSlider.add(panel);
		
		
		
		JLabel labelYears = new JLabel("years");
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(labelYears);
		textField = new JTextField();
		textField.setText(Integer.valueOf(yearSelected).toString());


		panel.add(textField);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		
		panelForSlider.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		
		JSlider slideYears = new JSlider(vFirstYear,vLastYear);
		slideYears.setValue(yearSelected);
		
		
		slideYears.setMinorTickSpacing(1);
		slideYears.setMajorTickSpacing(20);
		slideYears.setPaintLabels(true);
		slideYears.setPaintTicks(true);
		slideYears.setPaintTrack(true);
		
		controler.initMapPanel();
		

		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int textYear;
				String val = textField.getText();
				try {
					textYear = Integer.parseInt(val);
					if(textYear<1880 || textYear>2020 )textYear=1880;
					
				}catch(NumberFormatException exept) {
					textYear = 1880;
				}
				slideYears.setValue(textYear);	
			}
		});
		panel_1.add(slideYears);
		
		contentPane.add(panelForSlider, BorderLayout.SOUTH);

		
		mapPanel.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(selectArea.isSelected()) {
					
					Point lonLat = mapPanel.getTile(e.getPoint());
					setLatSelected(lonLat.x);
					setLonSelected(lonLat.y);

					mapPanel.selectArea(latSelected, lonSelected);
					mapPanel.repaint();
					controler.paintGraph();// pour le graph panel
					

				}
				
				if(selectLatitude.isSelected()) {
					int theLatitudeLine = mapPanel.getLatitude(e.getPoint());
					setLatitudeLine(theLatitudeLine);
					mapPanel.drawLine(latitudeLine);
					mapPanel.repaint();
					controler.painthisto();
				}

			}
		
		
		});
			
		/*pour mon graph panel*/	
		graphByear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(graphByear.isSelected()) {
					dialog.setVisible(true);
					
				}
				else {
					dialog.setVisible(false);
				}


			}});
		/*mise à jour en fonction de la ligne A refaire: avec mouse dragged peut être, je verrai*/
		dialog.getGraph().addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Point pointGraph = e.getPoint();
				GraphPanel graph = dialog.getGraph();
				int graphYear = graph.getXValueOnGraph(pointGraph);
				graph.drawVerticalLine(graphYear);
				slideYears.setValue(graphYear);
			}
		});
		
		
		/*Histo Panel*/
		latitudeHisto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(latitudeHisto.isSelected()) {
					myHisto.setVisible(true);
				}else {
					myHisto.setVisible(false);
				}
				
			}
		});
		
		
		slideYears.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				JSlider source = (JSlider) e.getSource();
				yearSelected = source.getValue();
				
				controler.initMapPanel();
				
				textField.setText(Integer.toString(yearSelected));
				
				/*pour la ligne du graphpanel*/
				GraphPanel graph = dialog.getGraph();
				graph.drawVerticalLine(yearSelected);
				
				/*change la valeur de l'histogramme selon l'année aussi*/
				controler.painthisto();
			}
			
		} );
		
		myHisto.getHisto().addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Point poinBar = e.getPoint();
				HistoPanel histoPan = myHisto.getHisto();
				int xLonBar = histoPan.getBarOnHisto(poinBar);
				histoPan.selectBar(xLonBar);
				setLonSelected(histoPan.getSelectedValue());
				mapPanel.selectArea(latitudeLine, lonSelected);
				mapPanel.repaint();
				controler.paintGraph();

				
			}
			
		});

		
		contentPane.add(mapPanel,BorderLayout.CENTER);
		Dimension mapSize = mapPanel.getPreferredSize();
		this.setSize(new Dimension((int)(mapSize.getWidth() + 1.275 * panelForMode.getPreferredSize().getWidth()), (int)(mapSize.getHeight() + 1.75 * panelForSlider.getPreferredSize().getHeight())));
	}
}
