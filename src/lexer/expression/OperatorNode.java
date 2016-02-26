package lexer.expression;

import model.OperatorToken;

/**
 * Created by kyle on 2/24/16.
 */
public class OperatorNode extends ExpressionNode {
    private ValueNode leftChild;
    private ValueNode rightChild;

    private OperatorToken value;

    public OperatorNode(OperatorToken operator, ValueNode leftValue, ValueNode rightValue) {
        this.leftChild = leftValue;
        this.rightChild = rightValue;
        this.value = operator;
    }
}
