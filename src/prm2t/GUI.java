package prm2t;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI implements ActionListener {

    private JFrame frame;
    private JLabel title;
    private JPanel north_panel;
    private JPanel west_panel;
    private JPanel central_panel;
    private JButton safe_button;
    private JButton load_button;
    private JButton solve_button;
    private JRadioButton easy;
    private JRadioButton normal;
    private JRadioButton hard;
    private ButtonGroup difficulty;
    private JLabel board;

    public GUI(){

        //frame - okno GUI
        // panel - główny 'widok' GUI

        title = new JLabel();
        title.setText("HITORI");
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setFont(new Font("Monospaced",Font.PLAIN,50));

        board = new JLabel();
        board.setText("Miejsce na plansze");
        board.setHorizontalTextPosition(JLabel.CENTER);
        board.setVerticalTextPosition(JLabel.CENTER);
        board.setFont(new Font("Monospaced",Font.PLAIN,50));

        north_panel = new JPanel();
        west_panel = new JPanel();
        central_panel = new JPanel();

        frame = new JFrame();
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setLayout(new BorderLayout(5,5));
        frame.add(central_panel,BorderLayout.CENTER);
        frame.add(west_panel,BorderLayout.WEST);
        frame.add(north_panel, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hitori Game");
        frame.pack();
        frame.setVisible(true);

        north_panel.setBackground(Color.GRAY);
        north_panel.setPreferredSize(new Dimension(100, 100));
        west_panel.setBackground(Color.darkGray);
        west_panel.setPreferredSize(new Dimension(100,100));
        central_panel.setBackground(Color.lightGray);
        north_panel.setPreferredSize(new Dimension(100,100));

        north_panel.add(title);
        central_panel.add(board);

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

        safe_button = new JButton("Safe");
        safe_button.addActionListener(this);
        west_panel.add(safe_button);

        //load_button - przycisk wczytania planszy

        load_button = new JButton("Load");
        load_button.addActionListener(this);
        west_panel.add(load_button);

        //solve_button - przycisk spr rozwiązanie planszy

        solve_button = new JButton("Solve");
        solve_button.addActionListener(this);
        west_panel.add(solve_button);

    }
    public static void test(String[] args){
        new GUI();
    }

    //akcje przycisków
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==easy){
            System.out.println("Eeasy");
        }
        else if(e.getSource()==normal){
            System.out.println("Normal");
        }
        else if(e.getSource()==hard){
            System.out.println("Hard");
        }
        if(e.getSource()==load_button){
            JFileChooser fileChooser = new JFileChooser();

            int response = fileChooser.showOpenDialog(null); //wybiera plik do otwarcia/wczytania do gry

            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(file);
            }
        }
        if(e.getSource()==safe_button){
            JFileChooser fileChooser = new JFileChooser();

            int response = fileChooser.showSaveDialog(null); //wybiera plik do zapisu gry

            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(file);
            }
        }

    }
}