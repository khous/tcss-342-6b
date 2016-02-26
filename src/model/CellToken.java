package model;

/**
 * Created by kyle on 2/23/16.
 */
public class CellToken extends Token {

    public static final int BAD_CELL = -1;

    private int row;
    private int column;

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
}
