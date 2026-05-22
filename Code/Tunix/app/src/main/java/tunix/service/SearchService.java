package tunix.service;

import tunix.api.ApiClient;

public class SearchService {
    private final ApiClient apiClient;

    public SearchService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
}
