package tunix.controller;

import tunix.app.TunixApp;
import tunix.view.center.HomeView;

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
