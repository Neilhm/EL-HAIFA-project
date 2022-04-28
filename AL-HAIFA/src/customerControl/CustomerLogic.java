package customerControl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.StandardCopyOption;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.net.URLDecoder;
import java.nio.file.Files;

import customerEntity.*;
import customerUtils.*;


public class CustomerLogic {
	private static CustomerLogic _instance;

	private CustomerLogic() {
	}

	public static CustomerLogic getInstance() {
		if (_instance == null)
			_instance = new CustomerLogic();
		return _instance;
	}
	
	public ArrayList<Flight> getFlights() {
		ArrayList<Flight> results = new ArrayList<Flight>();
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					String flightID = rs.getString(i++);
					String departure = rs.getString(i++).substring(0, 16);
					Date DepartureDate = (Date) parser.parse(departure);
					String landing = rs.getString(i++).substring(0, 16);
					Date LandingDate = (Date) parser.parse(landing);
					results.add(new Flight(flightID, DepartureDate, LandingDate, rs.getLong(i++),
							rs.getLong(i++),ConvertStringToStatus(rs.getString(i++))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(results);
		return results;
	}
	
	public ArrayList<Order> getOrders() {
		ArrayList<Order> results = new ArrayList<>();

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ORDER);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Order(rs.getLong(i++), rs.getDate(i++),
							ConvertStringToPayment(rs.getString(i++))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<SuppliesFlight> getFlightSupplies() {
		ArrayList<SuppliesFlight> results = new ArrayList<>();

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_SuppliesFlight);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new SuppliesFlight(rs.getString(i++), rs.getLong(i++),rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<SupplisEntertainment> getSupplisEntertainment() {
		ArrayList<SupplisEntertainment> results = new ArrayList<>();

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_SupplisEntertainment);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new SupplisEntertainment(rs.getLong(i++),rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Supplier> getSupplier() {
		ArrayList<Supplier> results = new ArrayList<>();

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_Supplier);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Supplier(rs.getLong(i++),rs.getString(i++),rs.getString(i++),
							rs.getString(i++),rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<FlightDetails> getFlightsDetails() {
		ArrayList<FlightDetails> results = new ArrayList<>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHTSDETAILS)){
					ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					int i = 1;
					results.add(new FlightDetails(rs.getString(i++), rs.getString(i++), rs.getString(i++),
							rs.getString(i++), rs.getString(i++), rs.getDate(i++), rs.getDate(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Customer> getCustomers() {
		ArrayList<Customer> results = new ArrayList<Customer>();

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_CUSTOMER);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Customer(rs.getLong(i++), rs.getString(i++),
							rs.getString(i++), rs.getString(i++), rs.getDate(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public customerUtils.Status ConvertStringToStatus(String str){
		for(customerUtils.Status st : customerUtils.Status.values()) {
			if(st.toString().equals(str))
				return st;
		}
		return null;
	}
	
	public customerUtils.PaymentMethod ConvertStringToPayment(String str){
		for(customerUtils.PaymentMethod st : customerUtils.PaymentMethod.values()) {
			if(st.toString().equals(str))
				return st;
		}
		return null;
	}
	
	public HashMap<String,Integer> getCapacity(String flighID) {
		Flight flight = CustomerLogic.getInstance().getFlight(flighID);
		ArrayList<FlightTicket> FlightTickets = CustomerLogic.getInstance().getFlightTickets();
		HashMap<String,Integer> hashSeats = new HashMap<>();
		hashSeats.put("Economy", 0);
		hashSeats.put("First", 0);
		hashSeats.put("Business", 0);
		ArrayList<Seat> seats = CustomerLogic.getInstance().getSeats();
		 for(Seat s : seats) {
			 if(s.getFlightID().equals(flight.getFlightID()))
				 hashSeats.put(s.getClassType(), hashSeats.get(s.getClassType())+1);
		 }
		 for(FlightTicket ft : FlightTickets) {
			 if(ft.getFlightID() != null) {
				 if(ft.getFlightID().equals(flight.getFlightID()))
					 hashSeats.put(ft.getClassType(), hashSeats.get(ft.getClassType())-1);
			 }
		 }
		 return hashSeats;
		 //int capacity = (int) (hashSeats.get(f.getFlightID()).size() - hashFlightTicket.get(f.getFlightID()).size());
	}
	
	public Flight getFlight (String flightID) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Flight flight = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_FLIGHT);) {
				stmt.setString(1, flightID);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				int i = 2;
				String departure = rs.getString(i++).substring(0, 16);
				Date DepartureDate = (Date) parser.parse(departure);
				String landing = rs.getString(i++).substring(0, 16);
				Date LandingDate = (Date) parser.parse(landing);
				flight = new Flight(flightID, DepartureDate, LandingDate, rs.getLong(i++),
						rs.getLong(i++),ConvertStringToStatus(rs.getString(i++)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flight;
}
	
	public ArrayList<FlightTicket> getFlightTickets() {
		ArrayList<FlightTicket> results = new ArrayList<FlightTicket>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHTTICKET);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new FlightTicket(rs.getLong(i++), rs.getLong(i++),
							rs.getLong(i++), rs.getString(i++), rs.getString(i++),
							rs.getString(i++),rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Seat> setCancellSeatsOfFlight(String flighID) {
		ArrayList<Seat> results = new ArrayList<Seat>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_CANCELL_SEATS)){
					int i = 1;
					stmt.setString(i++, "Cancelled");
					stmt.setString(i++, flighID);
					stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	public ArrayList<Seat> getSeatsOfFlight(String flightID) {
		ArrayList<Seat> results = new ArrayList<Seat>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_SEATSOFFLIGHT)){
					stmt.setString(1, flightID);
					ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					int i = 1;
					results.add(new Seat(rs.getString(i++), rs.getString(i++),
							rs.getString(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Seat> getAvailableSeatsOfFlight(String flightID) {
		ArrayList<Seat> availableSeats = new ArrayList<>();
		ArrayList<Seat> seatsOfFlight = getSeatsOfFlight(flightID);
		ArrayList<FlightTicket> flightTickets = getFlightTickets();
		for(Seat sInFlight : seatsOfFlight) {
			for(FlightTicket ft : flightTickets) {
				if(!ft.getFlightID().equals(sInFlight.getFlightID())
					|| !ft.getLine().equals(sInFlight.getLine())
					|| !ft.getSeatNumber().equals(sInFlight.getColumn())) {
					if(!sInFlight.getStatus().equals("Cancelled"))
						availableSeats.add(sInFlight);
				}
			}
		}
		return availableSeats;
	}
	
	public ArrayList<Seat> getSeats() {
		ArrayList<Seat> results = new ArrayList<Seat>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_SEAT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Seat(rs.getString(i++), rs.getString(i++),
							rs.getString(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public Seat getSeat(String flighID, String line, String seatNumber) {
		Seat result = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_SEAT)){
				stmt.setString(1, flighID);
				stmt.setString(2, line);
				stmt.setString(3, seatNumber);
					ResultSet rs = stmt.executeQuery();
				rs.next();
				int i = 1;
				result = new Seat(rs.getString(i++), rs.getString(i++),
							rs.getString(i++), rs.getString(i++), rs.getString(i++));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * fetches all customers from DB file.
     * @return ArrayList of Customers.
	 */
	public ArrayList<Airplane> getAirplanes() {
		ArrayList<Airplane> results = new ArrayList<Airplane>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_AIRPLANE);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Airplane(rs.getLong(i++), rs.getLong(i++),
							rs.getLong(i++), rs.getLong(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public customerUtils.Size ConvertStringToSize(String str){
		for(customerUtils.Size st : customerUtils.Size.values()) {
			if(st.toString().equals(str))
				return st;
		}
		return null;
	}
	
	/**
	 * fetches all shippers from DB file.
     * @return ArrayList of Shippers.
	 */
	public ArrayList<Airport> getAirports() {
		ArrayList<Airport> results = new ArrayList<Airport>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_AIRPORT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Airport(rs.getLong(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	/*----------------------------------------- ADD / REMOVE / UPDATE flight METHODS --------------------------------------------*/
	
	/**
	 * Adding a new flight with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 */

	public boolean addSuppliesInFlight(String flightID, long supplierID, String entertainmentName) {
		System.out.println(flightID);
		System.out.println(supplierID);
		System.out.println(entertainmentName);
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_SupplierForFlight)) {
				int i = 1;
				stmt.setString(i++, flightID);
				stmt.setLong(i++, supplierID);
				stmt.setString(i++, entertainmentName);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean removeSuppliesInFlight(String flightID) {
		System.out.println(flightID);
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_DEL_SupplierForFlight)) {
				int i = 1;
				stmt.setString(i++, flightID); // can't be null
				stmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addFlight(Flight f) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_FLIGHT)) {
				int i = 1;
				stmt.setString(i++, f.getFlightID());
				if (f.getDepartureDate() != null)
					stmt.setDate(i++, new java.sql.Date(f.getDepartureDate().getTime()));
				else
					stmt.setNull(i++, java.sql.Types.DATE);
				if (f.getLandingDate() != null)
					stmt.setDate(i++, new java.sql.Date(f.getLandingDate().getTime()));
				else
					stmt.setNull(i++, java.sql.Types.DATE);
				stmt.setLong(i++, f.getDepartureAirport()); 
				stmt.setLong(i++, f.getLandinAirport());
				stmt.setString(i++, f.getStatus().toString());
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
}
	
	public boolean addCustomer(Customer c) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_CUSTOMER)) {
				int i = 1;
				stmt.setLong(i++, c.getPassport());
				stmt.setString(i++, c.getFirstName());
				stmt.setString(i++, c.getLastName());
				stmt.setString(i++, c.getCitizenship());
				if (c.getDateOfBirth()!= null)
					stmt.setDate(i++, new java.sql.Date(c.getDateOfBirth().getTime()));
				else
					stmt.setNull(i++, java.sql.Types.DATE);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
}
	public boolean addFlightTicket(FlightTicket ft) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_FLIGHTTICKET)) {
				int i = 1;
				stmt.setLong(i++, ft.getOrderID());
				stmt.setLong(i++, ft.getTicketID());
				stmt.setLong(i++, ft.getCustomerPassport());
				stmt.setString(i++, ft.getClassType());
				stmt.setString(i++, ft.getMealType());
				stmt.setString(i++, ft.getFlightID());
				stmt.setString(i++, ft.getLine());
				stmt.setString(i++, ft.getSeatNumber());
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addOrder(long orderID) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_ORDER)) {
				int i = 1;
				stmt.setLong(i++, orderID);
				stmt.setNull(i++, java.sql.Types.DATE);
				stmt.setNull(i++, java.sql.Types.VARCHAR);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteOrder(long orderID) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_DEL_ORDER)) {
				int i = 1;
				stmt.setLong(i++, orderID);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean editOrder(long orderID, Date booking, String paymentMethod) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_ORDER)) {
				int i = 1;
				if (booking != null)
					stmt.setDate(i++, new java.sql.Date(booking.getTime()));
				else
					stmt.setNull(i++, java.sql.Types.DATE);
				stmt.setString(i++, paymentMethod);
				stmt.setLong(i++, orderID);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
public boolean addSeat(Seat s) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_SEAT)) {
				int i = 1;
				stmt.setString(i++, s.getFlightID());
				stmt.setString(i++, s.getLine());
				stmt.setString(i++, s.getColumn());
				stmt.setString(i++, s.getClassType());
				if (s.getStatus() != null)
					stmt.setString(i++,s.getStatus());
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
}
public boolean cancelledFlightTicket(FlightTicket ft) {
	try {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt = conn.prepareCall(Consts.SQL_CANCEL_TICKET)) {
			int i = 1;
			stmt.setString(i++, "Cancelled");
			stmt.setLong(i++, ft.getOrderID());
			stmt.setLong(i++, ft.getTicketID());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	return false;
}

public boolean editSeat(Seat s) {
	try {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_SEAT)) {
			int i = 1;
			stmt.setString(i++, s.getClassType());
			stmt.setString(i++, "Updated");
			stmt.setString(i++, s.getFlightID());
			stmt.setString(i++, s.getLine());
			stmt.setString(i++, s.getColumn());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	return false;
}

public boolean cancelFlight(Flight f) {
	try {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				CallableStatement stmt = conn.prepareCall(Consts.SQL_CANCEL_SEAT)) {
			int i = 1;
			stmt.setString(i++, f.getStatus().toString());
			stmt.setString(i++, f.getFlightID());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	return false;
}
	
public boolean editFlight(Flight f) {
try {
	Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
			CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_FLIGHT)) {
		int i = 1;
		if (f.getDepartureDate() != null)
			stmt.setDate(i++, new java.sql.Date(f.getDepartureDate().getTime()));
		else
			stmt.setNull(i++, java.sql.Types.DATE);
		if (f.getLandingDate() != null)
			stmt.setDate(i++, new java.sql.Date(f.getLandingDate().getTime()));
		else
			stmt.setNull(i++, java.sql.Types.DATE);
		stmt.setLong(i++, f.getDepartureAirport()); 
		stmt.setLong(i++, f.getLandinAirport());
		stmt.setString(i++, f.getStatus().toString());
		stmt.setString(i++, f.getFlightID());
		stmt.executeUpdate();
		return true;
	} catch (SQLException e) {
		e.printStackTrace();
	}
} catch (ClassNotFoundException e) {
	e.printStackTrace();
}
return false;
}
}
