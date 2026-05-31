package tunix.navigation.events;

import tunix.model.account.Artist;

public final class OpenArtistViewEvent {

    private final Artist artist;

    public OpenArtistViewEvent(Artist artist) {
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }
}