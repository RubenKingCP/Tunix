package tunix.model.musicContent;

import java.sql.Date;
import java.util.List;

import tunix.dto.enums.LibraryAssetType;
import tunix.model.ILibraryAsset;
import tunix.model.account.Account;
import tunix.model.account.Artist;

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
    public Account getCreator(){
        return artist;
    }

    @Override
    public LibraryAssetType getType() {
        return LibraryAssetType.ALBUM;
    }

    @Override
    public String getSubtitle() {
        if(artist == null) {
            return "Unknown";
        }
        return artist.getTitle();
    }

    @Override
    public List<Song> getDisplaySongs() {
        return songs;
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
