package tunix.service.auth;


import tunix.api.RegisterApiClient;
import tunix.dto.request.RegisterRequest;
import tunix.dto.response.ApiResponse;
import tunix.model.account.Account;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.RegisterSuccessfulEvent;
import tunix.dto.response.AccountResponse;

public class RegisterService {
    private final RegisterApiClient registerApiClient;
    private final EventBus eventBus;

    public RegisterService(RegisterApiClient registerApiClient, EventBus eventBus) {
        this.registerApiClient = registerApiClient;
        this.eventBus = eventBus;
    }

    public void register(RegisterRequest registerRequest) {

        ApiResponse<AccountResponse> response =
                registerApiClient.register(registerRequest);

        if (response.isSuccess()) {

            System.err.println("\nUser registered to database");

            AccountResponse dto = response.getData();

            Account account = Account.from(dto);

            eventBus.publish(new RegisterSuccessfulEvent(account));

        } else {

            System.err.println("No :(\n" + response.getMessage());
        }
    }
}
 