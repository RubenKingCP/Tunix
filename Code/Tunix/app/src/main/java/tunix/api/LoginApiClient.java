package tunix.api;

import tunix.dto.request.LoginRequest;
import tunix.dto.response.AccountResponse;
import tunix.dto.response.ApiResponse;

public class LoginApiClient {
    private final ApiClient apiClient;
    
    public LoginApiClient(ApiClient apiClient){
        this.apiClient = apiClient;
    }

    public ApiResponse<AccountResponse> login(LoginRequest loginRequest) {
        return apiClient.post("/account/login", loginRequest, AccountResponse.class);
    }
}
