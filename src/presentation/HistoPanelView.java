package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HistoPanelView extends JDialog {

	private static final long serialVersionUID = 1L;
	private  HistoPanel myHistoPanel = new HistoPanel(-180,180,-6.24f,8.97f);

	public HistoPanel getHisto() {
		return myHistoPanel;
	}

	public HistoPanelView() {
		setBounds(50, 500, 450, 300);
		setAlwaysOnTop(true);
		setLayout(new BorderLayout());
		myHistoPanel.setLayout(new FlowLayout());
		myHistoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(myHistoPanel);
	}

}
