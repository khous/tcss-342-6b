package model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kyle on 2/27/16.
 */
public class CellTest {

    @Test
    public void testGetCellId() throws Exception {
        Cell c = new Cell("", 1, 1);

        Assert.assertEquals(c.getCellId(), "A1");

        c.setRow(1);
        c.setColumn(2);
        Assert.assertEquals(c.getCellId(), "B1");

        c.setRow(1);
        c.setColumn(27);
        Assert.assertEquals(c.getCellId(), "AA1");

        c.setRow(1234);
        c.setColumn(2);
        Assert.assertEquals(c.getCellId(), "B1234");
//        Assert.assertEquals(c.getCellId(), "A1");
//        Assert.assertEquals(c.getCellId(), "A1");
//        Assert.assertEquals(c.getCellId(), "A1");
//        Assert.assertEquals(c.getCellId(), "A1");
//        Assert.assertEquals(c.getCellId(), "A1");
//        Assert.assertEquals(c.getCellId(), "A1");
    }
}