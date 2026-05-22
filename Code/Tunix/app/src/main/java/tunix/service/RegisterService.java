package tunix.service;

import tunix.api.RegisterApiClient;
import tunix.dto.request.RegisterRequest;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.AccountResponse;

public class RegisterService {
    private final RegisterApiClient registerApiClient;

    public RegisterService(RegisterApiClient registerApiClient) {
        this.registerApiClient = registerApiClient;
    }

    public boolean register(RegisterRequest registerRequest) {
        System.err.println("Reached register service\n " +
                            "\nRequest name: " + registerRequest.getUsername()
                            + "\nRequest email: " + registerRequest.getEmail()
                            + "\nRequest password: " + registerRequest.getPassword());

        ApiResponse<AccountResponse> response = registerApiClient.register(registerRequest);
        if (response.isSuccess()) {
            System.err.println("\nUser registered to dtaabae");
            return true;
        } else {
            System.err.println("No :(\n" + response.getMessage());
            return false;
        }
    }
}
