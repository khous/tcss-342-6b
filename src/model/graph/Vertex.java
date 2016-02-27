package model.graph;

import model.Cell;

/**
 * Created by kyle on 2/27/16.
 */
public class Vertex {
    private Cell cell;

    public int getIndirection() {
        return indirection;
    }

    public void setIndirection(int indirection) {
        this.indirection = indirection;
    }

    private int indirection;

    public Vertex (Cell cell, int indirection) {
        this.cell = cell;
        this.indirection = indirection;
    }

    public boolean equals (Object o) {
        return o instanceof Vertex &&
            cell.equals(((Vertex) o).getCell());
    }

    public Cell getCell () {
        return cell;
    }
}
