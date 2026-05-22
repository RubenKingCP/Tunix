package tunix.controller;

import tunix.view.main.HomeView;

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
