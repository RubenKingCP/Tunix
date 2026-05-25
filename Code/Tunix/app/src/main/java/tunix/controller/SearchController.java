package tunix.controller;

import java.util.List;

import javax.swing.JPanel;

import tunix.model.ILibraryAsset;
import tunix.service.SearchService;
import tunix.ui.views.main.center.SearchView;

public class SearchController {
    private final SearchService service;
    private SearchView view;

    public SearchController(SearchService service) {
        this.service = service;
        this.view = new SearchView(List.of());
    }

    public JPanel getView() {
        return view;
    }

    public List<ILibraryAsset> search(String query, String type) {
        List<ILibraryAsset> results = service.search(query, type);
        this.view = new SearchView(results);
        return results;
    }
}
