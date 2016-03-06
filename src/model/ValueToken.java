package model;

/**
 * Base class for tokens which can be evaluated to an integer. Basically, not operators.
 */
public abstract class ValueToken extends Token {
    public abstract int getValue();
    public abstract void setSheet (Spreadsheet sheet);
}
