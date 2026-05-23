package tunix.service.auth;

import tunix.api.LoginApiClient;
import tunix.dto.request.LoginRequest;
import tunix.dto.response.AccountResponse;
import tunix.dto.response.ApiResponse;
import tunix.event.EventBus;
import tunix.event.LoginSuccessEvent;
import tunix.model.Account;

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
            System.out.println("Usrer login");
            AccountResponse dto = response.getData();
            Account account = new Account(dto.getAccountId(), dto.getUsername(), dto.getEmail(), dto.getRole());

            eventBus.publish(new LoginSuccessEvent(account));
        } else {
            System.out.println(":(");
        }
    }
}
 