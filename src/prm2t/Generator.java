package prm2t;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    private int dificulty = 0;
    private final ArrayList<Values> board = new ArrayList<Values>();

    public Generator(int dificulty) {
        this.dificulty = dificulty;
    }
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
    /* pomocnicze gettery, możliwe że będą w przyszłości usunięte */
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
        int boardSize = 0;
        int blackSize = 0;
        if(dificulty==0) {
            boardSize = 25;
            blackSize = getRandomNumber(7, 7);
        }
        if(dificulty==1) {
            boardSize = 36;
            blackSize = getRandomNumber(7, 7);
        }
        if(dificulty==2) {
            boardSize = 49;
            blackSize = getRandomNumber(7, 7);
        }
            ArrayList<Integer> blackCoordinates = new ArrayList<>();
            ArrayList<Integer> whiteCoordinates = new ArrayList<>();
            boolean result1 = false;
            boolean result2 = false;

            do {
                blackCoordinates.clear();
                whiteCoordinates.clear();
                for (int i = 0; i < blackSize; ++i) {
                    int tempCoordinates = getRandomNumber(0, boardSize - 1);
                    if (blackCoordinates.contains(tempCoordinates)) {
                        --i;
                    } else {
                        blackCoordinates.add(tempCoordinates);
                    }
                }
                for (int i = 0; i < boardSize; ++i) {
                    if (!blackCoordinates.contains(i)) {
                        whiteCoordinates.add(i);
                    }

                }
                Collections.sort(blackCoordinates);
                Collections.sort(whiteCoordinates);
                result1 = checkNextBlack(boardSize, blackCoordinates);
                result2 = checkNextWhite(whiteCoordinates);
            } while(!result1 || !result2);
//            System.out.println(whiteCoordinates);
//            System.out.println(blackCoordinates);

            for(int i=0; i<whiteCoordinates.size(); ++i) {
                int width = (int)(Math.sqrt(boardSize));
                int randomNumber = getRandomNumber(1,width);
                //board.add(whiteCoordinates.get(i),new Values(randomNumber,false));
                /* w tym miejscu pozostaje do zrobienia funkcja dodająca losowe cyfry w białe miejsca, bez powtorzen
                * nastepnie w czarne miejsca z wystepujacymi juz cyframi w rzedzie lub wierszu  */
            }
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
    private boolean isThereBlackArea(int boardsize,int coordinates,ArrayList<Integer> blackCoordinates) {
        int pixel = returnTop(coordinates);
        if(pixel == -1) {
            return true;
        }
        if(blackCoordinates.contains(pixel)) {
            return false;
        }

        pixel = returnBottom(coordinates);
        if(pixel == -1) {
            return true;
        }
        if(blackCoordinates.contains(pixel)) {
            return false;
        }

        pixel = returnNext(coordinates);
        if(pixel == -1) {
            return true;
        }
        if(blackCoordinates.contains(pixel)) {
            return false;
        }

        pixel = returnPrevious(coordinates);
        if(pixel == -1) {
            return true;
        }
        if(blackCoordinates.contains(pixel)) {
            return false;
        }
        return true;
    }
    /* jeżeli true, plansza poprawna */
    private boolean checkNextWhite(ArrayList<Integer> whiteCoordinates) {
        ArrayList<Integer> checkedWhitePixels = new ArrayList<>();
        int seed = whiteCoordinates.get(0);
        checkedWhitePixels.add(seed);
        for(int i = 0; i<checkedWhitePixels.size(); ++i) {
            if(whiteCoordinates.contains(returnNext(seed))) {
                if(!checkedWhitePixels.contains(returnNext(seed))) {
                    checkedWhitePixels.add(returnNext(seed));
                }
            }
            if(whiteCoordinates.contains(returnPrevious(seed))) {
                if(!checkedWhitePixels.contains(returnPrevious(seed))) {
                    checkedWhitePixels.add(returnPrevious(seed));
                }
            }
            if(whiteCoordinates.contains(returnTop(seed))) {
                if(!checkedWhitePixels.contains(returnTop(seed))) {
                    checkedWhitePixels.add(returnTop(seed));
                }
            }
            if(whiteCoordinates.contains(returnBottom(seed))) {
                if(!checkedWhitePixels.contains(returnBottom(seed))) {
                    checkedWhitePixels.add(returnBottom(seed));
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

    private int returnTop(int coordinates) {
        return coordinates+5;
    }

    private int returnBottom(int coordinates) {
        return coordinates-5;
    }

    private int returnPrevious(int coordinates) {
        if(coordinates%5 == 0) {
            return -1;
        }
        return coordinates-1;
    }

    private int returnNext(int coordinates) {
        if((coordinates+1)%5 == 0) {
            return -1;
        }
        return coordinates+1;
    }


}
