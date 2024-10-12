package model;

import java.util.ArrayList;
import java.util.List;


public class Shelter {
    private List<Pet> pets;

    // Constructor
    public Shelter() {
        this.pets = new ArrayList<>();
    }

    public List<Pet> getPets() {
        return pets;
    }

    // add a new stray pet into our sheltor
    // MODIFIES: this
    // EFFECTS: add the new pet into our shelter pets list
    public void addPet(Pet pet) {
        pets.add(pet);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public Pet getPetByName(String name) {
        for (Pet pet : this.getPets()) {
            if (pet.getPetName().equals(name)) {
                return pet;
            }
        }
        return null;
        
    }



}
