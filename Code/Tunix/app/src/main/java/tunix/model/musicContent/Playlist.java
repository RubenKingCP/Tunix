package tunix.model.musicContent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import tunix.dto.enums.LibraryAssetType;
import tunix.model.ILibraryAsset;
import tunix.model.PlaylistItem;
import tunix.model.account.Account;
import tunix.model.account.User;

@Getter
public class Playlist implements ILibraryAsset {

    private String title;
    private int id;
    private List<PlaylistItem> playlistItems;
    private Account creator;
    private List<Account> coauthors;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean isPublic;

    public Playlist(String title, int id, Account creator) {
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
    public LibraryAssetType getType() {
        return LibraryAssetType.PLAYLIST;
    }
    
    public String getName() {
        return title;
    }

    @Override
    public List<Song> getDisplaySongs() {

        return playlistItems.stream()
                .map(PlaylistItem::getSong)
                .toList();
    }

    @Override
    public String getSubtitle() {
        return "Playlist • " + playlistItems.size() + " songs";
    }

    public List<PlaylistItem> getPlaylistItems() {
        return playlistItems;
    }

    public Account getCreator() {
        return creator;
    }

    public List<Account> getCoauthors() {
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