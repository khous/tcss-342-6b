package view;

import model.CellToken;
import model.Spreadsheet;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The man in the middle between the UI and the Models
 */


public class UI extends JPanel {
    JTable table;
    private final Timer myTimer;
    private boolean changeInProgress = false;
    private SoundPlayer mySounds;

    @SuppressWarnings("deprecation")
    public UI(final Spreadsheet sheet) {
        mySounds = new SoundPlayer();
        mySounds.play("sounds/CB.wav");
        myTimer = new Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                float r = (float) (Math.random() * 255);
                float g = (float) (Math.random() * 255);
                float b = (float) (Math.random() * 255);

                table.setBackground(Color.getHSBColor(r, g, b));

            }
        });
        myTimer.start();

        table = new JTable(26, 26);


        JScrollPane scroll = new JScrollPane(table);

        for (int i = 0; i < 26; i++) {
            System.out.println();
            for (int kd = 0; kd < 26; kd++) {
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
                String columnName = model.getColumnName(column);
                Object data = model.getValueAt(row, column);
                System.out.println(data);

                sheet.changeCellFormulaAndRecalculate(new CellToken(row, column + 1), data.toString());

                changeInProgress = true;
                model.setValueAt(sheet.getCell(row, column + 1).getValue(), row, column);
                changeInProgress = false;
                mySounds.play("sounds/L.wav");


            }
        });

        JFrame frame = new JFrame("342 SpreadSheet - Kevin, Kyle, Kyaw");
        frame.setSize(1000, 500);
        frame.setVisible(true);
        frame.setForeground(Color.CYAN);
        table.setBackground(Color.red);
        frame.add(scroll, BorderLayout.CENTER);
        frame.setCursor(Cursor.CROSSHAIR_CURSOR);
        repaint();
    }

    public void changeFormulaApp(int row, int column, int value) {
        table.setValueAt(value, row, column);
    }

    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.draw((Shape) table);

    }

    public void changefromconsole() {
        table.setValueAt(2500, 1, 1);

    }

    public void stopmusic() {
        mySounds.stop("sounds/CB.wav");

    }

    public void playmusic() {
        mySounds.play("sounds/CB.wav");

    }


}
