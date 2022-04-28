package customerBoundary;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TableAllowColumnSelection extends JPanel {
	private JTable table;
	private JTextField textField;
    public TableAllowColumnSelection() {
        initializePanel();
    }

    public static void showFrame() {
        JPanel panel = new TableAllowColumnSelection();
        panel.setOpaque(true);

        JFrame frame = new JFrame("JTable Column Selection");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TableAllowColumnSelection::showFrame);
    }

    private void initializePanel() {
        this.setPreferredSize(new Dimension(500, 500));

        table = new JTable(new PremiereLeagueTableModel());
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        
        textField = new JTextField();
        add(textField);
        textField.setColumns(10);
        textField.setEditable(false);
        // setting to false disallow row selection in the table model
        table.setRowSelectionAllowed(true);
        // setting to true allow column selection in the table model
        table.setColumnSelectionAllowed(false);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 500, 500);
        this.add(pane);
        table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				printRow();
				
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
        System.out.println(table.getSelectedRow());
    }
    
    private void printRow() {
    	int column = 0;
    	int row = table.getSelectedRow();
    	String value = table.getModel().getValueAt(row, column).toString();
    	textField.setText(value);
    	
    }

    static class PremiereLeagueTableModel extends AbstractTableModel {
        // TableModel's column names
        private final String[] columnNames = {
                "CLUB", "MP", "W", "D", "L", "GF", "GA", "GD", "PTS"
        };

        // TableModel's data
        private final Object[][] data = {
                {"Chelsea", 8, 6, 1, 1, 16, 3, 13, 19},
                {"Liverpool", 8, 5, 3, 0, 22, 6, 16, 18},
                {"Manchester City", 8, 5, 2, 1, 16, 3, 13, 17},
                {"Brighton", 8, 4, 3, 1, 8, 5, 3, 15},
                {"Tottenham", 8, 5, 0, 3, 9, 12, -3, 15}
        };

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
    }
}