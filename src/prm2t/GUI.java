package prm2t;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GUI implements ActionListener {

    private Main al;
    private JFrame frame;
    private JLabel title;
    private JPanel north_panel;
    private JPanel west_panel;
    private JPanel central_panel;
    private JButton save_button;
    private JButton load_button;
    private JButton solve_button;

    private JButton generate_button;
    private JRadioButton easy;
    private JRadioButton normal;
    private JRadioButton hard;
    private ButtonGroup difficulty;
    private JButton[] buttons;
    private JDialog resultWindow;
    private JLabel resultLabel;

    private Dificulty diff = Dificulty.NONE;


    private boolean rdy=false;

    public boolean isRdy() {
        return rdy;
    }

    public int getDiff() {
        return diff.getDiff();
    }
    private enum Dificulty {
        NONE(-1),EASY(0),NORMAL(1),HARD(2);

        private int diff;
        Dificulty(int diff) {
            this.diff = diff;
        }
        public int getDiff(){
            return this.diff;
        }
    }



    public GUI(Main main){

        //frame - okno GUI
        // panel - główny 'widok' GUI
        al = main;
        title = new JLabel();
        title.setText("HITORI");
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setFont(new Font("Monospaced",Font.PLAIN,50));

        north_panel = new JPanel();
        west_panel = new JPanel();
        central_panel = new JPanel();

        frame = new JFrame();
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setLocation(400,100);
        frame.setLayout(new BorderLayout(5,5));
        frame.add(central_panel,BorderLayout.CENTER);
        frame.add(west_panel,BorderLayout.WEST);
        frame.add(north_panel, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hitori Game");
        frame.pack();


        north_panel.setBackground(Color.GRAY);
        north_panel.setPreferredSize(new Dimension(100, 100));
        west_panel.setBackground(Color.darkGray);
        west_panel.setPreferredSize(new Dimension(100,100));
        central_panel.setBackground(Color.lightGray);
        north_panel.setPreferredSize(new Dimension(100,100));

        north_panel.add(title);
        //central_panel.add(board);

        easy = new JRadioButton("Easy");
        easy.addActionListener(this);
        west_panel.add(easy);

        normal = new JRadioButton("Normal");
        normal.addActionListener(this);
        west_panel.add(normal);

        hard = new JRadioButton("Hard");
        hard.addActionListener(this);
        west_panel.add(hard);

        difficulty = new ButtonGroup();
        difficulty.add(easy);
        difficulty.add(normal);
        difficulty.add(hard);

        //safe_button - przycisk do zapisu gry

        save_button = new JButton("Save");
        save_button.addActionListener(this);
        west_panel.add(save_button);

        //load_button - przycisk wczytania planszy

        load_button = new JButton("Load");
        load_button.addActionListener(this);
        west_panel.add(load_button);

        //solve_button - przycisk spr rozwiązanie planszy

        solve_button = new JButton("Solve");
        solve_button.addActionListener(this);
        west_panel.add(solve_button);

        generate_button = new JButton("Generate");
        generate_button.addActionListener(this);
        west_panel.add(generate_button);

        //okienko wygranej

        resultWindow = new JDialog(frame);
        resultWindow.setLayout(new FlowLayout());
        JButton okButton = new JButton("ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultWindow.setVisible(false);
            }
        });
        resultLabel = new JLabel("");
        resultWindow.add(resultLabel);
        resultWindow.add(okButton);
        resultWindow.setSize(250,100);
        resultWindow.setLocation(775,450);

        //plansza

        buttons = new JButton[al.board.getSize()];
        central_panel.setLayout(new GridLayout((int) Math.sqrt(buttons.length),(int) Math.sqrt(buttons.length)));
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setBackground(Color.gray);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttons[i].setForeground(Color.black);
            buttons[i].setFocusPainted(false);
            int finalI = i;
            buttons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(e.getButton() == MouseEvent.BUTTON1) {
                        if (buttons[finalI].getBackground() == Color.white || buttons[finalI].getBackground() == Color.gray) {
                            buttons[finalI].setBackground(Color.black);
                        } else {
                            buttons[finalI].setBackground(Color.gray);
                        }
                        al.board.changeColor(finalI);
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        if(buttons[finalI].getBackground() == Color.white) {
                            buttons[finalI].setBackground(Color.gray);
                        } else{
                            buttons[finalI].setBackground(Color.white);
                        }
                    }
                }
            });
            central_panel.add(buttons[i]);
        }
        setButtons();


        frame.setVisible(true);
    }

    //akcje przycisków
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==easy){
            System.out.println("Easy");
            diff = Dificulty.EASY;
        }
        else if(e.getSource()==normal){
            System.out.println("Normal");
            diff = Dificulty.NORMAL;
        }
        else if(e.getSource()==hard){
            System.out.println("Hard");
            diff = Dificulty.HARD;
        }
        if(e.getSource()==load_button){
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null); //wybiera plik do otwarcia/wczytania do gry

            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    al.generator.generateFromText(file.toString());
                    al.board.updateBoard(al.generator.getBoard());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                    System.out.println("Wczytano " + file);
                    setButtons();
            }
        }
        if(e.getSource()==save_button){
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showSaveDialog(null); //wybiera plik do zapisu gry

            if(response == JFileChooser.APPROVE_OPTION){
                String file = new File(fileChooser.getSelectedFile().getAbsolutePath()).toString();
                String[] fileSplit = file.split("\\.");

                if(fileSplit.length == 1 || !Objects.equals(fileSplit[fileSplit.length - 1], "txt")){  //jesli plik nie ma lub ma złe rozszerzenie dodaj rozszerzenie .txt
                    file += ".txt";
                }
                if(!file.equals("")){
                    al.saver.saveBoard(file);
                    System.out.println("Zapisano do " + file);
                }
            }
        }
        if(e.getSource()==generate_button && getDiff() != -1){
            this.rdy = true;
            al.generator.generateRandom(0);
            al.board.updateBoard(al.generator.getBoard());
            setButtons();
        } else if(e.getSource()==generate_button && getDiff() == -1) {
            System.out.println("Wybierz trudność");
        }
        if(e.getSource()==solve_button){
            if(al.solver.checkIfBoardIsCorrect()) {
            resultLabel.setText("Rozwiązanie poprawne. Gratulacje!");
            } else {
                resultLabel.setText("Rozwiązanie niepoprawne. Próbuj dalej");
            }
            resultWindow.setVisible(true);
        }
    }

    private void setButtons(){
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(String.valueOf(al.generator.getValueFromBoard(i)));
            if(al.generator.getColorFromBoard(i)) {
                buttons[i].setBackground(Color.black);
            }else{
                buttons[i].setBackground(Color.gray);
            }
        }
    }

}