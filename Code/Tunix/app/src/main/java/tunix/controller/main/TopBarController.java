package tunix.controller.main;

import javax.swing.JPanel;

import tunix.controller.HomeController;
import tunix.controller.SearchController;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.LogoutEvent;
import tunix.navigation.events.SwitchCenterScreenEvent;
import tunix.navigation.events.SwitchProfileScreenEvent;
import tunix.service.SearchService;
import tunix.ui.views.main.TopBarView;
import tunix.ui.views.profile.UserProfileView;

public class TopBarController {
    private final SearchService searchService;
    private final TopBarView topBarView;
    private final EventBus eventBus;

    public TopBarController(SearchService searchService, EventBus eventBus) {
        this.searchService = searchService;
        this.eventBus = eventBus;
        this.topBarView = new TopBarView();
        this.topBarView.setController(this);
    }

    public JPanel getView() {
        return topBarView;
    }

    public void onSearch(String query, String searchType) {
        eventBus.publish(new SwitchCenterScreenEvent(SearchController.class));
    }

    public void onHomeButtonClicked() {
        eventBus.publish(new SwitchCenterScreenEvent(HomeController.class));
    }

    public void onProfileButtonClicked() {
        eventBus.publish(new SwitchProfileScreenEvent(UserProfileView.class));
    }

    public void onLogoutButtonClicked() {
        eventBus.publish(new LogoutEvent());
    }
}
