package tunix.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import tunix.model.account.Artist;
import tunix.model.musicContent.Album;
import tunix.model.musicContent.Song;
import tunix.ui.views.main.center.ArtistView;
 
public class ArtistController {

    private final ArtistView view;

    private Artist artist;
    private List<Song> topSongs;
    private List<Album> albums;

    public ArtistController() {
        this.view = new ArtistView();
        this.topSongs = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    public JPanel getView() {
        return view;
    }

    // =========================
    // PRIMARY DATA ENTRY POINT
    // =========================

    public void setArtist(
            Artist artist,
            List<Song> topSongs,
            List<Album> albums
    ) {
        loadArtist(artist, topSongs, albums);
    }

    // Cleaner semantic API for EventBus usage
    public void loadArtist(
            Artist artist,
            List<Song> topSongs,
            List<Album> albums
    ) {
        this.artist = artist;
        this.topSongs = (topSongs == null) ? new ArrayList<>() : topSongs;
        this.albums = (albums == null) ? new ArrayList<>() : albums;

        view.setArtistData(this.artist, this.topSongs, this.albums);
    }

    // =========================
    // GETTERS
    // =========================

    public Artist getArtist() {
        return artist;
    }

    public List<Song> getTopSongs() {
        return topSongs;
    }

    public List<Album> getAlbums() {
        return albums;
    }
}