package model;

import java.util.ArrayList;
import java.util.List;

//
public class Shelter {
    private List<Pet> pets;

    // Constructor
    public Shelter() {
        this.pets = new ArrayList<>();
    }

    // EFFECTS: return all the pets in the shelter
    public List<Pet> getPets() {
        return pets;
    }

    // add a new stray pet into our sheltor
    // MODIFIES: this
    // EFFECTS: add the new pet into our shelter pets list
    public void addPet(Pet pet) {
        pets.add(pet);
    }

    
    // MODIFIES: this
    // EFFECTS: return a pet if we can find a pet have the given name,
    //          return null if we cannot find
    public Pet getPetByName(String name) {
        for (Pet pet : this.getPets()) {
            if (pet.getPetName().equals(name)) {
                return pet;
            }
        }
        return null;
        
    }



}
