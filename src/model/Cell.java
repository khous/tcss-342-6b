package model;

import lexer.expression.ExpressionEngine;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Cell {

    /**
     * The formula for this cell
     */
    private String formula;//A1 + 5

    /**
     * The list of cells that this cell dependends on
     */
    private List<Cell> parents = new ArrayList<>();//[ A1 ]

    private ArrayDeque<Token> postFixExpression;

    //ExpressionTree
    /*
                 OpToken +
    CellToken A1           LiteralToken 5
     */

    /**
     * The computed value last assigned to this cell
     */
    private int value;
    private int row;
    private int column;

    /**
     * Initialize a new cell with the given formula
     * @param formula The formula
     */
    public Cell (String formula, int row, int column) {
        this.formula = formula;

        setRow(row);
        setColumn(column);
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    /**
     * This must be able to assign a value to itself when called, if it is unable, then something
     * broke in the graph generation.
     */
    public void calculate (Spreadsheet sheet) throws OperationNotSupportedException {
        //
        //TODO some crazy shit
        if (postFixExpression == null) return;

        this.value = ExpressionEngine.calculate(postFixExpression.clone(), sheet);
    }

    public String getFormula() {
        return formula;
    }

    public int getValue() {
        return value;
    }

    public void addParent (Cell parent) {
        parents.add(parent);
    }

    //TODO rebuild expression tree
    public void setFormula(String formula, Spreadsheet sheet) {
        this.formula = formula;

        postFixExpression = ExpressionEngine.getFormula(formula, sheet);

        parents.clear();

        for (Token t : postFixExpression) {
            if (t instanceof CellToken) {
                //add dependency to parents
                Cell c = sheet.getCell((CellToken) t);

                if (!parents.contains(c))
                    parents.add(c);
            } else if (t instanceof FunctionToken) {
                FunctionToken ft = (FunctionToken) t;

                parents.addAll(ft.getReferencedCells());
            }
        }
    }

    public List<Cell> getParents() {
        return parents;
    }

    public void setParents(List<Cell> parents) {
        this.parents = parents;
    }

    public String getCellId () {
        if (row < 0 || column < 0) return "";

        int n = column;
        StringBuilder cellId = new StringBuilder();

        //Convert the column to base26
        do {
            cellId.append((char)((n % 26) + 64));
            n /= 26;
        } while (n > 0);
        
        cellId = cellId.reverse();

        cellId.append(row);

        return cellId.toString();
    }

    public int getIndegree() {
        return parents.size();
    }

    public Cell clone () {
        return null;//// TODO: 2/23/16
    }

    public boolean equals (Object o) {
        if (this == o) return true;

        if (o instanceof Cell) {
            Cell cell = (Cell) o;
            return cell.row == row && cell.column == column && cell.formula.equals(formula);
        }

        return false;
    }
}
