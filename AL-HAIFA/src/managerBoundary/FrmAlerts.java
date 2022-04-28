package managerBoundary;

import java.awt.Color;
import java.awt.EventQueue;

public class FrmAlerts extends ManagerRootLayout{
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmAlerts frame = new FrmAlerts();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public FrmAlerts() {
		mnAlerts.setBackground(null);
	}

}
