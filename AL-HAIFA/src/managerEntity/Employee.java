package managerEntity;

import java.sql.Date;
import java.time.LocalDate;
public class Employee {
	/**
	 * 
	 */
	private final long id;
	private String firstName;
	private String lastName;
	private Date RecruitmentStartDate;
	private Date EndOfEmploymentDate;
	
	// Constructor
	public Employee(long id, String firstName, String lastName, Date RecruitmentStartDate, Date EndOfEmploymentDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.RecruitmentStartDate = RecruitmentStartDate;
		this.EndOfEmploymentDate = EndOfEmploymentDate;
	}
	
	//Partial Constructor
	public Employee(long id) {
		this.id = id;
	}
	
	// getters and setters
	public long getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getRecruitmentStartDate() {
		return RecruitmentStartDate;
	}
	public void setRecruitmentStartDate(Date RecruitmentStartDate) {
		this.RecruitmentStartDate = RecruitmentStartDate;
	}
	public Date getEndOfEmploymentDate() {
		return EndOfEmploymentDate;
	}
	public void setEndOfEmploymentDate(Date EndOfEmploymentDate) {
		this.EndOfEmploymentDate = EndOfEmploymentDate;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Employee))
			return false;
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "[firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	

	
}
