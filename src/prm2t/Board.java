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
    public int getValueFromBoard(int coordinates) { //skopiowane z generator

        Values v = userBoard.get(coordinates);
        if(v != null) {
            return v.getValue();
        } else {
            System.out.println("Podane koordynaty wykraczają poza plansze");
            return -1;
        }
    }

    @Override
    public String toString(){ //zwraca same wartości planszy, jeszcze bez koloru
        String rtn = "";
        for (int j = 0; j <Math.sqrt(size) ; j++) {
            for (int i = 0; i < Math.sqrt(size); i++) {
                rtn += getValueFromBoard((int) (j*Math.sqrt(size)+ i)) + " ";
            }
            rtn += "\n";
        }
        return rtn;
    }
}
