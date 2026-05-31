package tunix.api;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import tunix.dto.response.ApiResponse;
import tunix.dto.response.ArtistResponse;


public class ArtistApi {
    private final ApiClient apiClient;

    public ArtistApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResponse<ArtistResponse> getArtistById(int artistId) {
        System.out.println("ArtistApiClient: Fetching artist");
        return apiClient.get(
            "/artist/" + artistId, 
            new TypeReference<ApiResponse<ArtistResponse>>() {}
        );
    }

    public ApiResponse<List<ArtistResponse>> getArtistsByName(String query) {
        System.out.println("ArtistApiClient: Artist search query");
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        return apiClient.get(
                "/artist/name?query=" + encodedQuery,
                new TypeReference<ApiResponse<List<ArtistResponse>>>() {}
        );
    }

    
}
