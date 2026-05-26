package tunix.controller;

import javax.swing.JPanel;

import tunix.navigation.events.*;
import tunix.service.UserService;
import tunix.service.auth.SessionService;
import tunix.ui.views.profile.UserProfileView;

public class UserProfileController {
    private final UserProfileView view;
    private final UserService service;
    EventBus eventBus;

    public UserProfileController(EventBus eventBus, UserService service) {
        this.service = service;
        this.view = new UserProfileView(this);
        this.eventBus = eventBus;
    }

    public JPanel getView() {
        return view;
    }

    public boolean checkTrialEligibility() {
        return SessionService.Instance.getUser().isPremiumTrialUsed();
    }

    public boolean startTrial() {
        // Logic to start the trial for the user
        return true; // Return true if trial started successfully
    }

    public void purchasePremiumPlan(){

    }

    public void cancelPremium(){

    }

    public void drawView(){
        view.initGui();
    }
}
