package customerEntity;

import customerUtils.Column;

public class Seat {
	private final String flightID;
	private String line;
	private String column;
	private String classType;
	private String status;
	public Seat(String flightID, String line, String column, String classType, String status) {
		super();
		this.flightID = flightID;
		this.line = line;
		this.column = column;
		this.classType = classType;
		this.status = status;
	}
	
	public Seat(String flightID, String line, String column) {
		super();
		this.flightID = flightID;
		this.line = line;
		this.column = column;
	}

	public String getFlightID() {
		return flightID;
	}

	public String getLine() {
		return line;
	}

	public String getColumn() {
		return column;
	}

	public String getClassType() {
		return classType;
	}

	public String getStatus() {
		return status;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((flightID == null) ? 0 : flightID.hashCode());
		result = prime * result + ((line == null) ? 0 : line.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Seat))
			return false;
		Seat other = (Seat) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (flightID == null) {
			if (other.flightID != null)
				return false;
		} else if (!flightID.equals(other.flightID))
			return false;
		if (line == null) {
			if (other.line != null)
				return false;
		} else if (!line.equals(other.line))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("[flightID=%s, line=%s, column=%s, classType=%s, status=%s]", flightID, line, column,
				classType, status);
	}
	
}
