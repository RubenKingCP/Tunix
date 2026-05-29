package tunix.model.musicContent;

import java.util.List;

import tunix.dto.enums.LibraryAssetType;
import tunix.model.ILibraryAsset;
import tunix.model.account.Account;
import tunix.model.account.Artist;

public class Song implements ILibraryAsset {
    private final String title;
    private final Long songId;
    private final Artist artist;
    private final int duration; // Duration in seconds
    private final String filePathUrl;
    private final String coverImageUrl; // Optional cover image URL

    public Song(String title, Long songId, Artist artist, int duration, String filePathUrl, String coverImageUrl) {
        this.title = title;
        this.songId = songId;
        this.artist = artist;
        this.duration = duration;
        this.filePathUrl = filePathUrl;
        this.coverImageUrl = coverImageUrl;
    }

    @Override
    public Account getCreator(){
        return artist;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getId() {
        return songId.intValue();
    }

    @Override
    public LibraryAssetType getType() {
        return LibraryAssetType.SONG;
    }

    @Override
    public List<Song> getDisplaySongs() {
        return List.of(this);
    }

    @Override
    public String getSubtitle() {
        return artist.getTitle();
    }

    public Artist getArtist() {
        return artist;
    }

    public int getDuration() {
        return duration;
    }

    public String getFilePathUrl() {
        return filePathUrl;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }
}
