package prm2t;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Generator generator = new Generator(0);
        generator.generateFromText("resources\\plansza.txt");
        generator.generateRandom(0);

    }
}
