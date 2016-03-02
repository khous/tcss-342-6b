package lexer.expression;

import model.Cell;
import model.CellToken;
import model.LiteralToken;
import model.OperatorToken;
import model.Token;

/**
 * This is an expression tree, this tree will handle dereferencing cell addresses and doing computation
 * I think the dereferencing is just a lookup from the spreadsheet, the value in the cell should already be computed
 * That's the only way this makes sense, prevents cycles
 */
public class ExpressionTree {
    private ExpressionNode root;

    private ExpressionTree( ExpressionNode node ) {
        root = node;
    }

    public int compute () {
    	
        return postOrder(root);
    }
    
    public int postOrder(ExpressionNode node) {
    	Token t = node.getValue();
    	
    	if ( t == null) {
    		return 0;
    	}
    	if(t instanceof OperatorToken) {
    		switch (((OperatorToken) t).getOperatorToken()) {
    		case '+':
    			return postOrder(node.getLeftChild()) + postOrder(node.getRightChild());
    		case '-':
    			return postOrder(node.getLeftChild()) - postOrder(node.getRightChild());
    		case '*':
    			return postOrder(node.getLeftChild()) * postOrder(node.getRightChild());
    		case '/':
    			return postOrder(node.getLeftChild()) / postOrder(node.getRightChild());
    		}
    	} else if (t instanceof LiteralToken) {
    		int value = ((LiteralToken) t).getValue();
    		return value;
    	} else if (t instanceof CellToken) {
    		Cell a = ((CellToken) t).getCell();
    		int value = a.getValue();
    		return value; 
    	}
    	return 0;
    }
    
    /** Prints out this expression, fully parenthesized.
     */
    public void printExpression() {
        // Call a recursive helper method to perform the actual printing.
        printExpression(root);
        System.out.println();
    }

    /** Recursive helper method for printExpression(). Prints out the
     * expression in the subtree rooted at root, fully
     * parenthesized.
     *
     * @param root the root of the expression tree to print.
     */
    private void printExpression(ExpressionNode node) {
        // If the node is a leaf, just print the value.
        if (node.isLeaf()) {
            System.out.print(node.getValue());

        // Otherwise, print an opening parenthesis, the operands and
        // operator, and a closing parenthesis.
        } else {
            System.out.print("(");
            printExpression(node.getLeftChild());
            System.out.print(" " + node.getValue() + " ");
            printExpression(node.getRightChild());
            System.out.print(")");
        } 
    }

}
