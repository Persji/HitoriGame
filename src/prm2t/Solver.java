package prm2t;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Solver {
    Board board;

    public Solver(Board board){
        this.board = board;
    }
    /* zwraca true jak poprawna i false jak niepoprawna */
    boolean checkIfBoardIsCorrect() {
        int boardWidth = (int)Math.sqrt(board.getSize());
        ArrayList<Integer> blackCoordinates = new ArrayList<>();
        ArrayList<Integer> whiteCoordinates = new ArrayList<>();
        for(int i=0; i<board.getSize(); ++i) {
            if(board.getColorFromBoard(i)){
                blackCoordinates.add(i);
            }
        }
        for(int i=0; i<board.getSize(); ++i) {
            if(!board.getColorFromBoard(i)){
                whiteCoordinates.add(i);
            }
        }
//        System.out.println(blackCoordinates);
//        System.out.println(whiteCoordinates);
//        System.out.println("czarne " +checkNextBlack(board.getSize(),blackCoordinates));
//        System.out.println("biale " +checkNextWhite(whiteCoordinates,boardWidth));
//        System.out.println("rzad " +checkIfRowValueExist(boardWidth));
//        System.out.println("kol " + checkIfColValueExist(boardWidth));

        if(checkNextBlack(board.getSize(),blackCoordinates)
        && checkNextWhite(whiteCoordinates,boardWidth)
        && checkIfRowValueExist(boardWidth)
        && checkIfColValueExist(boardWidth)){

            System.out.println("wszystkie warunki poprawne");
            return true;
        }
        System.out.println("blad");
        return false;
    }

    private boolean checkIfRowValueExist(int boardWidth){
        for(int j=0; j<board.getSize(); ++j) {
            if(j%boardWidth == 0) {
                for(int i=1; i<boardWidth; ++i) {
                    if(board.getValueFromBoard(j) == board.getValueFromBoard(j+i)
                            && board.getColorFromBoard(j+i) != true
                            && board.getColorFromBoard(j) != true) {
                        //System.out.println("blad lewo " + j + " " + board.getValueFromBoard(j));
                        return false;
                    }
                }
            }
            else if(j%boardWidth == boardWidth-1) {
                for(int i=1; i<boardWidth; ++i) {
                    if(board.getValueFromBoard(j) == board.getValueFromBoard(j-i)
                            && board.getColorFromBoard(j-i) != true
                            && board.getColorFromBoard(j) != true) {
                        //System.out.println("blad prawo " + j + " " + board.getValueFromBoard(j));
                        return false;
                    }
                }
            }
            else {
                int left = j%boardWidth;
                int right = boardWidth - left - 1;
                for(int i=1; i<=left; ++i) {
                    if(board.getValueFromBoard(j) == board.getValueFromBoard(j-i)
                            && board.getColorFromBoard(j-i) != true
                            && board.getColorFromBoard(j) != true) {
                        //System.out.println("blad w lewo srodek " + j + " " + board.getValueFromBoard(j));
                        return false;
                    }
                }
                for(int i=1; i<=right; ++i) {
                    if(board.getValueFromBoard(j) == board.getValueFromBoard(j+i)
                            && board.getColorFromBoard(j+i) != true
                            && board.getColorFromBoard(j) != true) {
                        //System.out.println("blad w prawo srodek " + j + " " + board.getValueFromBoard(j));
                        return false;
                    }
                }
            }
        }
        return true;
    }
    /* funkcja zwraca true jezeli nie wystąpiła dana liczba w kolumnie */
    private boolean checkIfColValueExist(int boardWidth){
        for(int j=0; j<board.getSize(); ++j) {
            int mod = j%boardWidth;
            for(int i=0; i<board.getSize(); ++i) {
                if(mod == i%boardWidth && board.getValueFromBoard(j) == board.getValueFromBoard(i)
                        && board.getColorFromBoard(j) != true
                        && board.getColorFromBoard(i) != true
                        && i!=j) {
                    //System.out.println("blad kol " + j + " " + board.getValueFromBoard(j));
                    return false;
                }
            }
        }
        return true;
    }
    /* jeżeli true, plansza poprawna */
    private boolean checkNextBlack(int boardsize, ArrayList<Integer> blackCoordinates) {
        int boardWidth = (int)Math.sqrt(board.getSize());
        for(int i=0; i<blackCoordinates.size(); ++i) {;
            if(!isThereBlackArea(boardWidth, blackCoordinates.get(i), blackCoordinates)) {
                return false;
            }
        }
        return true;
    }
    /* jeżeli true, plansza poprawna */
    private boolean isThereBlackArea(int boardWidth,int coordinates,ArrayList<Integer> blackCoordinates) {
        int pixel = returnTop(coordinates, boardWidth);
        if(pixel == -1) {
            return true;
        }
        if(blackCoordinates.contains(pixel)) {
            return false;
        }

        pixel = returnBottom(coordinates, boardWidth);
        if(pixel == -1) {
            return true;
        }
        if(blackCoordinates.contains(pixel)) {
            return false;
        }

        pixel = returnNext(coordinates, boardWidth);
        if(pixel == -1) {
            return true;
        }
        if(blackCoordinates.contains(pixel)) {
            return false;
        }

        pixel = returnPrevious(coordinates, boardWidth);
        if(pixel == -1) {
            return true;
        }
        if(blackCoordinates.contains(pixel)) {
            return false;
        }
        return true;
    }
    /* jeżeli true, plansza poprawna */
    private boolean checkNextWhite(ArrayList<Integer> whiteCoordinates, int boardWidth) {
        ArrayList<Integer> checkedWhitePixels = new ArrayList<>();
        int seed = whiteCoordinates.get(0);
        checkedWhitePixels.add(seed);
        for(int i=0; i<checkedWhitePixels.size(); ++i) {
            if(whiteCoordinates.contains(returnNext(seed, boardWidth))) {
                if(!checkedWhitePixels.contains(returnNext(seed, boardWidth))) {
                    checkedWhitePixels.add(returnNext(seed, boardWidth));
                }
            }
            if(whiteCoordinates.contains(returnPrevious(seed, boardWidth))) {
                if(!checkedWhitePixels.contains(returnPrevious(seed, boardWidth))) {
                    checkedWhitePixels.add(returnPrevious(seed, boardWidth));
                }
            }
            if(whiteCoordinates.contains(returnTop(seed, boardWidth))) {
                if(!checkedWhitePixels.contains(returnTop(seed, boardWidth))) {
                    checkedWhitePixels.add(returnTop(seed, boardWidth));
                }
            }
            if(whiteCoordinates.contains(returnBottom(seed, boardWidth))) {
                if(!checkedWhitePixels.contains(returnBottom(seed, boardWidth))) {
                    checkedWhitePixels.add(returnBottom(seed, boardWidth));
                }
            }
            if(i+1<checkedWhitePixels.size()) {
                seed = checkedWhitePixels.get(i+1);
            }
        }
        if(checkedWhitePixels.size() == whiteCoordinates.size()) {
            return true;
        }
        return false;
    }

    private int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private int returnTop(int coordinates, int boardWidth) {
        return coordinates+boardWidth;
    }

    private int returnBottom(int coordinates, int boardWidth) {
        return coordinates-boardWidth;
    }

    private int returnPrevious(int coordinates, int boardWidth) {
        if(coordinates%boardWidth == 0) {
            return -1;
        }
        return coordinates-1;
    }

    private int returnNext(int coordinates, int boardWidth) {
        if((coordinates+1)%boardWidth == 0) {
            return -1;
        }
        return coordinates+1;
    }


}
