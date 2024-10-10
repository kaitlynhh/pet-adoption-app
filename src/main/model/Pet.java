package model;

// Creates a pet and it's available for adoption.
public class Pet {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String species;
    private String breed;
    private String adoptionStatus;

    // Constructor
    public Pet(int id, String name, int age, String gender, String species, String breed) {
        // TODO: contructor stub
    }

    //getters
    public int getId() {
        //stub
        return 0;
    }
    
    public String getPetName() {
        // stub
        return "";
    }

    public int getPetAge() {
        //stub
        return 0;
    }

    public String getGender() {
        //stub
        return "";
    }

    public String getSpecies() {
        //stub
        return "";
    }

    public String getBreed() {
        //stub
        return "";
    }

    // EFFECTS: return "Available" or "Adopted"
    public String getAdoptionStatus() {
        //stub
        return "";
    }

    // REQUIRES: pet must be available.
    // MODIFIES: this
    // EFFECTS: change the adoption status of the pet.
    public void markAsAdopted() {
        // stub
    }

    // EFFECTS: return true if the pet is available for adoption.
    public boolean isAvailable() {
        //stub
        return true;
    }

    //setters
    public void setName(String name) {
        this.name = name; 
    }

    public void setAge(int age) { 
        this.age = age; 
    }

    public void setSpecies(String species) {
        this.species = species; 
    }

    public void setBreed(String breed) {
        this.breed = breed; 
    }

    public void setStatus(String status) {
        this.adoptionStatus = status;
    }
}



