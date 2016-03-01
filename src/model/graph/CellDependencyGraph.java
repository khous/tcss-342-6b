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

//            adjacencies.add(new Vertex(parent, parent.getIndegree()));

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
        if (rootNodes.size() == 0) return null;

        Stack<Vertex> roots = new Stack<>();

        for (Cell c : rootNodes)
            roots.push(new Vertex(c, c.getIndegree()));

        ArrayDeque<Cell> output = new ArrayDeque<>();

        //get the start node, add it to the output
        //decrement all its children
        //If they are @ indirection 0, add them to the roots
        //do t

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
