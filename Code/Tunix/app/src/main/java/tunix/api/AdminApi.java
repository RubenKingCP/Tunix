package tunix.api;

import java.util.List;

import tunix.dto.request.BanRequest;
import tunix.dto.response.ApiResponse;

public class AdminApi {
    private final ApiClient apiClient;
    public AdminApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    public Boolean issueWarning(int artistId){
        
        return apiClient.post("/admin/issueWarning", artistId, boolean.class).getData();
    }

    public Boolean issueBan(BanRequest banRequest){
        return apiClient.post("/admin/issueBan", banRequest, boolean.class).getData();
    }
    public List<String> getArtistModerationHistory(int artistId) {
        ApiResponse<List<String>> response = apiClient.get("/admin/artistModerationHistory/" + artistId, new com.fasterxml.jackson.core.type.TypeReference<ApiResponse<List<String>>>(){});
        return response.getData();
    }

}
