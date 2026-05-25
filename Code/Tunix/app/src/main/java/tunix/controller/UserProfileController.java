package tunix.controller;

import javax.swing.JPanel;

import tunix.navigation.events.*;
import tunix.ui.views.profile.UserProfileView;

public class UserProfileController {
    private final UserProfileView view;
    EventBus eventBus;

    public UserProfileController(EventBus eventBus) {
        this.view = new UserProfileView();
        this.eventBus = eventBus;
    }

    public JPanel getView() {
        return view;
    }

    public void checkTrialEligibility() {
        // Logic to check if the user is eligible for a trial
    }

    public boolean startTrial() {
        // Logic to start the trial for the user
        return true; // Return true if trial started successfully
    }

    public void purchasePremiumPlan(){

    }

    public void drawView(){
        view.initGui();
    }
}
