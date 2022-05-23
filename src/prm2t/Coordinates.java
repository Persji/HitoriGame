package prm2t;

public class Coordinates {
    private final int rows;
    private final int cols;

    public Coordinates(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coordinates coord = (Coordinates) o;

        if (rows != coord.rows) {
            return false;
        }
        if (cols != coord.cols) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        return  31 * rows + cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

}
