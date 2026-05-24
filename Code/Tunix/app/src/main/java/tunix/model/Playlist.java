package tunix.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Playlist implements ILibraryAsset {

    private String title;
    private int id;
    private List<PlaylistItem> playlistItems;
    private User creator;
    private List<User> coauthors;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean isPublic;

    public Playlist(String title, int id, User creator) {
        this.title = title;
        this.id = id;
        this.creator = creator;

        this.playlistItems = new ArrayList<>();
        this.coauthors = new ArrayList<>();

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.isPublic = false;
    }

    public void addSong(Song song) {
        playlistItems.add(new PlaylistItem(song, playlistItems.size()));
        updatedAt = LocalDateTime.now();
    }

    public void removeSong(Song song) {
        playlistItems.removeIf(item -> item.getSong().equals(song));
        updatedAt = LocalDateTime.now();
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
        return "Playlist • " + playlistItems.size() + " songs";
    }

    public List<PlaylistItem> getPlaylistItems() {
        return playlistItems;
    }

    public User getCreator() {
        return creator;
    }

    public List<User> getCoauthors() {
        return coauthors;
    }

    public void addCoauthor(User user) {
    }

    public void removeCoauthor(User user) {
    }

    public void toggleVisibility() {
        isPublic = !isPublic;
    }
}