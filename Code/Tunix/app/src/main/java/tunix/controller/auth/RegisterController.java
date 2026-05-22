package tunix.controller.auth;

import tunix.dto.request.RegisterRequest;
import tunix.event.EventBus;
import tunix.event.GoToLoginButtonClicked;
import tunix.service.RegisterService;
import tunix.view.auth.RegisterView;

public class RegisterController {
    private final RegisterService registerService;
    private final RegisterView registerView;
    private final EventBus eventBus;
    
    public RegisterController(RegisterView registerView, RegisterService registerService, EventBus eventBus) {
        this.registerService = registerService;
        this.registerView = registerView;
        this.eventBus = eventBus;

        
    }

    public void onRegisterButtonClicked() {
        System.err.println("Request controller ereached \n");
        String username = registerView.getUsername();
        String email = registerView.getEmail();
        String password = registerView.getPassword();

        RegisterRequest registerRequest = new RegisterRequest(username, email, password);

        boolean success = registerService.register(registerRequest);
    }

    public void onGoToLoginButtonClicked() {
        eventBus.publish(new GoToLoginButtonClicked());
    }
}
