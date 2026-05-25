package tunix.controller;

import tunix.service.ArtistProfileService;
import tunix.ui.views.profile.ArtistProfileView;
import tunix.model.account.Artist;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.OpenSongUploadViewEvent;

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
