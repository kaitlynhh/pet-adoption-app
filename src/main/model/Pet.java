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
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.species = species;
        this.breed = breed;
        this.adoptionStatus = "available";
    }

    //getters
    public int getId() {
        return id;
    }
    
    public String getPetName() {
        return name;
    }

    public int getPetAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }

    // EFFECTS: return "Available" or "Adopted"
    public String getAdoptionStatus() {
        return adoptionStatus;
    }

    // REQUIRES: pet must be available.
    // MODIFIES: this
    // EFFECTS: change the adoption status of the pet into adopted.
    public void markAsAdopted() {
        this.adoptionStatus = "adopted";
    }

    // EFFECTS: return true if the pet is available for adoption.
    public boolean isAvailable() {
        return this.adoptionStatus.equals("available");
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



