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

        initShelter();
        initUI();

    }

    // initializes the main UI components
    public void initUI() {
        mainPanel = new JPanel(new BorderLayout());
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        field = new JTextField();
        submitButton = new JButton("submit");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(field, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        add(mainPanel);
        setJMenuBar(createMenuBar());
        submitButton.addActionListener(new SubmitButtonListener());
        displayMainMenu();
    }

    // displays the main menu with different function options.
    private void displayMainMenu() {
        displayArea.setText("Welcome to Stray Pets Adoption App!\n"
        + "1. View Available Pets\n"
        + "2. Submit Adoption Application\n"
        + "3. View Your Applications\n"
        + "4. Report a Stray Pet and Add it to our Shelter\n"
        + "5. Upload Adopt Story\n"
        + "0. Exit\n"
        + "Please enter your choice:");
    }

    private void handleMenuSelection(String choice) {
        switch(choice) {
            case "1":
            viewAvailablePets();
            break;
            case "2":
            submitAdoptionApplication();
            break;
            case "3":
            viewUserApplications();
            break;
            case "4":
            reportStrayPet();
            break;
            case "5":
            uploadAdoptStory();
            break;
            case "0":
            promptSaveBeforeExit();
            break;

            default:
            displayArea.append("\n Invalid choice, please try again.");
        }
    }

    private void viewAvailablePets() {
        displayArea.setText("--- Available Pets ---\n");
        if (shelter.getPets().isEmpty()) {
            displayArea.append("No pets are currently available for adoption.\n");
        } else {
            for (Pet pet : shelter.getPets()) {
                if (pet.isAvailable()) {
                    displayArea.append(pet.getPetName() + " - " + pet.getSpecies() + " (" + pet.getBreed() + ")\n");
                }
            }
        }
    }

    // Create a Menu Bar with options to load, save and exit.
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem loadItem = new JMenuItem("Load Data");
        loadItem.addActionListener(e -> loadUserData());
        JMenuItem saveItem = new JMenuItem("Save Data");
        saveItem.addActionListener(e -> saveUserData());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        return menuBar;
    }

    // initializes the Shelter with some pets in our shelter
    public void initShelter() {
        Pet otto = new Pet("Otto", "Male", "Dog", "Golden");
        Pet bob = new Pet("Bob", "Male", "cat", "Birman");
        Pet boots = new Pet("Boots", "Female", "Dog", "Corgi");
        shelter.addPet(otto);
        shelter.addPet(bob);
        shelter.addPet(boots);
    }

    // Allows the user to submit an adoption application
    private void submitAdoptionApplication() {
        displayArea.setText("Enter the name of the pet you want to adopt:\n");
        for (Pet pet : shelter.getPets()) {
            if (pet.isAvailable()) {
                displayArea.append(pet.getPetName() + "\n");
            }
        }
    } // TODO: check this method
    
    // Displays user's applications
    private void viewUserApplications() {
        displayArea.setText("--- My Adoption Applications ---\n");
        if (currentUser.getApplications().isEmpty()) {
            displayArea.append("You haven't submitted any applications.\n");
        } else {
            for (AdoptApplication app : currentUser.getApplications()) {
                displayArea.append("Application for " + app.getPetname() + " (Status: " + app.getStatus() + ")\n");
            }
        }
    }

}
