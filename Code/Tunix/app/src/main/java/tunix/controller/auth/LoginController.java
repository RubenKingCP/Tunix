package tunix.controller.auth;

import tunix.dto.request.LoginRequest;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.GoToRegisterButtonClicked;
import tunix.service.auth.LoginService;
import tunix.service.auth.SessionService;
import tunix.ui.views.auth.LoginView;

public class LoginController {
    private final LoginView loginView;
    private final LoginService loginService;
    private final EventBus eventBus;
    private final SessionService sessionService;

    public LoginController(LoginView loginView, LoginService loginService, SessionService sessionService, EventBus eventBus){
        this.loginView = loginView;
        this.loginService = loginService;
        this.eventBus = eventBus;
        this.sessionService = sessionService;
    }

    public void onLogin(String username, String password){
        LoginRequest loginRequest = new LoginRequest(username, password);
        loginService.login(loginRequest);
    }

    public void onGoToRegisterButtonClicked() {
        eventBus.publish(new GoToRegisterButtonClicked());
    }
}