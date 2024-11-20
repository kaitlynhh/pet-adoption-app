package ui;

import java.io.FileNotFoundException;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to my project!");
        SwingUtilities.invokeLater(() -> {
            try {
                new AdoptionAppGUI().setVisible(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        // new AdoptionAppGUI();
        // new AdoptionApp();
    }
}
