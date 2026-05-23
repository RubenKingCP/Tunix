package tunix.api;

import tunix.dto.request.RegisterRequest;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.AccountResponse;

public class RegisterApiClient {
    private final ApiClient apiClient;
    
    public RegisterApiClient(ApiClient apiClient){
        this.apiClient = apiClient;
    }

    public ApiResponse<AccountResponse> register(RegisterRequest registerRequest) {
        System.err.println("Reached ApiClient service\n " +
                            "\nRequest name: " + registerRequest.getUsername()
                            + "\nRequest email: " + registerRequest.getEmail()
                            + "\nRequest password: " + registerRequest.getPassword());
        return apiClient.post("/account/register", registerRequest, AccountResponse.class);
    }
}
