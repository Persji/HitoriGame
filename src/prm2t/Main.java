package prm2t;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main implements ActionListener {
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();


                Generator generator = new Generator();
                //generator.generateFromText("resources\\plansza.txt");
                generator.generateRandom(gui.getDiff());

                Board board = new Board(generator.getBoard());

                Saver saver = new Saver(board, generator);
                saver.saveBoard("resources/savedBoard.txt");



    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
