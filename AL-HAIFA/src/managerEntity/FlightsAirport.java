package managerEntity;

import managerUtils.FlightBoard;

public class FlightsAirport {
	private long flightID;
	private long airportID;
	private FlightBoard flightBoard;
	
	public FlightsAirport(long flightID, long airportID, FlightBoard flightBoard) {
		super();
		this.flightID = flightID;
		this.airportID = airportID;
		this.flightBoard = flightBoard;
	}

	public long getFlight() {
		return flightID;
	}

	public long getAirport() {
		return airportID;
	}
	
	public FlightBoard getFlightBoard() {
		return flightBoard;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (airportID ^ (airportID >>> 32));
		result = prime * result + (int) (flightID ^ (flightID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FlightsAirport))
			return false;
		FlightsAirport other = (FlightsAirport) obj;
		if (airportID != other.airportID)
			return false;
		if (flightID != other.flightID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("FlightsAirport [flightID=%s, airportID=%s, flightBoard=%s]", flightID, airportID,
				flightBoard);
	}
}
