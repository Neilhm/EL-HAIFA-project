package customerBoundary;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import customerBoundary.*;
import customerControl.*;
import customerEntity.Airplane;
import customerEntity.Airport;
import customerEntity.Flight;
import customerEntity.FlightTicket;
import customerEntity.Seat;
import customerUtils.Status;
import managerBoundary.Item;

public class PrintFlightDetails extends JFrame {
	// extends JFrame

	private JTable table;
	private JPanel contentPane;
	private static DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrintFlightDetails frame = new PrintFlightDetails();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PrintFlightDetails() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setBackground(SystemColor.inactiveCaptionBorder);
		setBounds(100, 100, 1020, 610);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Flights details");
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(432, 52, 434, 34);
		getContentPane().add(lblNewLabel);

		tableModel = new DefaultTableModel();
		String header[] = new String[] { "Flight ID", "Departure Date", "Landing Date", "Departure Airport",
				"Lading Airport", "Status", "Remaining Capacity" };
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

		table = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				JComponent jc = (JComponent) comp;// for Custom JComponent
				int modelRow = convertRowIndexToModel(row);
				try {
					Status type = (Status) (getModel().getValueAt(modelRow, header.length - 2));
					comp.setForeground(Color.black);
					if (type.equals(Status.Cancelled)) {
						comp.setBackground(Color.red);
					} else if (type.equals(Status.Delayed)) {
						comp.setBackground(Color.orange);
					} else if (type.equals(Status.Ontime)) {
						comp.setBackground(Color.green);
					}
				} catch (Exception e) {
				}
				return comp;
			}
		};
		scrollPane.setViewportView(table);
		table.setModel(tableModel);
		table.setRowHeight(20);
		initTableData();
		table.setEnabled(false);

	}

	// This method set data to be presented on table according to recommendation id
	private void initTableData() {
		ArrayList<Flight> flights = CustomerLogic.getInstance().getFlights();
		ArrayList<Airport> airports = CustomerLogic.getInstance().getAirports();
		ArrayList<Seat> seats = CustomerLogic.getInstance().getSeats();
		HashMap<String, ArrayList<Seat>> hashSeats = new HashMap<>();
		HashMap<Long, Airport> hashPort = new HashMap<>();
		for (Airport ap : airports) {
			hashPort.put(ap.getAirportID(), ap);
		}

		ArrayList<FlightTicket> FlightTickets = CustomerLogic.getInstance().getFlightTickets();
		HashMap<String, ArrayList<FlightTicket>> hashFlightTicket = new HashMap<>();
		for (Flight f : flights) {
			ArrayList<FlightTicket> ft = new ArrayList<FlightTicket>();
			hashFlightTicket.put(f.getFlightID(), ft);
			ArrayList<Seat> s = new ArrayList<Seat>();
			hashSeats.put(f.getFlightID(), s);
		}
		System.out.println(FlightTickets);
		for (FlightTicket ft : FlightTickets) {
			if (ft.getFlightID() != null)
				hashFlightTicket.get(ft.getFlightID()).add(ft);
		}
		for (Seat s : seats) {
			hashSeats.get(s.getFlightID()).add(s);
		}
		// Following code clear table (used while browsing between orders)

		tableModel = (DefaultTableModel) (table.getModel());
		tableModel.setRowCount(0);

		// Following code gets all orders details for selected order id and updates
		// table rows
		for (Flight f : flights) {
			Vector<Object> data = new Vector<Object>();
			data.add(f.getFlightID());
			// data.add(f.getTailNum());
			data.add(f.getDepartureDate());
			data.add(f.getLandingDate());
			data.add(hashPort.get(f.getDepartureAirport()));
			data.add(hashPort.get(f.getLandinAirport()));
			data.add(f.getStatus());
			int capacity = (int) (hashSeats.get(f.getFlightID()).size() - hashFlightTicket.get(f.getFlightID()).size());
			data.add(capacity);
			tableModel.addRow(data);
		}
		// -------------------
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		// First row is been focused and selected by default
		table.requestFocus();

		// Notifies all listeners that all cell values in the table's rows may have
		// changed.
		tableModel.fireTableDataChanged();
	}
}
