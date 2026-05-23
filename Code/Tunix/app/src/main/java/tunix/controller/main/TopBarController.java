package tunix.controller.main;

import javax.swing.JPanel;

import tunix.event.EventBus;
import tunix.event.LogoutEvent;
import tunix.event.SwitchMainScreen;
import tunix.service.SearchService;
import tunix.view.main.HomeView;
import tunix.view.main.TopBarView;

public class TopBarController {
    private final SearchService searchService;
    private final TopBarView topBarView;
    private final EventBus eventBus;
    private final HomeView homeView;

    public TopBarController(TopBarView topBarView, SearchService searchService, EventBus eventBus, HomeView homeView) {
        this.topBarView = topBarView;
        this.searchService = searchService;
        this.eventBus = eventBus;
        this.homeView = homeView;
    }
    public void onSearch(String query, String searchType) {
        // Handle search logic using searchService
    }
    public void onHomeButtonClicked() {
        eventBus.publish(new SwitchMainScreen(homeView));
    }
    public void onProfileButtonClicked() {
        eventBus.publish(new SwitchMainScreen(new tunix.view.center.UserProfileView()));
    }

    public void onLogoutButtonClicked() {
        eventBus.publish(new LogoutEvent());
    }
}
