package ui;

import model.AdoptApplication;
import model.Pet;
import model.Shelter;
import model.User;
import java.util.Scanner;

public class AdoptionApp {
    private Pet otto;
    private Pet bob;
    private Pet boots;
    private  Shelter shelter;
    private User currentUser;
    private  Scanner scanner;
    private boolean keepGoing;

    public AdoptionApp() {
        this.shelter = new Shelter();
        this.scanner = new Scanner(System.in); // String command = null;
        this.keepGoing = true;
        begin();
    }

    public void begin() {
        System.out.println("Welcome to Stray Pets Adoption App!");
        init();
        System.out.println("Please enter your username: ");
        String userName = scanner.next();
        System.out.println("Are you an adopter or a staff? (Please enter 'adopter' or 'staff')");
        String userRole = scanner.next();
        currentUser = new User(userName, userRole);

        while (keepGoing) {
            displayMenu();
            int choice = getUserChoice();
            handleUserChoice(choice);
        }
        System.out.println("Thank you for using the Stray Pets Adoption App");
    }



        private int getUserChoice() {
            System.out.println("Enter Your Choice:");
            return scanner.nextInt();
        }

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



        private void handleUserChoice(int choice){

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
                case 5:
                uploadAdoptStory();
                break;
                case 0:
                keepGoing = false;
                System.out.println("Quit Application.");
                break;

                default:
                System.out.println("Invalid choice, try again please");
            }
        }
    

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

    private void viewAvailablePets() {
        System.out.println("\n--- Available Pets ---");
        if (shelter.getPets().isEmpty()) {
            System.out.println("No pets are currently available for adoption");
        } else {
            for (Pet pet : shelter.getPets()) {
                if (pet.getAdoptionStatus() == "available");
                System.out.println(pet.getPetName() + " " + pet.getSpecies() + " "
                + pet.getBreed() + " " + pet.getAdoptionStatus());
            }
        }
    }


    private void submitAdoptionApplication() {
        System.out.println("\n--- Submit Adoption Application ---");

    if (shelter.getPets().isEmpty()) {
        System.out.println("No pets are available for adoption.");
    }

    // System.out.println("Enter your user name: ");
    // String userName = scanner.next();
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
        System.out.println("Invalid pet ID.");
    } else {
        currentUser.submitApplication(application);
        application.updateStatus("submitted");
        System.out.println("Your application for " + selectedPet.getPetName() + " has been submitted.");
    }
    }


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
            System.out.println("Application for Pet: " + application.getPetname() + " (Status: " + application.getStatus() + ")");
        }
    }
    }


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


    


    
    
}
