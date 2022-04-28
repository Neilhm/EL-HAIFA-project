package managerControl;

import java.io.File;
import java.io.FileWriter;
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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JOptionPane;

import java.net.URLDecoder;
import java.nio.file.Files;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import managerEntity.*;
import managerUtils.*;

public class ManagerLogic {
	private static ManagerLogic _instance;

	private ManagerLogic() {
	}

	public static ManagerLogic getInstance() {
		if (_instance == null)
			_instance = new ManagerLogic();
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
					long flightID = rs.getLong(i++);
					String date1 = rs.getString(i++).substring(0, 16);
					Date DepartureDate = (Date) parser.parse(date1);
					String date2 = rs.getString(i++).substring(0, 16);
					Date LandingDate = (Date) parser.parse(date2);
					results.add(new Flight(flightID, DepartureDate, LandingDate,
							ConvertStringToStatus(rs.getString(i++)), rs.getLong(i++), rs.getLong(i++), rs.getLong(i++),
							rs.getLong(i++), rs.getLong(i++), rs.getDate(i++)));
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
		return results;
	}

	public ArrayList<BigFlightsReport> getFlightsForReport(Date from, Date to) {
		ArrayList<BigFlightsReport> results = new ArrayList<BigFlightsReport>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHTREPORT)) {
				String year, month, day;
				year = String.valueOf(from.getYear() + 1900);
				int m = from.getMonth() + 1;
				if (m < 10)
					month = "0" + String.valueOf(m);
				else
					month = String.valueOf(m);
				int d = from.getDate();
				if (d < 10)
					day = "0" + String.valueOf(d);
				else
					day = String.valueOf(d);
				String dateFrom = year + "-" + month + "-" + day;
				year = String.valueOf(to.getYear() + 1900);
				m = to.getMonth() + 1;
				if (m < 10)
					month = "0" + String.valueOf(m);
				else
					month = String.valueOf(m);
				d = to.getDate();
				if (d < 10)
					day = "0" + String.valueOf(d);
				else
					day = String.valueOf(d);
				String dateTo = year + "-" + month + "-" + day;
				stmt.setString(1, dateFrom);
				stmt.setString(2, dateTo);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					int i = 1;
					results.add(new BigFlightsReport(rs.getLong(i++), rs.getString(i++), rs.getString(i++),
							rs.getString(i++), rs.getString(i++), dateFormat(rs.getString(i++)),
							dateFormat(rs.getString(i++)),
							ConvertStringToStatus(rs.getString(i++))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	public Date dateFormat(String stringDate) {
		stringDate = stringDate.substring(0, 16);
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = (Date) parser.parse(stringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	public ArrayList<AirportPastMonthReport> getAirportPastMonthReport() {
		ArrayList<AirportPastMonthReport> results = new ArrayList<AirportPastMonthReport>();
		int total = 0;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_AIRPORTREPORT)) {
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					int i = 1;
					String country = rs.getString(i++);
					int countofCountry = rs.getInt(i++);
					total = rs.getInt(i++);
					results.add(
							new AirportPastMonthReport(country, 100 * (Double.valueOf(countofCountry) / total), total));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	public managerUtils.Status ConvertStringToStatus(String str) {
		if(str != null) {
			for (managerUtils.Status st : managerUtils.Status.values()) {
				if (st.toString().equals(str))
					return st;
			}
		}
		return null;
	}

	public FlightAttendants getFlightAttendant(long id) {
		FlightAttendants ft = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHTATTENDANTS);) {

				// stmt.setLong(1, id);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					int i = 1;
					if (rs.getLong(1) == id) {
						ft = new FlightAttendants(rs.getLong(i++), rs.getString(i++), rs.getString(i++),
								rs.getDate(i++), rs.getDate(i++));
					}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ft;
	}

	public ArrayList<FlightAttendants> getCrewMembers(long flightID) {
		ArrayList<FlightAttendants> results = new ArrayList<FlightAttendants>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ATTENDANTSINFLIGHT);) {

				// stmt.setLong(1, flightID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					int i = 1;
					if (rs.getLong(1) == flightID) {
						results.add(getFlightAttendant(rs.getLong(2)));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	public ArrayList<FlightAttendants> getFlightAttendants() {
		ArrayList<FlightAttendants> results = new ArrayList<FlightAttendants>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHTATTENDANTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new FlightAttendants(rs.getLong(i++), rs.getString(i++), rs.getString(i++),
							rs.getDate(i++), rs.getDate(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<AttendantsInFlight> getAttendantsInFlights() {
		ArrayList<AttendantsInFlight> results = new ArrayList<AttendantsInFlight>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ATTENDANTSINFLIGHT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new AttendantsInFlight(rs.getLong(i++), rs.getLong(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	public Pilot getPilot(long pilotID) {
		Pilot pilot = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_PILOT);) {

				stmt.setLong(1, pilotID);
				ResultSet rs = stmt.executeQuery();
				int i = 1;
				pilot = new Pilot(rs.getLong(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++),
						rs.getDate(i++), rs.getLong(i++), rs.getDate(i++));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pilot;
	}

	public Flight getFlight(long flightID) {
		Flight flight = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_FLIGHT);) {
				stmt.setLong(1, flightID);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				int i = 1;
				flight = (new Flight(rs.getLong(i++), rs.getDate(i++), rs.getDate(i++),
						ConvertStringToStatus(rs.getString(i++)), rs.getLong(i++), rs.getLong(i++), rs.getLong(i++),
						rs.getLong(i++), rs.getLong(i++), rs.getDate(i++)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flight;
	}

	/**
	 * fetches all customers from DB file.
	 * 
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
					results.add(new Airplane(rs.getLong(i++), rs.getLong(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	public managerUtils.Size ConvertStringToSize(String str) {
		for (managerUtils.Size st : managerUtils.Size.values()) {
			if (st.toString().equals(str))
				return st;
		}
		return null;
	}

	/**
	 * fetches all shippers from DB file.
	 * 
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
					results.add(new Airport(rs.getLong(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * fetches all products from DB file.
	 * 
	 * @return ArrayList of Products.
	 */

	/**
	 * fetches all orders from DB file.
	 * 
	 * @return ArrayList of Orders.
	 */
	public ArrayList<Pilot> getPilots() {
		ArrayList<Pilot> results = new ArrayList<Pilot>();

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_PILOT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Pilot(rs.getLong(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++),
							rs.getDate(i++), rs.getLong(i++), rs.getDate(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * fetches all orderDetails from DB file.
	 * 
	 * @return ArrayList of OrderDetails.
	 */
//	public ArrayList<AttendantsInFlight> getFlightDetails(long flightId) {
//		ArrayList<AttendantsInFlight> temp = new ArrayList<AttendantsInFlight>();
//		ArrayList<AttendantsInFlight> results = new ArrayList<AttendantsInFlight>();
//		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
//					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ATTENDANTSINFLIGHT);) {
//				ResultSet rs = stmt.executeQuery();
//				while (rs.next()) {
//					int i = 1;
//					temp.add(new AttendantsInFlight(rs.getLong(i++), rs.getLong(i++)));
//
//				}
//				for (AttendantsInFlight t : temp) {
//					if (t.getFlightId() == flightId)
//						results.add(t);
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		return results;
//	}

	/*----------------------------------------- ADD / REMOVE / UPDATE ORDER METHODS --------------------------------------------*/

	/**
	 * Adding a new order with the parameters received from the form. return true if
	 * the insertion was successful, else - return false
	 * 
	 * @return
	 */
	public boolean addFlight(long tailNum, long idDeparture, long idLanding, Date DepartureDate, Date LandingDate,
			long firstPilot, long SecondPilot, String timepicker1, String timepicker2) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			String year, month, day;
			year = String.valueOf(DepartureDate.getYear() + 1900);
			int m = DepartureDate.getMonth() + 1;
			if (m < 10)
				month = "0" + String.valueOf(m);
			else
				month = String.valueOf(m);
			int d = DepartureDate.getDate();
			if (d < 10)
				day = "0" + String.valueOf(d);
			else
				day = String.valueOf(d);
			String date = year + "-" + month + "-" + day + " " + timepicker1;
			DepartureDate = (Date) parser.parse(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String year, month, day;
			year = String.valueOf(LandingDate.getYear() + 1900);
			int m = LandingDate.getMonth() + 1;
			if (m < 10)
				month = "0" + String.valueOf(m);
			else
				month = String.valueOf(m);
			int d = LandingDate.getDate();
			if (d < 10)
				day = "0" + String.valueOf(d);
			else
				day = String.valueOf(d);
			String date = year + "-" + month + "-" + day + " " + timepicker2;
			LandingDate = (Date) parser.parse(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_FLIGHT)) {
				int i = 1;
				if (DepartureDate != null)
					stmt.setDate(i++, new java.sql.Date(DepartureDate.getTime()));
				else
					stmt.setNull(i++, java.sql.Types.DATE);
				if (LandingDate != null)
					stmt.setDate(i++, new java.sql.Date(LandingDate.getTime()));
				else
					stmt.setNull(i++, java.sql.Types.DATE);
				stmt.setString(i++, "Ontime");
				stmt.setLong(i++, tailNum); // can't be null
				stmt.setLong(i++, idDeparture); // can't be null
				stmt.setLong(i++, idLanding); // can't be null
				stmt.setLong(i++, firstPilot);
				stmt.setLong(i++, SecondPilot);
				LocalDate localDate = LocalDate.now();
				Date now = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
				stmt.setDate(i++, new java.sql.Date(now.getTime()));
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
	public boolean changeStatusOfFlight(long flightId, String status) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_EDIT_STATUSFLIGHT)) {
				int i = 1;
				stmt.setString(i++, status);
				LocalDate localDate = LocalDate.now();
				Date now = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
				stmt.setDate(i++, new java.sql.Date(now.getTime()));
				stmt.setLong(i++, flightId);
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

	public boolean editFlight(long flightId, long tailNum, long idDeparture, long idLanding, Date DepartureDate,
			Date LandingDate, long firstPilot, long secondPilot, String timepicker1, String timepicker2,
			String status) {
		try {
			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				String year, month, day;
				year = String.valueOf(DepartureDate.getYear() + 1900);
				int m = DepartureDate.getMonth() + 1;
				if (m < 10)
					month = "0" + String.valueOf(m);
				else
					month = String.valueOf(m);
				int d = DepartureDate.getDate();
				if (d < 10)
					day = "0" + String.valueOf(d);
				else
					day = String.valueOf(d);
				String date = year + "-" + month + "-" + day + " " + timepicker1;
				DepartureDate = (Date) parser.parse(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				String year, month, day;
				year = String.valueOf(LandingDate.getYear() + 1900);
				int m = LandingDate.getMonth() + 1;
				if (m < 10)
					month = "0" + String.valueOf(m);
				else
					month = String.valueOf(m);
				int d = LandingDate.getDate();
				if (d < 10)
					day = "0" + String.valueOf(d);
				else
					day = String.valueOf(d);
				String date = year + "-" + month + "-" + day + " " + timepicker2;
				LandingDate = (Date) parser.parse(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_EDIT_FLIGHT)) {
				int i = 1;
				if (DepartureDate != null)
					stmt.setDate(i++, new java.sql.Date(DepartureDate.getTime()));
				else
					stmt.setNull(i++, java.sql.Types.DATE);
				if (LandingDate != null)
					stmt.setDate(i++, new java.sql.Date(LandingDate.getTime()));
				else
					stmt.setNull(i++, java.sql.Types.DATE);
				stmt.setString(i++, status);
				stmt.setLong(i++, tailNum); // can't be null
				stmt.setLong(i++, idDeparture); // can't be null
				stmt.setLong(i++, idLanding); // can't be null
				stmt.setLong(i++, firstPilot);
				stmt.setLong(i++, secondPilot);
				LocalDate localDate = LocalDate.now();
				Date now = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
				stmt.setDate(i++, new java.sql.Date(now.getTime()));
				stmt.setLong(i++, flightId);
				stmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, this, "nooooo", 0);
		return false;
	}

	public boolean addAttendantsInFlight(long UniqueSerialNumber, long AttendantsID) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_ATTENDANTSINFLIGHT)) {
				int i = 1;
				stmt.setLong(i++, UniqueSerialNumber); // can't be null
				stmt.setLong(i++, AttendantsID); // can't be null

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

	public boolean removeAttendantsInFlight(long flightID) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_DEL_ATTENDANTSINFLIGHT)) {
				int i = 1;
				stmt.setLong(i++, flightID); // can't be null

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

	public String ConvertDate(Date date) {
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
		String StringDate = year + "-" + month + "-" + day + " " + Hours + ":" + Minutes;
		return StringDate;
	}

	public ArrayList<Flight> getUpdatedFlights() {
		ArrayList<Flight> results = new ArrayList<Flight>();
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_UPDATEDFLIGHTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					long flightID = rs.getLong(i++);
					String date1 = rs.getString(i++).substring(0, 16);
					Date DepartureDate = (Date) parser.parse(date1);
					String date2 = rs.getString(i++).substring(0, 16);
					Date LandingDate = (Date) parser.parse(date2);
					results.add(new Flight(flightID, DepartureDate, LandingDate,
							ConvertStringToStatus(rs.getString(i++)), rs.getLong(++i), rs.getLong(i++), rs.getLong(i++),
							rs.getLong(i++), rs.getLong(i++), rs.getDate(i++)));
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
		return results;
	}

	public void SendJson () throws IOException{
		ArrayList<Flight> flightsArray = getUpdatedFlights();
		ArrayList<Airplane> planeArray = getAirplanes();
		ArrayList<Flight> result = new ArrayList<>();
		for(Flight f : flightsArray) {
			ArrayList<FlightAttendants> CrewMembers = getCrewMembers(f.getFlightID());
			if(CrewMembers.size() == planeArray.get(planeArray.indexOf(new Airplane(f.getTailNum()))).getSize()) {
				result.add(f);
			}
		}
		FileWriter writer = new FileWriter("json.txt");
			JsonObject base = new JsonObject();
			JsonArray added = new JsonArray();
			JsonArray cancelled = new JsonArray();
			base.put("added-updated", added);
			base.put("cancelled", cancelled);
			ArrayList<Seat> seatsArray = getSeats();
			for (Flight f : result) {
				JsonObject flightJason = new JsonObject();
				flightJason.put("flightID", String.valueOf(f.getFlightID()));
				flightJason.put("departureDate", ConvertDate(f.getDepartureDate()));
				flightJason.put("landingDate", ConvertDate(f.getLandingDate()));
				if(f.getStatus() != null)
					flightJason.put("status", f.getStatus().toString());
				else
					flightJason.put("status", "null");
				flightJason.put("departureAirport", String.valueOf(f.getDepartureAirport()));
				flightJason.put("landingAirport", String.valueOf(f.getLandingAirport()));
				JsonArray seats = new JsonArray();
				for (Seat s : seatsArray) {
					if (s.getTailNumber() == f.getTailNum()) {
						JsonObject seatJason = new JsonObject();
						seatJason.put("line", String.valueOf(s.getLine()));
						seatJason.put("seatNumber", s.getSeat());
						seatJason.put("classType", s.getClassType().toString());
						seats.add(seatJason);
					}
				}
				flightJason.put("seats", seats);
				if(f.getStatus() != null) {
					if (f.getStatus().toString().equals("Cancelled")) {
						cancelled.add(flightJason);
					} else {
						added.add(flightJason);
					}
				} else {
					added.add(flightJason);
				}
			}
			writer.write(Jsoner.prettyPrint(base.toJson()));
			writer.flush();
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
					results.add(new Seat(rs.getLong(i++), rs.getString(i++), rs.getLong(i++),
							ConvertStringToClassType(rs.getString(i++))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	public managerUtils.ClassType ConvertStringToClassType(String str) {
		for (managerUtils.ClassType st : managerUtils.ClassType.values()) {
			if (st.toString().equals(str))
				return st;
		}
		return null;
	}
}
