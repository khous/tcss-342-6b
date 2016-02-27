package model.graph;

import model.Cell;

import java.util.*;

/**
 * Created by kyle on 2/27/16.
 */
public class CellDependencyGraph {
    private HashMap<String, List<Vertex>> adjacencyList = new HashMap<>();

    //Vertices in the graph with indirection of one, if this is empty after building the graph, there exists a cycle
    private Stack<Cell> rootNodes = new Stack<>();

    private Stack<Cell> topologicallyOrderedCells;

    //Traverse the graph

    public HashMap<String, List<Vertex>> getAdjacencyList() {
        return adjacencyList;
    }

    public void buildGraph (Cell root) {
        String cellId = root.getCellId();

        if (adjacencyList.containsKey(cellId)) return;//This node was already processed

        List<Vertex> adjacencies = new ArrayList<>();

        for (Cell parent : root.getParents())
            adjacencies.add(new Vertex(parent, parent.getIndirection()));

        if (adjacencies.size() == 0)
            rootNodes.push(root);

        adjacencyList.put(root.getCellId(), adjacencies);

        for (Vertex parent : adjacencies) {
            buildGraph(parent.getCell());
        }
    }

    public Stack<Cell> getTopologicalOrder () {
        //There's a cycle, who knows where
        if (rootNodes.size() == 0) return null;

        Stack<Vertex> roots = new Stack<>();

        for (Cell c : rootNodes)
            roots.push(new Vertex(c, c.getIndirection()));

        Stack<Cell> output = new Stack<>();

        //get the start node, add it to the output
        //decrement all its children
        //If they are @ indirection 0, add them to the roots
        //do t

        while (!roots.isEmpty()) {
            Vertex node = roots.pop();
            output.push(node.getCell());

            //decrement indirection for vertices
            for (Vertex vertex : adjacencyList.get(node.getCell().getCellId())) {
                int indirection = Math.max(0, vertex.getIndirection());
                vertex.setIndirection(indirection);
                if (indirection == 0)
                    roots.push(vertex);
            }
        }

        return output;
    }
}
