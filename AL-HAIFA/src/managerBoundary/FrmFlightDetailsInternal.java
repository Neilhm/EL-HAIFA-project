package managerBoundary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

import managerControl.*;
import managerEntity.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyAdapter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.event.*;

public class FrmFlightDetailsInternal extends JPanel {

	private static final long serialVersionUID = 1L;
	private int iteration;
	private static JTable table;
	private JScrollPane scrollPane;
	private static DefaultTableModel tableModel;
	private static JComboBox<Item> comboBoxProductName;
	private JButton btnSaveFlightDetails = new JButton("Save");
	private JLabel lblAttendantsNumber;
	private JTextField tfAttendantsNumber;
	private JTextField tfAttendantId = new JTextField();
	private long flightID;
	private static ArrayList<FlightAttendants> crewMembers = new ArrayList<FlightAttendants>();
	private ArrayList<FlightAttendants> flightAttendants = new ArrayList<FlightAttendants>();
	private ArrayList<Flight> flights = new ArrayList<Flight>();
	private ArrayList<Airplane> airplanes = new ArrayList<Airplane>();

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This method is being used in order to launch the internal form.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public FrmFlightDetailsInternal() {

		iteration = 0;
		initComponents();
		createEvents();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This method initializing the design structure
// of form left fields
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void initComponents() {

		// Create table according to data structure
		table = new JTable();

		tableModel = new DefaultTableModel();
		String header[] = new String[] { "Attendats ID", "Full Name" };

		tableModel.setColumnIdentifiers(header);
		table.setModel(tableModel);
		table.setRowHeight(25);

		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(500, 125));
		scrollPane.setMaximumSize(new Dimension(450, 20000));
		add(scrollPane);

		// Set table dimensions
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(250);
		columnModel.getColumn(1).setPreferredWidth(1500);

		// add save and delete button
		btnSaveFlightDetails.setHorizontalAlignment(SwingConstants.LEFT);
		add(btnSaveFlightDetails);
		btnSaveFlightDetails.setEnabled(false);

		// add total order component
		lblAttendantsNumber = new JLabel("Attendants Number:");
		add(lblAttendantsNumber);
		tfAttendantsNumber = new JTextField();
		tfAttendantsNumber.setText("0");
		tfAttendantsNumber.setEditable(false);
		add(tfAttendantsNumber);
		tfAttendantsNumber.setColumns(10);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This method contain all the code for creating events
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void createEvents() {

		// Save button action - update/add/remove products in order according to table

		btnSaveFlightDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveOnClick(e);
			}
		});
		// -----------------------------------

		// add selected row listener to enable delete button when row selected
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
			}
		});

		// -----------------------------------
		// add delete key action to table row

		InputMap im = table.getInputMap(JTable.WHEN_FOCUSED);
		ActionMap am = table.getActionMap();

		Action deleteAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeSelectedRows(table);
				btnSaveFlightDetails.setEnabled(true);
				getOrderSubTotal();
			}

		};
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "Delete");
		am.put("Delete", deleteAction);

		// -----------------------------------

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method is been used by the primary form when user browse between orders
//This method clear table rows and selections and set data according to order selected in primary form
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void refreshComp(long flightID) {
		setFlightID(flightID);
		refreshDataButtons();
		initTableData();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set the order to be displayed
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setFlightID(long flightID) {
		this.flightID = flightID;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set the save/delete buttons to be disabled
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void refreshDataButtons() {
		btnSaveFlightDetails.setEnabled(false);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set data to be presented on table according to order id
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void initTableData() {

		// Following code stop cell editing in case table is been edited -its important
		// while user try to browse between orders without saving its edited values
		if (iteration == 0) {
			iteration++;
		} else {

			table.getColumnModel().getColumn(0).getCellEditor().stopCellEditing();
			table.getColumnModel().getColumn(1).getCellEditor().stopCellEditing();
		}

		// Following code clear table (used while browsing between orders)
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);

		// Following code gets all orders details for selected order id and updates
		// table rows
		crewMembers = ManagerLogic.getInstance().getCrewMembers(this.flightID);
		int i = 0;
		while (i < crewMembers.size()) {
			Vector<Object> data = new Vector<Object>();
			data.add(crewMembers.get(i).getId());
			data.add(crewMembers.get(i).getFullName());
			i++;
			tableModel.addRow(data);
		}
		// -------------------

		// Add empty Row
		addEmptyRow();
		// -------------------

		// First row is been focused and selected by default
		table.changeSelection(0, 0, false, false);
		table.requestFocus();
		// set column components
		setUpProductNameColumnComboBox(table, table.getColumnModel().getColumn(1));
		setUpTextEditor(table, 0, tfAttendantId);

		// Notifies all listeners that all cell values in the table's rows may have
		// changed.
		tableModel.fireTableDataChanged();

		// Update total order price according to rows line price sum
		getOrderSubTotal();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set product name column to present combo-box component with all products list
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setUpProductNameColumnComboBox(JTable table, TableColumn columnNum) {

		// Set combo-box item list data - get product id and name
		flightAttendants = ManagerLogic.getInstance().getFlightAttendants();
		Vector<Item> combomodel = new Vector<Item>();

		for (int i = 0; i < flightAttendants.size(); i++) {
			Item item = new Item(Long.toString(flightAttendants.get(i).getId()), flightAttendants.get(i).getFullName());
			combomodel.addElement(item);

		}
		comboBoxProductName = new JComboBox(combomodel);

		// Set action when value change update table row according to new selected
		// product and enable saving changes button
		comboBoxProductName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshProductIdOnNameChange(e);
				btnSaveFlightDetails.setEnabled(true);
				// if last row is been edited add one more row
				if (table.getSelectedRow() == table.getRowCount() - 1) {
					addEmptyRow();
				}

			}
		});

		// Set up the editor (combo-box) for the product-name cells.
		columnNum.setCellEditor(new DefaultCellEditor(comboBoxProductName));
		comboBoxProductName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);

		// Set up tool tips for the the product-name cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		columnNum.setCellRenderer(renderer);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set columns to present text field component in order to managerControl its data changes events
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setUpTextEditor(JTable table, int colIndex, JTextField cell) {
		TableColumn columnNum = table.getColumnModel().getColumn(colIndex);
		columnNum.setCellEditor(new DefaultCellEditor(cell));
		cell.putClientProperty("JTextField.isTableCellEditor", Boolean.TRUE);

		if (colIndex == 0)// Following code update row data when user change row product id item and
							// enable save button
		{

			cell.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					btnSaveFlightDetails.setEnabled(true);
				}

				public void keyReleased(KeyEvent arg0) {
					// Get product selected for combo-box by its Id
					int index = (cell.getText().length() > 0)
							? flightAttendants.indexOf(new FlightAttendants(Long.parseLong(cell.getText())))
							: -1;
					if (index <= flightAttendants.size() && index > -1) {
						String name = flightAttendants.get(index).getFullName();
						// Retrieve object item (id+name) for combo-box selection
						Item object = new Item(cell.getText(), name);
						table.setValueAt(object, table.getSelectedRow(), 1);
						// Update row according to product selection
						updateRowNewProduct(Long.parseLong(object.getId()));
						getOrderSubTotal();
						// If no empty row add new one
						if (table.getSelectedRow() == table.getRowCount() - 1) {
							addEmptyRow();
						}

					} else {
						// If product not found set combo to present instruction for user
						Item object = new Item(null, "please select valid Attendant");
						table.setValueAt(object, table.getSelectedRow(), 1);
					}

				}
			});
		}

		else // Following code update totals when one of the cells changed and enable saving
				// its changes
		{
			cell.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {

					btnSaveFlightDetails.setEnabled(true);
				}

				public void keyReleased(KeyEvent arg0) {
					if (cell.getText() != null && cell.getText().length() > 0) {
						String value = cell.getText();
						if (colIndex == 4) {
							if (value.charAt(value.length() - 1) != '%') {
								NumberFormat numberFormat = NumberFormat.getPercentInstance();
								value = numberFormat.format(Float.parseFloat(value) / 100);
								cell.setText(value);
								cell.setCaretPosition(value.length() - 1);
							}
						} else {
							cell.setText(value);
						}

						table.setValueAt(value, table.getSelectedRow(), table.getSelectedColumn());
						getOrderSubTotal();
					}
				}
			});

			// Set text field courser before % when updating discount
			if (colIndex == 4) {
				cell.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						String value = cell.getText();
						cell.setCaretPosition(value.length() - 1);
					}
				});
			}
		}

		// Set up tool tips
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for Edit");
		columnNum.setCellRenderer(renderer);

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method refresh Product-Id cell value when user change selection on combo-box
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void refreshProductIdOnNameChange(ActionEvent e) {
		JComboBox comboBox = (JComboBox) e.getSource();
		if (comboBox.getSelectedItem() != "please select valid Attendant") {
			Item item = (Item) comboBox.getSelectedItem();
			if (item != null) {
				table.setValueAt(item.getId(), table.getSelectedRow(), 0);
			}
			updateRowNewProduct(Long.parseLong(item.getId()));
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method add Empty Row in order to allow adding new items
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addEmptyRow() {

		Vector<Object> data = new Vector<Object>();
		data.add("");
		data.add("");
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.addRow(data);
		// -------------------

	}

	public void updateRowNewProduct(long id) {
		flightAttendants = ManagerLogic.getInstance().getFlightAttendants();
		getOrderSubTotal();
	}

	public static final int getComponentIndex(Component component) {
		if (component != null && component.getParent() != null) {

			Container c = component.getParent();
			for (int i = 0; i < c.getComponentCount(); i++) {
				if (c.getComponent(i) == component)
					return i;
			}
		}

		return -1;
	}

	private void getOrderSubTotal() {
		tfAttendantsNumber.setText(String.valueOf(table.getRowCount() - 1));
	}

	public void removeSelectedRows(JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int[] rows = table.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			if (rows[i] != table.getRowCount() - 1) {
				model.removeRow(rows[i] - i);
			}
		}
	}

	private void msgbox(String s) {
		JOptionPane.showMessageDialog(this, s);
	}

	private boolean findDuplicate(ArrayList<AttendantsInFlight> aInF) {
		Set<AttendantsInFlight> set = new HashSet<>();
		for (int i = 0; i < aInF.size(); i++) {
			if (set.contains(aInF.get(i)))
				return true;
			else
				set.add(aInF.get(i));
		}
		return false;
	}

	private Boolean btnSaveOnClick(java.awt.event.ActionEvent evt) {
		table.getColumnModel().getColumn(0).getCellEditor().stopCellEditing();
		table.getColumnModel().getColumn(1).getCellEditor().stopCellEditing();
		int rowIndex = 0;
		while (rowIndex < table.getRowCount()) {
			if ((rowIndex) + 1 == table.getRowCount()) {
				if (table.getValueAt(rowIndex, 0).toString().length() <= 0)
					break;
			}
			rowIndex++;
		}
		Boolean success = true;
		ArrayList<AttendantsInFlight> aInF = new ArrayList<>();
		try {
			rowIndex = 0;
			while (rowIndex < table.getRowCount()) {
				if (table.getValueAt(rowIndex, 0).toString().length() <= 0)
					break;
				aInF.add(new AttendantsInFlight(flightID, Long.parseLong(table.getValueAt(rowIndex, 0).toString())));
				rowIndex++;
			}
			if (findDuplicate(aInF) == true) {
				msgbox("Remove Duplicate");
				return false;
			}
			flights = ManagerLogic.getInstance().getFlights();
			long tailNumber = flights.get(flights.indexOf(new Flight(flightID))).getTailNum();
			airplanes = ManagerLogic.getInstance().getAirplanes();
			Airplane ap = airplanes.get(airplanes.indexOf(new Airplane(tailNumber)));
			if (aInF.size() > ap.getSize()) {
				msgbox("Max Number Of Attendants Is " + ap.getSize());
				return false;
			}
			ArrayList<AttendantsInFlight> allAttendantsInFlight = ManagerLogic.getInstance().getAttendantsInFlights();
			for (AttendantsInFlight a : allAttendantsInFlight) {
				for (AttendantsInFlight b : aInF) {
					if (a.getFlightAttendantId() == b.getFlightAttendantId() && a.getFlightId() != b.getFlightId()) {
						if(AreFlightsInSameDay(a.getFlightId(), b.getFlightId())) {
							FlightAttendants ft = ManagerLogic.getInstance().getFlightAttendant(b.getFlightAttendantId());
							msgbox("Attendant " + ft.getFullName() + " Is assigned for flight " +  a.getFlightId());
							return false;
						}
					}

				}
			}
			success = ManagerLogic.getInstance().removeAttendantsInFlight(flightID);
			for (AttendantsInFlight a : aInF) {
				success = ManagerLogic.getInstance().addAttendantsInFlight(a.getFlightId(), a.getFlightAttendantId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (success) {
			msgbox("Crew Edited Successfully!");
			refreshDataButtons();
			initTableData();
		} else
			msgbox("Failure!");
		return true;
	}

	public boolean AreFlightsInSameDay(long flighID1, long flighID2) {
		System.out.println(flighID1);
		System.out.println(flighID2);
		Flight flight1 = ManagerLogic.getInstance().getFlight(flighID1);
		Flight flight2 = ManagerLogic.getInstance().getFlight(flighID2);
		if(flight1.getDepartureDate().equals(flight2.getDepartureDate()))
			return true;
		return false;
	}

}
