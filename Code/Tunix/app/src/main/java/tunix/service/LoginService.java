package tunix.service;

import tunix.api.LoginApiClient;

public class LoginService {
    private final LoginApiClient loginApiClient;

    public LoginService(LoginApiClient loginApiClient) {
        this.loginApiClient = loginApiClient;
    }
}
