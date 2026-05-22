package tunix.controller.auth;

import tunix.dto.request.RegisterRequest;
import tunix.event.EventBus;
import tunix.event.GoToLoginButtonClicked;
import tunix.service.auth.RegisterService;
import tunix.service.auth.SessionService;
import tunix.view.auth.RegisterView;

public class RegisterController {
    private final RegisterService registerService;
    private final RegisterView registerView;
    private final EventBus eventBus;
    private final SessionService sessopService;
    
    public RegisterController(RegisterView registerView, RegisterService registerService, SessionService sessionService, EventBus eventBus) {
        this.registerService = registerService;
        this.registerView = registerView;
        this.sessopService = sessionService;
        this.eventBus = eventBus;

        
    }

    public void onRegisterButtonClicked() {
        String username = registerView.getUsername();
        String email = registerView.getEmail();
        String password = registerView.getPassword();

        RegisterRequest registerRequest = new RegisterRequest(username, email, password);

        registerService.register(registerRequest);
    }

    public void onGoToLoginButtonClicked() {
        eventBus.publish(new GoToLoginButtonClicked());
    }
}
