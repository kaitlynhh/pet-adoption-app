package model;

import java.util.ArrayList;
import java.util.List;

// Create a user for the system
public class User {
    private int id;
    private String name;
    private String role;
    private List<AdoptApplication> applications;

    public User(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.applications = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        //TODO: constructor
        return "";
    }

    // get the role, may be "Adopter" or "staff"
    public String getRole() {
        //TODO: stub
        return "";
    }

    public List<AdoptApplication> getApplications() {
        return applications;
    }

    // submit a adoption application for a pet
    // REQUIRES: application not null
    // MODIFIES: this
    // EFFECTS: add the application to user's list of application
    public void submitApplication(AdoptApplication application) {
        //TODO: stub
    }




}

