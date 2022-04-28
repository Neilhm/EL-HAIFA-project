package customerEntity;

import java.util.Date;

public class Customer {
	private long passport;
	private String firstName;
	private String lastName;
	private String citizenship;
	private Date DateOfBirth;
	public Customer(long passport, String firstName, String lastName, String citizenship, Date dateOfBirth) {
		super();
		this.passport = passport;
		this.firstName = firstName;
		this.lastName = lastName;
		this.citizenship = citizenship;
		DateOfBirth = dateOfBirth;
	}
	public Customer(long passport) {
		super();
		this.passport = passport;
	}
	public long getPassport() {
		return passport;
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
	public String getCitizenship() {
		return citizenship;
	}
	public Date getDateOfBirth() {
		return DateOfBirth;
	}
	public void setPassport(long passport) {
		this.passport = passport;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (passport ^ (passport >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Customer))
			return false;
		Customer other = (Customer) obj;
		if (passport != other.passport)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("Customer [passport=%s, firstName=%s, lastName=%s, citizenship=%s, DateOfBirth=%s]",
				passport, firstName, lastName, citizenship, DateOfBirth);
	}

}
