package model;

import lexer.expression.ExpressionEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Cell {

    /**
     * The formula for this cell
     */
    private String formula;//A1 + 5

    /**
     * The list of cells that this cell dependends on
     */
    private List<Cell> parents = new ArrayList<>();//[ A1 ]

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
        setFormula(formula);
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

    public void calculate () {
        //TODO some crazy shit
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
    public void setFormula(String formula) {
        this.formula = formula;

        Stack postFix = ExpressionEngine.getFormula(formula);
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
        while (n > 0) {
            cellId.append((char)((n % 26) + 64));
            n /= 26;
        }
        cellId = cellId.reverse();

        cellId.append(row);

        return cellId.toString();
    }

    public int getIndirection () {
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
