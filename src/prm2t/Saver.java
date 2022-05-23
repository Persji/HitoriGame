package prm2t;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.lang.Math;

//Klasa Saver:
//
//Board board: aktualna plansza
//
//metody:
//saveBoard - zapisuje aktualną planszę tak by można było później kontynuować rozgrywkę
//exportBoard - zapisuje aktualną planszę w formie gotowej do wydruku
//checkDirectory - sprawdza czy istnieje dana ścieżka

//1-0 4-1 4-0 5-0 2-0
//5-0 4-0 2-0 2-1 3-0
//2-0 2-1 3-0 4-0 3-1
//3-0 1-0 1-1 2-0 4-0
//5-1 2-0 1-0 5-1 5-0

public class Saver {
    Board board;
    Generator generator; //do usuniecia kiedy w Board beda odpowiednie metody

    public Saver(Board board ,Generator generator){
        this.board = board;
        this.generator = generator; //do usuniecia kiedy w Board beda odpowiednie metody
    }

    public void saveBoard(String directory){
        String text = "";
        int size = (int) Math.sqrt(board.getSize());

        for(int i=0; i<size;i++){ //rzędy
            for(int j=0; j<size;j++){ //kolumny
                text += generator.getValueFromBoard(i,j) + "-" + generator.getColorFromBoard(i,j) + " "; //zamien na board.getValue i board.getColor
            }
            text += "\n";
        }
        try {
            FileWriter writer = new FileWriter(directory);
            writer.write(text);
            writer.close();
        }catch(IOException e){
            System.out.println("Bład przy zapisie planszy");
            e.printStackTrace();
        }
    }

    public void exportBoard(String directory){
        //zapisz plansze w formie gotowej do wydruku
    }

    /**sprawdza czy folder jest dostepny */
    boolean checkDirectory(String directory){
        return Files.isDirectory(Paths.get(directory))  ;
    }

}










