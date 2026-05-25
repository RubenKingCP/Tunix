package tunix.model.account;
import tunix.dto.enums.Role;

public class User extends Account {

    private String displayName;
    private String profilePictureUrl;

    private boolean premium;
    private boolean premiumTrialUsed;

    private int downloadedSongsCount;

    public User(Long id,
                String username,
                String email,
                String displayName,
                String profilePictureUrl,
                boolean premium,
                boolean premiumTrialUsed,
                int downloadedSongsCount) {

        super(id, username, email, Role.USER);

        this.displayName = displayName;
        this.profilePictureUrl = profilePictureUrl;

        this.premium = premium;
        this.premiumTrialUsed = premiumTrialUsed;

        this.downloadedSongsCount = downloadedSongsCount;
    }

    // =========================
    // PROFILE
    // =========================

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

    // =========================
    // PREMIUM
    // =========================

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean hasUsedPremiumTrial() {
        return premiumTrialUsed;
    }

    public void setPremiumTrialUsed(boolean premiumTrialUsed) {
        this.premiumTrialUsed = premiumTrialUsed;
    }

    // =========================
    // DOWNLOADS
    // =========================

    public int getDownloadedSongsCount() {
        return downloadedSongsCount;
    }

    public void setDownloadedSongsCount(int downloadedSongsCount) {
        this.downloadedSongsCount = downloadedSongsCount;
    }

    // =========================
    // HELPERS
    // =========================

    public boolean canStartPremiumTrial() {
        return !premium && !premiumTrialUsed;
    }
}