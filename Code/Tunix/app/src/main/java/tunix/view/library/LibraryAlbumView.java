package tunix.view.library;

public class LibraryAlbumView implements LibraryAssetView {
    private final int id;
    private final String title;
    private final String artistName;
    private final int songCount;
    private final String coverImageUrl;
    private final Runnable onClickAction;

    public LibraryAlbumView(int id, String title, String artistName, int songCount, String coverImageUrl) {
        this(id, title, artistName, songCount, coverImageUrl, null);
    }

    public LibraryAlbumView(int id, String title, String artistName, int songCount, String coverImageUrl, Runnable onClickAction) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.songCount = songCount;
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
        return "Album";
    }

    @Override
    public String getSubtitle() {
        return artistName + " • " + songCount + " songs";
    }

    @Override
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getSongCount() {
        return songCount;
    }

    @Override
    public void onClick() {
        if (onClickAction != null) {
            onClickAction.run();
        }
    }
}
