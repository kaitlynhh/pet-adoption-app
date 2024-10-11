package model;

// Creates an adoption application
public class AdoptApplication {
    private int apid;
    private int userid;
    private int petid;
    private String status; //pending? submitted? approved? rejected?

    //Constructor
    public AdoptApplication(int apid, int userid, int petid) {
        //TODO: constructor
    }

    public int getApid() {
        //TODO
        return 0;
    }

    public int getUserid() {
        //TODO
        return 0;
    }

    public int getPetid() {
        //TODO
        return 0;
    }

    public String getStatus() {
        //TODO
        return "";
    }

    // REQUIRES: application not null
    // MODIFIES: this
    // EFFECTS: updates application status with the new status provided
    public void updateStatus(String newStatus) {
        //stub
    }

}
