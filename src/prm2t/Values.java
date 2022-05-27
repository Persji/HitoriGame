package prm2t;

public class Values {
    private final int value;
    /* false białe, true czarne */
    private boolean color;
    public Values(int value, boolean color) {
        this.value = value;
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public boolean getColor() {
        return color;
    }
}
