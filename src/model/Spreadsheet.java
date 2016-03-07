package model;


import model.graph.CellDependencyGraph;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayDeque;

public class Spreadsheet {
    public static final int DEFAULT_DIMENSION = 26;
    private Cell[][] mySpreadSheet;

    public Spreadsheet () {
        this(DEFAULT_DIMENSION);
    }

    public Spreadsheet (int size) {
        mySpreadSheet = new Cell[size][size];
    }

    public Cell[][] getMySpreadSheet () { return mySpreadSheet; }

    public Cell getCell (int row, int column) {
        //TODO bound checking
        Cell c = mySpreadSheet[row][column];

        if (c == null) {
            c = new Cell("", row, column);
            mySpreadSheet[row][column] = c;
        }

        return c;
    }

    public void setCell (int row, int column, Cell cell) {
        //TODO encapsulation and bound checking
        cell.setFormula(cell.getFormula(), this);
        mySpreadSheet[row][column] = cell;
    }

    public int getNumberOfRows () { return mySpreadSheet.length; }
    public int getNumberOfColumns () { return mySpreadSheet[0].length; }

    public String printValues () {
        StringBuilder sb = new StringBuilder();

        for (Cell[] row : mySpreadSheet) {
            for (Cell c : row) {
                if (c != null) {
                    sb.append(c.getValue()).append(", ");
                }
            }
            System.out.println();
        }

        return sb.toString();
    }

    public String getCellFormula (CellToken ct) {
        //TODO bound checking
        Cell c = mySpreadSheet[ct.getRow()][ct.getColumn()];
        return c.getFormula();
    }

    public String printAllFormulas () {
        StringBuilder sb = new StringBuilder();

        for (Cell[] row : mySpreadSheet) {
            for (Cell c : row) {
                if (c != null) {
                    sb.append(c.getFormula()).append(", ");
                }
            }
            System.out.println();
        }

        return sb.toString();
    }

    public Cell getCell (CellToken ct) {
        Cell c = getCell(ct.getRow(), ct.getColumn());

        if (c == null)
            setCell(ct.getRow(), ct.getColumn(), c);

        return c;
    }

    public void changeCellFormulaAndRecalculate(CellToken ct, String formula) {
        Cell c = getCell(ct.getRow(), ct.getColumn());

        c.setFormula(formula, this);//this needs to rediscover its parents
//        recalculate(c);

        /*
         * Unfortunately I realized to late that I only resolved the upward looking dependency chain, changing values
         * upstream in this chain still requires a full recompute of the spreadsheet
         */
        for (Cell[] row : mySpreadSheet)
            for (Cell cell : row) {
                if (cell != null)
                    recalculate(cell);
            }

    }

    private void recalculate (Cell c) {
        //discover graph
        CellDependencyGraph graph = new CellDependencyGraph();
        graph.buildGraph(c);
        ArrayDeque<Cell> sortedCells = graph.getTopologicalOrder();

        while (!sortedCells.isEmpty()) {
            try {
                sortedCells.remove().calculate(this);
            } catch (OperationNotSupportedException e) {
                System.err.println("unsupported operation caught");
            }
        }
    }
}
