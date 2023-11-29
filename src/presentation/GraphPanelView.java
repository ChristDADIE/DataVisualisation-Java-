package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GraphPanelView extends JDialog {

	private static final long serialVersionUID = 1L;
	
	
	private GraphPanel myGraphPanel = new GraphPanel(1880,2020,-6,8);

	public GraphPanel getGraph() {
		return myGraphPanel;
	}
	public GraphPanelView() {
		
		setBounds(50, 100, 450, 300);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		myGraphPanel.setLayout(new FlowLayout());
		myGraphPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		add(myGraphPanel);
		
		

	}

}
