package model;

import org.json.JSONObject;
import persistence.Writable;

// Creates an adoption application that user can submit to apply for adopting pets, and staff can approve or reject it.
public class AdoptApplication implements Writable {
    private String username;
    private String petname;
    private String status; // pending? submitted? approved? rejected?

    // Constructor, an initial application with pending status
    public AdoptApplication(String username, String petname) {
        this.username = username;
        this.petname = petname;
        this.status = "pending";
    }

    // EFFECTS: return the user id.
    public String getUsername() {
        return username;
    }

    // EFFECTS: return the pet id.
    public String getPetname() {
        return petname;
    }

    // EFFECTS: return the application status.
    public String getStatus() {
        return status;
    }

    // REQUIRES: application not null
    // MODIFIES: this
    // EFFECTS: updates application status with the new status provided
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        EventLog.getInstance().logEvent(new Event(
                "Adoption application status updated for pet: " + petname
                        + " by user: " + username + ". New status: " + newStatus));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("petname", petname);
        json.put("status", status);
        return json;
        // throw new UnsupportedOperationException("Unimplemented method 'toJson'");
    }

}
