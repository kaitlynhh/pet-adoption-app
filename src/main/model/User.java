package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


// Create a user for the system
public class User implements Writable {
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


    // return the user's username
    public String getName() {
        return name;
    }

    // get the role, may be "Adopter" or "staff"
    public String getRole() {
        return role;
    }

    // EFFECTS: get all the applications the user submitted
    public List<AdoptApplication> getApplications() {
        return applications;
    }

    // EFFECTS: get all stories the user has uploaded
    public List<String> getStories() {
        return stories;
    }

    // EFFECTS: get all adopted pets
    public List<Pet> getAdoptedPets() {
        return adoptedPets;
    }

    // submit a adoption application for a pet
    // REQUIRES: application not null
    // MODIFIES: this
    // EFFECTS: add the application to user's list of application
    public void submitApplication(AdoptApplication application) {
        this.applications.add(application);
        application.updateStatus("submitted");
    }

    // upload an adopt story
    // REQUIRES: user already adopted a pet
    // MODIFIES: this
    // EFFECTS: add the story to user's list of stories
    public void addAdoptStory(String story) {
        this.stories.add(story);
    }

    // add a pet to user's adopted list
    // MODIFIES: this
    // EFFECTS: add a new adopted stray pet to the user's list of adopted pets
    public void addAdoptedPets(Pet pet) {
        this.adoptedPets.add(pet);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("role", role);
        json.put("applications", applicationsToJson());
        json.put("Stories", stories);
        return json;
        // throw new UnsupportedOperationException("Unimplemented method 'toJson'");
    }

    // EFFECTS: returns this users applications as a JSON array
    private JSONArray applicationsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (AdoptApplication application : applications) {
            jsonArray.put(application.toJson());
        }
        return jsonArray;
    }

}

