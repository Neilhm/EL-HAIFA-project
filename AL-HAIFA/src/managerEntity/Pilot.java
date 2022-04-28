package managerEntity;

import java.sql.Date;
import java.time.LocalDate;

import managerUtils.PilotType;

public class Pilot extends Employee {
	private long LicenseNumber;
	private Date licenseDate;

	public Pilot(long id, String firstName, String lastName, Date RecruitmentStartDate,
			Date EndOfEmploymentDate, long LicenseNumber, Date licenseDate) {
		super(id, firstName, lastName, RecruitmentStartDate, EndOfEmploymentDate);
		this.LicenseNumber = LicenseNumber;
		this.licenseDate = licenseDate;
	
		// TODO Auto-generated constructor stub
	}

	public Pilot(long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public long getLicense() {
		return LicenseNumber;
	}

	public void setLicense(long LicenseNumber) {
		this.LicenseNumber = LicenseNumber;
	}
	
	public Date getLicenseDate() {
		return licenseDate;
	}

	public void setLicenseDate(Date licenseDate) {
		this.licenseDate = licenseDate;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", getFirstName(), getLastName());
	}
}
