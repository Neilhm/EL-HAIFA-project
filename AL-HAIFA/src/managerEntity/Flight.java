package managerEntity;

import java.util.Date;

import managerUtils.Status;

public class Flight {
	// TODO Auto-generated constructor stub
	
	private final long flightID;
	private Date DepartureDate;
	private Date LandingDate;
	private Status status;
	private long tailNum;
	private long DepartureAirport;
	private long LandingAirport;
	private long firstPilot;
	private long seconedPilot;
	private Date updateDate;
	
	
	public Flight(long flightID, Date departureDate, Date landingDate, Status status,
			long tailNum, long DepartureAirport, long LandingAirport,
			long firstPilot, long seconedPilot, Date updateDate) {
		super();
		this.flightID = flightID;
		this.tailNum = tailNum;
		this.DepartureDate = departureDate;
		this.LandingDate = landingDate;
		this.DepartureAirport = DepartureAirport;
		this.LandingAirport = LandingAirport;
		this.firstPilot = firstPilot;
		this.seconedPilot = seconedPilot;
		this.status = status;
		this.updateDate = updateDate;
	}

	

	public Flight(long flightID) {
		super();
		this.flightID = flightID;
	}




	public long getFlightID() {
		return flightID;
	}



	public Date getDepartureDate() {
		return DepartureDate;
	}



	public Date getLandingDate() {
		return LandingDate;
	}



	public Status getStatus() {
		return status;
	}



	public long getTailNum() {
		return tailNum;
	}



	public long getDepartureAirport() {
		return DepartureAirport;
	}



	public long getLandingAirport() {
		return LandingAirport;
	}



	public long getFirstPilot() {
		return firstPilot;
	}



	public long getSeconedPilot() {
		return seconedPilot;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setDepartureDate(Date departureDate) {
		DepartureDate = departureDate;
	}



	public void setLandingDate(Date landingDate) {
		LandingDate = landingDate;
	}



	public void setStatus(Status status) {
		this.status = status;
	}



	public void setTailNum(long tailNum) {
		this.tailNum = tailNum;
	}



	public void setDepartureAirport(long departureAirport) {
		DepartureAirport = departureAirport;
	}



	public void setLandingAirport(long landingAirport) {
		LandingAirport = landingAirport;
	}



	public void setFirstPilot(long firstPilot) {
		this.firstPilot = firstPilot;
	}



	public void setSeconedPilot(long seconedPilot) {
		this.seconedPilot = seconedPilot;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + flightID);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Flight))
			return false;
		Flight other = (Flight) obj;
		if (flightID != other.flightID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"Flight [flightID=%s, DepartureDate=%s, LandingDate=%s, status=%s, tailNum=%s, DepartureAirport=%s, LandingAirport=%s, firstPilot=%s, seconedPilot=%s]",
				flightID, DepartureDate, LandingDate, status, tailNum, DepartureAirport, LandingAirport, firstPilot,
				seconedPilot);
	}
}
