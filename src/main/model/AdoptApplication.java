package model;

// Creates an adoption application
public class AdoptApplication {
    private int apid;
    private int userid;
    private int petid;
    private String status; //pending? submitted? approved? rejected?

    //Constructor, an initial application with pending status
    public AdoptApplication(int apid, int userid, int petid) {
        this.apid = apid;
        this.userid = userid;
        this.petid = petid;
        this.status = "pending";
    }

    public int getApid() {
        return apid;
    }

    public int getUserid() {
        return userid;
    }

    public int getPetid() {
        return petid;
    }

    public String getStatus() {
        return status;
    }

    // REQUIRES: application not null
    // MODIFIES: this
    // EFFECTS: updates application status with the new status provided
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

}
