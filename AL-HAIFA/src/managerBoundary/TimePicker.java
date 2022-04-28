package managerBoundary;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

public class TimePicker {
	static JComboBox comboBox = new JComboBox();
  public static void main(String args[]) throws ParseException {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setLayout(new FlowLayout());
    SpinnerModel model1 = new SpinnerDateModel();
    initSelectTime();
    comboBox.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		
    	}
    });
    f.getContentPane().add(comboBox);
    JSpinner spinner = new JSpinner(model1);
    f.getContentPane().add(spinner);
    
    
    f.setSize(592, 203);
    f.setVisible(true);
    

  }
  @SuppressWarnings({ "rawtypes", "unchecked" })
	private static void initSelectTime() {
	  ArrayList<String> timePicker = new ArrayList<>();
	    for(int i = 0 ; i < 24 ; i++) {
	    	timePicker.add(i+":"+"00");
	    	timePicker.add(i+":"+"30");
	    }
	    System.out.println(timePicker);
	    Vector model = new Vector();
		int i=0;
		while (i<timePicker.size()){
			model.addElement( new Item(String.valueOf(i), timePicker.get(i)));
			;i++;}
		comboBox = new JComboBox( model );
		comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
  }
}