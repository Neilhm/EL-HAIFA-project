package customerEntity;

public class SuppliesFlight {
	private String flightID;
	private long supplierID;
	private String entertainmentName;
	public SuppliesFlight(String flightID, long supplierID, String entertainmentName) {
		super();
		this.flightID = flightID;
		this.supplierID = supplierID;
		this.entertainmentName = entertainmentName;
	}
	public String getFlightID() {
		return flightID;
	}
	public long getSupplierID() {
		return supplierID;
	}
	public String getEntertainmentName() {
		return entertainmentName;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	public void setSupplierID(long supplierID) {
		this.supplierID = supplierID;
	}
	public void setEntertainmentName(String entertainmentName) {
		this.entertainmentName = entertainmentName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entertainmentName == null) ? 0 : entertainmentName.hashCode());
		result = prime * result + ((flightID == null) ? 0 : flightID.hashCode());
		result = prime * result + (int) (supplierID ^ (supplierID >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SuppliesFlight))
			return false;
		SuppliesFlight other = (SuppliesFlight) obj;
		if (entertainmentName == null) {
			if (other.entertainmentName != null)
				return false;
		} else if (!entertainmentName.equals(other.entertainmentName))
			return false;
		if (flightID == null) {
			if (other.flightID != null)
				return false;
		} else if (!flightID.equals(other.flightID))
			return false;
		if (supplierID != other.supplierID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("SuppliesFlight [flightID=%s, supplierID=%s, entertainmentName=%s]", flightID, supplierID,
				entertainmentName);
	}
}
