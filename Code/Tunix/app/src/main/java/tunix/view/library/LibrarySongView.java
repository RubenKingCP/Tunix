package tunix.view.library;

public class LibrarySongView implements LibraryAssetView {
    private final int id;
    private final String title;
    private final String artistName;
    private final int durationSeconds;
    private final String coverImageUrl;
    private final Runnable onClickAction;

    public LibrarySongView(int id, String title, String artistName, int durationSeconds, String coverImageUrl) {
        this(id, title, artistName, durationSeconds, coverImageUrl, null);
    }

    public LibrarySongView(int id, String title, String artistName, int durationSeconds, String coverImageUrl, Runnable onClickAction) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.durationSeconds = durationSeconds;
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
        return "Song";
    }

    @Override
    public String getSubtitle() {
        return artistName;
    }

    @Override
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public String getArtistName() {
        return artistName;
    }

    @Override
    public void onClick() {
        if (onClickAction != null) {
            onClickAction.run();
        }
    }
}
