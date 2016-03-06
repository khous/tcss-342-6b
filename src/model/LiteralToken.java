package model;

/**
 * A simple literal token which can only be a number.
 */
public class LiteralToken extends ValueToken {

    private int value;

    public LiteralToken (int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setSheet (Spreadsheet sheet) {}

    public void setValue(int value) {
        this.value = value;
    }
}
