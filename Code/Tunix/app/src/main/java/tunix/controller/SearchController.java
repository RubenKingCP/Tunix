package tunix.controller;

import javax.swing.JPanel;

import tunix.ui.views.main.center.SearchView;

public class SearchController {
    private final SearchView view;

    public SearchController() {
        this.view = new SearchView();
    }

    public JPanel getView() {
        return view;
    }
}
