package prm2t;

import java.util.ArrayList;

public class Solver {
    Board board;
    Generator generator;

    public Solver(Board board ,Generator generator){
        this.board = board;
        this.generator = generator;
    }
    /* zwraca true jak poprawna i false jak niepoprawna */
    boolean checkIfBoardIsCorrect(ArrayList board) {
        return true;
    }

}
