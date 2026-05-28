package tunix.api;

import com.fasterxml.jackson.core.type.TypeReference;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.LibraryResponse;

public class LibraryApiClient {

    private final ApiClient apiClient;

    public LibraryApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // =========================
    // GET LIBRARY
    // =========================
    public ApiResponse<LibraryResponse> getLibrary(Integer accountId) {

        String path = "/library/" + accountId;

        System.out.println("LibraryApiClient: GET library for account " + accountId);

        return apiClient.get(
                path,
                new TypeReference<ApiResponse<LibraryResponse>>() {}
        );
    }


}