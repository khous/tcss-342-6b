package model.graph;

import model.Cell;

/**
 * Created by kyle on 2/27/16.
 */
public class Vertex {
    private Cell cell;

    public int getIndegree() {
        return indegree;
    }

    public void setIndegree(int indegree) {
        this.indegree = indegree;
    }

    private int indegree;

    public Vertex (Cell cell, int indegree) {
        this.cell = cell;
        this.indegree = indegree;
    }

    public boolean equals (Object o) {
        return o instanceof Vertex &&
            cell.equals(((Vertex) o).getCell());
    }

    public Cell getCell () {
        return cell;
    }
}
