package model;

import function.Function;
import function.FunctionLibrary;
import lexer.expression.ExpressionEngine;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 3/5/16.
 */
public class FunctionToken extends ValueToken {
    /**
     * ie AVG
     */
    private String functionKey;

    /**
     * This is what the user entered ie AVG(A1 + A2, 1,2,3,AVG(1,2,3))
     */
    private String functionString;//TODO reconsider name

    /**
     * This is the in order list of arguments which were discovered
     */
    private ArrayDeque<ArrayDeque<Token>> arguments;//arguments are all expressions

    /**
     * This is the list of cells referenced by this token, these refs will show up in the arguments, should also recursively
     * discover the refs of inner functions ie AVG(AVG(A1))
     */
    private List<Cell> referencedCells;

    private Spreadsheet sheet;

    public void setSheet (Spreadsheet sheet) {
        this.sheet = sheet;
    }

    public void setFunctionKey(String functionKey) {
        this.functionKey = functionKey;
    }

    public String getFunctionString() {
        return functionString;
    }

    public void setFunctionString(String functionString) {
        this.functionString = functionString;
    }

    public ArrayDeque<ArrayDeque<Token>> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayDeque<ArrayDeque<Token>> arguments) {
        referencedCells = new ArrayList<>();

        for (ArrayDeque<Token> oneArgument : arguments)
            for (Token t : oneArgument)
                if (t instanceof ValueToken)
                    if (t instanceof CellToken)
                        referencedCells.add(((CellToken) t).getCell());
                    else if (t instanceof FunctionToken)
                        referencedCells.addAll(((FunctionToken) t).getReferencedCells());

        this.arguments = arguments;
    }

    public List<Cell> getReferencedCells() {
        return referencedCells;
    }

    /**
     * Conform to the value token API, this performs the calculation
     * @return
     */
    @Override
    public int getValue() {
        FunctionLibrary fl = new FunctionLibrary();

        Function f = fl.getFunction(functionKey);

        List<Integer> intArguments = new ArrayList<>();

        for (ArrayDeque<Token> argList : arguments) {
            try {
                intArguments.add(new Integer(ExpressionEngine.calculate(argList, sheet)));
            } catch (Exception e) {
                System.err.println("Operation not supported");
            }
        }

        return f.compute(intArguments);
    }

    public boolean isBadFormula () {
        return functionString == null || functionString.equals("");//TODO
    }
}
