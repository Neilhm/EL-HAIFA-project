package managerBoundary;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.cliftonlabs.json_simple.Jsoner;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import customerBoundary.ButtonColumn;
import managerControl.ManagerLogic;
import managerEntity.Airplane;
import managerEntity.Airport;
import managerEntity.AttendantsInFlight;
import managerEntity.Flight;
import managerEntity.FlightAttendants;
import managerUtils.Status;

public class flightsStatus extends JFrame {
	private JTable table;
	private JPanel contentPane;
	private static DefaultTableModel tableModel;
	private static JComboBox<Item> comboBoxStatus;
	private int headerLength;
	private Color clr;
	private JButton btnSaveFlightDetails = new JButton("Save Changes");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					flightsStatus frame = new flightsStatus();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public flightsStatus() {
		initialize();
		createEvents();
	}

	private void initialize() {
		setBackground(SystemColor.inactiveCaptionBorder);
		setBounds(100, 100, 1020, 610);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("FLIGHTS STATUS");
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(432, 52, 434, 34);
		getContentPane().add(lblNewLabel);
		String header[] = new String[] { "FlightID", "DepartureDate", "LandingDate", "Departure Airport",
				"Landing Airport", "FlightCrew", "Status" };
		headerLength = header.length;
		tableModel = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == (header.length - 1))
					return true; // or a condition at your choice with row and column
				else
					return false;
			}
		};
		tableModel.setColumnIdentifiers(header);
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		panel.setBounds(10, 97, 986, 363);
		getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(480, 300));
		scrollPane.setBounds(10, 11, 966, 329);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(tableModel);
		table.setRowHeight(20);
		initTableData();
		btnSaveFlightDetails.setHorizontalAlignment(SwingConstants.LEFT);
		getContentPane().add(btnSaveFlightDetails);
		btnSaveFlightDetails.setBounds(416, 471, 117, 42);
	}

	private void initTableData() {
		ArrayList<Flight> flights = ManagerLogic.getInstance().getFlights();
		ArrayList<Airport> airports = ManagerLogic.getInstance().getAirports();
		HashMap<Long, Airport> hashPort = new HashMap<>();
		for (Airport ap : airports) {
			hashPort.put(ap.getAirportID(), ap);
		}
		// ArrayList<FlightTicket> flightTickets =
		// CustomerLogic.getInstance().getFlightTickets();
		tableModel = (DefaultTableModel) (table.getModel());
		tableModel.setRowCount(0);
		for (Flight f : flights) {
			Vector<Object> data = new Vector<Object>();
			data.add(f.getFlightID());
			data.add(changeFormat(f.getDepartureDate()));
			data.add(changeFormat(f.getLandingDate()));
			data.add(hashPort.get(f.getDepartureAirport()));
			data.add(hashPort.get(f.getLandingAirport()));
			data.add(flightCrew(f));
			data.add(f.getStatus().toString());
			tableModel.addRow(data);
		}
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		// First row is been focused and selected by default
		table.requestFocus();
		setUpStatusColumnComboBox(table, table.getColumnModel().getColumn(headerLength - 1));
		// Notifies all listeners that all cell values in the table's rows may have
		// changed.
		tableModel.fireTableDataChanged();
	}

	public String flightCrew(Flight f) {
		ArrayList<FlightAttendants> crewMembers = ManagerLogic.getInstance().getCrewMembers(f.getFlightID());
		ArrayList<Airplane> airplanes = ManagerLogic.getInstance().getAirplanes();
		Airplane ap = airplanes.get(airplanes.indexOf(new Airplane(f.getTailNum())));
		if (crewMembers.size() < ap.getSize()) {
			return ("Not Ready");
		}
		return "Ready";
	}

	public void setUpStatusColumnComboBox(JTable table, TableColumn columnNum) {
		Vector<Item> combomodel = new Vector<Item>();
		Item item = new Item(Status.Ontime.toString(), Status.Ontime.toString());
		Item item2 = new Item(Status.Delayed.toString(), Status.Delayed.toString());
		Item item3 = new Item(Status.Cancelled.toString(), Status.Cancelled.toString());
		combomodel.addElement(item);
		combomodel.addElement(item2);
		combomodel.addElement(item3);
		comboBoxStatus = new JComboBox(combomodel);

		// Set action when value change update table row according to new selected
		// product and enable saving changes button
		comboBoxStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveFlightDetails.setEnabled(true);
			}
		});

		// Set up the editor (combo-box) for the product-name cells.
		columnNum.setCellEditor(new DefaultCellEditor(comboBoxStatus));
		comboBoxStatus.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);

		// Set up tool tips for the the product-name cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		renderer.setBackground(new Color(204, 204, 204));
		columnNum.setCellRenderer(renderer);
	}

	private void createEvents() {
		btnSaveFlightDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveOnClick(e);
			}
		});
	}

	private Boolean btnSaveOnClick(java.awt.event.ActionEvent evt) {
		table.getColumnModel().getColumn(headerLength - 1).getCellEditor().stopCellEditing();
		ArrayList<Flight> flightsUpdate = new ArrayList<>();
		try {
			int rowIndex = 0;
			while (rowIndex < table.getRowCount()) {
				if (table.getValueAt(rowIndex, 0).toString().length() <= 0)
					break;
				flightsUpdate.add(new Flight(Long.parseLong(table.getValueAt(rowIndex, 0).toString())));
				flightsUpdate.get(flightsUpdate.size() - 1)
						.setStatus(ConvertStringToStatus(table.getValueAt(rowIndex, headerLength - 1).toString()));
				rowIndex++;

			}
			ArrayList<Flight> flights = ManagerLogic.getInstance().getFlights();
			ArrayList<Flight> flightChangedStatus = new ArrayList<Flight>();
			for (Flight f : flightsUpdate) {
				for(Flight f2 : flights) {
					if(f.equals(f2)) {
						if(!f.getStatus().equals(f2.getStatus())) {
							ManagerLogic.getInstance().changeStatusOfFlight(f.getFlightID(), f.getStatus().toString());
							flightChangedStatus.add(f);
						}
					}
				}

			}
			creatXML(flightChangedStatus);
		} catch (Exception e) {
			e.printStackTrace();
			msgbox("Failure!");
			return false;
		}
		msgbox("saved Successfully!");
		initTableData();
		return true;
	}

	private void msgbox(String s) {
		JOptionPane.showMessageDialog(this, s);
	}

	public managerUtils.Status ConvertStringToStatus(String str) {
		for (managerUtils.Status st : managerUtils.Status.values()) {
			if (st.toString().equals(str))
				return st;
		}
		return null;
	}

	private String changeFormat(Date date) {
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
		String Hours, Minutes;
		if (date.getHours() < 10) {
			Hours = String.valueOf("0" + date.getHours());
		} else
			Hours = String.valueOf(date.getHours());
		if (date.getMinutes() < 10) {
			Minutes = String.valueOf("0" + date.getMinutes());
		} else
			Minutes = String.valueOf(date.getMinutes());
		return (year + "-" + month + "-" + day + " " + Hours + ":" + Minutes);
	}

	public void creatXML(ArrayList<Flight> flightsUpdate) throws ParserConfigurationException, TransformerException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("flights");
		doc.appendChild(rootElement);
		for (Flight f : flightsUpdate) {
			Element flight = doc.createElement("flight");
			// add staff to root
			rootElement.appendChild(flight);
			// add xml attribute
			flight.setAttribute("id", String.valueOf(f.getFlightID()));
			Element status = doc.createElement("status");
			status.setTextContent(f.getStatus().toString());
			flight.appendChild(status);
		}
		writeXml(doc, System.out);
	}

	private static void writeXml(Document doc, OutputStream output) throws TransformerException, IOException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
// pretty print
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		DOMSource source = new DOMSource(doc);
		FileWriter writer = new FileWriter(new File("xml.txt"));
		StreamResult result = new StreamResult(writer);
		transformer.transform(source, result);
		
	}
}