package tunix.service.auth;

import tunix.api.LoginApiClient;
import tunix.dto.request.LoginRequest;
import tunix.dto.response.AccountResponse;
import tunix.dto.response.ApiResponse;
import tunix.model.account.Account;
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

        Account account = Account.from(dto);

        eventBus.publish(new LoginSuccessEvent(account));

        eventBus.publish(new SwitchScreenEvent(MainPanel.class));

        eventBus.publish(new SwitchCenterScreenEvent(HomeView.class));

    } else {
        System.out.println(":(");
    }
}

    public void logout() {
        
    }
}
 