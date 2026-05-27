package tunix.controller;

import javax.swing.JPanel;

import tunix.controller.main.PaymentController;
import tunix.navigation.events.*;
import tunix.service.ArtistRequestService;
import tunix.service.UserService;
import tunix.service.auth.SessionService;
import tunix.ui.views.profile.UserProfileView;

public class UserProfileController {
    private final UserProfileView view;
    private final UserService service;
    private final ArtistRequestService artistRequestService;
    EventBus eventBus;

    public UserProfileController(EventBus eventBus, UserService service, ArtistRequestService artistRequestService) {
        this.service = service;
        this.view = new UserProfileView(this);
        this.eventBus = eventBus;
        this.artistRequestService = artistRequestService;
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

    public boolean purchasePremiumPlan() {
        PaymentController paymentController = new PaymentController();
        boolean paymentSucceeded = paymentController.showPaymentPopup();

        if (!paymentSucceeded) {
            return false;
        }

        return service.buyPremium(SessionService.Instance.getUser().getLongId());
    }

    public boolean cancelPremium(){
        return service.cancelPremium(SessionService.Instance.getUser().getLongId());
    }

    public void drawView(){
        view.initGui();
    }

    public void requestArtistStatus(String stageName, String bio) {
        artistRequestService.makeRequest(Long.valueOf(SessionService.Instance.getAccount().getLongId()), stageName, bio);
    }
}
