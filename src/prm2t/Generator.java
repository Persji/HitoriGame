package prm2t;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    private int dificulty = 0;
    private final ArrayList<Values> board = new ArrayList<Values>();

    public List<Values> getBoard(){return board;}

    /* funkcja generujaca plansze z pliku txt */
    public void generateFromText(String path) throws IOException {

        int j = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                for (int i = 0;i< words.length;++i) {
                    String[] value = words[i].split("-");
                    int correctValue = Integer.parseInt(value[0]);
                    int correctColor = Integer.parseInt(value[1]);
                    if(correctColor == 0) {
                        board.add(j, new Values(correctValue,false));
                    }
                    else{
                        board.add(j, new Values(correctValue,true));
                    }
                    ++j;
                }
                line = bufferedReader.readLine();
            }

        } catch(Exception e) {
            System.out.println("Nie odnaleziono wskazanego pliku");
        }

    }
    public int getValueFromBoard(int coordinates) {

        Values v = board.get(coordinates);
        if(v != null) {
            return v.getValue();
        } else {
            System.out.println("Podane koordynaty wykraczają poza plansze");
            return -1;
        }
   }

    public boolean getColorFromBoard(int coordinates) {

        Values v = board.get(coordinates);
        if(v != null) {
            return v.getColor();
        } else {
            System.out.println("Podane koordynaty wykraczają poza plansze");
            return false;
        }
    }
    /* funkcja generujaca losowa plansze na podstawie wbranego przez uzytkownika poziomu trudnosci */
    public void generateRandom(int dificulty) {
        board.clear();
        int boardSize = 0;
        int blackSize = 0;
        if(dificulty==0) {
            boardSize = 25;
            blackSize = getRandomNumber(7, 7);
        }
        if(dificulty==1) {
            boardSize = 36;
            blackSize = getRandomNumber(11, 11);
        }
        if(dificulty==2) {
            boardSize = 49;
            blackSize = getRandomNumber(14, 14);
        }

        int boardWidth = (int)Math.sqrt(boardSize);
        ArrayList<Integer> blackCoordinates = new ArrayList<>();
        ArrayList<Integer> whiteCoordinates = new ArrayList<>();
        boolean result1 = false;
        boolean result2 = false;
            do {
                blackCoordinates.clear();
                whiteCoordinates.clear();
                for (int i=0; i<blackSize; ++i) {
                    int tempCoordinates = getRandomNumber(0, boardSize-1);
                    if (blackCoordinates.contains(tempCoordinates)) {
                        --i;
                    } else {
                        blackCoordinates.add(tempCoordinates);
                    }
                }
                for (int i=0; i<boardSize; ++i) {
                    if (!blackCoordinates.contains(i)) {
                        whiteCoordinates.add(i);
                    }

                }
                Collections.sort(blackCoordinates);
                Collections.sort(whiteCoordinates);
                result1 = checkNextBlack(boardWidth, blackCoordinates);
                result2 = checkNextWhite(whiteCoordinates, boardWidth);
            } while(!result1 || !result2);
        //System.out.println(blackCoordinates);
        //System.out.println(whiteCoordinates);

            /* wpisywanie czarnych i bialych pol do boarda */
            for(int i=0; i<boardSize; ++i) {
                if (whiteCoordinates.contains(i)) {
                    board.add(i, new Values(-1, false));
                }
                else {
                    board.add(i, new Values(-1,true));
                }
            }

            /* wpisywanie liczb do bialych pol */
            for(int i=0; i<boardSize; ++i){

                if(!getColorFromBoard(i)) {
                    int random = getRandomNumber(1,boardWidth);
                    board.set(i,new Values(random, false));
                }
                if(checkIfColValueExist(i, boardWidth) && !getColorFromBoard(i) && checkIfRowValueExist(i, boardWidth)){
                    //System.out.println(i+ " " + getValueFromBoard(i));
                }
                else if(getColorFromBoard(i)){
                    board.set(i,new Values(-1, true));
                }
                else {
                    //for(int j=0; j<boardSize; ++j) {
                       // System.out.println(i+ " " + getValueFromBoard(j));
                    //}
                    //System.out.println(i+ " " + getValueFromBoard(i));
                    int random1 = getRandomNumber(1,boardWidth);
                    //System.out.println(i+ " " +random1 + " ");
                    board.set(i,new Values(random1, false));
                    --i;
                }
            }
            /* wpisywanie liczb do czarnych pol */
            for(int i=0; i<boardSize; ++i) {
                if(getValueFromBoard(i) == -1) {
                    int cords = getRandomNumber(0,boardSize-1);
                    //System.out.println(cords);
                    if(cords%boardWidth == i%boardWidth && !getColorFromBoard(cords)) {
                        board.set(i,new Values(getValueFromBoard(cords),true));
                    }
                    else {
                        --i;
                    }
                }
            }
            clearColors();

        }

    /* funkcja zwraca true jezeli nie wystąpiła dana liczba w rzedzie */
    private boolean checkIfRowValueExist(int coordinates, int boardWidth){
        if(coordinates%boardWidth == 0) {
            for(int i=1; i<boardWidth; ++i) {
                if(getValueFromBoard(coordinates) == getValueFromBoard(coordinates+i) && getValueFromBoard(coordinates+i) != -1) {
                    //System.out.println("blad lewo" + " " + getValueFromBoard(coordinates));
                    return false;
                }
            }
        }
        else if(coordinates%boardWidth == boardWidth-1) {
            for(int i=1; i<boardWidth; ++i) {
                if(getValueFromBoard(coordinates) == getValueFromBoard(coordinates-i) && getValueFromBoard(coordinates-i) != -1) {
                    //System.out.println("blad prawo" + " " + getValueFromBoard(coordinates));
                    return false;
                }
            }
        }
        else {
            int left = coordinates%boardWidth;
            int right = boardWidth - left - 1;
            for(int i=1; i<=left; ++i) {
                if(getValueFromBoard(coordinates) == getValueFromBoard(coordinates-i) && getValueFromBoard(coordinates-i) != -1) {
                    //System.out.println("blad w lewo srodek" + " " + getValueFromBoard(coordinates));
                    return false;
                }
            }
            for(int i=1; i<=right; ++i) {
                if(getValueFromBoard(coordinates) == getValueFromBoard(coordinates+i) && getValueFromBoard(coordinates+i) != -1) {
                    //System.out.println("blad w prawo srodek" + " " + getValueFromBoard(coordinates));
                    return false;
                }
            }
        }
        return true;
    }
    /* funkcja zwraca true jezeli nie wystąpiła dana liczba w kolumnie */
    private boolean checkIfColValueExist(int coordinates, int boardWidth){
        int mod = coordinates%boardWidth;
        for(int i=0; i<board.size(); ++i) {
            if(mod == i%boardWidth && getValueFromBoard(coordinates) == getValueFromBoard(i) && getValueFromBoard(coordinates) != -1 && i!=coordinates) {
                //System.out.println("blad kol" + " " + getValueFromBoard(coordinates));
                return false;
            }
        }
        return true;
    }
    /* jeżeli true, plansza poprawna */
    private boolean checkNextBlack(int boardsize, ArrayList<Integer> blackCoordinates) {
        for(int i=0; i< blackCoordinates.size(); ++i) {;
            if(!isThereBlackArea(boardsize, blackCoordinates.get(i), blackCoordinates)) {
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

    private void clearColors(){
        for (int i = 0; i < board.size(); i++) {
            board.set(i,new Values(board.get(i).getValue(), false));
        }
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
