package tunix.controller;

import javax.swing.JPanel;

import tunix.ui.views.profile.AdminProfileView;

public class AdminProfileController {
<<<<<<< HEAD
    public AdminProfileController () {
        
=======
    private final AdminProfileView view;

    public AdminProfileController() {
        this.view = new AdminProfileView();
    }

    public JPanel getView() {
        return view;
>>>>>>> 10f6e0eba850784602d4e03de338fcc43c3e917c
    }
}
