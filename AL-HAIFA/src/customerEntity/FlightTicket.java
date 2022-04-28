package customerEntity;

public class FlightTicket {
	private long orderID;
	private long ticketID;
	private long customerPassport;
	private String flightID;
	private String classType;
	private String mealType;
	private String line;
	private String seatNumber;
	public FlightTicket(long orderID, long ticketID, long customerPassport, String classType,
			String mealType,String flightID, String line, String seatNumber) {
		super();
		this.orderID = orderID;
		this.ticketID = ticketID;
		this.customerPassport = customerPassport;
		this.flightID = flightID;
		this.classType = classType;
		this.mealType = mealType;
		this.line = line;
		this.seatNumber = seatNumber;
	}
	public FlightTicket(long orderID, long ticketID, long customerPassport, String classType,
			String mealType,String flightID) {
		super();
		this.orderID = orderID;
		this.ticketID = ticketID;
		this.customerPassport = customerPassport;
		this.flightID = flightID;
		this.classType = classType;
		this.mealType = mealType;
		this.line = null;
		this.seatNumber = null;
	}
	public long getOrderID() {
		return orderID;
	}
	public long getTicketID() {
		return ticketID;
	}
	public long getCustomerPassport() {
		return customerPassport;
	}
	public String getFlightID() {
		return flightID;
	}
	public String getClassType() {
		return classType;
	}
	public String getMealType() {
		return mealType;
	}
	public String getLine() {
		return line;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}
	public void setTicketID(long ticketID) {
		this.ticketID = ticketID;
	}
	public void setCustomerPassport(long customerPassport) {
		this.customerPassport = customerPassport;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (orderID ^ (orderID >>> 32));
		result = prime * result + (int) (ticketID ^ (ticketID >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FlightTicket))
			return false;
		FlightTicket other = (FlightTicket) obj;
		if (orderID != other.orderID)
			return false;
		if (ticketID != other.ticketID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format(
				"FlightTicket [orderID=%s, ticketID=%s, customerPassport=%s, flightID=%s, classType=%s, mealType=%s, line=%s, seatNumber=%s]",
				orderID, ticketID, customerPassport, flightID, classType, mealType, line, seatNumber);
	}

}
