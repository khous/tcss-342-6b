package lexer.expression;

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
        ExpressionEngine ee = new ExpressionEngine();

        ArrayDeque<Token> s = ExpressionEngine.getFormula("1 + 1");
        Assert.assertEquals(s.size(), 3);

        s = ExpressionEngine.getFormula("-1 + 1");
        Assert.assertEquals(s.size(), 4);

        Assert.assertEquals(0, ExpressionEngine.calculate(s));

        s = ExpressionEngine.getFormula("(1 + (2 - 1))");
        Assert.assertEquals(5, s.size());

        Assert.assertEquals("1 2 1 - +", ExpressionEngine.postfixToString(s));

        s = ExpressionEngine.getFormula("2^(1+3)");
        Assert.assertEquals("2 1 3 + ^", ExpressionEngine.postfixToString(s));
        Assert.assertEquals(16, ExpressionEngine.calculate(s));
    }

    @Test
    public void testGetCellToken() throws Exception {

    }

    @Test
    public void testPrintCellToken() throws Exception {

    }
}