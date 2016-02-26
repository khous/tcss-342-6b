package model;

/**
 *
 */
public class OperatorToken extends Token {
    public static final char PLUS        = '+';
    public static final char MINUS       = '-';
    public static final char DIVIDE      = '/';
    public static final char MULTIPLY    = '*';
    public static final char LEFT_PAREN  = '(';
    public static final char RIGHT_PAREN = ')';
    public static final char CARET       = '^';

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
                return 2;

            default:
                // This case should NEVER happen
                System.out.println("Error in operatorPriority.");
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
                (ch == LEFT_PAREN) );
    }

    public int priority () {
        return priority(operator);
    }
}
