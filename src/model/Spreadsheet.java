package model;


public class Spreadsheet {
    public static final int DEFAULT_DIMENSION = 3;
    private Cell[][] mySpreadSheet = new Cell[DEFAULT_DIMENSION][DEFAULT_DIMENSION];

    public Cell getCell (int row, int column) {
        //TODO bound checking

        return ((Cell) mySpreadSheet[row][column]).clone();
    }

    public void setCell (int row, int column, Cell cell) {
        //TODO encapsulation and bound checking
        mySpreadSheet[row][column] = cell;
    }
}
