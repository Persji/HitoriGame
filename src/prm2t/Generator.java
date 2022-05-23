package prm2t;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

//Klasa Generator:
//
//int dificulty - przechowuje poziom trudności czyli w zasadzie jak duża będzie plansza
//
//metody:
//generateFromText - generuje planszę podaną przez użytkownika
//generateRandom - generuje losową planszę zależną od wybranego poziomu trudności
//setDificulty - ustawia poziom trudności
//getDificulty - zwraca poziom trudności
//checkFile - sprawdza czy istnieje ścieżka wczytywanego pliku
public class Generator {

    private int dificulty = 0;
    private final ArrayList<Values> board = new ArrayList<Values>();

    public Generator(int dificulty) {
        this.dificulty = dificulty;
    }

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
            return v.getCoordinates();
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

    public void generateRandom(int dificulty) {
        if(dificulty==0) {
            int boardSize = 25;
            int blackSize = getRandomNumber(7,8);
            ArrayList<Integer> blackCoordinates = new ArrayList<>();
            for(int i=0; i<blackSize; ++i) {
                int tempCoordinates = getRandomNumber(0,boardSize-1);
                if(blackCoordinates.contains(tempCoordinates)){
                    --i;
                }
                else{
                    blackCoordinates.add(tempCoordinates);
                }
            }
            Collections.sort(blackCoordinates);
            System.out.println(blackCoordinates);
        }
        else if(dificulty==1){

        }
        else{

        }
    }

    public int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
