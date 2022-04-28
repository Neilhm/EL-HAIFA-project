package customerBoundary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TableView;

import customerControl.CustomerLogic;
import customerEntity.Customer;
import customerEntity.Flight;
import customerEntity.FlightTicket;

public class AlternativeFlights extends JFrame {
	private JTable table;
	private JPanel contentPane;
	private static DefaultTableModel tableModel;
	private static ArrayList<FlightTicket> flightTickets;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlternativeFlights frame = new AlternativeFlights(flightTickets);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AlternativeFlights(ArrayList<FlightTicket> ft) {
		if(ft != null && !ft.isEmpty()) 
			flightTickets = new ArrayList<>(ft);
		initialize();
	}

	private void initialize() {
		setBackground(SystemColor.inactiveCaptionBorder);
		setBounds(100, 100, 1020, 610);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Customers Details");
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(432, 52, 434, 34);
		getContentPane().add(lblNewLabel);
		String header[] = new String[] { "Customer ID", "First Name", "Last Name", "FlightID",
				"Line", "SeatNumber","Day","Evening","Night","AlternativeFlights"};
		tableModel = new DefaultTableModel(){

			   @Override
			   public boolean isCellEditable(int row, int column) {
				   if(column == (header.length-1))
					   return true; // or a condition at your choice with row and column
				   else
					   return false;
			   }
			};
		tableModel.setColumnIdentifiers(header);
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		// panel.setBounds(10, 35, 400, 215);
		panel.setBounds(10, 97, 986, 465);
		getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(480, 300));
		scrollPane.setBounds(10, 11, 966, 483);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(tableModel);
		table.setRowHeight(20);

		Action view = new AbstractAction(){
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf(e.getActionCommand());
		        View frame = new View((Vector)((DefaultTableModel)table.getModel()).getDataVector().get(modelRow));
		        frame.setVisible(true);
		    }
		};
		ButtonColumn buttonColumn = new ButtonColumn(table, view, 9);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		if(flightTickets != null && !flightTickets.isEmpty()) 
			initTableData();
	}
	private void initTableData() {
		int day = 0;
		int evening = 0;
		int night = 0;
		ArrayList<Customer> customers = CustomerLogic.getInstance().getCustomers();
		ArrayList<FlightTicket> flightTickets = CustomerLogic.getInstance().getFlightTickets();
		tableModel = (DefaultTableModel) (table.getModel());
		tableModel.setRowCount(0);
		for(FlightTicket ft : flightTickets) {
			Vector<Object> data = new Vector<Object>();
			Customer c = customers.get(customers.indexOf(new Customer(ft.getCustomerPassport())));
			data.add(c.getPassport());
			data.add(c.getFirstName());
			data.add(c.getLastName());
			data.add(ft.getFlightID());
			data.add(ft.getLine());
			data.add(ft.getSeatNumber());
			data.add(getDay(c));
			data.add(getEvening(c));
			data.add(getNight(c));
			data.add("View");
			tableModel.addRow(data);
		}
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		// First row is been focused and selected by default
		table.requestFocus();

		// Notifies all listeners that all cell values in the table's rows may have
		// changed.
		tableModel.fireTableDataChanged();
	}
	
	private String getDay(Customer c) {
		int day = 0;
		int from = 500;
	    int to = 1100;
		ArrayList<Flight> flights = CustomerLogic.getInstance().getFlights();
		for(FlightTicket ft: flightTickets) {
				if(ft.getCustomerPassport() == c.getPassport()) {
					Flight f = flights.get(flights.indexOf(new Flight(ft.getFlightID())));
					Date departure = f.getDepartureDate();
				    Calendar cal = Calendar.getInstance();
				    cal.setTime(departure);
				    int t = cal.get(Calendar.HOUR_OF_DAY) * 100 + cal.get(Calendar.MINUTE);
				    if(to > from && t >= from && t <= to || to < from && (t >= from || t <= to)) {
				    	day++;
				    }
				}
		}
		return String.valueOf(day);
	}
	private String getEvening(Customer c) {
		int evening = 0;
		int from = 1100;
	    int to = 1700;
		ArrayList<Flight> flights = CustomerLogic.getInstance().getFlights();
		for(FlightTicket ft: flightTickets) {
				if(ft.getCustomerPassport() == c.getPassport()) {
					Flight f = flights.get(flights.indexOf(new Flight(ft.getFlightID())));
					Date departure = f.getDepartureDate();
				    Calendar cal = Calendar.getInstance();
				    cal.setTime(departure);
				    int t = cal.get(Calendar.HOUR_OF_DAY) * 100 + cal.get(Calendar.MINUTE);
				    if(to > from && t >= from && t <= to || to < from && (t >= from || t <= to)) {
				    	evening++;
				    }
				}
		}
		return String.valueOf(evening);
	}
	private String getNight(Customer c) {
		int night = 0;
		int from = 1700;
	    int to = 2300;
		ArrayList<Flight> flights = CustomerLogic.getInstance().getFlights();
		for(FlightTicket ft: flightTickets) {
				if(ft.getCustomerPassport() == c.getPassport()) {
					Flight f = flights.get(flights.indexOf(new Flight(ft.getFlightID())));
					Date departure = f.getDepartureDate();
				    Calendar cal = Calendar.getInstance();
				    cal.setTime(departure);
				    int t = cal.get(Calendar.HOUR_OF_DAY) * 100 + cal.get(Calendar.MINUTE);
				    if(to > from && t >= from && t <= to || to < from && (t >= from || t <= to)) {
				    	night++;
				    }
				}
		}
		return String.valueOf(night);
	}
}
