package model.graph;

import model.Cell;

/**
 * A vertex in a cell dependency graph
 */
public class Vertex {

    /**
     * The cell held by this vertex
     */
    private Cell cell;

    /**
     * Get the indegree of this vertex. Indegree is the number of edges to this cell.
     * @return The indegree
     */
    public int getIndegree() {
        return indegree;
    }

    /**
     * Set the indegree of this vertex
     * @param indegree
     */
    public void setIndegree(int indegree) {
        this.indegree = indegree;
    }

    /**
     * The indegree of this vertex
     */
    private int indegree;

    public Vertex (Cell cell, int indegree) {
        this.cell = cell;
        this.indegree = indegree;
    }

    /**
     * Is this vertex equal to another object based on if o is a vertex and the cells are the same.
     * @param o The comparison object
     * @return True if they are equal
     */
    public boolean equals (Object o) {
        return o instanceof Vertex &&
            cell.equals(((Vertex) o).getCell());
    }

    /**
     * Get the cell referenced by this vertex.
     * @return
     */
    public Cell getCell () {
        return cell;
    }
}
