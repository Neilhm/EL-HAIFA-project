package managerBoundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.event.MenuListener;

import main.LogIn;

import javax.swing.event.MenuEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.UIManager;

public class ManagerRootLayout extends JFrame {
	private JPanel contentPane;
	static ManagerRootLayout frame;
	private JMenu mnHome;
	protected JMenu mnAlerts;
	protected static String user;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ManagerRootLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ManagerRootLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 521);
		createMenuBar();
	}
	
	 public void createMenuBar() {
	        JMenuBar menuBar = new JMenuBar();
	        ImageIcon icon = new ImageIcon("exit.png");
	        JMenu file = new JMenu("File");

	        file.setMnemonic(KeyEvent.VK_F);

	        JMenuItem eMenuItem = new JMenuItem("Exit", icon);
	        eMenuItem.setMnemonic(KeyEvent.VK_E);
	        eMenuItem.setToolTipText("Exit application");
	        eMenuItem.addActionListener((ActionEvent event) -> {
	            System.exit(0);
	        });
	        file.add(eMenuItem);
	        
	        JMenuItem eMenuItem2 = new JMenuItem("LogOut", icon);
	        eMenuItem2.setMnemonic(KeyEvent.VK_E);
	        eMenuItem2.addActionListener((ActionEvent event) -> {
	        	new LogIn().setVisible(true);
	        	JFrame f1 = (JFrame) SwingUtilities.windowForComponent(menuBar);
				f1.dispose();
	        });
	        file.add(eMenuItem2);
	        file.add(eMenuItem);
	        menuBar.add(file);
	    
			
	        mnHome = new JMenu("Home");	        
			menuBar.add(mnHome);
			mnHome.addMenuListener(new MenuListener() {
				public void menuCanceled(MenuEvent e) {
				}
				public void menuDeselected(MenuEvent e) {
				}
				public void menuSelected(MenuEvent e) {
					new ManagerFly(user).setVisible(true);
					JFrame f1 = (JFrame) SwingUtilities.windowForComponent(menuBar);
					f1.dispose();
				}
			});
			mnAlerts = new JMenu("Alerts");
			//mnalerts.setBackground(Color.red);
			mnAlerts.setOpaque(true);
			menuBar.add(mnAlerts);
			mnAlerts.addMenuListener(new MenuListener() {
				public void menuCanceled(MenuEvent e) {
				}
				public void menuDeselected(MenuEvent e) {
				}
				public void menuSelected(MenuEvent e) {
					new FrmAlerts().setVisible(true);
					JFrame f1 = (JFrame) SwingUtilities.windowForComponent(menuBar);
					f1.dispose();
				}
			});
			setJMenuBar(menuBar);
	    }
	 
	 private void changePanel(JPanel panel) {
		    getContentPane().removeAll();
			System.out.println("in");
		    getContentPane().add(panel);
			System.out.println("in");
		    getContentPane().doLayout();
		    update(getGraphics());
		}
	 
	 public void updateMenuSelectionHome() {
		 mnHome.setOpaque(true);
		 mnHome.setBackground(SystemColor.activeCaption);
	 
	 }
	 public void msgbox(String s){
	  	   JOptionPane.showMessageDialog(this, s);
	  	}
	 public void updateMenuSelectionFlights() { 
	 }
}
