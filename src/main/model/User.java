package model;

import java.util.ArrayList;
import java.util.List;


// Create a user for the system
public class User {
    private String name;
    private String role;
    private List<AdoptApplication> applications;
    private List<String> stories;
    private List<Pet> adoptedPets;

    public User(String name, String role) {
        this.name = name;
        this.role = role;
        this.applications = new ArrayList<>();
        this.stories = new ArrayList<>();
        this.adoptedPets = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    // get the role, may be "Adopter" or "staff"
    public String getRole() {
        return role;
    }

    public List<AdoptApplication> getApplications() {
        return applications;
    }

    public List<String> getStories() {
        return stories;
    }

    public List<Pet> getAdoptedPets() {
        return adoptedPets;
    }

    // submit a adoption application for a pet
    // REQUIRES: application not null
    // MODIFIES: this
    // EFFECTS: add the application to user's list of application
    public void submitApplication(AdoptApplication application) {
        this.applications.add(application);
    }

    // upload an adopt story
    // REQUIRES: user already adopted a pet
    // MODIFIES: this
    // EFFECTS: add the story to user's list of stories
    public void addAdoptStory(String story) {
        this.stories.add(story);
    }




}

