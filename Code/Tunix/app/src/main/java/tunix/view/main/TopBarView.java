package tunix.view.main;

import javax.swing.JPanel;

import tunix.controller.main.TopBarController;

public class TopBarView extends JPanel{
    private TopBarController topBarController;

    public void setController(TopBarController topBarController) {
        this.topBarController = topBarController;
    }
}
