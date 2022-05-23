package prm2t;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI implements ActionListener {

    private JFrame frame;
    private JLabel safe_label;
    private JLabel load_label;
    private JLabel solve_label;
    private JPanel panel;
    private JButton safe_button;
    private JButton load_button;
    private JButton solve_button;
    private JRadioButton easy;
    private JRadioButton normal;
    private JRadioButton hard;
    private ButtonGroup difficulty;

    public GUI(){

        // panel - główny 'widok' GUI

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        panel.setLayout(new GridLayout(0, 1));

        easy = new JRadioButton("Easy");
        easy.addActionListener(this);
        panel.add(easy);

        normal = new JRadioButton("Normal");
        normal.addActionListener(this);
        panel.add(normal);

        hard = new JRadioButton("Hard");
        hard.addActionListener(this);
        panel.add(hard);

        difficulty = new ButtonGroup();
        difficulty.add(easy);
        difficulty.add(normal);
        difficulty.add(hard);

        //safe_button - przycisk do zapisu gry

        safe_button = new JButton("Safe");
        safe_button.addActionListener(this);
        safe_label = new JLabel("x1");
        panel.add(safe_button);
        panel.add(safe_label);

        //load_button - przycisk wczytania planszy

        load_button = new JButton("Load");
        load_button.addActionListener(this);
        load_label = new JLabel("x2");
        panel.add(load_button);
        panel.add(load_label);

        //solve_button - przycisk spr rozwiązanie planszy

        solve_button = new JButton("Solve");
        solve_button.addActionListener(this);
        solve_label = new JLabel("x3");
        panel.add(solve_button);
        panel.add(solve_label);

        //frame - okno GUI

        frame = new JFrame();
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hitori Game");
        frame.pack();
        frame.setVisible(true);

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