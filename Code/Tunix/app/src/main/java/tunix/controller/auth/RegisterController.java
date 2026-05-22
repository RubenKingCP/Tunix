package tunix.controller.auth;

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
        
    }

    public void onGoToLoginButtonClicked() {
        eventBus.publish(new GoToLoginButtonClicked());
    }
}
