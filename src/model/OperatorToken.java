package model;

import javax.naming.OperationNotSupportedException;

/**
 *
 */
public class OperatorToken extends Token {
    public static final char PLUS        = '+',
                             MINUS       = '-',
                             DIVIDE      = '/',
                             MULTIPLY    = '*',
                             LEFT_PAREN  = '(',
                             RIGHT_PAREN = ')',
                             CARET       = '^';

    private final char operator;

    public OperatorToken (char operator) {
        this.operator = operator;
    }

    public char getOperatorToken () {
        return operator;
    }

    /**
     * Given an operator, return its priority.
     * Paren Exponent Mult Div Add Sub
     * priorities:
     *   +, - : 0
     *   *, / : 1
     *   (    : 2
     *
     * @param ch  a char
     * @return  the priority of the operator
     */
    public static int priority (char ch) {
        if (!isOperator(ch)) {
            // This case should NEVER happen
            System.out.println("Error in operatorPriority.");
            System.exit(0);
        }
        switch (ch) {
            case OperatorToken.PLUS:
            case OperatorToken.MINUS:
                return 0;
            case OperatorToken.MULTIPLY:
            case OperatorToken.DIVIDE:
                return 1;
            case OperatorToken.LEFT_PAREN:
                return 5;
            case OperatorToken.CARET:
                return 2;

            default:
                // This case should NEVER happen
                System.out.println("Error in operatorPriority for value " + Character.toString(ch));
                System.exit(0);
                return -1;
        }
    }

    /**
     * Return true if the char ch is an operator of a formula.
     * Current operators are: +, -, *, /, (.
     * @param ch  a char
     * @return  whether ch is an operator
     */
    public static boolean isOperator (char ch) {
        return ((ch == PLUS) ||
                (ch == MINUS) ||
                (ch == MULTIPLY) ||
                (ch == DIVIDE) ||
                (ch == CARET) ||
                (ch == LEFT_PAREN) );
    }

    public int priority () {
        return priority(operator);
    }

    public int compute (int leftOperand, int rightOperand) throws OperationNotSupportedException {
        int computedValue = 0;
        //do the appropriate operation for this instance of operator token
        switch (operator) {
            case PLUS:
                computedValue = leftOperand + rightOperand;
                break;
            case MINUS:
                computedValue = leftOperand - rightOperand;
                break;
            case MULTIPLY:
                computedValue = leftOperand * rightOperand;
                break;
            case DIVIDE:
                computedValue = leftOperand / rightOperand;
                break;
            case CARET:
                computedValue = (int) Math.pow(leftOperand, rightOperand);
                break;
            default:
                throw new OperationNotSupportedException("Unexpected operator: " + operator);
        }
        return computedValue;
    }

    public int compute (int operand) throws OperationNotSupportedException {
        int result = 0;
        //do the appropriate unary operation for this instance of operator token
        switch (operator) {
            case MINUS:
                result = -operand;
                break;
            case PLUS:
                result = operand;
                break;
            default:
                throw new OperationNotSupportedException("Unexpected unary operator: " + operator);
        }

        return result;
    }

}
