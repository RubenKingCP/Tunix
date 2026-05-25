package tunix.controller.main;

import tunix.navigation.events.EventBus;
import tunix.navigation.events.LogoutEvent;
import tunix.navigation.events.SwitchCenterScreenEvent;
import tunix.navigation.events.SwitchProfileScreenEvent;
import tunix.service.SearchService;
import tunix.service.auth.SessionService;
import tunix.ui.views.main.TopBarView;
import tunix.ui.views.main.center.HomeView;
import tunix.ui.views.main.center.SearchView;
import tunix.ui.views.profile.UserProfileView;

public class TopBarController {
    private final SearchService searchService;
    private final TopBarView topBarView;
    private final EventBus eventBus;

    public TopBarController(TopBarView topBarView, SearchService searchService, EventBus eventBus) {
        this.topBarView = topBarView;
        this.searchService = searchService;
        this.eventBus = eventBus;
    }
    public void onSearch(String query, String searchType) {
        eventBus.publish(new SwitchCenterScreenEvent(SearchView.class));
    }
    public void onHomeButtonClicked() {
        eventBus.publish(new SwitchCenterScreenEvent(HomeView.class));
    }
    public void onProfileButtonClicked() {
        eventBus.publish(new SwitchProfileScreenEvent(UserProfileView.class));
    }

    public void onLogoutButtonClicked() {
        eventBus.publish(new LogoutEvent());
    }
}
