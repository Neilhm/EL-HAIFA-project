package managerEntity;

import managerUtils.ClassType;

public class Seat {
	private long line;
	private String seat;
	private long tailNumber;
	private ClassType classType;
	
	public Seat(long line, String seat, long tailNumber, ClassType classType) {
		super();
		this.line = line;
		this.seat = seat;
		this.tailNumber = tailNumber;
		this.classType = classType;
	}

	public long getLine() {
		return line;
	}

	public String getSeat() {
		return seat;
	}

	public long getTailNumber() {
		return tailNumber;
	}

	public ClassType getClassType() {
		return classType;
	}

	public void setLine(long line) {
		this.line = line;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public void setTailNumber(int tailNumber) {
		this.tailNumber = tailNumber;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (line ^ (line >>> 32));
		result = prime * result + ((seat == null) ? 0 : seat.hashCode());
		result = prime * result + (int) (tailNumber ^ (tailNumber >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Seat))
			return false;
		Seat other = (Seat) obj;
		if (line != other.line)
			return false;
		if (seat == null) {
			if (other.seat != null)
				return false;
		} else if (!seat.equals(other.seat))
			return false;
		if (tailNumber != other.tailNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Seat [line=%s, Seat=%s, tailNumber=%s, classType=%s]", line, seat, tailNumber, classType);
	}
	
}
