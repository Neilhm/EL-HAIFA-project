package customerEntity;
import java.util.Date;

import customerUtils.Status;

public class Flight {
	// TODO Auto-generated constructor stub
	
	private final String flightID;
	private Date departureDate;
	private Date landingDate;
	private long departureAirport;
	private long landinAirport;
	private Status status;

	
	
	public Flight(String flightID, Date departureDate, Date landingDate, long departureAirport,
			long landinAirport, Status status) {
		super();
		this.flightID = flightID;
		this.departureDate = departureDate;
		this.landingDate = landingDate;
		this.status = status;
		this.departureAirport = departureAirport;
		this.landinAirport = landinAirport;
	}
	

	public Flight(String flightID) {
		super();
		this.flightID = flightID;
	}



	public String getFlightID() {
		return flightID;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public Date getLandingDate() {
		return landingDate;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public long getDepartureAirport() {
		return departureAirport;
	}



	public long getLandinAirport() {
		return landinAirport;
	}



	public void setDepartureAirport(long departureAirport) {
		this.departureAirport = departureAirport;
	}



	public void setLandinAirport(long landinAirport) {
		this.landinAirport = landinAirport;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flightID == null) ? 0 : flightID.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Flight))
			return false;
		Flight other = (Flight) obj;
		if (flightID == null) {
			if (other.flightID != null)
				return false;
		} else if (!flightID.equals(other.flightID))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return String.format(
				"Flight [flightID=%s, departureDate=%s, landingDate=%s, departureAirport=%s, landinAirport=%s, status=%s]",
				flightID, departureDate, landingDate, departureAirport, landinAirport, status);
	}
}
