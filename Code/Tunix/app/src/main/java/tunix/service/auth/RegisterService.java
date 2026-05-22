package tunix.service.auth;


import tunix.api.RegisterApiClient;
import tunix.dto.request.RegisterRequest;
import tunix.dto.response.ApiResponse;
import tunix.event.EventBus;
import tunix.event.RegisterSuccessfulEvent;
import tunix.model.Account;
import tunix.dto.response.AccountResponse;

public class RegisterService {
    private final RegisterApiClient registerApiClient;
    private final EventBus eventBus;

    public RegisterService(RegisterApiClient registerApiClient, EventBus eventBus) {
        this.registerApiClient = registerApiClient;
        this.eventBus = eventBus;
    }

    public void register(RegisterRequest registerRequest) {
        ApiResponse<AccountResponse> response = registerApiClient.register(registerRequest);
        if (response.isSuccess()) {
            System.err.println("\nUser registered to dtaabae");
            AccountResponse dto = response.getData();
            Account account = new Account(
                dto.getAccountId(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getRole()
            );

            eventBus.publish(new RegisterSuccessfulEvent(account));
             
        } else {
            System.err.println("No :(\n" + response.getMessage());
        }
    }
}
 