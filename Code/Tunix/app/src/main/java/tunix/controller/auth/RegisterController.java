package tunix.controller.auth;

import tunix.dto.request.RegisterRequest;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.GoToLoginButtonClicked;
import tunix.service.auth.RegisterService;
import tunix.service.auth.SessionService;
import tunix.ui.views.auth.RegisterView;

public class RegisterController {

    private final RegisterService registerService;
    private final RegisterView registerView;
    private final EventBus eventBus;

    public RegisterController(RegisterService registerService, SessionService sessionService, EventBus eventBus) {
        this.registerService = registerService;
        this.eventBus = eventBus;
        this.registerView = new RegisterView();
        this.registerView.setController(this);
    }

    public RegisterView getView() {
        return registerView;
    }

    public void onRegisterButtonClicked() {
        registerView.clearMessage();
        String username = registerView.getUsername();
        String email    = registerView.getEmail();
        String password = registerView.getPassword();
        RegisterRequest registerRequest = new RegisterRequest(username, email, password);
        registerService.register(
            registerRequest,
            () -> registerView.showSuccess("Registration successful! Please log in."),
            errorMessage -> registerView.showError(errorMessage != null && !errorMessage.isBlank()
                    ? errorMessage
                    : "Registration failed. Please try again.")
        );
    }

    public void onGoToLoginButtonClicked() {
        registerView.clearMessage();
        eventBus.publish(new GoToLoginButtonClicked());
    }
}