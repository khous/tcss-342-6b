package model;

import lexer.expression.ExpressionEngine;

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
    private List<Cell> parents;//[ A1 ]

    //ExpressionTree
    /*
                 OpToken +
    CellToken A1           LiteralToken 5
     */

    /**
     * The computed value last assigned to this cell
     */
    private int value;

    /**
     * Initialize a new cell with the given formula
     * @param formula The formula
     */
    public Cell (String formula) {
        setFormula(formula);
    }

    public void calculate () {
        //TODO some crazy shit
    }

    public String getFormula() {
        return formula;
    }

    public List<Cell> getParents() {
        return parents;
    }

    public int getValue() {
        return value;
    }

    //TODO rebuild expression tree
    public void setFormula(String formula) {
        this.formula = formula;

        Stack postFix = ExpressionEngine.getFormula(formula);
    }

    public void setParents(List<Cell> parents) {
        this.parents = parents;
    }

    public Cell clone () {
        return null;//// TODO: 2/23/16
    }
}
