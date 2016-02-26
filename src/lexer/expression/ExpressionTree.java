package lexer.expression;

/**
 * This is an expression tree, this tree will handle dereferencing cell addresses and doing computation
 * I think the dereferencing is just a lookup from the spreadsheet, the value in the cell should already be computed
 * That's the only way this makes sense, prevents cycles
 */
public class ExpressionTree {
    private ExpressionNode root;

    public int compute () {
        //do a post order traversal
        //resolving operator tokens to real operations

        return 0;
    }
}
