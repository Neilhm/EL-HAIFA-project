package managerEntity;

import java.util.Date;

import managerUtils.Status;

public class BigFlightsReport {
	private long flighID;
	private String departureCountry;
	private String departurecity;
	private String landingCountry;
	private String landingecity;
	private Date departureDate;
	private Date landingDate;
	private Status status;
	public BigFlightsReport(long flighID, String departureCountry, String departurecity, String landingCountry,
			String landingecity, Date departureDate, Date landingDate, Status status) {
		super();
		this.flighID = flighID;
		this.departureCountry = departureCountry;
		this.departurecity = departurecity;
		this.landingCountry = landingCountry;
		this.landingecity = landingecity;
		this.departureDate = departureDate;
		this.landingDate = landingDate;
		this.status = status;
	}
	public long getFlighID() {
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
	public Status getStatus() {
		return status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (flighID ^ (flighID >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof BigFlightsReport))
			return false;
		BigFlightsReport other = (BigFlightsReport) obj;
		if (flighID != other.flighID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format(
				"FlightReport [flighID=%s, departureCountry=%s, departurecity=%s, landingCountry=%s, landingecity=%s, departureDate=%s, landingDate=%s, status=%s]",
				flighID, departureCountry, departurecity, landingCountry, landingecity, departureDate, landingDate,
				status);
	}
}
