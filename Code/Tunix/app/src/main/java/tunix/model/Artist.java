package tunix.model;

import tunix.dto.enums.Role;

public class Artist extends Account implements ILibraryAsset {
    private String biography;
    private int followersCount;

    public Artist(Long id, String username, String email, Role accountStatus, String biography, int followersCount) {
        super(id, username, email, accountStatus);
        this.biography = biography;
        this.followersCount = followersCount;
    }

    @Override
    public String getTitle() {
        return getUsername();
    }

    @Override
    public int getId() {
        return super.getId();
    }

    public Long getIdLong() {
        return super.getLongId();
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }
}
