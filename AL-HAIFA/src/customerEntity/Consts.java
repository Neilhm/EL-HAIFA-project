package customerEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;

public class Consts {
	//private static Connection conn;
	private Consts() {
		throw new AssertionError();
	}
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
	public static final String SQL_ADD_FLIGHT = "{ call qryInsFlights(?,?,?,?,?,?) }";
	public static final String SQL_DEL_SupplierForFlight = "{call qryDelSupplierForFlight(?)}";
	public static final String SQL_ADD_SupplierForFlight = "{ call qryInsSupplierForFlight(?,?,?) }";
	public static final String SQL_ADD_FLIGHTTICKET = "{ call qryInsFlightTicket(?,?,?,?,?,?,?,?) }";
	public static final String SQL_ADD_SEAT = "{ call qryInsSeats(?,?,?,?,?) }";
	public static final String SQL_ADD_CUSTOMER = "{ call qryInsCustomer(?,?,?,?,?) }";
	public static final String SQL_ADD_ORDER = "{call qryInsOrder(?,?,?)}";
	public static final String SQL_DEL_ORDER = "{call qryDelOrder(?)}";
	public static final String SQL_SEL_AIRPLANE = "SELECT * FROM Tblplane";
	public static final String SQL_SEL_AIRPORT = "SELECT * FROM TblAirport";
	public static final String SQL_GET_SEATSOFFLIGHT = "{call qryGetSeatsOfFlight(?)}";
	public static final String SQL_SEL_SEAT = "SELECT * FROM TblSeat";
	public static final String SQL_GET_SEAT = "{call qryGetSeat(?,?,?)}";
	public static final String SQL_SEL_CUSTOMER = "SELECT * FROM TblCustomer";
	public static final String SQL_CANCELL_SEATS = "{call qryCancellSeatsOfFlight(?,?)}";
	public static final String SQL_SEL_FLIGHTTICKET = "SELECT * FROM TblFlightTicket";
	public static final String SQL_SEL_FLIGHT = "SELECT * FROM TblFlight";
	public static final String SQL_SEL_ORDER = "SELECT * FROM TblOrder";
	public static final String SQL_SEL_SuppliesFlight = "SELECT * FROM TblSuppliesFlight";
	public static final String SQL_SEL_SupplisEntertainment = "SELECT * FROM TblSupplisEntertainment";
	public static final String SQL_SEL_Supplier = "SELECT * FROM TblSupplier";
	public static final String SQL_GET_FLIGHT = "{call qryGetFlight(?)}";
	public static final String SQL_UPD_FLIGHT = "{call qryUpdFlight(?,?,?,?,?,?) }";
	public static final String SQL_UPD_SEAT = "{call qryUpdSeat(?,?,?,?,?) }";
	public static final String SQL_CANCEL_SEAT = "{call qryCancelSeat(?,?) }";
	public static final String SQL_CANCEL_TICKET = "{call qryCancelTicket(?,?,?)}";
	public static final String SQL_SEL_FLIGHTSDETAILS = "Select * FROM FlightsDetails";
	public static final String SQL_UPD_ORDER = "{call qryUpdOrder(?,?,?)}";
	private static String getDBPath() {
		try {
	String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();			
	String decoded = URLDecoder.decode(path, "UTF-8");
	System.out.println("stored data path:" + decoded);
	// System.out.println(decoded) - Can help to check the returned path
	if (decoded.contains(".jar")) {
		String dir = System.getProperty("user.dir").replace('\\', '/');
		return dir+"/CustomerFlyDatabase.accdb";
	} else {
		decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
		System.out.println(decoded + "src/customerEntity/CustomerFlyDatabase.accdb");
		return decoded + "src/customerEntity/CustomerFlyDatabase.accdb";
	}
	
	}catch (Exception e) {
	e.printStackTrace();
	return null;
	}
	}
	
	public static String getDBHelp() {
		try {
			String mdbFileName = "Database.accdb";
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
