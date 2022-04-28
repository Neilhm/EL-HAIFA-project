package managerEntity;


import managerUtils.ClassType;

public class SeatInFlight {
	private long flightID;
	private long seatID;
	private ClassType classType;
	private boolean isOrdered;
	public SeatInFlight() {
	}
	public SeatInFlight(long flightID, long seatID, ClassType classType, boolean isOrdered) {
		super();
		this.flightID = flightID;
		this.seatID = seatID;
		this.classType = classType;
		this.isOrdered = isOrdered;
	}
	public long getFlightID() {
		return flightID;
	}
	public long getSeatID() {
		return seatID;
	}
	public ClassType getClassType() {
		return classType;
	}
	public boolean isOrdered() {
		return isOrdered;
	}
	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (flightID ^ (flightID >>> 32));
		result = prime * result + (int) (seatID ^ (seatID >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SeatInFlight))
			return false;
		SeatInFlight other = (SeatInFlight) obj;
		if (flightID != other.flightID)
			return false;
		if (seatID != other.seatID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("SeatInFlight [flightID=%s, seatID=%s, classType=%s, isOrdered=%s]", flightID, seatID,
				classType, isOrdered);
	}
}
