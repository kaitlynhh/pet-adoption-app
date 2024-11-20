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
    private static int WIDTH = 600;
    private static int HEIGHT = 400;
    private ImageIcon usersetupImage;
    private JLabel imageAsLabel;
    private JPanel setupPanel;
    private Shelter shelter;
    private User currentUser;
    private JsonReader reader;
    private JsonWriter writer;

    private JPanel mainPanel;
    private JTextArea displayArea;
    private JTextField field;
    private JButton submitButton;
    private JButton backToMenuButton;

    // constructor - set up main GUI window
    public AdoptionAppGUI() throws FileNotFoundException {
        setTitle("Stray Pets Adoption App");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        shelter = new Shelter();
        writer = new JsonWriter(JSON_STORE);
        reader = new JsonReader(JSON_STORE);

        initShelter();
        initUI();
        initializeCurrentUser();

    }

    // initializes the main UI components
    public void initUI() {
        mainPanel = new JPanel(new BorderLayout());
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        field = new JTextField();
        submitButton = new JButton("submit");
        backToMenuButton = new JButton("Back to Menu"); // Initialize the Back to Menu button
        backToMenuButton.setVisible(false); // Initially hidden

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(field, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        inputPanel.add(backToMenuButton, BorderLayout.WEST);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        add(mainPanel);
        setJMenuBar(createMenuBar());
        submitButton.addActionListener(new SubmitButtonListener());
        backToMenuButton.addActionListener(e -> displayMainMenu());
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
        // Hide Back to Menu button and show Submit button
        backToMenuButton.setVisible(false);
        submitButton.setVisible(true);        
    }

    private void handleMenuSelection(String choice) {
        switch (choice) {
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
        displayArea.append("\nClick 'back to menu' button to return\n");
        backToMenuButton.setVisible(true);
        submitButton.setVisible(false);
    }


    // Allows the user to submit an adoption application
    private void submitAdoptionApplication() {
        displayArea.setText("\n--- Submit Adoption Application ---");

        displayArea.append("\nEnter the name of the pet you want to adopt from the list:\n");
        for (Pet pet : shelter.getPets()) {
            if (pet.isAvailable()) {
                displayArea.append(pet.getPetName() + "\n");
            }
        }
        displayArea.append("n Type the name below and click submit.\n");
        submitButton.setVisible(true);
        submitButton.addActionListener(e -> {
            String petName = field.getText();
            Pet selectedPet = shelter.getPetByName(petName);

            if (selectedPet == null || !selectedPet.isAvailable()) {
                displayArea.append("Invalid pet name.");
            } else {
                AdoptApplication application = new AdoptApplication(currentUser.getName(), petName);
                currentUser.submitApplication(application);
                application.updateStatus("submitted");
                currentUser.addAdoptedPets(selectedPet);
                displayArea.append("Your application for " + selectedPet.getPetName() + " has been submitted.");
            }
        });
        displayArea.append("\nClick 'back to menu' button to return\n");
        backToMenuButton.setVisible(true);
        submitButton.setVisible(false);
    }
    
    // Displays user's applications
    private void viewUserApplications() {
        // if (currentUser == null) {
        //     displayArea.setText("No user logged in. Please start by entering user details.\n");
        //     return;
        // }
    
        displayArea.setText("--- My Adoption Applications ---\n");
        String userName = currentUser.getName();
        displayArea.append("\n--- Applications for " + userName + "---");
        if (currentUser.getApplications().isEmpty()) {
            displayArea.append("You haven't submitted any adoption applications.\n");
        } else {
            for (AdoptApplication application : currentUser.getApplications()) {
                displayArea.append("\nApplication for Pet: " + application.getPetname()
                        + " (Status: " + application.getStatus() + ")\n");
            }
        }

        backToMenuButton.setVisible(true);
        submitButton.setVisible(false);
    }
    

    private void reportStrayPet() {
        displayArea.setText("--- Add a Stray Pet to Our Shelter---\n");
        displayArea.append("Enter pet name(Give him/her a cute name!): ");
        // TODO
        String petName = field.getText().trim();
        displayArea.append("Enter pet type (e.g. Dog, Cat):");
        String petSpecies = field.getText().trim();
        backToMenuButton.setVisible(true);
        submitButton.setVisible(false);

    }

    private void uploadAdoptStory() {
        displayArea.setText("\n--- Upload Adopt Story ---");

        if (currentUser == null || currentUser.getAdoptedPets().isEmpty()) {
            displayArea.setText("You haven't adopted any pets or we can't find user log in.");
            backToMenuButton.setVisible(true);
            submitButton.setVisible(false);
            return;
        }
        displayArea.setText("Please write your adoption story and click submit:\n");
        backToMenuButton.setVisible(true);
        submitButton.setVisible(true);

        submitButton.addActionListener(e -> {
            String story = field.getText();
            currentUser.addAdoptStory(story);
            displayArea.append("\nYour adoption story has been uploaded successfully!\n");
            
            field.setText(""); // Clear input
        });

    }

    private void saveUserData() {
        try {
            writer.open();
            writer.write(currentUser);
            writer.close();
            displayArea.append("\nData saved successfully.\n");
        } catch (FileNotFoundException e) {
            displayArea.append("\nError: Unable to save data.\n");
        }
    }

    private void loadUserData() {
        try {
            currentUser = reader.read();
            displayArea.append("\nData loaded successfully.\n");
        } catch (IOException e) {
            displayArea.append("\nError: Unable to load data.\n");
        }
    }

    // Prompt to Save Before Exit
    private void promptSaveBeforeExit() {
        int response = JOptionPane.showConfirmDialog(this,
                "Do you want to save your data before exiting?",
                "Exit",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            saveUserData();
        }
        System.exit(0);
    }

    // Prompts the user to input their name and role
    private void initializeCurrentUser() {

        JPanel userForm = new JPanel(new GridLayout(2, 2));
        JTextField nameField = new JTextField();
        JComboBox<String> roleField = new JComboBox<>(new String[]{"Adopter", "Staff"});

        userForm.add(new JLabel("Enter your name:"));
        userForm.add(nameField);
        userForm.add(new JLabel("Select your role:"));
        userForm.add(roleField);

        int result = JOptionPane.showConfirmDialog(this, userForm, "User Setup",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String role = (String) roleField.getSelectedItem();

            if (!name.isEmpty() && role != null) {
                currentUser = new User(name, role);
                displayArea.append("Welcome, " + name + " (" + role + ")!\n");
            } else {
                JOptionPane.showMessageDialog(this, "Both name and role are required.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                initializeCurrentUser(); // Retry if input is invalid
            }
        } else {
            System.exit(0); // Exit if the user cancels
        }
    }


    // Listener for Submit Button
    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userInput = field.getText();
            handleMenuSelection(userInput);
            field.setText("");
        }
    }

    // Helper method to clear old listeners before adding a new one
    private void clearPreviousListeners(JButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }

    private void loadImages() {
        String sep = System.getProperty("file.separator");
        usersetupImage = new ImageIcon(System.getProperty("user.dir") + sep
				+ "images" + sep + "dog's paw.jpg");
    }

    private void setPawImage() {
		imageAsLabel = new JLabel(usersetupImage);
		mainPanel.add(imageAsLabel);
    }

}
