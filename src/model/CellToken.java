package model;

import lexer.expression.ExpressionEngine;

/**
 * Created by kyle on 2/23/16.
 */
public class CellToken extends ValueToken {

    public static final int BAD_CELL = -1;

    private int row;
    private int column;

    public CellToken () {}

    public CellToken (int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Spreadsheet getSheet() {
        return sheet;
    }

    public void setSheet(Spreadsheet sheet) {
        this.sheet = sheet;
    }

    private Spreadsheet sheet;
    
    public CellToken (Spreadsheet s) {
    	sheet = s;
    }

    public Cell getCell () {
		return sheet.getCell(row, column);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow () {
        return row;
    }

    public int getValue() {
        return getCell().getValue();
    }

    public String toString () {
        return (new Cell("", row, column)).getCellId();
    }

    public boolean isBadCell () {
        return row == BAD_CELL || column == BAD_CELL;
    }
}
