package controller;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import model.Spreadsheet;

/**
 * The man in the middle between the UI and the Models
 */


public class App extends JPanel{
	JTable table;
	public App (Spreadsheet k){
		TableModel spread = new TableModel() {
			
			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeTableModelListener(TableModelListener l) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return 3;
			}
			
			@Override
			public String getColumnName(int columnIndex) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return 3;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addTableModelListener(TableModelListener l) {
				// TODO Auto-generated method stub
				
			}
		};
		
		

		
		table = new JTable(26,26); 
		 JScrollPane scroll = new JScrollPane(table);
		JLabel table2 = new JLabel();
		
		 
		 
		 table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int column = e.getColumn();
				TableModel model = (TableModel)e.getSource();
				String columnName = model.getColumnName(column);
				Object data = model.getValueAt(row, column);
				System.out.println(data);
				 //table.setValueAt(e.getSource(),0,0);
				
			}
		    });

			for(int i=0;i<26;i++){
	    		
	    		System.out.println();
	    		for(int kd=0;kd<26;kd++){
	    			if(k.getCell(i, kd)==null){
	    				table.setValueAt(0, i, kd);
	    			}else{
	    				table.setValueAt(((k.getCell(i, kd).getValue())), i,kd);
	    			}
	    			
	    		}
	    	}
		 
		 
		JFrame frame = new JFrame("342 SpreadSheet - Kevin, Kyle, Kyaw");
		frame.add(scroll, BorderLayout.CENTER);
		frame.add(table2,BorderLayout.WEST);
		frame.setSize(1000, 500);
		frame.setVisible(true);
		TableCellEditor c = table.getCellEditor();
		if(c!=null){
		System.out.println(c.getCellEditorValue());	
		}
				repaint();
	}
	

	
	 protected void paintComponent(final Graphics theGraphics) {
	        super.paintComponent(theGraphics);
	        final Graphics2D g2d = (Graphics2D) theGraphics;
	        g2d.draw((Shape) table);
	        
	 }
	 
	 public void changefromconsole(){
		 table.setValueAt(2500, 1, 1);
		 
	 }
	

}
