package tunix.controller;

import javax.swing.JPanel;

import tunix.service.ArtistProfileService;
import tunix.ui.views.profile.ArtistProfileView;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.OpenSongUploadViewEvent;

public class ArtistProfileController {
    private final ArtistProfileView artistProfileView;
    private final ArtistProfileService artistProfileService;
    private final EventBus eventBus;

    public ArtistProfileController(ArtistProfileService artistProfileService, EventBus eventBus) {
        this.artistProfileView = new ArtistProfileView();
        this.artistProfileService = artistProfileService;
        this.eventBus = eventBus;
    }

    public JPanel getView() {
        return artistProfileView;
    }

    public void onUploadSongClicked() {
        eventBus.publish(new OpenSongUploadViewEvent());
    }

    public void draw() {
        // Code to draw the artist profile view, including artist information and songs
    }
}
