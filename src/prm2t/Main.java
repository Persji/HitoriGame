package prm2t;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main implements ActionListener {
    GUI gui;
    Saver saver;
    Generator generator;
    Board board;
    public void initGUI(){
        gui = new GUI(this);
    }

    public GUI getGui() {
        return gui;
    }



    public static void  main(String[] args) throws IOException {
                Main main = new Main();
                main.initGUI();
                GUI gui = main.getGui();
                main.generator = new Generator();
                main.generator.generateFromText("resources\\plansza.txt");
                //generator.generateRandom(gui.getDiff());

                main.board = new Board(main.generator.getBoard());
                main.gui.update();

                main.saver = new Saver(main.board, main.generator);
                //main.saver.saveBoard("resources/savedBoard.txt");



    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
