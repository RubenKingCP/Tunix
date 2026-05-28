package tunix.api;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import tunix.dto.response.ApiResponse;
import tunix.dto.response.AlbumResponse;
import tunix.model.ILibraryAsset;
import tunix.model.musicContent.Album;

public class AlbumApi {
    private final ApiClient apiClient;

    public AlbumApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<ILibraryAsset> getUserAlbums(int longId) {
        ApiResponse<List<AlbumResponse>> userAlbums = apiClient.get("/albums/user/" + longId, new TypeReference<ApiResponse<List<AlbumResponse>>>() {});
        if (userAlbums.isSuccess()) {
            List<ILibraryAsset> assets = new ArrayList<>();
            for (AlbumResponse album : userAlbums.getData()) {
                Date releaseDate = album.getReleaseDate() != null ? Date.valueOf(album.getReleaseDate()) : null;
                assets.add(new Album(album.getTitle(), album.getId().intValue(), null, new ArrayList<>(), releaseDate));
            }
            return assets;
        }
        
        return new ArrayList<>();
    }
    
    public ApiResponse<List<AlbumResponse>> getAlbumsByName(String query) {
        System.out.println("AlbumApi: Reached the get albums by name query: " + query);
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        return apiClient.get("/albums/name?query=" + encodedQuery, new TypeReference<ApiResponse<List<AlbumResponse>>>() {
        });
    }
}
