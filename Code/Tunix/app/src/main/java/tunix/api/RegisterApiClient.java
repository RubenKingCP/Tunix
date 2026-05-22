package tunix.api;

import tunix.dto.request.RegisterRequest;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.RegisterResponse;

public class RegisterApiClient {
    private final ApiClient apiClient;
    
    public RegisterApiClient(ApiClient apiClient){
        this.apiClient = apiClient;
    }

    public ApiResponse<RegisterResponse> register(RegisterRequest registerRequest) {
        System.err.println("Reached registeer Api client\n");
        return apiClient.post("/account/register", registerRequest, RegisterResponse.class);
    }
}
