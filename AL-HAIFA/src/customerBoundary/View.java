package customerBoundary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import customerControl.CustomerLogic;
import customerEntity.Airport;
import customerEntity.Flight;
import customerEntity.FlightTicket;
import customerEntity.Seat;
import customerUtils.Status;

public class View extends JFrame{
	private JTable table;
	private JPanel contentPane;
	private static Vector vector;
	private static HashMap<String, String> data;
	private static DefaultTableModel tableModel;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View(vector);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public View(Vector v) {
		data = new HashMap<>();
		data.put("customerID", v.get(0).toString());
		data.put("firstName", v.get(1).toString());
		data.put("lastName", v.get(2).toString());
		data.put("flightID", v.get(3).toString());
		data.put("Line", v.get(4).toString());
		data.put("SeatNumber", v.get(5).toString());
		System.out.println(data.get("flightID"));
		initialize();
	}
	
	private void initialize() {
		setBackground(SystemColor.inactiveCaptionBorder);
		setBounds(100, 100, 1020, 610);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Flights Details");
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(432, 52, 434, 34);
		getContentPane().add(lblNewLabel);
		String header[] = new String[] {"Flight ID", "Departure Date", "Landing Date",
				"Departure Airport", "Lading Airport","Seat"};
		tableModel = new DefaultTableModel();
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
		table.setEnabled(false);
		initTableData();
	}
	private void initTableData() {
		ArrayList<Flight> flights = CustomerLogic.getInstance().getFlights();
		ArrayList<Airport> airports = CustomerLogic.getInstance().getAirports();
		Flight cancelledFlight = CustomerLogic.getInstance().getFlight(data.get("flightID"));
		Seat cancelledSeat = CustomerLogic.getInstance().getSeat(data.get("flightID"), data.get("Line"), data.get("SeatNumber"));
		Airport departure, landing;
		departure = airports.get(airports.indexOf(new Airport(cancelledFlight.getDepartureAirport())));
		landing = airports.get(airports.indexOf(new Airport(cancelledFlight.getLandinAirport())));
		LocalDate localDate = LocalDate.now();
		Date now = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		int noOfDays = 14; //i.e two weeks
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cancelledFlight.getDepartureDate());
		calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
		Date moreTwoWeeks = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, -noOfDays-noOfDays);
		Date lessTwoWeeks = calendar.getTime();
		for(Flight f : flights) {
			if(!f.getStatus().equals(Status.Cancelled) && !f.equals(cancelledFlight) && f.getDepartureDate().after(now)
					&& lessTwoWeeks.compareTo(f.getDepartureDate()) * f.getDepartureDate().compareTo(moreTwoWeeks) >= 0) {
				if(f.getDepartureAirport() == departure.getAirportID() &&
						f.getLandinAirport() == landing.getAirportID()) {
					ArrayList<Seat> seatsOfFlight = CustomerLogic.getInstance().getAvailableSeatsOfFlight(f.getFlightID());
					for(Seat s : seatsOfFlight) {
						if(s.getClassType().equals(cancelledSeat.getClassType())) {
						Vector<Object> row = new Vector<Object>();
						row.add(f.getFlightID());
						row.add(f.getDepartureDate());
						row.add(f.getLandingDate());
						row.add(departure);
						row.add(landing);
						row.add(String.format("line=%s, column=%s",s.getLine(),s.getColumn()));
						tableModel.addRow(row);
						}
					}
				}
			}
		}
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		// First row is been focused and selected by default
		table.requestFocus();

		// Notifies all listeners that all cell values in the table's rows may have
		// changed.
		tableModel.fireTableDataChanged();
		 
	}
}
