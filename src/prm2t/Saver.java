package prm2t;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.lang.Math;


public class Saver {
    Board board;
    Generator generator; //do usuniecia kiedy w Board beda odpowiednie metody

    public Saver(Board board ,Generator generator){
        this.board = board;
        this.generator = generator; //do usuniecia kiedy w Board beda odpowiednie metody
    }
    /** zapisuje aktualną planszę do pliku txt.*/
    public void saveBoard(String directory){
        String text = "";
        int size = (int) Math.sqrt(board.getSize());

        for(int i=0; i<size;i++){
            for(int j=0; j<size;j++){
                if(generator.getColorFromBoard(i*size+j)){ //zamien na board.getColor
                    text += generator.getValueFromBoard(i*size+j) + "-1 "; //zamien na board.getValue
                }else{
                    text += generator.getValueFromBoard(i*size+j) + "-0 "; //zamien na board.getValue
                }
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










