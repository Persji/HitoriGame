package prm2t;

public class Values {
    private final int value;
    private final boolean color;

    public Values(int value, boolean color) {
        this.value = value;
        this.color = color;
    }

    public int getCoordinates() {
        return value;
    }

    public boolean getColor() {
        return color;
    }
}
