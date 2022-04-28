package managerEntity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.StandardCopyOption;

import javax.swing.JOptionPane;

import java.nio.file.Files;

public class Consts {
	//private static Connection conn;
	private Consts() {
		throw new AssertionError();
	}
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
	public static final String SQL_SEL_ATTENDANTSINFLIGHT = "SELECT * FROM TblAttendantsInFlight";
	public static final String SQL_ADD_FLIGHT = "{ call qryInsFlight(?,?,?,?,?,?,?,?,?) }";
	public static final String SQL_EDIT_FLIGHT = "{ call qryUpdFlight(?,?,?,?,?,?,?,?,?,?) }";
	public static final String SQL_EDIT_STATUSFLIGHT = "{ call qryEditStatusOfFlight(?,?,?) }";
	public static final String SQL_INS_FLIGHTSAIRPORT = "{ call qryInsFlightsAirport(?,?,?) }";
	public static final String SQL_INS_TBLPILOTINFLIGHT = "{ call qryInsPilotInFlight(?,?,?) }";
	public static final String SQL_SEL_AIRPLANE = "SELECT * FROM TblAirplane";
	public static final String SQL_SEL_AIRPORT = "SELECT * FROM TblAirport";
	public static final String SQL_SEL_PILOT = "SELECT * FROM TblPilot";
	public static final String SQL_SEL_FLIGHT = "SELECT * FROM TblFlight";
	public static final String SQL_GET_FLIGHT = "{call qryGetFlight(?)}";
	public static final String SQL_SEL_UPDATEDFLIGHTS = "SELECT * FROM qryUpdatedFlights";
	public static final String SQL_SEL_FLIGHTATTENDANTS = "SELECT * FROM TblFlightAttendants";
	public static final String SQL_ADD_ATTENDANTSINFLIGHT = "{ call qryInsAttendantsInFLight(?,?) }";
	public static final String SQL_DEL_ATTENDANTSINFLIGHT = "{ call qryRemoveAttendantsInFLight(?) }";
	public static final String SQL_SEL_FLIGHTREPORT= "{call DetailsOfBigflights (?,?)}";
	public static final String SQL_SEL_AIRPORTREPORT= "SELECT * FROM DetailsOfAirportPastMonth";
	public static final String SQL_SEL_SEAT= "SELECT * FROM TblSeat";

	
	private static String getDBPath() {
		try {
	String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();			
	String decoded = URLDecoder.decode(path, "UTF-8");
	//System.out.println("stored data path:" + decoded);
	// System.out.println(decoded) - Can help to check the returned path
	if (decoded.contains(".jar")) {
		String dir = System.getProperty("user.dir").replace('\\', '/');
		System.out.println("jarr");
		return dir+"/ManagerFlyDatabase.accdb";
	} else {
		decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
		System.out.println(decoded + "src/managerEntity/ManagerFlyDatabase.accdb");
		return decoded + "src/managerEntity/ManagerFlyDatabase.accdb";
	}
	} catch (Exception e) {
	e.printStackTrace();
	return null;
	}
	}
	public static String getDBHelp() {
		try {
			String mdbFileName = "ManagerFlyDatabase.accdb";
	        String tempDbPath = System.getenv("TEMP").replace('\\', '/') + "/" + mdbFileName; 
	        System.out.println("stored data path: " + tempDbPath);
	        // retrieve .mdb database from the JAR file and save to %TEMP% folder
	        InputStream strmIn = Consts.class.getResourceAsStream(mdbFileName);
	        File f = new File(tempDbPath);
	        System.out.println(strmIn);
	        try {
	            Files.copy(strmIn, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
	        	} catch (IOException e) {
	            e.printStackTrace();
	        	}
	   		String path = tempDbPath;
    		String decoded = URLDecoder.decode(path, "UTF-8");
    		return decoded;
			} catch (Exception e) {
					e.printStackTrace();
					return null;
			}
		}
}
