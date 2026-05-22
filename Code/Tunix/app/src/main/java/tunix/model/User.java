package tunix.model;

import tunix.dto.enums.Role;

public class User extends Account {

    private String displayName;
    private String profilePictureUrl;
    
    public User(int id, String username, String email, Role accountStatus) {
        super(id, username, email, accountStatus);
    }
    

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    public void checkTrialEligibility() {
        // Logic to check if the user is eligible for a trial
    }
    public boolean startTrial() {
        // Logic to start the trial for the user
        return true; // Return true if trial started successfully
    }
    public boolean updatePremiumStatus(boolean isPremium) {
        // Logic to update the user's premium status
        return true; // Return true if update was successful
    }
}
