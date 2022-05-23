package prm2t;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int size;
    private  List<Integer> userBoard = new ArrayList<Integer>();
    private List<Integer> generatedList = new ArrayList<Integer>();

    public Board(List<Integer> genList) {
        this.generatedList = genList;
        this.size = generatedList.size();
        this.userBoard = genList;
    }

    public int getSize() {
        return size;
    }
    public List<Integer> getUserBoard() {
        return userBoard;
    }

    public void updateBoard(List<Integer> updatedBoard) {
        userBoard = updatedBoard;
    }
}
