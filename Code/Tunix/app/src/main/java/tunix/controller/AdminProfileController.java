package tunix.controller;

import javax.swing.JPanel;

import tunix.ui.views.profile.AdminProfileView;

public class AdminProfileController {
    private final AdminProfileView view;

    public AdminProfileController() {
        this.view = new AdminProfileView();
    }

    public JPanel getView() {
        return view;
    }
}
