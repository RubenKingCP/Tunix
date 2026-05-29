package tunix.controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tunix.model.account.Artist;
import tunix.model.musicContent.Album;
import tunix.model.musicContent.Song;
import tunix.service.FollowService;
import tunix.ui.views.main.center.ArtistView;

public class ArtistController {

    private final ArtistView view;
    private final FollowService followService;
    private final int currentUserId;

    private Artist artist;
    private List<Song> topSongs;
    private List<Album> albums;
    private boolean following = false;

    public ArtistController(FollowService followService, int currentUserId) {
        this.followService = followService;
        this.currentUserId = currentUserId;
        this.view = new ArtistView();
        this.view.setController(this);
        this.topSongs = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    public JPanel getView() { return view; }

    // =========================
    // PRIMARY DATA ENTRY POINT
    // =========================
    public void setArtist(Artist artist, List<Song> topSongs, List<Album> albums) {
        loadArtist(artist, topSongs, albums);
    }

    public void loadArtist(Artist artist, List<Song> topSongs, List<Album> albums) {
        this.artist   = artist;
        this.topSongs = (topSongs == null) ? new ArrayList<>() : topSongs;
        this.albums   = (albums   == null) ? new ArrayList<>() : albums;

        // Check real follow state on a background thread, then update UI
        new Thread(() -> {
            boolean isFollowing = (artist != null)
                && followService.isFollowing(currentUserId, artist.getId());
            this.following = isFollowing;
            SwingUtilities.invokeLater(() -> {
                view.setArtistData(this.artist, this.topSongs, this.albums);
                view.updateFollowButton(isFollowing);
            });
        }).start();
    }

    // =========================
    // FOLLOW / UNFOLLOW
    // =========================
    public void toggleFollow() {
        if (artist == null) return;

        new Thread(() -> {
            try {
                if (following) {
                    followService.unfollowArtist(currentUserId, artist.getId());
                } else {
                    followService.followArtist(currentUserId, artist.getId());
                }
                following = !following;
                final boolean nowFollowing = following;
                SwingUtilities.invokeLater(() -> view.updateFollowButton(nowFollowing));

            } catch (Exception e) {
                System.err.println("toggleFollow failed: " + e.getMessage());
            }
        }).start();
    }

    // =========================
    // GETTERS
    // =========================
    public Artist getArtist()       { return artist; }
    public List<Song> getTopSongs() { return topSongs; }
    public List<Album> getAlbums()  { return albums; }
    public boolean isFollowing()    { return following; }
}