package model.graph;

import model.Cell;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kyle on 2/27/16.
 */
public class CellDependencyGraphTest {


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

        List<Vertex> aV = adjacencyList.get("A1");
        Assert.assertNotNull(aV);

        List<Vertex> bV = adjacencyList.get("A2");
        Assert.assertNotNull(bV);
        Assert.assertTrue(bV.contains(cellToVertex(g)) && bV.contains(cellToVertex(a)));

        List<Vertex> cV = adjacencyList.get("A3");
        Assert.assertNotNull(cV);
        Assert.assertTrue(cV.contains(cellToVertex(f)) && cV.contains(cellToVertex(b)));

        List<Vertex> dV = adjacencyList.get("A4");
        Assert.assertNotNull(dV);
        Assert.assertTrue(dV.contains(cellToVertex(c)));

        List<Vertex> eV = adjacencyList.get("A5");
        Assert.assertNotNull(eV);
        Assert.assertTrue(eV.contains(cellToVertex(a)));

        List<Vertex> fV = adjacencyList.get("A6");
        Assert.assertNotNull(fV);
        Assert.assertTrue(fV.contains(cellToVertex(b)));

        List<Vertex> gV = adjacencyList.get("A7");
        Assert.assertNotNull(gV);
        Assert.assertTrue(gV.contains(cellToVertex(e)));
    }

    private Vertex cellToVertex (Cell c) {
        return new Vertex(c, c.getIndirection());
    }

    @Test
    public void testGetTopologicalOrder () throws Exception {

    }
}