package managerEntity;

import java.sql.Date;
import java.time.LocalDate;

public class FlightAttendants extends Employee {

	public FlightAttendants(long id, String firstName, String lastName, Date RecruitmentStartDate,
			Date EndOfEmploymentDate) {
		super(id, firstName, lastName, RecruitmentStartDate, EndOfEmploymentDate);
		// TODO Auto-generated constructor stub
	}

	public FlightAttendants(long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

}
