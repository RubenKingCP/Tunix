package tunix.controller;

import javax.swing.JPanel;

import tunix.navigation.events.EventBus;
import tunix.navigation.events.SwitchCenterScreenEvent;
import tunix.ui.views.profile.ArtistProfileView;

public class ArtistProfileController {

    private final ArtistProfileView artistProfileView;


    private final EventBus eventBus;

    public ArtistProfileController(
                                   EventBus eventBus) {


        this.eventBus = eventBus;

        this.artistProfileView = new ArtistProfileView();

        setupListeners();
    }

    private void setupListeners() {

        artistProfileView.setUploadSongListener(e -> {

            eventBus.publish(
                    new SwitchCenterScreenEvent(
                            UploadSongController.class
                    )
            );
        });
    }

    public JPanel getView() {

        return artistProfileView;
    }

    public void draw() {

        artistProfileView.repaint();

        artistProfileView.revalidate();
    }
}