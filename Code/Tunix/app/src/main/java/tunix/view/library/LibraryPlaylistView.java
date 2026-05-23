package tunix.view.library;

public class LibraryPlaylistView implements LibraryAssetView {
    private final int id;
    private final String title;
    private final String ownerName;
    private final int songCount;
    private final boolean isPublic;
    private final String coverImageUrl;
    private final Runnable onClickAction;

    public LibraryPlaylistView(int id, String title, String ownerName, int songCount, boolean isPublic, String coverImageUrl) {
        this(id, title, ownerName, songCount, isPublic, coverImageUrl, null);
    }

    public LibraryPlaylistView(int id, String title, String ownerName, int songCount, boolean isPublic, String coverImageUrl, Runnable onClickAction) {
        this.id = id;
        this.title = title;
        this.ownerName = ownerName;
        this.songCount = songCount;
        this.isPublic = isPublic;
        this.coverImageUrl = coverImageUrl;
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
        return "Playlist";
    }

    @Override
    public String getSubtitle() {
        return ownerName + " • " + songCount + " songs";
    }

    @Override
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getSongCount() {
        return songCount;
    }

    public boolean isPublic() {
        return isPublic;
    }

    @Override
    public void onClick() {
        if (onClickAction != null) {
            onClickAction.run();
        }
    }
}
