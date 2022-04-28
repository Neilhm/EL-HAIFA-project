package main;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import managerBoundary.FrmFlights;
import managerBoundary.ManagerFly;
import managerBoundary.ManagerRootLayout;
import managerBoundary.MyFrame;
import managerBoundary.flightsStatus;
import customerBoundary.CustomerFly;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame {
		private JLabel password1, label;
		private JTextField username;
		private JButton button;
		private JPasswordField Password;
		static LogIn frame;
		private JPanel panel;
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						frame = new LogIn();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		public LogIn() {
			try {
			    UIManager.setLookAndFeel(new NimbusLookAndFeel());
			    UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
			    UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
			    UIManager.put( "nimbusFocus", new Color(115,164,209) );
			    UIManager.put( "nimbusGreen", new Color(176,179,50) );
			    UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
			    UIManager.put( "nimbusOrange", new Color(191,98,4) );
			    UIManager.put( "nimbusRed", new Color(169,46,34) );
			    UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
			   UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
			    SwingUtilities.updateComponentTreeUI(this);
			} catch (UnsupportedLookAndFeelException exc) {
			    System.err.println("Nimbus: Unsupported Look and feel!");
			}
//			new FrmSignUp().setVisible(true);
//			JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
//			f1.dispose();
			// declaring a button variable
			button = new JButton("Login");
			// creating a JPanel class
			panel = new JPanel();
			panel.setLayout(null);
			// JFrame class
			setTitle("LOGIN PAGE");
			setLocation(new Point(500, 300));
			add(panel);
			setSize(new Dimension(400, 200));
			// Username label constructor
			label = new JLabel("Username");
			label.setBounds(100, 8, 70, 20);
			panel.add(label);
			// Username TextField constructor
			username = new JTextField();
			username.setBounds(100, 27, 193, 28);
			panel.add(username);
			// Password Label constructor
			password1 = new JLabel("Password");
			password1.setBounds(100, 55, 70, 20);
			panel.add(password1);
			// Password TextField
			Password = new JPasswordField();
			Password.setBounds(100, 75, 193, 28);
			panel.add(Password);
			// Button constructor
			button = new JButton("Login");
			button.setBounds(100, 110, 90, 25);
			button.setForeground(Color.WHITE);
			button.setBackground(Color.BLACK);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buttonPress(e);
				}
			});
			panel.add(button);
		}
		// Imlementing an action event listener class with conditional statement
		public void buttonPress(ActionEvent e) {
			if(username.getText().isEmpty() || Password.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Must Enter a UserName and Password ");
			}else {
			String Username = username.getText();
			String Password1 = Password.getText();

			if (Username.equals("employeeManager") && Password1.equals("1")) {
				JOptionPane.showMessageDialog(null, "Login Successful");
				new ManagerFly("employeeManager").setVisible(true);
				JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
				f1.dispose();
			} else if (Username.equals("flightManager") && Password1.equals("1")) {
				JOptionPane.showMessageDialog(null, "Login Successful");
				new ManagerFly("flightManager").setVisible(true);
				JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
				f1.dispose(); 
			} else if (Username.equals("interManager") && Password1.equals("1")) {
				JOptionPane.showMessageDialog(null, "Login Successful");
				new ManagerFly("interManager").setVisible(true);
				JFrame f1 = (JFrame) SwingUtilities.windowForComponent(panel);
				f1.dispose(); 
			}  else if (Username.equals("adminManager") && Password1.equals("1")) {
				JOptionPane.showMessageDialog(null, "Login Successful");
				new ManagerFly("adminManager").setVisible(true);
				JFrame f2 = (JFrame) SwingUtilities.windowForComponent(panel);
				f2.dispose();  
			} else if (Username.equals("customer") && Password1.equals("1")) {
				JOptionPane.showMessageDialog(null, "Login Successful");
				new CustomerFly().setVisible(true);
				JFrame f2 = (JFrame) SwingUtilities.windowForComponent(panel);
				f2.dispose();
			}else {
				JOptionPane.showMessageDialog(null, "Username or Password mismatch ");
			}
		}
		}
}

