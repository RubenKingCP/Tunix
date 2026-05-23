package tunix.service;

import com.google.common.eventbus.EventBus;

public class ArtistProfileService {
    
    private final EventBus eventBus;

    public ArtistProfileService(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
