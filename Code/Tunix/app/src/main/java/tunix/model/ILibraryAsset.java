package tunix.model;

import java.awt.Color;

import tunix.navigation.events.EventBus;
import tunix.service.auth.SessionService;

public interface ILibraryAsset {
    public EventBus eventBus = SessionService.Instance.getEventBus();
    
    String getTitle();

    int getId();

    String getType();

    String getSubtitle();

    default boolean isCircularAvatar() {
        return false;
    }

    default Color getDisplayColor() {
        int hash = Math.abs(getTitle().hashCode());
        int r = 40 + (hash % 80);
        int g = 50 + ((hash / 7) % 80);
        int b = 70 + ((hash / 11) % 80);
        return new Color(r, g, b);
    }

    default void onClick() {
        System.out.println("Opened " + getType() + ": " + getTitle());
    }
}
