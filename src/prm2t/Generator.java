package prm2t;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    private HashMap<Coordinates,Values> board = new HashMap<>();

    public Generator(int dificulty) {
        this.dificulty = dificulty;
    }

    public void generateFromText(String path) throws IOException {

        int j = -1;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                for (int i = 0;i< words.length;++i) {
                    String[] value = words[i].split("-");
                    int correctValue = Integer.parseInt(value[0]);
                    int correctColor = Integer.parseInt(value[1]);
                    if(i%5 == 0) {
                        ++j;
                    }
                    board.put(new Coordinates(j,i%5), new Values(correctValue,correctColor));
                }
                line = bufferedReader.readLine();
            }
        } catch(Exception e) {
            System.out.println("Nie odnaleziono wskazanego pliku");
        }

    }

    public int getValueFromBoard(int row, int col) {

        Values v = board.get(new Coordinates(row,col));
        return v.getValue();
   }
    public int getColorFromBoard(int row, int col) {
        Values v = board.get(new Coordinates(row,col));
        return v.getColor();
    }

//    public int xxx(int row, int col) {
//        Coordinates key = new Coordinates(row,col);
//        Values value = board.get(key);
//        System.out.println(value.getValue());
//        return value.getColor();
//    }

}
