package prm2t;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Generator generator = new Generator(0);
        generator.generateFromText("resources/plansza.txt");
//       System.out.println(generator.getValueFromBoard(24));
//        System.out.println(generator.getColorFromBoard(24));
        generator.generateRandom(0);
       //System.out.println(generator.xxx(4,4));
    }
}
