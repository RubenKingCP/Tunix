package tunix.controller;

import javax.swing.JPanel;

import tunix.ui.views.main.center.HomeView;

public class HomeController {
    private final HomeView view;

    public HomeController() {
        this.view = new HomeView();
    }

    public JPanel getView() {
        return view;
    }

    public void draw() {
        System.out.println("Drawing Home");
        view.display();
    }
}
