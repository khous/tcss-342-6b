package lexer.expression;

import model.Token;

/**
 * Prototype node in an expression tree
 */
public class ExpressionNode {
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;

    private Token value;
}
