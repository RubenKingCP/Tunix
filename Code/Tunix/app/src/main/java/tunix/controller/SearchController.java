package tunix.controller;

import java.util.List;

import javax.swing.JPanel;

import tunix.model.ILibraryAsset;
import tunix.navigation.events.EventBus;
import tunix.service.SearchService;
import tunix.ui.views.main.center.SearchView;

public class SearchController {
    private final SearchService service;
    private final EventBus eventBus;
    private SearchView view;

    public SearchController(SearchService service, EventBus eventBus) {
        this.service = service;
        this.eventBus = eventBus;
        this.view = new SearchView(List.of(), eventBus);
    }

    public JPanel getView() {
        return view;
    }

    public List<ILibraryAsset> search(String query, String type) {
        List<ILibraryAsset> results = service.search(query, type);
        this.view = new SearchView(results, eventBus);
        return results;
    }
}
