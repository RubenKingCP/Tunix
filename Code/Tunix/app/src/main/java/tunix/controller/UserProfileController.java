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
        return service.startTrial(SessionService.Instance.getUser().getLongId());
        //return true; // Return true if trial started successfully
    }

    public boolean purchasePremiumPlan(){
        return service.buyPremium(SessionService.Instance.getUser().getLongId());
    }

    public boolean cancelPremium(){
        return service.cancelPremium(SessionService.Instance.getUser().getLongId());
    }

    public void drawView(){
        view.initGui();
    }
}
