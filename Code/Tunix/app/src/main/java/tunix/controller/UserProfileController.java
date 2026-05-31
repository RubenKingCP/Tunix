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
    private final EventBus eventBus;

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
        return SessionService.Instance.getUser().isTrialEligible();
    }

    public boolean startTrial() {
        // Validate trial eligibility before attempting
        if (!checkTrialEligibility()) {
            System.err.println("User is not eligible for trial. Already premium or trial already used.");
            return false;
        }

        boolean success = service.startTrial(SessionService.Instance.getUser().getLongId());
        if (success) {
            // Keep local session in sync so the UI reflects the new state immediately
            SessionService.Instance.getUser().setPremium(true);
            SessionService.Instance.getUser().setPremiumTrialUsed(true);
        }
        return success;
    }

    public boolean purchasePremiumPlan() {
        // Don't allow purchasing if already premium
        if (SessionService.Instance.getUser().isPremium()) {
            System.err.println("User is already premium.");
            return false;
        }

        PaymentController paymentController = new PaymentController();
        boolean paymentSucceeded = paymentController.showPaymentPopup();
        if (!paymentSucceeded) {
            return false;
        }

        boolean success = service.buyPremium(SessionService.Instance.getUser().getLongId());
        if (success) {
            SessionService.Instance.getUser().setPremium(true);
        }
        return success;
    }

    public boolean cancelPremium() {
        // Only allow cancelling if user is premium
        if (!SessionService.Instance.getUser().isPremium()) {
            System.err.println("User is not premium.");
            return false;
        }

        boolean success = service.cancelPremium(SessionService.Instance.getUser().getLongId());
        if (success) {
            SessionService.Instance.getUser().setPremium(false);
        }
        return success;
    }

    public void drawView() {
        view.initGui();
    }

    public void requestArtistStatus(String stageName, String bio) {
        artistRequestService.makeRequest(Long.valueOf(SessionService.Instance.getAccount().getLongId()), stageName, bio);
    }
}