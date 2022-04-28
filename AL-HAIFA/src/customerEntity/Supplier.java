package customerEntity;

public class Supplier {
	private long ID;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	public Supplier(long iD, String firstName, String lastName, String phoneNumber, String email) {
		super();
		ID = iD;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	public Supplier(long iD) {
		super();
		ID = iD;
	}
	public long getID() {
		return ID;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFullName() {
		return firstName+" "+lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ID ^ (ID >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Supplier))
			return false;
		Supplier other = (Supplier) obj;
		if (ID != other.ID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("Supplier [ID=%s, firstName=%s, lastName=%s, phoneNumber=%s, email=%s]", ID, firstName,
				lastName, phoneNumber, email);
	}
}
