package tunix.controller.main;

import tunix.event.EventBus;
import tunix.service.SearchService;
import tunix.view.main.TopBarView;

public class TopBarController {
    private final SearchService searchService;
    private final TopBarView topBarView;
    private final EventBus eventBus;

    public TopBarController(TopBarView topBarView, SearchService searchService, EventBus eventBus) {
        this.topBarView = topBarView;
        this.searchService = searchService;
        this.eventBus = eventBus;
    }
}
