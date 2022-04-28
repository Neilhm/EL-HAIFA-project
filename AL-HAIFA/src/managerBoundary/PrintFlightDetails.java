package managerBoundary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import managerBoundary.*;
import managerControl.*;
import managerEntity.Airplane;
import managerEntity.Airport;
import managerEntity.BigFlightsReport;
import managerEntity.Flight;
import managerEntity.Seat;
import managerUtils.Status;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;

public class PrintFlightDetails extends JFrame {
	// extends JFrame

	private JTable table;
	private static DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrintFlightDetails frame = new PrintFlightDetails();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PrintFlightDetails() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setBackground(SystemColor.inactiveCaptionBorder);
		setBounds(100, 100, 1020, 610);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Only Large Flights details");
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(350, 21, 268, 34);
		getContentPane().add(lblNewLabel);

		tableModel = new DefaultTableModel();
		String header[] = new String[] { "Flight ID", "Departure Country", "Departure City", "Landing Country",
				"Landing City", "Departure Date", "Landing Date", "Status" };
		tableModel.setColumnIdentifiers(header);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		// panel.setBounds(10, 35, 400, 215);
		panel.setBounds(10, 143, 986, 419);
		getContentPane().add(panel);
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
		btnButton.setBounds(460, 109, 89, 23);
		getContentPane().add(btnButton);

		JDateChooser dateTo = new JDateChooser();
		dateTo.setBounds(556, 81, 130, 23);
		getContentPane().add(dateTo);

		JDateChooser dateFrom = new JDateChooser();
		dateFrom.setBounds(349, 81, 130, 23);
		getContentPane().add(dateFrom);

		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(304, 81, 35, 23);
		getContentPane().add(lblFrom);

		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(524, 81, 25, 23);
		getContentPane().add(lblTo);
		btnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initTableData(dateFrom.getDate(), dateTo.getDate());
			}
		});

	}

	// This method set data to be presented on table according to recommendation id
	private void initTableData(Date from, Date to) {
		if(to.before(from)) {
			JOptionPane.showMessageDialog(this, "invalid input");
			return;
		}
		ArrayList<BigFlightsReport> flights = ManagerLogic.getInstance().getFlightsForReport(from, to);
		if(flights.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No flights in this range");
			return;
		}
		tableModel = (DefaultTableModel) (table.getModel());
		tableModel.setRowCount(0);
		// Following code gets all orders details for selected order id and updates
		// table rows
		for (BigFlightsReport f : flights) {
			Vector<Object> data = new Vector<Object>();
			data.add(f.getFlighID());
			data.add(f.getDepartureCountry());
			data.add(f.getDeparturecity());
			data.add(f.getLandingCountry());
			data.add(f.getLandingecity());
			data.add(changeFormat(f.getDepartureDate()));
			data.add(changeFormat(f.getLandingDate()));
			if (f.getStatus() == null) {
			}
			data.add((f.getStatus() == null) ? "No Status" : f.getStatus().toString());
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
}
