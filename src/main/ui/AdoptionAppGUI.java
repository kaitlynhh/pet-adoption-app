package ui;

import model.AdoptApplication;
import model.Pet;
import model.Shelter;
import model.User;

import persistence.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.IOException;

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
    

    // constructor - set up main GUI window
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
    public void initUI() {
        loadImages();
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        setJMenuBar(createMenuBar());
        displayMainMenu();
        
    }

    // displays the main menu with different function options.
    private void displayMainMenu() {
        mainPanel.removeAll();
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1, 10, 10));
        // buttons for each munu option and add them to panel
        JButton viewPetsButton =  new JButton("View Available Pets");
        JButton submitApplicationButton = new JButton("Submit Adoption Application");
        JButton viewApplicationsButton = new JButton("View My Applications");
        JButton reportStrayPetButton = new JButton("Report a stray pet");
        JButton uploadStoryButton = new JButton("Upload Adopt Story");
        JButton exitButton = new JButton("Exit");

        viewPetsButton.addActionListener(e -> viewAvailablePets());
        submitApplicationButton.addActionListener(e -> submitAdoptionApplication());
        viewApplicationsButton.addActionListener(e -> viewUserApplications());
        reportStrayPetButton.addActionListener(e -> reportStrayPet());
        uploadStoryButton.addActionListener(e -> uploadAdoptStory());
        exitButton.addActionListener(e -> promptSaveBeforeExit());

        menuPanel.add(viewPetsButton);
        menuPanel.add(submitApplicationButton);
        menuPanel.add(viewApplicationsButton);
        menuPanel.add(reportStrayPetButton);
        menuPanel.add(uploadStoryButton);
        menuPanel.add(exitButton);
        mainPanel.add(menuPanel, BorderLayout.CENTER);

        mainPanel.revalidate(); // refresh the UI
        mainPanel.repaint();       
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


    // Allows the user to submit an adoption application
    private void submitAdoptionApplication() {
        mainPanel.removeAll();
        JTextArea instructions = new JTextArea();
        instructions.setEditable(false);
        instructions.setText("\n--- Submit Adoption Application ---");
        instructions.append("\nEnter the name of the pet you want to adopt from the list:\n");
        for (Pet pet : shelter.getPets()) {
            if (pet.isAvailable()) {
                instructions.append(pet.getPetName() + "\n");
            }
        }
        JTextField petNameField = new JTextField();
        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back to Menu");
        
        submitButton.addActionListener(e -> {
            String petName = petNameField.getText().trim();
            Pet selectedPet = shelter.getPetByName(petName);

            if (selectedPet == null || !selectedPet.isAvailable()) {
                displayArea.append("Invalid pet name.");
            } else {
                AdoptApplication application = new AdoptApplication(currentUser.getName(), petName);
                currentUser.submitApplication(application);
                application.updateStatus("submitted");
                currentUser.addAdoptedPets(selectedPet);
                instructions.append("Your application for " + selectedPet.getPetName() + " has been submitted.");
                petNameField.setText("");
            }
        });
        backButton.addActionListener(e -> displayMainMenu());
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(petNameField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(instructions), BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    // Displays user's applications
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
    

    private void reportStrayPet() {
        mainPanel.removeAll();
        JTextArea instructions = new JTextArea("--- Add a Stray Pet to Our Shelter---\n");
        instructions.setEditable(false);
        JTextField name = new JTextField();
        JTextField species = new JTextField();
        JTextField gender = new JTextField();
        JTextField breed = new JTextField();
        JButton submit = new JButton("Submit");
        JButton back = new JButton("Back");
        submit.addActionListener(e -> {
            shelter.addPet(new Pet(name.getText().trim(), gender.getText().trim(),
                    species.getText().trim(), breed.getText().trim()));
            instructions.append("\n Stray pet added: " + name.getText() + "\n");
            name.setText("");
            species.setText("");
            gender.setText("");
            breed.setText("");
        });
        back.addActionListener(e -> displayMainMenu());

        JPanel form = new JPanel(new GridLayout(5, 2));
        form.add(new JLabel("Name:"));
        form.add(name);
        form.add(new JLabel("Species:"));
        form.add(species);
        form.add(new JLabel("Gender:"));
        form.add(gender);
        form.add(new JLabel("Breed:"));
        form.add(breed);
        form.add(submit);
        form.add(back);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(instructions), BorderLayout.NORTH);
        mainPanel.add(form, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();

    }

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
        JTextArea storyField = new JTextArea(10, 30);
        JButton submit = new JButton("Submit");

        submit.addActionListener(e -> {
            currentUser.addAdoptStory(storyField.getText().trim());
            instructions.append("\nYour adoption story has been uploaded successfully!\n");
            storyField.setText(""); // Clear input
        });
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JScrollPane(storyField), BorderLayout.CENTER);
        inputPanel.add(submit, BorderLayout.SOUTH); // Submit button below the input
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(instructions), BorderLayout.NORTH); // Instructions at the top
        mainPanel.add(inputPanel, BorderLayout.CENTER); // Input and submit at the center
        mainPanel.add(createBackButton(), BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

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
    private JButton createBackButton() {
        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> displayMainMenu());
        return back;
    }


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

}
// JList
