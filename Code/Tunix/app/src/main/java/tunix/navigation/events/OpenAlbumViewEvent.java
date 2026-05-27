package tunix.navigation.events;

import tunix.model.musicContent.Album;

public class OpenAlbumViewEvent {

    private final Album album;

    public OpenAlbumViewEvent(Album album) {
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }
}