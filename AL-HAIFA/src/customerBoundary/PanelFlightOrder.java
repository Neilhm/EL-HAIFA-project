package customerBoundary;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import customerControl.CustomerLogic;
import customerEntity.Flight;
import customerEntity.FlightDetails;
import managerBoundary.Item;

public class PanelFlightOrder extends JPanel {
	private static final long serialVersionUID = 1L;
	private int iteration;
	private static JTable table;
	private JScrollPane scrollPane;
	private static DefaultTableModel tableModel;

	PanelFlightOrder() {
		iteration = 0;
		initComponents();
	}
	
	public JTable getTable() {
		return table;
	}

	public void initComponents() {

		// Create table according to data structure
		table = new JTable();
		tableModel = new DefaultTableModel();
		String header[] = new String[] { "ID","Departure Date", "Landing Date"};

		tableModel.setColumnIdentifiers(header);
		table.setRowSelectionAllowed(true);
		table.setModel(tableModel);
		table.setRowHeight(25);

		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(380, 90));
		scrollPane.setMaximumSize(new Dimension(450, 20000));
		add(scrollPane);

		// Set table dimensions
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(250);
		columnModel.getColumn(1).setPreferredWidth(550);
		columnModel.getColumn(2).setPreferredWidth(550);
	}
	public void refreshComp(Date fromDate, Date toDate, long fromAirport, long toAirport) {
		initTableData(fromDate, toDate, fromAirport, toAirport);
	}
	private void initTableData(Date fromDate, Date toDate, long fromAirport, long toAirport) {
		if(toDate == null || fromDate == null || toDate.before(fromDate)) {
			JOptionPane.showMessageDialog(this, "invalid input");
			tableModel.setRowCount(0);
			return;
		}
		ArrayList<Flight> flights = CustomerLogic.getInstance().getFlights();
		ArrayList<Flight> result = new ArrayList<>();
		for(Flight f : flights) {
			if((f.getDepartureDate().after(fromDate) && f.getDepartureDate().before(toDate)) && 
					(f.getLandingDate().after(fromDate) && f.getLandingDate().before(toDate))) {
				if(f.getDepartureAirport() == fromAirport &&
						f.getLandinAirport() == toAirport) {
					if(!f.getStatus().toString().equals("Cancelled"))
						result.add(f);
				}
				
			}
		}
		System.out.println(result);
		if(result.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No flights in this range");
			tableModel.setRowCount(0);
			return;
		}
		tableModel = (DefaultTableModel) (table.getModel());
		tableModel.setRowCount(0);
		// Following code gets all orders details for selected order id and updates
		// table rows
		for (Flight f : result) {
			Vector<Object> data = new Vector<Object>();
			data.add(f.getFlightID());
			data.add(changeFormat(f.getDepartureDate()));
			data.add(changeFormat(f.getLandingDate()));
			tableModel.addRow(data);
		}
		// -------------------
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		// First row is been focused and selected by default
		table.requestFocus();
		table.setDefaultEditor(Object.class, null);
		// Notifies all listeners that all cell values in the table's rows may have
		// changed.
		tableModel.fireTableDataChanged();
	}
	private String changeFormat(Date date) {
		String year, month, day;
		year = String.valueOf(date.getYear() + 1900);
		int m = date.getMonth() + 1;
		if (m < 10)
			month = "0" + String.valueOf(m);
		else
			month = String.valueOf(m);
		int d = date.getDate();
		if (d < 10)
			day = "0" + String.valueOf(d);
		else
			day = String.valueOf(d);
		String Hours, Minutes;
		if (date.getHours() < 10) {
			Hours = String.valueOf("0" + date.getHours());
		} else
			Hours = String.valueOf(date.getHours());
		if (date.getMinutes() < 10) {
			Minutes = String.valueOf("0" + date.getMinutes());
		} else
			Minutes = String.valueOf(date.getMinutes());
		return (year + "-" + month + "-" + day + " " + Hours + ":" + Minutes);
	}
}
