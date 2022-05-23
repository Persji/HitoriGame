package prm2t;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int size;
    private  List<Values> userBoard = new ArrayList<Values>();
    private List<Values> generatedList = new ArrayList<Values>();

    public Board(List<Values> genList) {
        this.generatedList = genList;
        this.size = generatedList.size();
        this.userBoard = genList;
    }

    public int getSize() {
        return size;
    }
    public List<Values> getUserBoard() {
        return userBoard;
    }

    public void updateBoard(List<Values> updatedBoard) {
        userBoard = updatedBoard;
    }


    public void updateBoard(int xy,int option){
        switch (option) {
            case 0:
            {
                changeColor(xy);
            }
        }
    }

    public void changeColor(int xy){
        userBoard.add(xy,new Values(userBoard.get(xy).getValue(),
                !(userBoard.get(xy).getColor())));
    }
}
