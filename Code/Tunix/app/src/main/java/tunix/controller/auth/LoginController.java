package tunix.controller.auth;

import tunix.event.EventBus;
import tunix.event.GoToRegisterButtonClicked;
import tunix.service.auth.LoginService;
import tunix.view.auth.LoginView;

public class LoginController {
    private final LoginView loginView;
    private final LoginService loginService;
    private final EventBus eventBus;

    public LoginController(LoginView loginView, LoginService loginService, EventBus eventBus){
        this.loginView = loginView;
        this.loginService = loginService;
        this.eventBus = eventBus;
    }

    public void onLogin(String username, String password){
        System.out.println("Login button Clicked");
    }

    public void onGoToRegisterButtonClicked() {
        System.out.println("Register button clciked\n");
        eventBus.publish(new GoToRegisterButtonClicked());
        System.err.println("Register button event published \n");
    }
}
