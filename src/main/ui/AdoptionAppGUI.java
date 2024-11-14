package ui;

import model.AdoptApplication;
import model.Pet;
import model.Shelter;
import model.User;

import persistence.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AdoptionAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/myApplications.json";
    private Shelter shelter;
    private User currectUser;
    private JsonReader reader;
    private JsonWriter writer;

    private JPanel mainPanel;
    private JTextArea displayArea;
    private JTextField field;
    private JButton submitButton;

    // constructor - set up main GUI window
    public AdoptionAppGUI() throws FileNotFoundException {
        setTitle("Stray Pets Adoption App");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        shelter = new Shelter();
        writer = new JsonWriter(JSON_STORE);
        reader = new JsonReader(JSON_STORE);

    }

    
}
