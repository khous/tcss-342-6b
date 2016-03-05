package lexer.expression;

import model.Cell;
import model.CellToken;
import model.Spreadsheet;
import org.junit.Assert;
import model.Token;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by kyle on 3/2/16.
 */
public class ExpressionEngineTest {

    @Test
    public void testFormulaToExpressionTree() throws Exception {

    }

    @Test
    public void testGetFormula() throws Exception {
        Spreadsheet ss = new Spreadsheet();

        ArrayDeque<Token> postfixStack = ExpressionEngine.getFormula("1 + 1", ss);
        Assert.assertEquals(postfixStack.size(), 3);

        postfixStack = ExpressionEngine.getFormula("-1 + 1", ss);
        Assert.assertEquals(postfixStack.size(), 4);

        Assert.assertEquals(0, ExpressionEngine.calculate(postfixStack, ss));

        postfixStack = ExpressionEngine.getFormula("(1 + (2 - 1))", ss);
        Assert.assertEquals(5, postfixStack.size());

        Assert.assertEquals("1 2 1 - +", ExpressionEngine.postfixToString(postfixStack));

        postfixStack = ExpressionEngine.getFormula("2^(1+3)", ss);
        Assert.assertEquals("2 1 3 + ^", ExpressionEngine.postfixToString(postfixStack));
        Assert.assertEquals(16, ExpressionEngine.calculate(postfixStack, ss));

        postfixStack = ExpressionEngine.getFormula("AVG(1+3)", ss);
        Assert.assertEquals("AVG(1+3)", ExpressionEngine.postfixToString(postfixStack));

//        postfixStack = ExpressionEngine.getFormula("AVG(1+3, 3)", ss);
        Assert.assertEquals(2, ExpressionEngine.calculateFromFormula("AVG(1,2,3)", ss));

        ss.setCell(0, 1, new Cell("13", 0, 1));
        ss.setCell(1, 1, new Cell("A0", 1, 1));
        ss.setCell(2, 1, new Cell("", 2, 1));


        ss.changeCellFormulaAndRecalculate(new CellToken(1, 1), "A0");
        ss.changeCellFormulaAndRecalculate(new CellToken(2, 1), "AVG(A0,0)");

        Assert.assertEquals(13, ExpressionEngine.calculateFromFormula("A1", ss));
        Assert.assertEquals(6, ExpressionEngine.calculateFromFormula("A2", ss));
    }

    @Test
    public void testGetCellToken() throws Exception {

    }

    @Test
    public void testPrintCellToken() throws Exception {

    }
}