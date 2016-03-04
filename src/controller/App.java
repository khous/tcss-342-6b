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

import model.Cell;
import model.CellToken;
import model.Spreadsheet;

/**
 * The man in the middle between the UI and the Models
 */


public class App extends JPanel{
	JTable table;
	public App (final Spreadsheet sheet){
		table = new JTable(26,26);
		JScrollPane scroll = new JScrollPane(table);
		JLabel table2 = new JLabel();

		for(int i=0;i<26;i++){
			System.out.println();
			for(int kd=0;kd<26;kd++){
				if(sheet.getCell(i, kd)==null){
					table.setValueAt(0, i, kd);
				}else{
					table.setValueAt(((sheet.getCell(i, kd).getValue())), i,kd);
				}
			}
		}

		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int column = e.getColumn();
				TableModel model = (TableModel)e.getSource();
				String columnName = model.getColumnName(column);
				Object data = model.getValueAt(row, column);
				System.out.println(data);
				sheet.changeCellFormulaAndRecalculate(new CellToken(row, column), data.toString());
			}
		});

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
	
	public void changeFormulaApp (int row, int column, int value) {
		table.setValueAt(value, row, column);
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
