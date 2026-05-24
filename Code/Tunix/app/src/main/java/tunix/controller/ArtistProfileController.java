package tunix.controller;

import tunix.event.EventBus;
import tunix.service.ArtistProfileService;
import tunix.view.profile.ArtistProfileView;
import tunix.event.OpenSongUploadViewEvent;
import tunix.model.Artist;

public class ArtistProfileController {
    private ArtistProfileView artistProfileView;
    private ArtistProfileService artistProfileService;
    private EventBus eventBus;
    private Artist artist;

    public ArtistProfileController(Artist artist, ArtistProfileView artistProfileView, ArtistProfileService artistProfileService, EventBus eventBus) {
        this.artist = artist;
        this.artistProfileView = artistProfileView;
        this.artistProfileService = artistProfileService;
        this.eventBus = eventBus;
    }

    public void onUploadSongClicked() {
        eventBus.publish(new OpenSongUploadViewEvent());
    }

    public void draw() {
        // Code to draw the artist profile view, including artist information and songs
    }
}
