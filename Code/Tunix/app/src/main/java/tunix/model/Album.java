package tunix.model;

import java.sql.Date;
import java.util.List;

public class Album implements ILibraryAsset{
    private Artist artist;
    private String title;
    private int albumId;
    private List<Song> songs;
    private Date releaseDate;
    

    public Album(String title, int albumId, Artist artist, List<Song> songs, Date releaseDate) {
        this.title = title;
        this.albumId = albumId;
        this.artist = artist;
        this.songs = songs;
        this.releaseDate = releaseDate;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getId() {
        return albumId;
    }

    @Override
    public String getType() {
        return "Album";
    }

    @Override
    public String getSubtitle() {
        return artist.getTitle();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public Artist getArtist() {
        return artist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }
}
