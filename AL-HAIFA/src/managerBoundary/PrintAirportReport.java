package managerBoundary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import managerControl.ManagerLogic;
import managerEntity.AirportPastMonthReport;
import managerEntity.BigFlightsReport;

import javax.swing.JTextField;

public class PrintAirportReport extends JFrame {
	private JTable table;
	private JPanel contentPane;
	private static DefaultTableModel tableModel;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrintAirportReport frame = new PrintAirportReport();
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
	public PrintAirportReport() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setBackground(SystemColor.inactiveCaptionBorder);
		// setBounds(100, 100, 450, 300);
		setBounds(100, 100, 1020, 610);
		getContentPane().setLayout(null);
		LocalDate now = LocalDate.now();
		LocalDate earlier = now.minusMonths(1);
		JLabel lblNewLabel = new JLabel("Report Of Flights By Destination Country In " + earlier.getMonth());
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(265, 32, 567, 34);
		getContentPane().add(lblNewLabel);

		tableModel = new DefaultTableModel();
		String header[] = new String[] { "Country", "Percentage" };
		tableModel.setColumnIdentifiers(header);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		// panel.setBounds(10, 35, 400, 215);
		panel.setBounds(10, 77, 986, 433);
		getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(480, 300));
		scrollPane.setBounds(10, 11, 966, 405);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(tableModel);
		table.setRowHeight(20);

		JLabel lblNewLabel_2 = new JLabel("Total Flights:");
		lblNewLabel_2.setForeground(new Color(0, 0, 128));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(77, 509, 165, 34);
		getContentPane().add(lblNewLabel_2);
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		textField = new JTextField();
		textField.setBounds(212, 515, 37, 31);
		textField.setColumns(10);
		textField.setBackground(Color.DARK_GRAY);
		textField.setFont(font1);
		getContentPane().add(textField);
		initTableData();

	}

	// This method set data to be presented on table according to recommendation id
	private void initTableData() {
		ArrayList<AirportPastMonthReport> airports = ManagerLogic.getInstance().getAirportPastMonthReport();
		// Following code clear table (used while browsing between orders)

		tableModel = (DefaultTableModel) (table.getModel());
		tableModel.setRowCount(0);

		// Following code gets all orders details for selected order id and updates
		// table rows
		for (AirportPastMonthReport f : airports) {
			Vector<Object> data = new Vector<Object>();
			data.add(f.getCountry());
			data.add((Math.floor(f.getPercentage() * 100)/100) + "%");
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
		System.out.println(airports);
		if (airports.size() > 0)
			textField.setText(String.valueOf(airports.get(0).getTotal()));
		else
			textField.setText("0");
		textField.setEnabled(false);
	}
}
