package tunix.service;

import tunix.api.RegisterApiClient;

public class RegisterService {
    private final RegisterApiClient RegisterApiClient;

    public RegisterService(RegisterApiClient registerApiClient) {
        this.RegisterApiClient = registerApiClient;
    }
}
