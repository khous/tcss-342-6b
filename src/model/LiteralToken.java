package model;

/**
 * Created by kyle on 2/23/16.
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
