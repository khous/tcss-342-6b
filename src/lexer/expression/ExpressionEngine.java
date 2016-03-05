package lexer.expression;

import function.Function;
import model.*;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * The purpose of this class is to assimilate user input into valid expressions and compute the values.
 * TODO This class should be able to assume that when it's computing, everything is dereferenced to values
 * TODO what should this class have? a list of expressions to compute? A tree? Should this actually be something a
 * cell has?
 */
public class ExpressionEngine {
    /**
     * getFormula
     *
     * Given a string that represents a formula that is an infix
     * expression, return a stack of Tokens so that the expression,
     * when read from the bottom of the stack to the top of the stack,
     * is a postfix expression.
     *
     * A formula is defined as a sequence of tokens that represents
     * a legal infix expression.
     *
     * A token can consist of a numeric literal, a cell reference, or an
     * operator (+, -, *, /).
     *
     * Multiplication (*) and division (/) have higher precedence than
     * addition (+) and subtraction (-).  Among operations within the same
     * level of precedence, grouping is from left to right.
     *
     * This algorithm follows the algorithm described in Weiss, pages 105-108.
     */
    public static ArrayDeque<Token> getFormula(String formula, Spreadsheet sheet) {
        ArrayDeque<Token> returnStack = new ArrayDeque<>();  // stack of Tokens (representing a postfix expression)
        boolean error = false;
        char ch = ' ';

        int literalValue;

        int index = 0;  // index into formula
        Stack<OperatorToken> operatorStack = new Stack<>();  // stack of operators

        while (index < formula.length() ) {
            // get rid of leading whitespace characters
            while (index < formula.length() ) {
                ch = formula.charAt(index);
                if (!Character.isWhitespace(ch)) {
                    break;
                }
                index++;
            }

            if (index == formula.length() ) {
                error = true;
                break;
            }

            // ASSERT: ch now contains the first character of the next token.
            if (OperatorToken.isOperator(ch)) {
                // We found an operator token
                switch (ch) {
                    case OperatorToken.PLUS:
                    case OperatorToken.MINUS:
                    case OperatorToken.MULTIPLY:
                    case OperatorToken.DIVIDE:
                    case OperatorToken.CARET:
                    case OperatorToken.LEFT_PAREN:
                        // push operatorTokens onto the output stack until
                        // we reach an operator on the operator stack that has
                        // lower priority than the current one.
                        OperatorToken stackOperator;
                        while (!operatorStack.isEmpty()) {
                            stackOperator = operatorStack.peek();
                            if ( (stackOperator.priority() >= OperatorToken.priority(ch)) &&
                                    (stackOperator.getOperatorToken() != OperatorToken.LEFT_PAREN) ) {

                                // output the operator to the return stack
                                operatorStack.pop();
                                returnStack.push(stackOperator);
                            } else {
                                break;
                            }
                        }
                        break;

                    default:
                        // This case should NEVER happen
                        System.out.println("Error in getFormula.");
                        System.exit(0);
                        break;
                }
                // push the operator on the operator stack
                operatorStack.push(new OperatorToken(ch));

                index++;

            } else if (ch == OperatorToken.RIGHT_PAREN) {
                OperatorToken stackOperator;
                stackOperator = operatorStack.pop();
                // This code does not handle operatorStack underflow.
                while (stackOperator.getOperatorToken() != OperatorToken.LEFT_PAREN) {
                    // pop operators off the stack until a LeftParen appears and
                    // place the operators on the output stack
                    returnStack.push(stackOperator);
                    stackOperator = operatorStack.pop();
                }

                index++;
            } else if (Character.isDigit(ch)) {
                // We found a literal token
                literalValue = ch - '0';
                index++;
                while (index < formula.length()) {
                    ch = formula.charAt(index);
                    if (Character.isDigit(ch)) {
                        literalValue = (literalValue * 10) + (ch - '0');
                        index++;
                    } else {
                        break;
                    }
                }
                // place the literal on the output stack
                returnStack.push(new LiteralToken(literalValue));

            } else if (Character.isUpperCase(ch)) {
                // We found a cell reference token, or a function
                CellToken cellToken = getCellToken(formula, index);
                if (cellToken.getRow() == CellToken.BAD_CELL) {
                    FunctionToken ft = getFunctionToken(formula, index, sheet);
                    if (ft.isBadFormula()) {
                        error = true;
                        break;
                    } else {
                        // place the cell reference on the output stack
                        returnStack.push(ft);
                        index += ft.toString().length();
                    }
                } else {
                    cellToken.setSheet(sheet);
                    returnStack.push(cellToken);
                    index += cellToken.toString().length();
                }
            } else {
                error = true;
                break;
            }
        }

        // pop all remaining operators off the operator stack
        while (!operatorStack.isEmpty()) {
            returnStack.push(operatorStack.pop());
        }

        if (error) {
            // a parse error; return the empty stack
            new ArrayDeque<Token>();
        }

        return returnStack;
    }

    public static FunctionToken getFunctionToken (String inputString, int startIndex, Spreadsheet sheet) {
        int index = startIndex;

        ArrayDeque<ArrayDeque<Token>> arguments = new ArrayDeque<>();
        FunctionToken funcToken = new FunctionToken();

        // handle a bad startIndex
        if ((startIndex < 0) || (startIndex >= inputString.length())) {
            return funcToken;
        }

        //Iterate until end or paren
        StringBuilder functionKey = new StringBuilder();
        while (inBoundsOfString(inputString, startIndex) && inputString.charAt(index) != OperatorToken.LEFT_PAREN) {
            functionKey.append(inputString.charAt(index));
            index++;
        }
        //So now we have the function key, next we parse the arguments
        funcToken.setFunctionKey(functionKey.toString());

        //parse each comma separated argument, compile a list using get formula
        //Go until we reach our right paren, how to handle inner parentheses? maybe use a zero sum tracker
        int unclosedParentheses = 1;
        index++;//Moving past opening parenthesis, TODO better error checking whitespace handling
        StringBuilder argument = new StringBuilder();
        int functionArgumentStartIndex = index;

        while (inBoundsOfString(inputString, index) && unclosedParentheses != 0) {
            char currentCharacter = inputString.charAt(index);

            if (currentCharacter == ',' && unclosedParentheses == 1) {//Can't include inner function arguments
                String argumentString = inputString.substring(functionArgumentStartIndex, index);
                arguments.add(getFormula(argumentString, sheet));
                argument.setLength(0);//Empty the builder for next argument
                functionArgumentStartIndex = index + 1;
            } else if (currentCharacter == OperatorToken.LEFT_PAREN) {
                unclosedParentheses++;
            } else if (currentCharacter == OperatorToken.RIGHT_PAREN) {
                unclosedParentheses--;
                //Throw the last argument in
                if (unclosedParentheses == 0) {
                    arguments.add(getFormula(inputString.substring(functionArgumentStartIndex, index), sheet));
                }
            }

            index++;
        }

        funcToken.setSheet(sheet);
        funcToken.setArguments(arguments);
        funcToken.setFunctionString(inputString.substring(startIndex, index));

        return funcToken;
    }

    public static boolean inBoundsOfString (String s, int index) { return index < s.length(); }

    /**
     * getCellToken
     *
     * Assuming that the next chars in a String (at the given startIndex)
     * is a cell reference, set cellToken's column and row to the
     * cell's column and row.
     * If the cell reference is invalid, the row and column of the return CellToken
     * are both set to CellToken.BAD_CELL (which should be a final int that equals -1).
     * Also, return the index of the position in the string after processing
     * the cell reference.
     * (Possible improvement: instead of returning a CellToken with row and
     * column equal to CellToken.BAD_CELL, throw an exception that indicates a parsing error.)
     *
     * A cell reference is defined to be a sequence of CAPITAL letters,
     * followed by a sequence of digits (0-9).  The letters refer to
     * columns as follows: A = 0, B = 1, C = 2, ..., Z = 25, AA = 26,
     * AB = 27, ..., AZ = 51, BA = 52, ..., ZA = 676, ..., ZZ = 701,
     * AAA = 702.  The digits represent the row number.
     *
     * @param inputString  the input string
     * @param startIndex  the index of the first char to process
     * @return  index corresponding to the position in the string just after the cell reference
     */
    public static CellToken getCellToken (String inputString, int startIndex) {
        char ch;
        int column,
            row,
            index = startIndex;
        CellToken cellToken = new CellToken();

        // handle a bad startIndex
        if ((startIndex < 0) || (startIndex >= inputString.length())) {
            cellToken.setColumn(CellToken.BAD_CELL);
            cellToken.setRow(CellToken.BAD_CELL);
            return cellToken;
        }

        // get rid of leading whitespace characters
        while (index < inputString.length() ) {
            ch = inputString.charAt(index);
            if (!Character.isWhitespace(ch)) {
                break;
            }
            index++;
        }
        if (index == inputString.length()) {
            // reached the end of the string before finding a capital letter
            cellToken.setColumn(CellToken.BAD_CELL);
            cellToken.setRow(CellToken.BAD_CELL);
            return cellToken;
        }

        // ASSERT: index now points to the first non-whitespace character

        ch = inputString.charAt(index);
        // process CAPITAL alphabetic characters to calculate the column
        if (!Character.isUpperCase(ch)) {
            cellToken.setColumn(CellToken.BAD_CELL);
            cellToken.setRow(CellToken.BAD_CELL);
            return cellToken;
        } else {
            column = (ch - 'A') + 1;//One base columns
            index++;
        }

        while (index < inputString.length() ) {
            ch = inputString.charAt(index);
            if (Character.isUpperCase(ch)) {
                column = ((column + 1) * 26) + (ch - 'A');
                index++;
            } else {
                break;
            }
        }
        if (index == inputString.length() ) {
            // reached the end of the string before fully parsing the cell reference
            cellToken.setColumn(CellToken.BAD_CELL);
            cellToken.setRow(CellToken.BAD_CELL);
            return cellToken;
        }

        // ASSERT: We have processed leading whitespace and the
        // capital letters of the cell reference

        // read numeric characters to calculate the row
        if (Character.isDigit(ch)) {
            row = ch - '0';
            index++;
        } else {
            cellToken.setColumn(CellToken.BAD_CELL);
            cellToken.setRow(CellToken.BAD_CELL);
            return cellToken;
        }

        while (index < inputString.length() ) {
            ch = inputString.charAt(index);
            if (Character.isDigit(ch)) {
                row = (row * 10) + (ch - '0');
                index++;
            } else {
                break;
            }
        }

        // successfully parsed a cell reference
        cellToken.setColumn(column);
        cellToken.setRow(row);
        return cellToken;
    }

    public static String printCellToken (CellToken ct) {
        return ct.getRow() + ":" + ct.getColumn();
    }
    
    public static int calculate (ArrayDeque<Token> postfixExpression, Spreadsheet sheet)
            throws OperationNotSupportedException {
        ArrayDeque<ValueToken> operands = new ArrayDeque<>();
        int calculatedValue;

        while (!postfixExpression.isEmpty()) {
            Token currentToken = postfixExpression.removeLast();

            if (currentToken instanceof OperatorToken) {
                ValueToken rightOperand = operands.removeLast();
                assignSheetToValueToken(rightOperand, sheet);

                if (operands.isEmpty()) {
                    //do a unary operation
                    calculatedValue = ((OperatorToken) currentToken).compute(rightOperand.getValue());
                } else {
                    ValueToken leftOperand = operands.removeLast();
                    assignSheetToValueToken(leftOperand, sheet);

                    calculatedValue = ((OperatorToken) currentToken)
                            .compute(leftOperand.getValue(), rightOperand.getValue());
                }

                operands.addLast(new LiteralToken(calculatedValue));
            } else {
                operands.addLast((ValueToken) currentToken);
            }
        }

        ValueToken finalValue = operands.pop();
        assignSheetToValueToken(finalValue, sheet);

        return finalValue.getValue();
    }

    public static void assignSheetToValueToken (ValueToken t, Spreadsheet sheet) {
        t.setSheet(sheet);
    }

    public static String postfixToString (ArrayDeque<Token> expression) {
        String out = "";

        Iterator i = expression.descendingIterator();
        while (i.hasNext()) {
            Token t = (Token)i.next();

            if (t instanceof ValueToken)
                if (t instanceof FunctionToken)
                    out += ((FunctionToken) t).getFunctionString();
                else
                    out += ((ValueToken) t).getValue();
            else
                out += ((OperatorToken) t).getOperatorToken();

            out += " ";
        }

        return out.trim();
    }

    public static int calculateFromFormula (String formula, Spreadsheet sheet) throws OperationNotSupportedException {
        return calculate(getFormula(formula, sheet), sheet);
    }
}
