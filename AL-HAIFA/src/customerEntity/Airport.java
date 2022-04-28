package customerEntity;

public class Airport {
	
	private final long airportID;
	private String country;
	private String city;
	// Constructor
	public Airport(long airportID, String country, String city) {
		super();
		this.airportID = airportID;
		this.country = country;
		this.city = city;
	}
	
	//Partial Constructor
	public Airport(long airportID) {
		this.airportID = airportID;
	}
	
	//getters and setters
	public long getAirportID() {
		return airportID;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (airportID ^ (airportID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Airport))
			return false;
		Airport other = (Airport) obj;
		if (airportID != other.airportID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("[%s, %s]", country, city);
	}

	

}
