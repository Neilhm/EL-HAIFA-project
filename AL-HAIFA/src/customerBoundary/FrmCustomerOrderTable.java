package customerBoundary;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import customerControl.CustomerLogic;
import customerEntity.Customer;
import customerEntity.Flight;
import customerEntity.FlightTicket;
import customerEntity.Seat;
import managerBoundary.Item;

public class FrmCustomerOrderTable extends JPanel {
	private static final long serialVersionUID = 1L;
	private int iteration;
	private static JTable table;
	private JScrollPane scrollPane;
	private static DefaultTableModel tableModel;
	private static JComboBox<Item> comboBoxCustomerName;
	private static JComboBox<Item> comboBoxMealType;
	private static JComboBox<Item> comboBoxClassType;
	protected JButton btnSaveFlightDetails = new JButton("Save");
	private JLabel lblCustomersNumber;
	private JTextField tfCustomerNumber;
	private JTextField tfCustomerId = new JTextField();
	private String flightID;
	private long orderID;
	private static ArrayList<Customer> orderMembers = new ArrayList<Customer>();
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private ArrayList<Flight> flights = new ArrayList<Flight>();
	private ArrayList<Seat> availableSeats = new ArrayList<Seat>();
	private JLabel lblSeats;
	private JTextField tfSeats;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This method is being used in order to launch the internal form.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public FrmCustomerOrderTable() {

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
		String header[] = new String[] { "Customer ID", "Full Name", "classType", "mealType" };

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
		columnModel.getColumn(0).setPreferredWidth(100);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(200);
		columnModel.getColumn(3).setPreferredWidth(200);

		// add save and delete button
		btnSaveFlightDetails.setHorizontalAlignment(SwingConstants.LEFT);
		add(btnSaveFlightDetails);
		btnSaveFlightDetails.setEnabled(false);

		// add total order component
		lblCustomersNumber = new JLabel("Customers Number:");
		add(lblCustomersNumber);
		tfCustomerNumber = new JTextField();
		tfCustomerNumber.setText("0");
		tfCustomerNumber.setEditable(false);
		add(tfCustomerNumber);
		tfCustomerNumber.setColumns(10);

		lblSeats = new JLabel("Seats");
		add(lblSeats);

		tfSeats = new JTextField();
		add(tfSeats);
		tfSeats.setColumns(20);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This method contain all the code for creating events
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void createEvents() {

		// Save button action - update/add/remove products in order according to table
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
	public void refreshComp(String flightID, long orderID) {
		setFlightID(flightID);
		setOrderID(orderID);
		refreshDataButtons();
		initTableData();
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set the order to be displayed
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setFlightID(String flightID) {
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
			table.getColumnModel().getColumn(2).getCellEditor().stopCellEditing();
			table.getColumnModel().getColumn(3).getCellEditor().stopCellEditing();
		}

		// Following code clear table (used while browsing between orders)
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);

		// Add empty Row
		addEmptyRow();
		// -------------------

		// First row is been focused and selected by default
		table.changeSelection(0, 0, false, false);
		table.requestFocus();
		// set column components
		setUpCustomerNameColumnComboBox(table, table.getColumnModel().getColumn(1));
		setUpClassTypeColumnComboBox(table, table.getColumnModel().getColumn(2));
		setUpMealTypeColumnComboBox(table, table.getColumnModel().getColumn(3));
		setUpTextEditor(table, 0, tfCustomerId);

		// Notifies all listeners that all cell values in the table's rows may have
		// changed.
		tableModel.fireTableDataChanged();

		// Update total order price according to rows line price sum
		getOrderSubTotal();
		ArrayList<Seat> seats = CustomerLogic.getInstance().getAvailableSeatsOfFlight(flightID);
		int Economy = 0;
		int First = 0;
		int Business = 0;
		for (Seat s : seats) {
			if (s.getClassType().equals("Economy")) {
				Economy++;
			} else if (s.getClassType().equals("First")) {
				First++;
			} else if (s.getClassType().equals("Business")) {
				Business++;
			}
		}
		tfSeats.setText("Economy: " + Economy + " First: " + First + " Business: " + Business);
		tfSeats.setEditable(false);

	}

	public void setUpClassTypeColumnComboBox(JTable table, TableColumn columnNum) {

		// Set combo-box item list data - get product id and name
		Vector<Item> combomodel = new Vector<Item>();
		Item item = new Item("Economy", "Economy");
		combomodel.addElement(item);
		Item item2 = new Item("Business", "Business");
		combomodel.addElement(item2);
		Item item3 = new Item("First", "First");
		combomodel.addElement(item3);
		comboBoxClassType = new JComboBox(combomodel);

		// Set action when value change update table row according to new selected
		// product and enable saving changes button
		comboBoxClassType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveFlightDetails.setEnabled(true);
				// if last row is been edited add one more row
				if (table.getSelectedRow() == table.getRowCount() - 1) {
					addEmptyRow();
				}

			}
		});
		// Set up the editor (combo-box) for the product-name cells.
		columnNum.setCellEditor(new DefaultCellEditor(comboBoxClassType));
		comboBoxClassType.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);

		// Set up tool tips for the the product-name cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		columnNum.setCellRenderer(renderer);
	}

	public void setUpMealTypeColumnComboBox(JTable table, TableColumn columnNum) {

		// Set combo-box item list data - get product id and name
		customers = CustomerLogic.getInstance().getCustomers();
		Vector<Item> combomodel = new Vector<Item>();
		Item item = new Item("free", "free");
		combomodel.addElement(item);
		Item item2 = new Item("regular", "regular");
		combomodel.addElement(item2);
		Item item3 = new Item("vegetarian", "vegetarian");
		combomodel.addElement(item3);
		Item item4 = new Item("vegan", "vegan");
		combomodel.addElement(item3);
		Item item5 = new Item("kosher", "kosher");
		combomodel.addElement(item3);

		comboBoxMealType = new JComboBox(combomodel);

		// Set action when value change update table row according to new selected
		// product and enable saving changes button
		comboBoxMealType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveFlightDetails.setEnabled(true);
				// if last row is been edited add one more row
				if (table.getSelectedRow() == table.getRowCount() - 1) {
					addEmptyRow();
				}

			}
		});

		// Set up the editor (combo-box) for the product-name cells.
		columnNum.setCellEditor(new DefaultCellEditor(comboBoxMealType));
		comboBoxMealType.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);

		// Set up tool tips for the the product-name cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		columnNum.setCellRenderer(renderer);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This method set product name column to present combo-box component with all products list
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setUpCustomerNameColumnComboBox(JTable table, TableColumn columnNum) {

		// Set combo-box item list data - get product id and name
		customers = CustomerLogic.getInstance().getCustomers();
		Vector<Item> combomodel = new Vector<Item>();

		for (int i = 0; i < customers.size(); i++) {
			Item item = new Item(Long.toString(customers.get(i).getPassport()), customers.get(i).getFullName());
			combomodel.addElement(item);

		}
		comboBoxCustomerName = new JComboBox(combomodel);

		// Set action when value change update table row according to new selected
		// product and enable saving changes button
		comboBoxCustomerName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshCustomerIdOnNameChange(e);
				btnSaveFlightDetails.setEnabled(true);
				// if last row is been edited add one more row
				if (table.getSelectedRow() == table.getRowCount() - 1) {
					addEmptyRow();
				}

			}
		});

		// Set up the editor (combo-box) for the product-name cells.
		columnNum.setCellEditor(new DefaultCellEditor(comboBoxCustomerName));
		comboBoxCustomerName.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);

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
							? customers.indexOf(new Customer(Long.parseLong(cell.getText())))
							: -1;
					if (index <= customers.size() && index > -1) {
						String name = customers.get(index).getFullName();
						// Retrieve object item (id+name) for combo-box selection
						Item object = new Item(cell.getText(), name);
						table.setValueAt(object, table.getSelectedRow(), 1);
						// Update row according to product selection
						updateRowNewCustomer(Long.parseLong(object.getId()));
						getOrderSubTotal();
						// If no empty row add new one
						if (table.getSelectedRow() == table.getRowCount() - 1) {
							addEmptyRow();
						}

					} else {
						// If product not found set combo to present instruction for user
						Item object = new Item(null, "please select valid Customer");
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
	private void refreshCustomerIdOnNameChange(ActionEvent e) {
		JComboBox comboBox = (JComboBox) e.getSource();
		if (comboBox.getSelectedItem() != "please select valid Customer") {
			Item item = (Item) comboBox.getSelectedItem();
			if (item != null) {
				table.setValueAt(item.getId(), table.getSelectedRow(), 0);
			}
			updateRowNewCustomer(Long.parseLong(item.getId()));

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

	public void updateRowNewCustomer(long id) {
		customers = CustomerLogic.getInstance().getCustomers();
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
		tfCustomerNumber.setText(String.valueOf(table.getRowCount() - 1));
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

	private boolean findDuplicate(ArrayList<FlightTicket> ft) {
		Set<FlightTicket> set = new HashSet<>();
		for (int i = 0; i < ft.size(); i++) {
			if (set.contains(ft.get(i)))
				return true;
			else
				set.add(ft.get(i));
		}
		return false;
	}

	protected Boolean btnSaveOnClick(java.awt.event.ActionEvent evt) {
		table.getColumnModel().getColumn(0).getCellEditor().stopCellEditing();
		table.getColumnModel().getColumn(1).getCellEditor().stopCellEditing();
		table.getColumnModel().getColumn(2).getCellEditor().stopCellEditing();
		table.getColumnModel().getColumn(3).getCellEditor().stopCellEditing();
		int rowIndex = 0;
		while (rowIndex < table.getRowCount()) {
			if ((rowIndex) + 1 == table.getRowCount()) {
				if (table.getValueAt(rowIndex, 0).toString().length() <= 0)
					break;
			}
			rowIndex++;
		}
		Boolean success = true;
		ArrayList<FlightTicket> flightTickets = new ArrayList<>();
		try {
			rowIndex = 0;
			while (rowIndex < table.getRowCount()) {
				if (table.getValueAt(rowIndex, 0).toString().length() <= 0)
					break;
				flightTickets.add(new FlightTicket(orderID, rowIndex,
						Long.parseLong(table.getValueAt(rowIndex, 0).toString()),
						table.getValueAt(rowIndex, 2).toString(), table.getValueAt(rowIndex, 3).toString(), flightID));
				rowIndex++;
			}
			if (findDuplicate(flightTickets) == true) {
				msgbox("Remove Duplicate");
				return false;
			}
			flights = CustomerLogic.getInstance().getFlights();
			ArrayList<Seat> seats = CustomerLogic.getInstance().getAvailableSeatsOfFlight(flightID);
			for (Seat s : seats) {
				for (FlightTicket ft : flightTickets) {
					if (ft.getLine() == null && ft.getClassType().equals(s.getClassType())) {
						ft.setLine(s.getLine());
						ft.setSeatNumber(s.getColumn());
						break;
					}
				}
			}
			for (FlightTicket ft : flightTickets) {
				if (ft.getLine() == null) {
					msgbox("No room in flight");
					return false;
				}
			}
			CustomerLogic.getInstance().addOrder(orderID);
			for (FlightTicket ft : flightTickets) {
				CustomerLogic.getInstance().addFlightTicket(ft);
			}
//			success = ManagerLogic.getInstance().removeAttendantsInFlight(flightID);
//			for (AttendantsInFlight a : aInF) {
//				success = ManagerLogic.getInstance().addAttendantsInFlight(a.getFlightId(), a.getFlightAttendantId());
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (success) {
			msgbox("Order Added Successfully!");
			refreshDataButtons();
			initTableData();
			return true;
		} else {
			msgbox("Failure!");
			return false;
		}
			
		
	}

//	public boolean AreFlightsInSameDay(long flighID1, long flighID2) {
//		System.out.println(flighID1);
//		System.out.println(flighID2);
//		Flight flight1 = ManagerLogic.getInstance().getFlight(flighID1);
//		Flight flight2 = ManagerLogic.getInstance().getFlight(flighID2);
//		if(flight1.getDepartureDate().equals(flight2.getDepartureDate()))
//			return true;
//		return false;
//	}

}
