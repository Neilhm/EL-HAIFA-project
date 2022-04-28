package managerEntity;

public class AirportPastMonthReport {
	private String country;
	private Double percentage;
	private int total;
	public AirportPastMonthReport(String country, Double percentage, int total) {
		super();
		this.country = country;
		this.percentage = percentage;
		this.total = total;
	}
	public String getCountry() {
		return country;
	}
	public Double getPercentage() {
		return percentage;
	}
	public int getTotal() {
		return total;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AirportPastMonthReport))
			return false;
		AirportPastMonthReport other = (AirportPastMonthReport) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("AirportPastMonth [country=%s, percentage=%s]", country, percentage);
	}
	}
