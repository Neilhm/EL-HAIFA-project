package managerBoundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;

import managerControl.ManagerLogic;
import managerEntity.AirportPastMonthReport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class ManagerFly extends ManagerRootLayout{
	private JPanel contentPane;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerFly frame = new ManagerFly(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ManagerFly(String string) {
		user = string;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		updateMenuSelectionHome();
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(ManagerFly.class.getResource("/managerBoundary/images/logo.png")));
		
		JButton btnReport = new JButton("Print Flight Details");
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		
		JButton btnReport_1 = new JButton("Print Airport Details");
		btnReport_1.setForeground(Color.BLACK);
		btnReport_1.setBackground(SystemColor.activeCaption);
		
		JButton btnJson = new JButton("Send json");
		btnJson.setForeground(Color.BLACK);
		btnJson.setBackground(SystemColor.activeCaption);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLogo)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btnReport, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnReport_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnJson, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))
					.addGap(13))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnJson)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReport)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReport_1))
						.addComponent(lblLogo))
					.addContainerGap(45, Short.MAX_VALUE))
		);
		
		JButton btnFlights = new JButton("Flights");
		btnFlights.setPreferredSize(new Dimension(100, 25));
		btnFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrmFlights(user).setVisible(true);
				JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
				f1.dispose();
			}
		});
		btnReport.setForeground(Color.BLACK);
		btnReport.setBackground(SystemColor.activeCaption);
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PrintFlightDetails frame = new PrintFlightDetails();
				frame.setVisible(true);
				
			}
		});
		btnReport_1.setForeground(Color.BLACK);
		btnReport_1.setBackground(SystemColor.activeCaption);
		btnReport_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PrintAirportReport frame = new PrintAirportReport();
				frame.setVisible(true);
				
			}
		});
		
		btnJson.setForeground(Color.BLACK);
		btnJson.setBackground(SystemColor.activeCaption);
		btnJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ManagerLogic.getInstance().SendJson();
					msgbox("Json Is Send");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msgbox("Something Wrong Happend");
				}
				
			}
		});
		JButton btnEmployees = new JButton("Employees");
		btnEmployees.setPreferredSize(new Dimension(100, 25));
		
		btnEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				flightsStatus frame = new flightsStatus();
				frame.setVisible(true);
			}
		});
		
		JButton btnAirports = new JButton("Airports");
		btnAirports.setPreferredSize(new Dimension(100, 25));
		
		JButton btnStatus = new JButton("Status");
		btnStatus.setPreferredSize(new Dimension(100, 25));
		btnFlights.setEnabled(false);
		btnJson.setEnabled(false);
		btnReport_1.setEnabled(false);
		btnReport.setEnabled(false);
		btnStatus.setEnabled(false);
		btnJson.setEnabled(false);
		btnEmployees.setEnabled(false);
		btnAirports.setEnabled(false);
		if(user.equals("interManager")) {
			btnStatus.setEnabled(true);
			
		}else if(user.equals("flightManager")) {
			btnFlights.setEnabled(true);
			btnJson.setEnabled(true);
			btnReport.setEnabled(true);
		} else if(user.equals("employeeManager")) {
			btnEmployees.setEnabled(true);
			btnFlights.setEnabled(true);
		}
		else if(user.equals("adminManager")){
			btnFlights.setEnabled(true);
			btnJson.setEnabled(true);
			btnReport_1.setEnabled(true);
			btnReport.setEnabled(true);
			btnJson.setEnabled(true);
			btnEmployees.setEnabled(true);
			btnAirports.setEnabled(true);
		}
		btnStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				flightsStatus frame = new flightsStatus();
				frame.setVisible(true);
			}
		});
		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel.add(btnFlights);
		panel.add(btnEmployees);
		panel.add(btnAirports);
		panel.add(btnStatus);
		panel.add(btnAirports);

		contentPane.setLayout(gl_contentPane);
	}
}
