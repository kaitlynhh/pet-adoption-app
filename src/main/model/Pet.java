package model;

// Creates a pet and it's available for adoption.
public class Pet {
    private String name;
    private String gender;
    private String species;
    private String breed;
    private String adoptionStatus;

    // Constructor: construct a pet with available adoption status.
    public Pet(String name, String gender, String species, String breed) {
        this.name = name;
        this.gender = gender;
        this.species = species;
        this.breed = breed;
        this.adoptionStatus = "available";
    }

    
    public String getPetName() {
        return name;
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

}



