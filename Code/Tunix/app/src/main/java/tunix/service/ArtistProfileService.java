package tunix.service;

import tunix.navigation.events.EventBus;


public class ArtistProfileService {
    
    private final EventBus eventBus;

    public ArtistProfileService(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
