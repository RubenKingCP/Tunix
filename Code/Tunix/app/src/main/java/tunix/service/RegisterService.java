package tunix.service;

import tunix.api.RegisterApiClient;
import tunix.dto.request.RegisterRequest;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.RegisterResponse;

public class RegisterService {
    private final RegisterApiClient registerApiClient;

    public RegisterService(RegisterApiClient registerApiClient) {
        this.registerApiClient = registerApiClient;
    }

    public boolean register(RegisterRequest registerRequest) {
        System.err.println("Reached register service\n");
        ApiResponse<RegisterResponse> response = registerApiClient.register(registerRequest);
        return false;
    }
}
