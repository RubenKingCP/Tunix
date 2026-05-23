package tunix.view.library;

public class LibraryArtistView implements LibraryAssetView {
    private final int id;
    private final String title;
    private final int followersCount;
    private final String biography;
    private final String profileImageUrl;
    private final Runnable onClickAction;

    public LibraryArtistView(int id, String title, int followersCount, String biography, String profileImageUrl) {
        this(id, title, followersCount, biography, profileImageUrl, null);
    }

    public LibraryArtistView(int id, String title, int followersCount, String biography, String profileImageUrl, Runnable onClickAction) {
        this.id = id;
        this.title = title;
        this.followersCount = followersCount;
        this.biography = biography;
        this.profileImageUrl = profileImageUrl;
        this.onClickAction = onClickAction;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getType() {
        return "Artist";
    }

    @Override
    public String getSubtitle() {
        return followersCount + " followers";
    }

    @Override
    public String getCoverImageUrl() {
        return profileImageUrl;
    }

    @Override
    public boolean isCircularAvatar() {
        return true;
    }

    public String getBiography() {
        return biography;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    @Override
    public void onClick() {
        if (onClickAction != null) {
            onClickAction.run();
        }
    }
}
