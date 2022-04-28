package customerBoundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import customerControl.CustomerLogic;
import customerEntity.Airport;
import customerEntity.Flight;
import customerEntity.FlightTicket;
import customerEntity.Seat;
import customerUtils.Status;
import managerBoundary.FrmFlights;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class CustomerFly extends CustomerRootLayout{
	private JPanel contentPane;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerFly frame = new CustomerFly();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public CustomerFly() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		updateMenuSelectionHome();
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(CustomerFly.class.getResource("/customerBoundary/images/logo.png")));
		
		JButton btnReport = new JButton("Flights Report");
		btnReport.setEnabled(true);
		
		JButton btnImport = new JButton("Import from Manager-Fly");
		btnImport.setEnabled(true);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLogo)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnImport, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnReport, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))
					.addGap(20))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnImport, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnReport, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(lblLogo))
					.addContainerGap(58, Short.MAX_VALUE))
		);
		
		JButton btnFlights = new JButton("Flights");
		btnFlights.setPreferredSize(new Dimension(100, 25));
		btnFlights.setEnabled(false);
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ImportFromJson(e);
				} catch (NumberFormatException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					msgbox("Import Did Not succeed");
				}
			}
		});
		btnReport.setForeground(Color.BLACK);
		btnReport.setBackground(SystemColor.activeCaption);
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PrintFlightDetails frame = new PrintFlightDetails();
				frame.setVisible(true);
				
			}
		});
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		JButton btnSuppliers = new JButton("Suppliers");;
		btnSuppliers.setPreferredSize(new Dimension(100, 25));
		
		btnSuppliers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrmFlightSuplies("employeeManager").setVisible(true);
				JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
				f1.dispose();
			}
		});
		
		JButton btnAddCustomer = new JButton("Register");
		btnAddCustomer.setPreferredSize(new Dimension(100, 25));
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrmSignUp().setVisible(true);
				JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
				f1.dispose();
			}
		});
		
		JButton btnOrders = new JButton("Orders");
		btnOrders.setPreferredSize(new Dimension(100, 25));
		btnOrders.setEnabled(true);
		
		btnOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrmOrders().setVisible(true);
				JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
				f1.dispose();
			}
		});
		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel.add(btnFlights);
		panel.add(btnSuppliers);
		panel.add(btnAddCustomer);
		panel.add(btnOrders);
		panel.add(btnAddCustomer);
		btnFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.setLayout(gl_contentPane);
	}
	
	private void ImportFromJson(ActionEvent e) throws NumberFormatException, ParseException {
		ArrayList<Flight> flightsCancelled = new ArrayList<>();
		ArrayList<Flight> flightsAddedUpdated = new ArrayList<>();
		ArrayList<Seat> seats = new ArrayList<>();
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try (FileReader reader = new FileReader("json.txt")) {
			JsonObject base = (JsonObject) Jsoner.deserialize(reader);
			Iterator<Object> it = ((JsonArray) base.get("cancelled")).iterator();
			while (it.hasNext()) {
			JsonObject jsonObject = (JsonObject) it.next();
			flightsCancelled.add(new Flight(String.valueOf(jsonObject.get("flightID")),
					(Date)parser.parse(String.valueOf(jsonObject.get("departureDate"))),
					(Date)parser.parse(String.valueOf(jsonObject.get("landingDate"))),
					Long.parseLong(String.valueOf(jsonObject.get("landingAirport"))),Long.parseLong(String.valueOf(jsonObject.get("departureAirport"))),
					ConvertStringToStatus(String.valueOf(jsonObject.get("status")))));
		}
			Iterator<Object> it3 = ((JsonArray) base.get("added-updated")).iterator();
			while (it3.hasNext()) {
				JsonObject jsonObject = (JsonObject) it3.next();
				flightsAddedUpdated.add(new Flight(String.valueOf(jsonObject.get("flightID")),
						(Date)parser.parse(String.valueOf(jsonObject.get("departureDate"))),
						(Date)parser.parse(String.valueOf(jsonObject.get("landingDate"))),
						Long.parseLong(String.valueOf(jsonObject.get("landingAirport"))),Long.parseLong(String.valueOf(jsonObject.get("departureAirport"))),
						ConvertStringToStatus(String.valueOf(jsonObject.get("status")))));
				Iterator<Object> jsonSeats = ((JsonArray) jsonObject.get("seats")).iterator();
				while (jsonSeats.hasNext()) {
					JsonObject seatObject = (JsonObject) jsonSeats.next();
					seats.add(new Seat(String.valueOf(jsonObject.get("flightID")),
							String.valueOf(seatObject.get("line")),
							String.valueOf(seatObject.get("seatNumber")),
							String.valueOf(seatObject.get("classType")),null));
				}
				
				
			}
			
		} catch (FileNotFoundException o) {
			// TODO Auto-generated catch block
			o.printStackTrace();
			msgbox("File Not Found");
			return;
		} catch (IOException o) {
			// TODO Auto-generated catch block
			o.printStackTrace();
		} catch (JsonException o) {
			// TODO Auto-generated catch block
			o.printStackTrace();
		}
		ArrayList<Seat> cancelledseats = new ArrayList<>();
		ArrayList<Flight> flights = CustomerLogic.getInstance().getFlights();
		for(Flight f : flightsAddedUpdated) {
			if(flights.contains(f)) {
				CustomerLogic.getInstance().editFlight(f);
			} else {
				CustomerLogic.getInstance().addFlight(f);
				for(Seat s : seats) {
					if(s.getFlightID().equals(f.getFlightID())) {
						CustomerLogic.getInstance().addSeat(s);
					}
				}
			}
		}
		ArrayList<Seat> allSeats = CustomerLogic.getInstance().getSeats();
		for(Seat s : seats) {
			if(allSeats.contains(s)) {
				CustomerLogic.getInstance().editSeat(s);
				if(s.getStatus() != null)
					if(s.getStatus().equals("Cancelled"))
						cancelledseats.add(s);
			} else
				CustomerLogic.getInstance().addSeat(s);
		}
		for(Flight f : flightsCancelled) {
			CustomerLogic.getInstance().cancelFlight(f);
			CustomerLogic.getInstance().editFlight(f);
			cancelledseats.addAll(CustomerLogic.getInstance().getSeatsOfFlight(f.getFlightID()));
		}
		PrintFlightDetails frame = new PrintFlightDetails();
		frame.setVisible(true);
		ArrayList<FlightTicket> flightTickets = CustomerLogic.getInstance().getFlightTickets();
		ArrayList<FlightTicket> cancelledFlightTicket = new ArrayList<>();
		for(Seat s : cancelledseats) {
			for(FlightTicket ft : flightTickets) {
				if(ft.getFlightID().equals(s.getFlightID())
					&& ft.getLine().equals(s.getLine())
					&& ft.getSeatNumber().equals(s.getColumn())) {
					cancelledFlightTicket.add(ft);
						
				}
			}
		}
		for(FlightTicket ft : cancelledFlightTicket) {
			CustomerLogic.getInstance().cancelledFlightTicket(ft);
		}
		AlternativeFlights frame2 = new AlternativeFlights(cancelledFlightTicket);
		frame2.setVisible(true);
	}
	public customerUtils.Status ConvertStringToStatus(String str){
		for(customerUtils.Status st : customerUtils.Status.values()) {
			if(st.toString().equals(str))
				return st;
		}
		return null;
		
	}
	
	private void msgbox(String s) {
		JOptionPane.showMessageDialog(this, s);
	}
}
