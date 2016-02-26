package model;


public class Spreadsheet {
    public static final int DEFAULT_DIMENSION = 3;
    private Cell[][] mySpreadSheet;

    public Spreadsheet () {
        this(DEFAULT_DIMENSION);
    }

    public Spreadsheet (int size) {
        mySpreadSheet = new Cell[size][size];
    }


    public Cell getCell (int row, int column) {
        //TODO bound checking

        return mySpreadSheet[row][column].clone();
    }

    public void setCell (int row, int column, Cell cell) {
        //TODO encapsulation and bound checking
        mySpreadSheet[row][column] = cell;
    }

    public int getNumberOfRows () { return mySpreadSheet.length; }
    public int getNumberOfColumns () { return mySpreadSheet[0].length; }

    public void printValues () {

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
        }

        return sb.toString();
    }
}
