package prm2t;

import java.util.HashMap;

public class Board {
    private final int size;
    private HashMap<Coordinates,Values> userList = new HashMap<>();
    private HashMap<Coordinates,Values> generatedList = new HashMap<>();

    public Board(HashMap<Coordinates,Values> genList) {
        this.generatedList = genList;
        this.size = generatedList.size();
        this.userList = genList;
    }

    public int getSize() {
        return size;
    }
    public HashMap<Coordinates,Values> getUserList() {
        return generatedList;
    }

}
