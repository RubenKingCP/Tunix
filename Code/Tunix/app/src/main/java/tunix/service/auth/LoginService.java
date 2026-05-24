package tunix.service.auth;

import tunix.api.LoginApiClient;
import tunix.dto.request.LoginRequest;
import tunix.dto.response.AccountResponse;
import tunix.dto.response.ApiResponse;
import tunix.model.Account;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.LoginSuccessEvent;
import tunix.navigation.events.SwitchCenterScreenEvent;
import tunix.navigation.events.SwitchScreenEvent;
import tunix.ui.MainPanel;
import tunix.ui.views.main.center.HomeView;

public class LoginService {
    private final LoginApiClient loginApiClient;
    private final EventBus eventBus;

    public LoginService(LoginApiClient loginApiClient, EventBus eventBus) {
        this.loginApiClient = loginApiClient;
        this.eventBus = eventBus;
    }

    public void login(LoginRequest loginRequest) {
        ApiResponse<AccountResponse> response = loginApiClient.login(loginRequest);

        if (response.isSuccess()) {
            System.out.println("User login");
            AccountResponse dto = response.getData();
            Account account = new Account(dto.getAccountId(), dto.getUsername(), dto.getEmail(), dto.getRole());

            // Send success login
            eventBus.publish(new LoginSuccessEvent(account));

            // Send switch to main panel
            eventBus.publish(new SwitchScreenEvent(MainPanel.class));

            // Send show home view
            eventBus.publish(new SwitchCenterScreenEvent(HomeView.class));
        } else {
            // TODO: Add eventbus system failed view
            System.out.println(":(");
        }
    }

    public void logout() {
        
    }
}
 