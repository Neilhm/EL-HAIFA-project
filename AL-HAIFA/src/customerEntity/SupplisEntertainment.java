package customerEntity;

public class SupplisEntertainment {
	private long ID;
	private String name;
	public SupplisEntertainment(long iD, String name) {
		super();
		ID = iD;
		this.name = name;
	}
	public long getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ID ^ (ID >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SupplisEntertainment))
			return false;
		SupplisEntertainment other = (SupplisEntertainment) obj;
		if (ID != other.ID)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("SupplisEntertainment [ID=%s, name=%s]", ID, name);
	}
}
