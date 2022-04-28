package customerBoundary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

import customerControl.CustomerLogic;
import customerEntity.Flight;
import customerEntity.FlightDetails;

public class FrmOrderFlight extends JPanel{
	private static final long serialVersionUID = 1L;
	private static JTable table;
	private static DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	
	public FrmOrderFlight() {
		initialize();
	}
	private void initialize() {

		setBackground(SystemColor.inactiveCaptionBorder);
		setBounds(100, 100, 1020, 610);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Flights details");
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(350, 21, 268, 34);
		add(lblNewLabel);

		tableModel = new DefaultTableModel();
		String header[] = new String[] { "Flight ID","Departure Date", "Landing Date"};
		tableModel.setColumnIdentifiers(header);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		// panel.setBounds(10, 35, 400, 215);
		panel.setBounds(10, 143, 986, 419);
		add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(480, 300));
		scrollPane.setBounds(10, 11, 966, 483);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(tableModel);
		table.setRowHeight(20);

		JButton btnButton = new JButton("Go");
		btnButton.setBounds(430, 112, 89, 23);
		add(btnButton);

		JDateChooser dateTo = new JDateChooser();
		dateTo.setBounds(517, 81, 101, 20);
		add(dateTo);

		JDateChooser dateFrom = new JDateChooser();
		dateFrom.setBounds(378, 81, 101, 20);
		add(dateFrom);

		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(339, 81, 41, 20);
		add(lblFrom);

		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(489, 81, 32, 20);
		add(lblTo);
		btnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initTableData(dateFrom.getDate(), dateTo.getDate());
			}
		});

	}
	public void refreshComp(Date from, Date to) {
		initTableData(from, to);
		System.out.println(from.toString());
	}
	// This method set data to be presented on table according to recommendation id
		private void initTableData(Date from, Date to) {
			if(to.before(from)) {
				JOptionPane.showMessageDialog(this, "invalid input");
				return;
			}
			ArrayList<FlightDetails> flights = CustomerLogic.getInstance().getFlightsDetails();
			if(flights.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No flights in this range");
				return;
			}
			tableModel = (DefaultTableModel) (table.getModel());
			tableModel.setRowCount(0);
			// Following code gets all orders details for selected order id and updates
			// table rows
			for (FlightDetails f : flights) {
				Vector<Object> data = new Vector<Object>();
				data.add(f.getFlighID());
				data.add(f.getDepartureDate().toString());
				data.add(f.getLandingDate().toString());
				tableModel.addRow(data);
			}
			// -------------------
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(100);
			// First row is been focused and selected by default
			table.requestFocus();
			table.setEnabled(false);

			// Notifies all listeners that all cell values in the table's rows may have
			// changed.
			tableModel.fireTableDataChanged();
		}
}
