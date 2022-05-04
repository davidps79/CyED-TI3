package ui;

import java.io.IOException;

import model.Database;

public class Main {
    private Database back;

    public static void main(String[] args) throws Exception {
        String s1 = "alexander";
        String s2 = "al";
        System.out.println("COMP: " + s1.compareTo(s2));
        Main main = new Main();
        main.start();
    }

    public Main() throws Exception {
        try {
            this.back = new Database();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void start() {
    }
}