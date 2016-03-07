package view;

import model.Cell;
import model.CellToken;
import model.Spreadsheet;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.tree.ExpandVetoException;
import java.awt.*;

/**
 * The man in the middle between the UI and the Models
 */


public class UI extends JPanel {
    JTable table;
//    private final Timer myTimer;
    private boolean changeInProgress = false;
    private SoundPlayer mySounds;

    @SuppressWarnings("deprecation")
    public UI(final Spreadsheet sheet) {
//        mySounds = new SoundPlayer();
//        mySounds.play("sounds/CB.wav");
//        myTimer = new Timer(2000, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                float r = (float) (Math.random() * 255);
//                float g = (float) (Math.random() * 255);
//                float b = (float) (Math.random() * 255);
//
//                table.setBackground(Color.getHSBColor(r, g, b));
//
//            }
//        });
//        myTimer.start();

        table = new JTable(Spreadsheet.DEFAULT_DIMENSION, Spreadsheet.DEFAULT_DIMENSION);


        JScrollPane scroll = new JScrollPane(table);

        for (int i = 0; i < Spreadsheet.DEFAULT_DIMENSION; i++) {
            System.out.println();
            for (int kd = 0; kd < Spreadsheet.DEFAULT_DIMENSION; kd++) {
                if (sheet.getCell(i, kd).getValue() == 0) {

                } else {
                    table.setValueAt(((sheet.getCell(i, kd).getValue())), i, kd);
                }
            }
        }


        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (changeInProgress) return;

                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                Object data = model.getValueAt(row, column);
                System.out.println(data);

                changeInProgress = true;
                try {
                    sheet.changeCellFormulaAndRecalculate(new CellToken(row, column), data.toString());
                } catch (Exception error) {
                    error.printStackTrace();
                    sheet.changeCellFormulaAndRecalculate(new CellToken(row, column), "");//Set to a known good value
                }
                Cell[][] ss = sheet.getMySpreadSheet();
                for (Cell[] rowArray : ss)
                    for (Cell cell : rowArray) {
                        if (cell != null)
                            model.setValueAt(cell.getValue(), cell.getRow(), cell.getColumn());
                    }
                changeInProgress = false;
            }
        });

        JFrame frame = new JFrame("342 SpreadSheet - Kevin, Kyle, Kyaw");
        frame.setSize(1000, 500);
        frame.setVisible(true);
        frame.setForeground(Color.CYAN);
        table.setBackground(Color.WHITE);
        frame.add(scroll, BorderLayout.CENTER);
        frame.setCursor(Cursor.CROSSHAIR_CURSOR);
        repaint();
    }

    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.draw((Shape) table);

    }
}
