package customerEntity;

import java.util.Date;

public class FlightDetails {
	private String flighID;
	private String departureCountry;
	private String departurecity;
	private String landingCountry;
	private String landingecity;
	private Date departureDate;
	private Date landingDate;
	public FlightDetails(String flighID, String departureCountry, String departurecity, String landingCountry,
			String landingecity, Date departureDate, Date landingDate) {
		super();
		this.flighID = flighID;
		this.departureCountry = departureCountry;
		this.departurecity = departurecity;
		this.landingCountry = landingCountry;
		this.landingecity = landingecity;
		this.departureDate = departureDate;
		this.landingDate = landingDate;
	}
	public String getFlighID() {
		return flighID;
	}
	public String getDepartureCountry() {
		return departureCountry;
	}
	public String getDeparturecity() {
		return departurecity;
	}
	public String getLandingCountry() {
		return landingCountry;
	}
	public String getLandingecity() {
		return landingecity;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public Date getLandingDate() {
		return landingDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flighID == null) ? 0 : flighID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FlightDetails))
			return false;
		FlightDetails other = (FlightDetails) obj;
		if (flighID == null) {
			if (other.flighID != null)
				return false;
		} else if (!flighID.equals(other.flighID))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format(
				"FlightReport [flighID=%s, departureCountry=%s, departurecity=%s, landingCountry=%s, landingecity=%s, departureDate=%s, landingDate=%s]",
				flighID, departureCountry, departurecity, landingCountry, landingecity, departureDate, landingDate);
	}
}
