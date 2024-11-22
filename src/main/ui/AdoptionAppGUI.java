package ui;

import model.AdoptApplication;
import model.Pet;
import model.Shelter;
import model.User;

import persistence.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;


import java.io.FileNotFoundException;
import java.io.IOException;

// Create an AdoptionApp GUI.
// GUI extends JFrame
public class AdoptionAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/myApplications.json";
    private static int WIDTH = 600;
    private static int HEIGHT = 400;
    private ImageIcon pawImage;
    private JLabel pawLabel;
    private Shelter shelter;
    private User currentUser;
    private JsonReader reader;
    private JsonWriter writer;

    private JPanel mainPanel;
    private JTextArea displayArea;
    

    // constructor - set up main GUI window for the Adoption App.
    // EFFECTS: Initializes the shelter with default data, set up the main
    //          window and initializes UI conponents and starts the user
    //          set up.
    public AdoptionAppGUI() throws FileNotFoundException {
        setTitle("Stray Pets Adoption App");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        shelter = new Shelter();
        writer = new JsonWriter(JSON_STORE);
        reader = new JsonReader(JSON_STORE);

        initShelter();
        loadImages();
        initUI();
        SwingUtilities.invokeLater(this::initializeCurrentUser);

    }

    // initializes the main UI components
    // MODIFIES: this
    // EFFECTS: set up the initial UI components with menu bar and main menu
    public void initUI() {
        loadImages();
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        setJMenuBar(createMenuBar());
        displayMainMenu();
        
    }

    // displays the main menu with different function options.
    // MODIFIES: this
    // EFFECTS: clears the main panel and sets up the main menu with different
    //          feature buttons.
    private void displayMainMenu() {
        mainPanel.removeAll();
        // Buttons added
        JPanel menuPanel = createMenuPanel();
        mainPanel.setLayout(new BorderLayout());
        
        mainPanel.add(menuPanel, BorderLayout.CENTER);

        mainPanel.revalidate(); // refresh the UI
        mainPanel.repaint();       
    }


    // EFFECTS: Create and returns a Menu Bar with options to load, save and exit.
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

    // MODIFIES: shelter
    // EFFECTS: populates the shelter with some pets already exits.
    // initializes the Shelter with some pets in our shelter
    public void initShelter() {
        Pet otto = new Pet("Otto", "Male", "Dog", "Golden");
        Pet bob = new Pet("Bob", "Male", "cat", "Birman");
        Pet boots = new Pet("Boots", "Female", "Dog", "Corgi");
        shelter.addPet(otto);
        shelter.addPet(bob);
        shelter.addPet(boots);
    }

    // REQUIRES: shelter should have a list of pets
    // MODIFIES: this
    // EFFECTS: clears the main panel and displays a list of pets
    private void viewAvailablePets() {
        mainPanel.removeAll();
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
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
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> displayMainMenu());
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    // MODIFIES: mainPanel
    // EFFECTS: List all available pets for adoption, Allows the user to submit an adoption application
    private void submitAdoptionApplication() {
        mainPanel.removeAll();

        JTextArea instructions = new JTextArea("\n--- Submit Adoption Application ---");
        instructions.setEditable(false);
        instructions.append("\nEnter the name of the pet you want to adopt from the list:\n");
        for (Pet pet : shelter.getPets()) {
            if (pet.isAvailable()) {
                instructions.append(pet.getPetName() + "\n");
            }
        }

        JPanel inputPanel = createAdoptionForm(instructions);
        JButton backButton = createBackButton();

        // JTextField petNameField = new JTextField();
        // JButton submitButton = new JButton("Submit");
        // JButton backButton = new JButton("Back to Menu");
        // submitButton.addActionListener(e -> {
        //     String petName = petNameField.getText().trim();
        //     Pet selectedPet = shelter.getPetByName(petName);

        //     if (selectedPet == null || !selectedPet.isAvailable()) {
        //         displayArea.append("Invalid pet name.");
        //     } else {
        //         AdoptApplication application = new AdoptApplication(currentUser.getName(), petName);
        //         currentUser.submitApplication(application);
        //         application.updateStatus("submitted");
        //         currentUser.addAdoptedPets(selectedPet);
        //         instructions.append("Your application for " + selectedPet.getPetName() + " has been submitted.");
        //         petNameField.setText("");
        //     }
        // });
        // backButton.addActionListener(e -> displayMainMenu());
        // JPanel inputPanel = new JPanel(new BorderLayout());
        // inputPanel.add(petNameField, BorderLayout.CENTER);
        // inputPanel.add(submitButton, BorderLayout.EAST);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(instructions), BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    // MODIFIES: mainPanel
    // REQUIRES: clears the current view and Displays user's applications.
    private void viewUserApplications() {
        mainPanel.removeAll();
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setText("--- My Adoption Applications ---\n");
        String userName = currentUser.getName();
        displayArea.append("\n--- Applications for " + userName + "---");
        if (currentUser.getApplications().isEmpty()) {
            displayArea.append("\nYou haven't submitted any adoption applications.\n");
        } else {
            for (AdoptApplication application : currentUser.getApplications()) {
                displayArea.append("\nApplication for Pet: " + application.getPetname()
                        + " (Status: " + application.getStatus() + ")\n");
            }
        }
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> displayMainMenu());
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    // MODIFIES: mainPanel
    // EFFECTS: displays a form for reporting a stray pet. Allows user to type details
    //          add the stray pet into the shelter
    private void reportStrayPet() {
        mainPanel.removeAll();
        JTextArea instructions = new JTextArea("--- Add a Stray Pet to Our Shelter---\n");
        instructions.setEditable(false);
        JPanel form = createStrayPetForm(instructions);
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> displayMainMenu());
        form.add(backButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(instructions), BorderLayout.NORTH);
        mainPanel.add(form, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();

    }

    // MODIFIES: mainPanel
    // EFFECTS: if current user has no adopted pets, displays a message
    //          otherwise, displays a form for uploading an adopt story
    private void uploadAdoptStory() {
        mainPanel.removeAll();
        JTextArea instructions = new JTextArea("\n--- Upload Adopt Story ---");
        instructions.setEditable(false);

        if (currentUser.getAdoptedPets().isEmpty()) {
            instructions.append("\nYou haven't adopted any pets.");
            setupMainPanel(instructions, createBackButton());
            return;
        }
        instructions.append("Write your story below and click submit.");
        JPanel storyForm = createStoryForm(instructions);
        JButton backButton = createBackButton();
        // JTextArea storyField = new JTextArea(10, 30);
        // JButton submit = new JButton("Submit");

        // submit.addActionListener(e -> {
        //     currentUser.addAdoptStory(storyField.getText().trim());
        //     instructions.append("\nYour adoption story has been uploaded successfully!\n");
        //     storyField.setText(""); // Clear input
        // });
        // JPanel inputPanel = new JPanel(new BorderLayout());
        // inputPanel.add(new JScrollPane(storyField), BorderLayout.CENTER);
        // inputPanel.add(submit, BorderLayout.SOUTH); // Submit button below the input
        // mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(instructions), BorderLayout.NORTH); // Instructions at the top
        mainPanel.add(storyForm, BorderLayout.CENTER); // Input and submit at the center
        mainPanel.add(backButton, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // MODIFIES: the file at JSON_STORE
    // EFFECTS: saves the current user data to the specified JSON file
    //          if an error occurs shows an error message.
    private void saveUserData() {
        try {
            writer.open();
            writer.write(currentUser);
            writer.close();
            JOptionPane.showMessageDialog(this, "Data saved successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save data.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this.currentUser
    // EFFECTS: displays a dialog box asking if the user wants to exits
    //          if yes, exit and data saved automatically;
    private void loadUserData() {
        try {
            currentUser = reader.read();
            JOptionPane.showMessageDialog(this, "Data loaded successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load data.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Prompt to Save Before Exit
    // MODIFIES: System.exit
    // EFFECTS: displays a dialog box asking if the user wants to exits
    //          if yes, exit and data saved automatically;
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
    // REQUIRES: name and role are not empty
    // MODIFIES: this.currentUser
    // EFFECTS: displays a dialog for the user to input their name and role
    //          Creates a new user with given details and exits if cancel.
    private void initializeCurrentUser() {
        // Create a outerlayout for the main vertical box
        Box outerLayout = Box.createVerticalBox();
        // Add the image at the top
        pawLabel = new JLabel(pawImage);
        outerLayout.add(pawLabel);
        outerLayout.add(Box.createVerticalStrut(20));

        JPanel userForm = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField nameField = new JTextField();
        JComboBox<String> roleField = new JComboBox<>(new String[]{"Adopter", "Staff"});

        userForm.add(new JLabel("Enter your name:"));
        userForm.add(nameField);
        userForm.add(new JLabel("Select your role:"));
        userForm.add(roleField);

        outerLayout.add(userForm);

        int result = JOptionPane.showConfirmDialog(this, outerLayout, "User Setup",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
        
            String name = nameField.getText().trim();
            String role = (String) roleField.getSelectedItem();

            currentUser = new User(name, role);
            displayArea.append("Welcome, " + name + " (" + role + ")!\n");

            // if (!name.isEmpty() && role != null) {
            //     currentUser = new User(name, role);
            //     displayArea.append("Welcome, " + name + " (" + role + ")!\n");
            // } else {
            //     JOptionPane.showMessageDialog(this, "Both name and role are required.",
            //             "Invalid Input", JOptionPane.ERROR_MESSAGE);
            //     initializeCurrentUser(); // Retry if input is invalid
            // }
        } else {
            System.exit(0); // Exit if the user cancels
        }
    }


    // Helper to set up the main panel with a text area and a back button
    private void setupMainPanel(JTextArea instructions, JButton backButton) {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(instructions), BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Helper to create a "Back to Menu" button
    // EFFECTS: create a back to menu button with action
    private JButton createBackButton() {
        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> displayMainMenu());
        return back;
    }


    // MODIFIES: this
    // EFFECTS: load the image of dog's paw into the GUI as an icon.
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        String imagePath = System.getProperty("user.dir") + sep
				+ "data" + sep + "paw.png";
        
        pawImage = new ImageIcon(imagePath);
        if (pawImage.getIconWidth() == -1) {
            JOptionPane.showMessageDialog(this, "Image not found at: " + imagePath,
                    "Image Error", JOptionPane.ERROR_MESSAGE);
            pawImage = null;
        } else {
            Image scaledImage = pawImage.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
            pawImage = new ImageIcon(scaledImage);
        }
    }
    
    // heler method for report a stray pet
    // MODIFIES: JTextArea
    // EFFECTS: create a stray pet adoption form with basic informations
    private JPanel createStrayPetForm(JTextArea instructions) {
        JTextField nameField = new JTextField();
        JTextField speciesField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField breedField = new JTextField();
        JButton submitButton = new JButton("Submit");
    
        submitButton.addActionListener(e -> handleStrayPetSubmission(
                nameField.getText().trim(),
                speciesField.getText().trim(),
                genderField.getText().trim(),
                breedField.getText().trim(),
                instructions,
                nameField, speciesField, genderField, breedField));
    
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Species:"));
        form.add(speciesField);
        form.add(new JLabel("Gender:"));
        form.add(genderField);
        form.add(new JLabel("Breed:"));
        form.add(breedField);
        form.add(submitButton);
        return form;
    }

    private void handleStrayPetSubmission(String name, String species, String gender, String breed,
                                      JTextArea instructions, JTextField... fields) {
        if (name.isEmpty() || species.isEmpty() || gender.isEmpty() || breed.isEmpty()) {
            instructions.append("\nAll fields are required. Please try again.\n");
        } else {
            shelter.addPet(new Pet(name, gender, species, breed));
            instructions.append("\nStray pet added: " + name + "\n");
            for (JTextField field : fields) {
                field.setText(""); // Clear all input fields
            }
        }
    }

    // helper method for creating an adoption form with basic informations

    private JPanel createAdoptionForm(JTextArea instructions) {
        JTextField petNameField = new JTextField();
        JButton submitButton = new JButton("Submit");
    
        submitButton.addActionListener(e -> handleAdoptionSubmission(
                petNameField.getText().trim(),
                instructions,
                petNameField));
    
        JPanel form = new JPanel(new BorderLayout());
        form.add(petNameField, BorderLayout.CENTER);
        form.add(submitButton, BorderLayout.EAST);
        return form;
    }
    
    // helper method for submitting an adoption application
    private void handleAdoptionSubmission(String petName, JTextArea instructions, JTextField petNameField) {
        Pet selectedPet = shelter.getPetByName(petName);
    
        if (selectedPet == null || !selectedPet.isAvailable()) {
            instructions.append("\nInvalid pet name. Please try again.\n");
        } else {
            AdoptApplication application = new AdoptApplication(currentUser.getName(), petName);
            currentUser.submitApplication(application);
            application.updateStatus("submitted");
            currentUser.addAdoptedPets(selectedPet);
            instructions.append("\nYour application for " + selectedPet.getPetName() + " has been submitted.\n");
            petNameField.setText(""); // Clear the input field after submission
        }
    }

    // REQUIRES: JTextArea != null
    // MODIFIES: the panel created for he story form.
    // EFFECTS: creates and returns a JPanel containing a text area for the user to
    //          input story
    private JPanel createStoryForm(JTextArea instructions) {
        JTextArea storyField = new JTextArea(10, 30);
        JButton submitButton = new JButton("submit");

        submitButton.addActionListener(e -> handleStorySubmission(
                storyField.getText().trim(), instructions, storyField));
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(new JScrollPane(storyField), BorderLayout.CENTER);
        formPanel.add(submitButton, BorderLayout.SOUTH);

        return formPanel;
    }

    // REQUIRES: story != null, JTextArea != null
    // MODIFIES: the instructions JTextArea and the storyField JTextArea
    // EFFECTS: if the story input is empty, output try again
    //          if is not empty, adds the story to the current user's stories
    private void handleStorySubmission(String story, JTextArea instructions, JTextArea storyField) {
        if (story.isEmpty()) {
            instructions.append("\nStory cannot be empty. Please try again.\n");
        } else {
            currentUser.addAdoptStory(story);
            instructions.append("\nYour adoption story has been uploaded successfully!\n");
            storyField.setText(""); // Clear the input field after submission
        }
    }
    
    // MODIFIES: menu panel
    // EFFECTS: Creates and returns a JPanel with a grid, contains menu buttons for features
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 10, 10));
    
        // Add menu buttons
        menuPanel.add(createMenuButton("View Available Pets", e -> viewAvailablePets()));
        menuPanel.add(createMenuButton("Submit Adoption Application", e -> submitAdoptionApplication()));
        menuPanel.add(createMenuButton("View My Applications", e -> viewUserApplications()));
        menuPanel.add(createMenuButton("Report a Stray Pet", e -> reportStrayPet()));
        menuPanel.add(createMenuButton("Upload Adopt Story", e -> uploadAdoptStory()));
        menuPanel.add(createMenuButton("Exit", e -> promptSaveBeforeExit()));
    
        return menuPanel;
    }

    // REQUIRES: string != null
    // MODIFIES: Jbutton
    // EFFECTS: creates and returns a JButton with the specified text and associates the action with it
    private JButton createMenuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }
    

}
