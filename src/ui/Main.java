package ui;

import java.io.IOException;

import model.Database;

public class Main {
    private Database back;

    public static void main(String[] args) throws Exception {
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