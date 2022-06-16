package prm2t;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main implements ActionListener {
    GUI gui;
    Saver saver;
    Generator generator;
    public void initGUI(){
        GUI gui = new GUI(this);
    }

    public GUI getGui() {
        return gui;
    }

    public void saveBoard(String directory){saver.saveBoard(directory);}

    public void loadBoard(String directory) throws IOException {generator.generateFromText(directory);}

    public static void  main(String[] args) throws IOException {
                Main main = new Main();
                main.initGUI();
                GUI gui = main.getGui();
                main.generator = new Generator();
                //main.generator.generateFromText("resources\\plansza.txt");
                //generator.generateRandom(gui.getDiff());

                Board board = new Board(main.generator.getBoard());

                main.saver = new Saver(board, main.generator);
                //main.saver.saveBoard("resources/savedBoard.txt");



    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
