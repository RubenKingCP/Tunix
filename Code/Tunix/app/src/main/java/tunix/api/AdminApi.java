package tunix.api;

public class AdminApi {
    private final ApiClient apiClient;
    public AdminApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    public Boolean issueWarning(int artistId){
        
        return apiClient.post("/admin/issueWarning", artistId, boolean.class).getData();
    }

    public Boolean issueBan(int artistId){
        return apiClient.post("/admin/issueBan", artistId, boolean.class).getData();
    }

}
