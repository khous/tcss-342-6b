package model.graph;

import model.Cell;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kyle on 2/27/16.
 */
public class CellDependencyGraphTest {


    @Test
    public void testCyclicBuildGraph () throws Exception {
        String formula = "";

        Cell a = new Cell(formula, 1, 1);
        Cell b = new Cell(formula, 2, 1);

        a.addParent(b);
        b.addParent(a);

        CellDependencyGraph graph = new CellDependencyGraph();
        graph.buildGraph(a);

        ArrayDeque<Cell> topoOrder = graph.getTopologicalOrder();

        Assert.assertTrue(topoOrder.isEmpty());
    }

    @Test
    public void testBuildGraphWithOneNode () throws Exception {
        String formula = "";

        Cell a = new Cell(formula, 1, 1);

        CellDependencyGraph graph = new CellDependencyGraph();

        graph.buildGraph(a);
        Assert.assertTrue(!graph.getTopologicalOrder().isEmpty());
    }

    @Test
    public void testBuildGraph () throws Exception {
        String formula = "";

        Cell a = new Cell(formula, 1, 1);
        Cell b = new Cell(formula, 2, 1);
        Cell c = new Cell(formula, 3, 1);
        Cell d = new Cell(formula, 4, 1);
        Cell e = new Cell(formula, 5, 1);
        Cell f = new Cell(formula, 6, 1);
        Cell g = new Cell(formula, 7, 1);

        d.addParent(c);

        c.addParent(b);
        c.addParent(f);

        f.addParent(b);

        b.addParent(g);
        b.addParent(a);

        g.addParent(e);

        e.addParent(a);

        Cell root = d;
        CellDependencyGraph graph = new CellDependencyGraph();

        graph.buildGraph(root);

        HashMap<String, List<Vertex>> adjacencyList = graph.getAdjacencyList();

        Assert.assertEquals(adjacencyList.size(), 7);

        goesTo(adjacencyList, "A1", b, e);
        goesTo(adjacencyList, "A2", c, f);
        goesTo(adjacencyList, "A3", d);

//        Assert.assertTrue(goesTo(adjacencyList, "A4", d));

        goesTo(adjacencyList, "A5", g);
        goesTo(adjacencyList, "A6", c);
        goesTo(adjacencyList, "A7", b);

        testGetTopologicalOrder(graph);
    }

    private void goesTo (HashMap<String, List<Vertex>> adjacencyList, String root, Cell... cells) {
        List<Vertex> v = adjacencyList.get(root);
        Assert.assertNotNull(v);

        for (Cell c : cells) {
            Assert.assertTrue(v.contains(cellToVertex(c)));
        }
    }

    private Vertex cellToVertex (Cell c) {
        return new Vertex(c, c.getIndegree());
    }

    public void testGetTopologicalOrder (CellDependencyGraph graph) throws Exception {
        ArrayDeque<Cell> topologicalOrder = graph.getTopologicalOrder();

        Assert.assertEquals(topologicalOrder.remove().getCellId(), "A1");
        Assert.assertEquals(topologicalOrder.remove().getCellId(), "A5");
        Assert.assertEquals(topologicalOrder.remove().getCellId(), "A7");
        Assert.assertEquals(topologicalOrder.remove().getCellId(), "A2");
        Assert.assertEquals(topologicalOrder.remove().getCellId(), "A6");
        Assert.assertEquals(topologicalOrder.remove().getCellId(), "A3");
        Assert.assertEquals(topologicalOrder.remove().getCellId(), "A4");
    }
}