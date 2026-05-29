package tunix.api;

import java.util.List;

import tunix.dto.request.BanRequest;
import tunix.dto.response.ApiResponse;

public class AdminApi {
    private final ApiClient apiClient;
    public AdminApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    public Boolean issueBan(BanRequest banRequest) {
    Boolean result = apiClient.post("/admin/issueBan", banRequest, boolean.class).getData();
    return result != null && result;
}

public Boolean issueWarning(BanRequest request) {
    Boolean result = apiClient.post("/admin/issueWarning", request, boolean.class).getData();
    return result != null && result;
}
    public List<String> getArtistModerationHistory(int artistId) {
        ApiResponse<List<String>> response = apiClient.get("/admin/artistModerationHistory/" + artistId, new com.fasterxml.jackson.core.type.TypeReference<ApiResponse<List<String>>>(){});
        return response.getData();
    }

}
