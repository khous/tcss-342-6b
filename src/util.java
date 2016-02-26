/*
 * Utility functions.
 * You should place these methods in the appropriate classes.
 * @author Donald Chinn
 */
import model.*;

public class util {
    /**
     * Return a string associated with a token
     * @param expTreeToken  an ExpressionTreeToken
     * @return a String associated with expTreeToken
     */
    String printExpressionTreeToken (Token expTreeToken) {
        String returnString = "";
    
        if (expTreeToken instanceof OperatorToken) {
            returnString = ((OperatorToken) expTreeToken).getOperatorToken() + " ";
        } else if (expTreeToken instanceof CellToken) {
            returnString = printCellToken((CellToken) expTreeToken) + " ";
        } else if (expTreeToken instanceof LiteralToken) {
            returnString = ((LiteralToken) expTreeToken).getValue() + " ";
        } else {
            // This case should NEVER happen
            System.out.println("Error in printExpressionTreeToken.");
            System.exit(0);
        }
        return returnString;
    }

    

    
    /*
     * Return the priority of this OperatorToken.
     *
     * priorities:
     *   +, - : 0
     *   *, / : 1
     *   (    : 2
     *
     * @return  the priority of operatorToken
     */
    int priority () {
        switch (this.operatorToken) {
            case Plus:
                return 0;
            case Minus:
                return 0;
            case Mult:
                return 1;
            case Div:
                return 1;
            case LeftParen:
                return 2;
    
            default:
                // This case should NEVER happen
                System.out.println("Error in priority.");
                System.exit(0);
                break;
        }
    }
    
    

    
    /**
     *  Given a CellToken, print it out as it appears on the
     *  spreadsheet (e.g., "A3")
     *  @param cellToken  a CellToken
     *  @return  the cellToken's coordinates
     */
    String printCellToken (CellToken cellToken) {
        char ch;
        String returnString = "";
        int col;
        int largest = 26;  // minimum col number with number_of_digits digits
        int number_of_digits = 2;
    
        col = cellToken.getColumn();
    
        // compute the biggest power of 26 that is less than or equal to col
        // We don't check for overflow of largest here.
        while (largest <= col) {
            largest = largest * 26;
            number_of_digits++;
        }
        largest = largest / 26;
        number_of_digits--;
    
        // append the column label, one character at a time
        while (number_of_digits > 1) {
            ch = (char) ((col / largest) - 1) + 'A';
            returnString += ch;
            col = col % largest;
            largest = largest  / 26;
            number_of_digits--;
        }
    
        // handle last digit
        ch = col + 'A';
        returnString += ch;
    
        // append the row as an integer
        returnString += cellToken.getRow();
    
        return returnString;
    }

}