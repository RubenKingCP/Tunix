package tunix.service.auth;

import tunix.api.LoginApiClient;
import tunix.controller.HomeController;
import tunix.dto.request.LoginRequest;
import tunix.dto.response.AccountResponse;
import tunix.dto.response.ApiResponse;
import tunix.model.account.Account;
import tunix.model.account.Admin;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.LoginSuccessEvent;
import tunix.navigation.events.LogoutEvent;
import tunix.navigation.events.SwitchCenterScreenEvent;
import tunix.navigation.events.SwitchScreenEvent;
import tunix.ui.AdminPanel;
import tunix.ui.AuthPanel;
import tunix.ui.MainPanel;

public class LoginService {
    private final LoginApiClient loginApiClient;
    private final EventBus eventBus;

    public LoginService(LoginApiClient loginApiClient, EventBus eventBus) {
        this.loginApiClient = loginApiClient;
        this.eventBus = eventBus;

        this.eventBus.subscribe(LogoutEvent.class, ignored -> logout());
    }

    public void login(LoginRequest loginRequest) {
        System.out.println("Send request to service");
        ApiResponse<AccountResponse> response = loginApiClient.login(loginRequest);

        if (response.isSuccess()) {
            System.out.println("User login");

            AccountResponse dto = response.getData();

            Account account = Account.from(dto);
            if (account.getClass() != Admin.class){
                eventBus.publish(new LoginSuccessEvent(account));
                eventBus.publish(new SwitchScreenEvent(MainPanel.class));
                eventBus.publish(new SwitchCenterScreenEvent(HomeController.class));
            } else {
                eventBus.publish(new SwitchScreenEvent(AdminPanel.class));
            }
            
        } else {
            System.out.println(":(");
        }
    }

    public void logout() {
        if (SessionService.Instance != null) {
            SessionService.Instance.clear();
        }

        eventBus.publish(new SwitchScreenEvent(AuthPanel.class));
    }
}