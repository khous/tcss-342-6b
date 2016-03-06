package model.graph;

import model.Cell;

import java.util.*;

/**
 * Graph of cells designed to compute the topological sorted order.
 */
public class CellDependencyGraph {
    private HashMap<String, List<Vertex>> adjacencyList = new HashMap<>();

    /**
     * Vertices in the graph with indirection of one, if this is empty after building the graph, there exists a cycle
     */
    private Stack<Cell> rootNodes = new Stack<>();

    /**
     * Get the adjacency list representing this dependency graph
     * @return The adjacency list
     */
    public HashMap<String, List<Vertex>> getAdjacencyList() {
        return adjacencyList;
    }

    /**
     * Build the graph given a cell to start at. This single point of entry allows us to only recalculate cells that
     * are affected by a formula change. It's pretty sweet.
     * @param root The root at which to begin building the graph
     */
    public void buildGraph (Cell root) {
        List<Cell> parents = root.getParents();
        Vertex rootVertex = new Vertex(root, parents.size());
        String rootId = root.getCellId();
        List<Vertex> existingEntry = adjacencyList.get(rootId);

        if (existingEntry == null) {
            adjacencyList.put(rootId, new ArrayList<Vertex>());
        }

        for (Cell parent : parents) {
            String cellId = parent.getCellId();
            List<Vertex> adjacencies = adjacencyList.get(cellId);

            if (adjacencies == null)
                adjacencies = new ArrayList<>();

            if (adjacencies.contains(rootVertex))
                return;//cycle detected?

            adjacencies.add(rootVertex);
            adjacencyList.put(cellId, adjacencies);
        }

        if (parents.isEmpty() && !rootNodes.contains(root)) {
            rootNodes.push(root);
        }

        for (Cell parent : parents) {
            buildGraph(parent);
        }
    }

    /**
     * Calling remove on the output of this will return the next topological element in the graph
     * @return
     */
    public ArrayDeque<Cell> getTopologicalOrder () {
        //There's a cycle, who knows where
        if (rootNodes.size() == 0) return new ArrayDeque<>();

        Stack<Vertex> roots = new Stack<>();

        for (Cell c : rootNodes)
            roots.push(new Vertex(c, c.getIndegree()));

        ArrayDeque<Cell> output = new ArrayDeque<>();

        //get the start node, add it to the output
        //decrement all its children
        //If they are @ indirection 0, add them to the roots
        while (!roots.isEmpty()) {
            Vertex node = roots.pop();
            output.offer(node.getCell());

            //decrement indirection for vertices
            for (Vertex vertex : adjacencyList.get(node.getCell().getCellId())) {
                int indegree = Math.max(0, vertex.getIndegree() - 1);
                vertex.setIndegree(indegree);
                if (indegree == 0)
                    roots.push(vertex);
            }
        }

        return output;
    }
}
