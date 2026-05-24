package tunix.controller;

import tunix.ui.views.main.center.HomeView;

public class HomeController {
    HomeView view;
    public HomeController(HomeView view) {
        this.view = view;
        
    }
    public void draw() {
        System.out.println("Drawing Home");
        view.display();
    }
}
