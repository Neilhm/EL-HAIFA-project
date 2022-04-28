package managerEntity;

import java.util.ArrayList;

import managerUtils.Size;

public class Airplane {
	private final long tailNum;
	private long size;
	// Constructor
	public Airplane(long tailNum, long size) {
		this.tailNum = tailNum;
		this.size = size;
	}
	
	public Airplane(long tailNum) {
		this.tailNum = tailNum;
	}
	
	public long getTailNum() {
		return tailNum;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (tailNum ^ (tailNum >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Airplane))
			return false;
		Airplane other = (Airplane) obj;
		if (tailNum != other.tailNum)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("tailNum=%s, size=%s", tailNum, size);
	}

}
