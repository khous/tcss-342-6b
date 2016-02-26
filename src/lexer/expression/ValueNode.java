package lexer.expression;

import model.OperatorToken;
import model.Token;

/**
 * Token should be either a literal, or a celltoken
 */
public class ValueNode  extends ExpressionNode  {
    private Token value;

    public ValueNode(Token value) {
        this.value = value;
    }
}
