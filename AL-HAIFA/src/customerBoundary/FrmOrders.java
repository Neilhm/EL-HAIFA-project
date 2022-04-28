package customerBoundary;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import com.toedter.calendar.JDateChooser;

import customerBoundary.TableAllowColumnSelection.PremiereLeagueTableModel;
import customerControl.CustomerLogic;
import customerEntity.Airport;
import customerEntity.Flight;
import customerEntity.FlightDetails;
import customerEntity.Order;
import managerBoundary.Item;

import java.awt.Choice;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Button;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class FrmOrders extends CustomerRootLayout{
	private JPanel contentPane = new JPanel();
	private JPanel pnlOrderDetails = new JPanel();
	private JPanel pnlFliDetails = new JPanel();
	private JPanel pnlCusDetails = new JPanel();
	private JTextField tfOrderID = new JTextField();
	private JTextField tfPaymentMethod = new JTextField();;
	private JLabel lblBookingDate = new JLabel("Booking Date:");
	private JTextField tfBookingDate = new JTextField();
	private JLabel lblOrderD = new JLabel("Order ID:");
	private JLabel lblPaymentMethod = new JLabel("Payment Method:");
	private JComboBox cbPaymentMethod = new JComboBox();
	private JLabel lblFromPort = new JLabel("From:");
	private JComboBox cbDeparture = new JComboBox();
	private JLabel lblToPort = new JLabel("To: ");
	private JComboBox cbLanding = new JComboBox();
	private JLabel lblFromDate = new JLabel("From:");
	private JLabel lblToDate = new JLabel("To: ");
	private JDateChooser dtcFromDate = new JDateChooser();
	private JDateChooser dtcToDate = new JDateChooser();
	private ArrayList<Order> ordersArray;
	private Integer currentOrder;
	private JButton btnSearchFlight = new JButton("Search");
	private boolean inAddMode;
	private ArrayList<Airport> Airports;
	private PanelFlightOrder pnlOrderFlight;
	private FrmCustomerOrderTable pnlCustomerTable;
	private JTextField tfFlightID;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new FrmOrders();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public FrmOrders() {
		initComponents();
		fetchAndRefresh();
		createEvents();
	}
	private void initComponents () {
		initSelectDepartureAirport();
		initSelectLandingAirport();
		initSelectPaymentMethod();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(SystemColor.activeCaption);
		
		pnlOrderDetails.setBounds(55, 11, 215, 202);
		pnlOrderDetails.setBorder(null);
		pnlOrderDetails.setLayout(null);
		contentPane.add(pnlOrderDetails);
		
		
		lblOrderD.setBounds(10, 11, 64, 14);
		pnlOrderDetails.add(lblOrderD);
		
		tfOrderID.setText("(NEW)");
		tfOrderID.setEditable(false);
		tfOrderID.setColumns(10);
		tfOrderID.setBounds(79, 8, 64, 23);
		pnlOrderDetails.add(tfOrderID);
		
		lblPaymentMethod.setBounds(10, 64, 111, 14);
		pnlOrderDetails.add(lblPaymentMethod);
		
		tfPaymentMethod.setText((String) null);
		tfPaymentMethod.setEditable(false);
		tfPaymentMethod.setColumns(10);
		tfPaymentMethod.setBounds(10, 89, 79, 23);
		pnlOrderDetails.add(tfPaymentMethod);
		
		cbPaymentMethod.setBounds(91, 90, 114, 20);
		pnlOrderDetails.add(cbPaymentMethod);
		
		lblBookingDate.setBounds(10, 137, 92, 26);
		pnlOrderDetails.add(lblBookingDate);
		
		tfBookingDate.setText((String) null);
		tfBookingDate.setEditable(false);
		tfBookingDate.setColumns(10);
		tfBookingDate.setBounds(99, 139, 106, 23);
		
		pnlOrderDetails.add(tfBookingDate);
		
		pnlFliDetails.setBounds(280, 11, 395, 202);
		pnlFliDetails.setBorder(null);
		pnlFliDetails.setLayout(null);
		contentPane.add(pnlFliDetails);
		
		lblFromDate.setBounds(10, 11, 42, 21);
		pnlFliDetails.add(lblFromDate);
		
		dtcFromDate.setBounds(55, 11, 129, 28);
		pnlFliDetails.add(dtcFromDate);
		
		lblToDate.setBounds(211, 11, 33, 21);
		pnlFliDetails.add(lblToDate);
		

		dtcToDate.setBounds(242, 11, 129, 28);
		pnlFliDetails.add(dtcToDate);
		
		lblFromPort.setBounds(10, 50, 42, 21);
		pnlFliDetails.add(lblFromPort);
		
		cbDeparture.setBounds(55, 49, 129, 22);
		pnlFliDetails.add(cbDeparture);
		
		lblToPort.setBounds(211, 50, 33, 21);
		pnlFliDetails.add(lblToPort);
		
		cbLanding.setBounds(242, 49, 129, 22);
		pnlFliDetails.add(cbLanding);
		
		btnSearchFlight.setBounds(135, 82, 89, 23);
		pnlFliDetails.add(btnSearchFlight);
		
		pnlOrderFlight = new PanelFlightOrder();
		pnlOrderFlight.setBounds(0, 105, 385, 97);
		pnlFliDetails.add(pnlOrderFlight);
		
		pnlCusDetails.setBounds(55, 218, 620, 233);
		pnlCusDetails.setBorder(null);
		pnlCusDetails.setLayout(null);
		contentPane.add(pnlCusDetails);
		
		tfFlightID = new JTextField();
		tfFlightID.setBounds(10, 11, 129, 20);
		pnlCusDetails.add(tfFlightID);
		tfFlightID.setColumns(10);
		tfFlightID.setEditable(false);
		
		pnlCustomerTable = new FrmCustomerOrderTable();
		pnlCustomerTable.setBounds(10, 42, 600, 180);
		pnlCusDetails.add(pnlCustomerTable);
		
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(158, 66, 2, 2);
//		pnlCusDetails.add(scrollPane);

	}
	
	
	private void fetchAndRefresh() {
		ordersArray = CustomerLogic.getInstance().getOrders();
		currentOrder = (!ordersArray.isEmpty()) ? ordersArray.size()+1 : null;
		inAddMode = (ordersArray == null);
		refreshControls();
	}
	
	private void refreshControls() {
		//refreshNavigation();
		refreshOrderFields();
//		refreshDataButtons();
//		refreshCancelFlight();
	}
	
	private void createEvents() {
		cbPaymentMethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshPaymentOnChange(e);
			}
		});
		cbDeparture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cbLanding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnSearchFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSearchFlightOnClick(e);
				// fetchAndRefresh();
			}
		});
		
		pnlOrderFlight.getTable().addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent e) {
				saveSelectedRow();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		pnlCustomerTable.btnSaveFlightDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean success = pnlCustomerTable.btnSaveOnClick(e);
				if(success) {
					LocalDate localDate = LocalDate.now();
					Date now = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
					CustomerLogic.getInstance().editOrder(Long.parseLong(tfOrderID.getText()),
							now, tfPaymentMethod.getText());
					fetchAndRefresh();
				}else {
					CustomerLogic.getInstance().deleteOrder(Long.parseLong(tfOrderID.getText()));
				}
			}
		});
	}
	private void refreshPaymentOnChange(ActionEvent e) {
		JComboBox comboBox = (JComboBox) e.getSource();
		if (comboBox.getSelectedItem() != "select") {
			Item item = (Item) comboBox.getSelectedItem();
			if (item != null) {
				tfPaymentMethod.setText(item.getId());
			}
		}
	}
	private void saveSelectedRow() {
    	int column = 0;
    	int row = pnlOrderFlight.getTable().getSelectedRow();
    	String value = pnlOrderFlight.getTable().getModel().getValueAt(row, column).toString();
    	selectedFlight(value);
    	System.out.println(tfOrderID);
    	pnlCustomerTable.refreshComp(value, Long.parseLong(tfOrderID.getText()));
	}
	
	private void refreshOrderFields() {
		tfOrderID.setText(("" + currentOrder));
		tfPaymentMethod.setText(null);
		tfBookingDate.setText(null);
		refreshPaymentMethodComBoxSelectedByID();
	}
	
	private void refreshPaymentMethodComBoxSelectedByID() {
			cbPaymentMethod.getModel().setSelectedItem("select");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSelectPaymentMethod() {
		Vector model = new Vector();
		model.addElement(new Item("credit","credit"));
		model.addElement(new Item("paypal","paypal"));
		model.addElement(new Item("bankTransfer","bankTransfer"));
		cbPaymentMethod = new JComboBox(model);
		cbPaymentMethod.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSelectDepartureAirport() {
		Airports = CustomerLogic.getInstance().getAirports();
		Vector model = new Vector();
		int i = 0;
		while (i < Airports.size()) {
			model.addElement(new Item(Long.toString(Airports.get(i).getAirportID()), Airports.get(i).toString()));
			;
			i++;
		}
		cbDeparture = new JComboBox(model);
		cbDeparture.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSelectLandingAirport() {
		Airports = CustomerLogic.getInstance().getAirports();
		Vector model = new Vector();
		int i = 0;
		while (i < Airports.size()) {
			model.addElement(new Item(Long.toString(Airports.get(i).getAirportID()), Airports.get(i).toString()));
			;
			i++;
		}
		cbLanding = new JComboBox(model);
		cbLanding.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
	}
	
	@SuppressWarnings("unused")
	private void btnSearchFlightOnClick(java.awt.event.ActionEvent evt) {
		Date fromDate = dtcFromDate.getDate();
		Date toDate = dtcToDate.getDate();
		long fromAirport = Long.parseLong(((Item)cbDeparture.getSelectedItem()).getId());
		long toAirport = Long.parseLong(((Item)cbLanding.getSelectedItem()).getId());
		pnlOrderFlight.refreshComp(fromDate, toDate, fromAirport, toAirport);
	}
	
	
	
	@SuppressWarnings("unused")
	public void selectedFlight(String flighID) {
		tfFlightID.setText(flighID);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSelectFlight(ArrayList<Flight> flights) {
		Vector model = new Vector();
		int i = 0;
		while (i < flights.size()) {
			model.addElement(new Item(flights.get(i).getFlightID(), flights.get(i).toString()));
			;
			i++;
		}
//		cbFlightTime = new JComboBox(model);
//		cbFlightTime.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
//		cbFlightTime.setVisible(true);
	}
}
