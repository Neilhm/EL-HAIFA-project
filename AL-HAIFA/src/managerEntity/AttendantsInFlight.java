package managerEntity;

public class AttendantsInFlight {
	private long FlightId;
	private long FlightAttendantId;
	
	public AttendantsInFlight(long flightId, long flightAttendantId) {
		super();
		FlightId = flightId;
		FlightAttendantId = flightAttendantId;
	}
	
	@Override
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (FlightAttendantId ^ (FlightAttendantId >>> 32));
		result = prime * result + (int) (FlightId ^ (FlightId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AttendantsInFlight))
			return false;
		AttendantsInFlight other = (AttendantsInFlight) obj;
		if (FlightAttendantId != other.FlightAttendantId)
			return false;
		if (FlightId != other.FlightId)
			return false;
		return true;
	}
	public long getFlightId() {
		return FlightId;
	}
	public long getFlightAttendantId() {
		return FlightAttendantId;
	}
	public void setFlightId(long flightId) {
		FlightId = flightId;
	}
	public void setFlightAttendantId(long flightAttendantId) {
		FlightAttendantId = flightAttendantId;
	}

	@Override
	public String toString() {
		return String.format("AttendantsInFlight [FlightId=%s, FlightAttendantId=%s]", FlightId, FlightAttendantId);
	}
	

}
