package tunix.model.account;

import java.util.List;
import tunix.dto.enums.LibraryAssetType;
import tunix.model.ILibraryAsset;
import tunix.model.musicContent.Song;
import tunix.dto.enums.Role;

public class Artist extends Account implements ILibraryAsset{

    private String biography;
    private int followersCount;
    private String displayName;

    private boolean verified;

    public Artist(Long id,
                  String username,
                  String email,
                  String biography,
                  int followersCount,
                  boolean verified) {

        super(id, username, email, Role.ARTIST);

        this.biography = biography;
        this.followersCount = followersCount;
        this.verified = verified;
    }

    @Override
    public Account getCreator(){
        return this;
    }
    // =========================
    // DISPLAY
    // =========================

    public String getTitle() {
        return getUsername();
    }

    public String getSubtitle() {
        return followersCount + " followers";
    }

    public boolean isCircularAvatar() {
        return true;
    }

    // =========================
    // ARTIST DATA
    // =========================

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

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public int getId() {
        return getLongId();
    }

    @Override
    public LibraryAssetType getType() {
        return LibraryAssetType.ARTIST;
    }

    @Override
    public List<Song> getDisplaySongs() {
        return null;
    }
}