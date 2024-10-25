package ui;

import model.AdoptApplication;
import model.Pet;
import model.Shelter;
import model.User;
import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Provides the main interface for the stray pets adoption app,
// It manages user interactions and displays the function
public class AdoptionApp {
    private static final String JSON_STORE = "./data/myApplications.json";
    private Pet otto;
    private Pet bob;
    private Pet boots;
    private  Shelter shelter;
    private User currentUser;
    private  Scanner scanner;
    private boolean keepGoing;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Constructs an AdoptionApp instance and initialize our sheltor
    // and input scanner for user
    public AdoptionApp() throws FileNotFoundException{
        this.shelter = new Shelter();
        this.scanner = new Scanner(System.in); // String command = null;
        this.keepGoing = true;
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
        begin();
    }

    // EFFECTS: starts the application, enters the main application loop,
    // displays the main menu 
    public void begin() {
        System.out.println("Welcome to Stray Pets Adoption App!");
        init();

        System.out.println("Do you want to load your applications from file? (yes/no)");
        String loadChoice = scanner.next();
        if (loadChoice.equalsIgnoreCase("yes")) {
            loadUserData();
        } else {
            // init();
            System.out.println("Please enter your username: ");
            String userName = scanner.next();
            System.out.println("Are you an adopter or a staff? (Please enter 'adopter' or 'staff')");
            String userRole = scanner.next();
            currentUser = new User(userName, userRole);
        }

        while (keepGoing) {
            displayMenu();
            int choice = getUserChoice();
            handleUserChoice(choice);
        }

        // saveUserData();
        System.out.println("Thank you for using the Stray Pets Adoption App");
    }


    // REQUIRES: the user enters an integer
    // EFFECTS: return the user's choice as an integer
    private int getUserChoice() {
        System.out.println("Enter Your Choice:");
        return scanner.nextInt();
    }

    // display the main menu of this application, 
    // the user can select 1-5 or 0 by entering these numbers, 
    // each number represent a single function of thie application
    private void displayMenu() {
        System.out.println("\n--- Stray Pets Adoption ---");
        System.out.println("1. View Available Pets");
        System.out.println("2. Submit an Adoption Application");
        System.out.println("3. View Your Applications");
        System.out.println("4. Report a Stray Pet");
        System.out.println("5. Upload Adopt Story");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }


    // REQUIRES: choice in [1,5] or 0
    // EFFECTS: handles the user's choice by finding corresponding functionality
    private void handleUserChoice(int choice) {

        switch (choice) {
            case 1:
                viewAvailablePets();
                break;
            case 2:
                submitAdoptionApplication();
                break;
            case 3:
                viewUserApplications();
                break;
            case 4:
                reportStrayPet();
                break;
            case 5:
                uploadAdoptStory();
                break;
            case 0:
                promptSaveBeforeExit();
                break;

            default:
                System.out.println("Invalid choice, try again please");
        }
    }
    

    // EFFECTS: initializes our chelter with pets already exists
    private void init() {
        otto = new Pet("Otto", "Male", "Dog", "Golden");
        bob = new Pet("Bob", "Male", "cat", "Birman");
        boots = new Pet("Boots", "Female", "Dog", "Corgi");
        shelter = new Shelter();
        shelter.addPet(otto);
        shelter.addPet(bob);
        shelter.addPet(boots);
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\r?\n|\r");
    }

    // EFFECTS: displays he list of pets available for adoption,
    //          if no such pets, print no pets are currently available
    private void viewAvailablePets() {
        System.out.println("\n--- Available Pets ---");
        if (shelter.getPets().isEmpty()) {
            System.out.println("No pets are currently available for adoption");
        } else {
            for (Pet pet : shelter.getPets()) {
                if (pet.getAdoptionStatus() == "available") {
                    System.out.println(pet.getPetName() + " " + pet.getSpecies() + " "
                            + pet.getBreed() + " " + pet.getAdoptionStatus());
                }
            }
        }
    }


    // EFFECTS: allows the user to submit an adoption application for pet
    private void submitAdoptionApplication() {
        System.out.println("\n--- Submit Adoption Application ---");

        if (shelter.getPets().isEmpty()) {
            System.out.println("No pets are available for adoption.");
            return;
        }

        String userName = currentUser.getName();

        System.out.println("Enter the name of the pet you want to adopt among these available pets:");
        for (Pet pet : shelter.getPets()) {
            if (pet.isAvailable()) {
                System.out.println(pet.getPetName());
            }
        }

        String petName = scanner.next();
        Pet selectedPet = shelter.getPetByName(petName);
        AdoptApplication application = new AdoptApplication(userName, petName);

        if (selectedPet == null) {
            System.out.println("Invalid pet name.");
        } else {
            currentUser.submitApplication(application);
            application.updateStatus("submitted");
            currentUser.addAdoptedPets(selectedPet);
            System.out.println("Your application for " + selectedPet.getPetName() + " has been submitted.");
        }
    }

    // EFFECTS: displays the user's applications and status
    private void viewUserApplications() {
        System.out.println("\n--- My Adoption Applications ---");
        // System.out.println("Plese Enter you username to view applications:");
        // String userName = scanner.next();
        String userName = currentUser.getName();
        // scanner.nextLine();
        System.out.println("\n--- Applications for " + userName + "---");

        if (currentUser.getApplications().isEmpty()) {
            System.out.println("You haven't submitted any adoption applications.");
        } else {
            for (AdoptApplication application : currentUser.getApplications()) {
                System.out.println("Application for Pet: " + application.getPetname()
                        + " (Status: " + application.getStatus() + ")");
            }
        }
    }

    // EFFECTS: allows the user to report a stray cat to our shelter
    //          and add it to our available pets
    private void reportStrayPet() {
        System.out.println("\n--- Add a Stray Pet to Our Shelter---");
        System.out.print("Enter pet name(Give him/her a cute name!): ");
        String petName = scanner.next();

        System.out.print("Enter pet type (e.g., Dog, Cat): ");
        String petSpecies = scanner.next();

        System.out.print("Enter pet gender:(female? male?) ");
        String petGender = scanner.next();

        System.out.print("Enter pet breed: ");
        String petBreed = scanner.next();

        Pet strayPet = new Pet(petName, petGender, petSpecies, petBreed);
        shelter.addPet(strayPet);

        System.out.println("Stray pet " + petName + " has been added to the shelter.");
        System.out.println("\n Thank you for reporting!");
    }


    // EFFECTS: allows the user to upload the stories and we will add it to our available stories
    private void uploadAdoptStory() {
        System.out.println("\n--- Upload Adopt Story ---");
        if (currentUser.getAdoptedPets().isEmpty()) {
            System.out.println("You haven't adopted any pets.");
            return;
        }

        System.out.println("Write your adoption story: ");
        scanner.nextLine();
        String story = scanner.nextLine();
        currentUser.addAdoptStory(story);
        System.out.println("Yout adoption story has been uploaded successfully!");
    

    }

    // EFFECTS: saves the current user data to file
    private void saveUserData() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentUser);
            jsonWriter.close();
            System.out.println("Your data has been saved successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, we're unable to write to file: " + JSON_STORE);
        }
    }
    

    // EFFECTS: loads user data from file
    private void loadUserData() {
        try {
            currentUser = jsonReader.read();
            System.out.println("Applications loaded successfully!");
        } catch (IOException e) {
            System.out.println("Sorry, we have error loading applications from file: " + JSON_STORE);
            init();
        }
    }

    // EFFECTS:
    private void promptSaveBeforeExit() {
        System.out.println("Do you want to save your applications before exiting?(yes/no)");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("yes")) {
            saveUserData();
        }

        keepGoing = false;
        System.out.println("Quit Application.");
    }
    
}
