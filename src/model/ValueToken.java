package model;

/**
 * Created by kyle on 3/2/16.
 */
public abstract class ValueToken extends Token {
    public abstract int getValue();
    public abstract void setSheet (Spreadsheet sheet);
}
