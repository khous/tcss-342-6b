package lexer.expression;

import model.Token;

/**
 * Prototype node in an expression tree
 */
public class ExpressionNode {
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;

    private Token value;

    public ExpressionNode() {
    	
    }
    
	public ExpressionNode(Token token, ExpressionNode leftSubtree, ExpressionNode rightSubtree) {
		value = token;
		leftChild = leftSubtree;
		rightChild = rightSubtree;
	}

	public Token getValue() {
		return value;
	}

	public void setValue(Token value) {
		this.value = value;
	}

	public ExpressionNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(ExpressionNode leftChild) {
		this.leftChild = leftChild;
	}

	public ExpressionNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(ExpressionNode rightChild) {
		this.rightChild = rightChild;
	}
	
	public boolean isLeaf() {
		return leftChild == null && rightChild == null;
	}
}
