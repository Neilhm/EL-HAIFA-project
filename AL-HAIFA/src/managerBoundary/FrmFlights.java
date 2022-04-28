package managerBoundary;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import com.toedter.calendar.JDateChooser;

import managerControl.ManagerLogic;
import managerEntity.*;
import managerUtils.JTextFieldLimiter;
import managerUtils.Status;

import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.table.DefaultTableModel;
import java.awt.SystemColor;

public class FrmFlights extends ManagerRootLayout {

	private ArrayList<Flight> flightsArray;
	ArrayList<Airport> Airports = new ArrayList<Airport>();
	ArrayList<Pilot> pilots = new ArrayList<Pilot>();
	ArrayList<Airplane> airplaneArray = new ArrayList<Airplane>();
	private Integer currentFlight;
	private boolean inAddMode;
	private JTextField tfFlightID;
	private JTextField tfTailNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmFlights frame = new FrmFlights(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param string 
	 */
	public FrmFlights(String string) {
		user = string;
		System.out.println(user);
		initComponents();
		fetchAndRefresh();
		createEvents();
	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 521);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(ManagerFly.class.getResource("/managerBoundary/images/logo.png")));
		setTitle("Flight Details");
		contentPane.setLayout(null);
		pnlEmpDetails.setBounds(21, 41, 339, 171);
		pnlEmpDetails.setBorder(null);
		contentPane.add(pnlEmpDetails);
		tfFlightID = new JTextField();
		tfFlightID.setBounds(84, 11, 179, 23);
		tfFlightID.setEditable(false);
		tfFlightID.setColumns(10);
		tfTailNumber = new JTextField();
		tfTailNumber.setEditable(false);
		tfTailNumber.setBounds(84, 62, 54, 23);
		tfTailNumber.setColumns(10);
		pnlEmpContact.setBounds(370, 41, 347, 171);
		contentPane.add(pnlEmpContact);
		tfTailNumber.setDocument(new JTextFieldLimiter(10));
		pnlActionBtn = new JPanel();
		pnlActionBtn.setBounds(21, 400, 607, 33);
		tfNavigation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNavigation.setText("showing X of Y");
		tfNavigation.setColumns(6);
		contentPane.add(pnlActionBtn);
		pnlActionBtn.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		btnFirst.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnFirst.setHorizontalAlignment(SwingConstants.LEFT);
		pnlActionBtn.add(btnFirst);
		btnPrev.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlActionBtn.add(btnPrev);
		pnlActionBtn.add(tfNavigation);
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlActionBtn.add(btnNext);
		btnLast.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlActionBtn.add(btnLast);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfSearch = new JTextField();
		tfSearch.setText("Search");
		pnlActionBtn.add(tfSearch);
		tfSearch.setColumns(10);
		pnlActionBtn.add(btnSave);
		btnSave.setEnabled(inAddMode);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlActionBtn.add(btnRemove);
		btnRemove.setEnabled(!inAddMode);
		pnlActionBtn.add(btnAdd);
		btnAdd.setEnabled(!inAddMode);
		initSelectAirplane();
		initSelectAirportName();
		initSelectAirportLandingName();
		initSelectFirstPilot();
		initSelectSecondPilot();
		initSelectTime();
		initGroupLayoutleftPanel();
		initGroupLayoutRightPanel();

		panel = new FrmFlightDetailsInternal();
		panel.setForeground(SystemColor.control);
		panel.setBackground(SystemColor.activeCaptionBorder);
		panel.setBounds(100, 223, 515, 171);
		contentPane.add(panel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(21, 11, 173, 33);
		contentPane.add(lblNewLabel);
	}
	private void userFormat() {
		if(user.equals("employeeManager")) {
			dtcDepartureDate.setEnabled(false);
			dtcLandingDate.setEnabled(false);
			cbTimePicker1.setEnabled(false);
			cbTimePicker2.setEnabled(false);
			cbAirplaneName.setEnabled(false);
			cbAirportName.setEnabled(false);
			cbAirportLandingName.setEnabled(false);
			cbFirstPilot.setEnabled(false);
			cbSecondPilot.setEnabled(false);
			panel.setVisible(true);
		}
		if(user.equals("flightManager") || user.equals("adminManager")) {
			dtcDepartureDate.setEnabled(true);
			dtcLandingDate.setEnabled(true);
			cbTimePicker1.setEnabled(true);
			cbTimePicker2.setEnabled(true);
			cbAirplaneName.setEnabled(true);
			cbAirportName.setEnabled(true);
			cbAirportLandingName.setEnabled(true);
			cbFirstPilot.setEnabled(true);
			cbSecondPilot.setEnabled(true);
			panel.setVisible(false);
		}
	}
	private void createEvents() {
		cbAirplaneName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshAirPLaneOnNameChange(e);
				updateChangedField();
			}
		});
		cbFirstPilot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshFirstPilotOnNameChange(e);
				updateChangedField();
			}
		});
		cbSecondPilot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshSecondPilotOnNameChange(e);
				updateChangedField();
			}
		});
		cbAirportLandingName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshLandingPortOnNameChange(e);
				updateChangedField();
			}
		});
		cbAirportName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshDeparturePortOnNameChange(e);
				updateChangedField();
			}
		});
		tfTailNumber.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				int airplaneByIdIndex = -1;
				try {
					airplaneByIdIndex = airplaneArray.indexOf(new Airplane(Long.valueOf(tfTailNumber.getText())));

				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				if (airplaneByIdIndex <= airplaneArray.size() && airplaneByIdIndex > -1) {
					long planeNum = airplaneArray.get(airplaneByIdIndex).getTailNum();
					Item object = new Item(tfTailNumber.getText(), Long.toString(planeNum));

					cbAirplaneName.getModel().setSelectedItem(object);
				} else {
					cbAirplaneName.getModel().setSelectedItem(null);
				}
			}
		});
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFirstOnClick(e);
			}
		});
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLastOnClick(e);
			}
		});
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnPrevOnClick(evt);
			}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnNextOnClick(evt);
			}

		});
		dtcDepartureDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				updateChangedField();
			}
		});
		dtcLandingDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				updateChangedField();
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveOnClick(e);
				// fetchAndRefresh();
			}
		});
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddOnClick(e);
			}
		});

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method initializing the design structure
	// of form right side fields
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void initGroupLayoutRightPanel() {
		pnlEmpContact.setLayout(null);

		JLabel lblAirportDeparture = new JLabel("Airport Departure:");
		lblAirportDeparture.setBounds(0, 23, 104, 20);
		pnlEmpContact.add(lblAirportDeparture);

		cbAirportName.setBounds(131, 23, 200, 20);
		pnlEmpContact.add(cbAirportName);

		cbAirportLandingName.setBounds(131, 54, 200, 23);
		pnlEmpContact.add(cbAirportLandingName);

		JLabel lblAirportLanding = new JLabel("Airport Landing:");
		lblAirportLanding.setBounds(0, 52, 104, 25);
		pnlEmpContact.add(lblAirportLanding);

		cbAirportLandingName.setBounds(131, 54, 200, 20);
		pnlEmpContact.add(cbAirportLandingName);
		tfLanding = new JTextField();
		tfLanding.setBounds(105, 54, 26, 23);
		pnlEmpContact.add(tfLanding);
		tfLanding.setEditable(false);
		tfLanding.setColumns(10);

		tfDeparture = new JTextField();
		tfDeparture.setBounds(105, 22, 26, 23);
		pnlEmpContact.add(tfDeparture);
		tfDeparture.setEditable(false);
		tfDeparture.setColumns(10);

		JLabel lblFirstPilot = new JLabel("First Pilot:");
		lblFirstPilot.setBounds(0, 93, 104, 20);
		pnlEmpContact.add(lblFirstPilot);

		tfFirstPilot = new JTextField();
		tfFirstPilot.setEditable(false);
		tfFirstPilot.setColumns(10);
		tfFirstPilot.setBounds(105, 91, 26, 24);
		pnlEmpContact.add(tfFirstPilot);

		cbFirstPilot.setBounds(131, 92, 200, 24);
		pnlEmpContact.add(cbFirstPilot);

		JLabel lblSecondPilot = new JLabel("Second Pilot:");
		lblSecondPilot.setBounds(0, 126, 104, 25);
		pnlEmpContact.add(lblSecondPilot);

		tfSecondPilot = new JTextField();
		tfSecondPilot.setEditable(false);
		tfSecondPilot.setColumns(10);
		tfSecondPilot.setBounds(105, 128, 26, 23);
		pnlEmpContact.add(tfSecondPilot);

		cbSecondPilot.setBounds(131, 127, 200, 24);
		pnlEmpContact.add(cbSecondPilot);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This method initializing the design structure
	// of form left fields
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void initGroupLayoutleftPanel() {
		pnlEmpDetails.setLayout(null);
		lblFlightID.setBounds(10, 14, 64, 14);
		pnlEmpDetails.add(lblFlightID);
		lblDepartureDate.setBounds(10, 91, 92, 26);
		pnlEmpDetails.add(lblDepartureDate);
		lblAirplaneTail.setBounds(10, 66, 55, 14);
		pnlEmpDetails.add(lblAirplaneTail);
		lblLandingDate.setBounds(10, 116, 92, 26);
		pnlEmpDetails.add(lblLandingDate);
		dtcDepartureDate.setBounds(112, 94, 151, 23);
		pnlEmpDetails.add(dtcDepartureDate);
		dtcLandingDate.setBounds(112, 123, 151, 23);
		pnlEmpDetails.add(dtcLandingDate);
		pnlEmpDetails.add(tfTailNumber);
		pnlEmpDetails.add(tfFlightID);
		cbAirplaneName.setBounds(148, 63, 143, 20);
		pnlEmpDetails.add(cbAirplaneName);
		cbTimePicker1.setBounds(263, 95, 66, 22);
		pnlEmpDetails.add(cbTimePicker1);
		cbTimePicker2.setBounds(263, 124, 66, 22);
		pnlEmpDetails.add(cbTimePicker2);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSelectTime() {
		ArrayList<String> timePicker = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				timePicker.add("0" + i + ":" + "00");
				timePicker.add("0" + i + ":" + "30");
			} else {
				timePicker.add(i + ":" + "00");
				timePicker.add(i + ":" + "30");
			}
		}
		Vector model = new Vector();
		int i = 0;
		while (i < timePicker.size()) {
			model.addElement(new Item(String.valueOf(i), timePicker.get(i)));
			;
			i++;
		}
		cbTimePicker1 = new JComboBox(model);
		cbTimePicker1.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
		cbTimePicker2 = new JComboBox(model);
		cbTimePicker2.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSelectAirplane() {
		airplaneArray = ManagerLogic.getInstance().getAirplanes();
		Vector model = new Vector();
		int i = 0;
		while (i < airplaneArray.size()) {
			model.addElement(
					new Item(Long.toString(airplaneArray.get(i).getTailNum()), airplaneArray.get(i).toString()));
			;
			i++;
		}
		cbAirplaneName = new JComboBox(model);
		cbAirplaneName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSelectFirstPilot() {
		pilots = ManagerLogic.getInstance().getPilots();
		Vector model = new Vector();
		int i = 0;
		while (i < pilots.size()) {
			model.addElement(new Item(Long.toString(pilots.get(i).getId()), pilots.get(i).toString()));
			;
			i++;
		}
		cbFirstPilot = new JComboBox(model);
		cbFirstPilot.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}

	private void initSelectSecondPilot() {
		pilots = ManagerLogic.getInstance().getPilots();
		Vector model = new Vector();
		int i = 0;
		while (i < pilots.size()) {
			model.addElement(new Item(Long.toString(pilots.get(i).getId()), pilots.get(i).toString()));
			;
			i++;
		}
		cbSecondPilot = new JComboBox(model);
		cbSecondPilot.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSelectAirportName() {
		Airports = ManagerLogic.getInstance().getAirports();
		Vector model = new Vector();
		int i = 0;
		while (i < Airports.size()) {
			model.addElement(new Item(Long.toString(Airports.get(i).getAirportID()), Airports.get(i).toString()));
			;
			i++;
		}
		cbAirportName = new JComboBox(model);
		cbAirportName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}

	private void initSelectAirportLandingName() {
		Airports = ManagerLogic.getInstance().getAirports();
		Vector model = new Vector();
		int i = 0;
		while (i < Airports.size()) {
			model.addElement(new Item(Long.toString(Airports.get(i).getAirportID()), Airports.get(i).toString()));
			;
			i++;
		}
		cbAirportLandingName = new JComboBox(model);
		cbAirportLandingName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// fetches primary form data - orders , and refreshes controls.
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void fetchAndRefresh() {
		flightsArray = ManagerLogic.getInstance().getFlights();
		currentFlight = (!flightsArray.isEmpty()) ? 1 : null;
		inAddMode = (flightsArray == null);
		refreshControls();
	}

	private void refreshControls() {
		refreshNavigation();
		refreshFlightFields();
		refreshDataButtons();
		refreshCancelFlight();
	}

	private void setEnabledFalse() {
		dtcDepartureDate.setEnabled(false);
		dtcLandingDate.setEnabled(false);
		cbTimePicker1.setEnabled(false);
		cbTimePicker2.setEnabled(false);
		cbAirplaneName.setEnabled(false);
		cbAirportName.setEnabled(false);
		cbAirportLandingName.setEnabled(false);
		cbFirstPilot.setEnabled(false);
		cbSecondPilot.setEnabled(false);
		panel.setVisible(false);
	}

	private void setEnabledTrue() {
		dtcDepartureDate.setEnabled(true);
		dtcLandingDate.setEnabled(true);
		cbTimePicker1.setEnabled(true);
		cbTimePicker2.setEnabled(true);
		cbAirplaneName.setEnabled(true);
		cbAirportName.setEnabled(true);
		cbAirportLandingName.setEnabled(true);
		cbFirstPilot.setEnabled(true);
		cbSecondPilot.setEnabled(true);
		panel.setVisible(true);
		userFormat();
	}

	private void refreshCancelFlight() {
		if (tfFlightID.getText().equals("(NEW)")) {
			setEnabledTrue();
			return;
		} 
		if (flightsArray.get(currentFlight - 1).getStatus().equals(Status.Cancelled)) {
			setEnabledFalse();
		} else {
			setEnabledTrue();
		}
	}

	private void refreshNavigation() {
		tfNavigation.setText((!inAddMode) ? "" + currentFlight + " of " + flightsArray.size()
				: "" + (flightsArray.size() + 1) + " of " + (flightsArray.size() + 1));

		btnFirst.setEnabled(currentFlight != null && currentFlight > 1);
		btnPrev.setEnabled(currentFlight != null && currentFlight > 1);
		btnNext.setEnabled(currentFlight != null && currentFlight < flightsArray.size());
		btnLast.setEnabled(currentFlight != null && currentFlight < flightsArray.size());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the various order manipulation buttons,
	// according to form state
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void refreshDataButtons() {
		btnSave.setEnabled(inAddMode);
		btnAdd.setEnabled(!inAddMode);
		btnRemove.setEnabled(!inAddMode);
		tfSearch.setEnabled(!inAddMode);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// updates the empArray controls with a given employee's information.
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void refreshFlightFields() {
		Flight flight = (!inAddMode) ? flightsArray.get(currentFlight - 1) : null;
		tfFlightID.setText((flight != null) ? ("" + flight.getFlightID()) : "(NEW)");
		tfTailNumber.setText((flight != null) ? Long.toString(flight.getTailNum()) : null);
		dtcDepartureDate.setDate((flight != null) ? flight.getDepartureDate() : null);
		dtcLandingDate.setDate((flight != null) ? flight.getLandingDate() : null);
		tfLanding.setText((flight != null) ? Long.toString(flight.getLandingAirport()) : null);
		tfDeparture.setText((flight != null) ? Long.toString(flight.getDepartureAirport()) : null);
		tfFirstPilot.setText((flight != null) ? Long.toString(flight.getFirstPilot()) : null);
		tfSecondPilot.setText((flight != null) ? Long.toString(flight.getSeconedPilot()) : null);
		refreshTimePickerComBoxSelectedByID();
		refreshPlaneComBoxSelectedByID();
		refreshAirportComBoxSelectedByID();
		refreshPilotComBoxSelectedByID();

		panel.refreshComp((flight != null) ? (flight.getFlightID()) : 0);
	}

	private void refreshTimePickerComBoxSelectedByID() {
		if (!inAddMode) {
			Flight flight = flightsArray.get(currentFlight - 1);
			Date d = flight.getDepartureDate();
			String Hours, Minutes;
			if (d.getHours() < 10) {
				Hours = String.valueOf("0" + d.getHours());
			} else
				Hours = String.valueOf(d.getHours());
			if (d.getMinutes() < 10) {
				Minutes = String.valueOf("0" + d.getMinutes());
			} else
				Minutes = String.valueOf(d.getMinutes());
			cbTimePicker1.getModel().setSelectedItem(Hours + ":" + Minutes);
			Date t = flight.getLandingDate();
			if (t.getHours() < 10) {
				Hours = String.valueOf("0" + t.getHours());
			} else
				Hours = String.valueOf(t.getHours());
			if (t.getMinutes() < 10) {
				Minutes = String.valueOf("0" + t.getMinutes());
			} else
				Minutes = String.valueOf(t.getMinutes());
			cbTimePicker2.getModel().setSelectedItem(Hours + ":" + Minutes);
		} else {
			cbTimePicker1.getModel().setSelectedItem("select");
			cbTimePicker2.getModel().setSelectedItem("select");
		}
	}

	private void refreshPlaneComBoxSelectedByID() {
		if (!inAddMode) {
			int planeByIdIndex = airplaneArray.indexOf(new Airplane(Long.parseLong(tfTailNumber.getText())));
			if (planeByIdIndex <= airplaneArray.size() && planeByIdIndex > 0) {
				String fullName = airplaneArray.get(planeByIdIndex).toString();
				Item object = new Item(tfTailNumber.getText(), fullName);

				cbAirplaneName.getModel().setSelectedItem(object);
			}
		} else {
			cbAirplaneName.getModel().setSelectedItem("select");
		}
	}

	private void refreshPilotComBoxSelectedByID() {
		if (!inAddMode) {
			int pilotByIdIndex = pilots.indexOf(new Pilot(Long.parseLong(tfFirstPilot.getText())));
			if (pilotByIdIndex <= pilots.size() && pilotByIdIndex > 0) {
				String fullName = pilots.get(pilotByIdIndex).toString();
				Item object = new Item(tfFirstPilot.getText(), fullName);
				cbFirstPilot.getModel().setSelectedItem(object);
			}
			int pilotByIdIndex2 = pilots.indexOf(new Pilot(Long.parseLong(tfSecondPilot.getText())));
			if (pilotByIdIndex2 <= pilots.size() && pilotByIdIndex2 > 0) {
				String fullName = pilots.get(pilotByIdIndex2).toString();
				Item object = new Item(tfSecondPilot.getText(), fullName);
				cbSecondPilot.getModel().setSelectedItem(object);
			}
		} else {
			cbFirstPilot.getModel().setSelectedItem("select");
			cbSecondPilot.getModel().setSelectedItem("select");
		}
	}

	private void refreshAirportComBoxSelectedByID() {
		if (!inAddMode) {
			int portByIdIndex = Airports.indexOf(new Airport(Long.parseLong(tfLanding.getText())));
			if (portByIdIndex <= Airports.size() && portByIdIndex > 0) {
				String fullName = Airports.get(portByIdIndex).toString();
				Item object = new Item(tfLanding.getText(), fullName);
				cbAirportLandingName.getModel().setSelectedItem(object);
			}
			int portByIdIndex2 = Airports.indexOf(new Airport(Long.parseLong(tfDeparture.getText())));
			if (portByIdIndex2 <= Airports.size() && portByIdIndex2 > 0) {
				String fullName = Airports.get(portByIdIndex2).toString();
				Item object = new Item(tfDeparture.getText(), fullName);
				cbAirportName.getModel().setSelectedItem(object);
			}
		} else {
			cbAirportLandingName.getModel().setSelectedItem("select");
			cbAirportName.getModel().setSelectedItem("select");
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the view to present previous order
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void btnPrevOnClick(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPrevOnClick
		inAddMode = false;
		currentFlight--;
		refreshControls();
	}// GEN-LAST:event_btnPrevOnClick

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the view to present next order
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void btnNextOnClick(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNextOnClick
		currentFlight++;
		refreshControls();
	}// GEN-LAST:event_btnNextOnClick

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the view to present last order on list
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void btnLastOnClick(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLastOnClick
		currentFlight = flightsArray.size();
		refreshControls();
	}// GEN-LAST:event_btnLastOnClick

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Updates the view to present first order on list
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void btnFirstOnClick(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnFirstOnClick
		inAddMode = false;
		currentFlight = 1;
		refreshControls();
	}// GEN-LAST:event_btnFirstOnClick

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// On any change on order fields updates the view to enable save
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void updateChangedField() {
		btnSave.setEnabled(true);
	}

	/**
	 * Add a new order OR Edit a exist order (through Control package) to the DB.
	 */
	@SuppressWarnings("unused")
	private void btnSaveOnClick(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSaveOnClick
		switch (inputValid()) {
		case 0:
			Long id = (!inAddMode) ? Long.parseLong(tfFlightID.getText()) : -1;
			boolean saveLastStateTemp = inAddMode;
			long landingPort = Long.parseLong(tfLanding.getText());
			long departurePort = Long.parseLong(tfDeparture.getText());
			long tailNumber = Long.parseLong(tfTailNumber.getText());
			Date LandingDate = dtcLandingDate.getDate();
			Date DepartureDate = dtcDepartureDate.getDate();
			long firstPilot = Long.parseLong(tfFirstPilot.getText());
			long secondPilot = Long.parseLong(tfSecondPilot.getText());
			String timepicker1 = String.valueOf(cbTimePicker1.getSelectedItem());
			String timepicker2 = String.valueOf(cbTimePicker2.getSelectedItem());
			if (tfFlightID.getText().equals("(NEW)")) {
				boolean isAdded = ManagerLogic.getInstance().addFlight(tailNumber, departurePort, landingPort,
						DepartureDate, LandingDate, firstPilot, secondPilot, timepicker1, timepicker2);
				if (isAdded) {
					JOptionPane.showMessageDialog(this, "Flight added successfully!");
					fetchAndRefresh();
				} else
					JOptionPane.showMessageDialog(this, "Failure!");
			} else {
				String status = flightsArray.get(currentFlight-1).getStatus().toString();
				boolean isEdit = ManagerLogic.getInstance().editFlight(Long.parseLong(tfFlightID.getText()), tailNumber,
						departurePort, landingPort, DepartureDate, LandingDate, firstPilot, secondPilot, timepicker1,
						timepicker2, status);
				if (isEdit) {
					JOptionPane.showMessageDialog(this, "Flight edited successfully!");
					fetchAndRefresh();
				} else
					JOptionPane.showMessageDialog(this, "Failure!");
			}
			break;
		case 1:
			JOptionPane.showMessageDialog(this, "All field are required!");
			break;
		case 2:
			JOptionPane.showMessageDialog(this, "LandingDate should be after DepartureDate!");
			break;
		case 3:
			JOptionPane.showMessageDialog(this, "DepartureDate should be more than two month");
			break;
		case 4:
			JOptionPane.showMessageDialog(this, "Departure and landing cant be the same");
			break;
		case 5:
			JOptionPane.showMessageDialog(this, "Airplane Is Occcupied");
			break;
		}

	}// GEN-LAST:event_btnSaveOnClick

	private void btnAddOnClick(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddOnClick
		inAddMode = true;
		currentFlight = flightsArray.size() + 1;
		refreshControls();
	}// GEN-LAST:event_btnAddOnClick

	/**
	 * Delete order (through Control package) from the DB.
	 */
	private void btnRemoveOnClick(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRemoveOnClick
	}// GEN-LAST:event_btnRemoveOnClick

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Used after updating\adding employee - it retrieved updated data without
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// loosing
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// current
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// employee
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// location
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// by
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// its
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// id
	// fetches empArray, and tries to refresh controls to given employee.
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void fetchAndRefresh(Long id) {
		flightsArray = ManagerLogic.getInstance().getFlights();
		if (!flightsArray.isEmpty()) {
			if (id != null)
				currentFlight = (!inAddMode) ? flightsArray.indexOf(new Flight(id)) + 1 : flightsArray.size(); //

			if (id == null || currentFlight == 0) // indexOf could return -1.
				currentFlight = 1;
		} else
			currentFlight = null;
		inAddMode = (currentFlight == null);
		refreshControls();
	}

	private int inputValid() {
		if (cbAirplaneName.getSelectedItem() == null || cbAirportName.getSelectedItem() == null
				|| tfTailNumber.getText().length() < 1 || dtcLandingDate.getDate() == null
				|| dtcDepartureDate.getDate() == null) {
			return 1;
		}
		if (dtcLandingDate.getDate().before(dtcDepartureDate.getDate()) || cbTimePicker1.getSelectedItem().toString()
				.compareTo(cbTimePicker2.getSelectedItem().toString()) == 1) {
			return 2;
		}
		LocalDate now = LocalDate.now();
		LocalDate nowPlus2Months = now.plusMonths(2);
		LocalDate departureDate = dtcDepartureDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (tfFlightID.getText().equals("(NEW)")) {
			if (!departureDate.isAfter(nowPlus2Months) && !departureDate.isEqual(nowPlus2Months)) {
				return 3;
			}
		}
		if (tfDeparture.getText().equals(tfLanding.getText())) {
			return 4;
		}
		if (IsAirplaneOcccupied()) {
			return 5;
		}
		return 0;
	}

	private boolean IsAirplaneOcccupied() {
		for (Flight f : flightsArray) {
			if (f.getTailNum() == Long.parseLong(tfTailNumber.getText())) {
				if (tfFlightID.getText().equals("(NEW)")) {
					if (f.getDepartureDate().equals(changeFormat(dtcDepartureDate.getDate()))) {
						return true;
					}
				} else if (AreFlightsInSameDay(f.getFlightID(), Long.parseLong(tfFlightID.getText()))) {
					return true;
				}
			}
		}
		return false;
	}

	private Date changeFormat(Date date) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String year, month, day;
			year = String.valueOf(date.getYear() + 1900);
			int m = date.getMonth() + 1;
			if (m < 10)
				month = "0" + String.valueOf(m);
			else
				month = String.valueOf(m);
			int d = date.getDate();
			if (d < 10)
				day = "0" + String.valueOf(d);
			else
				day = String.valueOf(d);
			String dateString = year + "-" + month + "-" + day;
			date = (Date) parser.parse(dateString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return date;
	}

	private boolean AreFlightsInSameDay(long flighID1, long flighID2) {
		if(flighID1 == flighID2)
			return false;
		Flight flight1 = ManagerLogic.getInstance().getFlight(flighID1);
		Flight flight2 = ManagerLogic.getInstance().getFlight(flighID2);
		if (flight1.getDepartureDate().equals(flight2.getDepartureDate()))
			return true;
		return false;
	}

	class ItemRenderer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value != null) {
				Item item = (Item) value;
				setText(item.getDescription().toUpperCase());
			}

			if (index == -1) {
				Item item = (Item) value;
				setText("" + item.getId());
			}

			return this;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Set Id according combobox chosen description
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void refreshAirPLaneOnNameChange(ActionEvent e) {
		JComboBox comboBox = (JComboBox) e.getSource();
		if (comboBox.getSelectedItem() != "select") {
			Item item = (Item) comboBox.getSelectedItem();
			if (item != null) {
				tfTailNumber.setText(item.getId());
			}
		}
	}

	private void refreshLandingPortOnNameChange(ActionEvent e) {
		JComboBox comboBox = (JComboBox) e.getSource();
		if (comboBox.getSelectedItem() != "select") {
			Item item = (Item) comboBox.getSelectedItem();
			if (item != null) {
				tfLanding.setText(item.getId());
			}
		}
	}

	private void refreshFirstPilotOnNameChange(ActionEvent e) {
		JComboBox comboBox = (JComboBox) e.getSource();
		if (comboBox.getSelectedItem() != "select") {
			Item item = (Item) comboBox.getSelectedItem();
			if (item != null) {
				tfFirstPilot.setText(item.getId());
			}
		}
	}

	private void refreshSecondPilotOnNameChange(ActionEvent e) {
		JComboBox comboBox = (JComboBox) e.getSource();
		if (comboBox.getSelectedItem() != "select") {
			Item item = (Item) comboBox.getSelectedItem();
			if (item != null) {
				tfSecondPilot.setText(item.getId());
			}
		}
	}

	private void refreshDeparturePortOnNameChange(ActionEvent e) {
		JComboBox comboBox = (JComboBox) e.getSource();
		if (comboBox.getSelectedItem() != "select") {
			Item item = (Item) comboBox.getSelectedItem();
			if (item != null) {
				tfDeparture.setText(item.getId());
			}
		}
	}

	private JPanel pnlEmpDetails = new JPanel();
	private JPanel pnlEmpContact = new JPanel();
	private JPanel contentPane;
	private FrmFlightDetailsInternal panel;
	private JTextField tfNavigation = new JTextField();
	private JLabel lblAirplaneTail = new JLabel("Airplane:");
	private JLabel lblDepartureDate = new JLabel("Departure Date:");
	private JLabel lblFlightID = new JLabel("Flight ID:");
	private JLabel lblLandingDate = new JLabel("Landing Date:");
	private JDateChooser dtcDepartureDate = new JDateChooser();
	private JDateChooser dtcLandingDate = new JDateChooser();
	private JButton btnFirst = new JButton("|<");
	private JButton btnPrev = new JButton("<<");
	private JButton btnNext = new JButton(">>");
	private JButton btnLast = new JButton(">|");
	private final JButton btnSave = new JButton("Save");
	private final JButton btnAdd = new JButton("Add New");
	private final JButton btnRemove = new JButton("Delete");
	private JPanel pnlActionBtn;
	private JTextField tfSearch;
	private JComboBox cbTimePicker1 = new JComboBox();
	private JComboBox cbTimePicker2 = new JComboBox();
	private JComboBox cbAirplaneName = new JComboBox();
	private JComboBox cbAirportName = new JComboBox();
	private JComboBox cbAirportLandingName = new JComboBox();
	private JComboBox cbFirstPilot = new JComboBox();
	private JComboBox cbSecondPilot = new JComboBox();
	private DefaultTableModel tableModel;
	private final JLabel lblNewLabel = new JLabel("Flight Details:");
	private JTextField tfLanding;
	private JTextField tfDeparture;
	private JTextField tfFirstPilot;
	private JTextField tfSecondPilot;
}
