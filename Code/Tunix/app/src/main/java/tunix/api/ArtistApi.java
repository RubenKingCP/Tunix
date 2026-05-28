package tunix.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import tunix.dto.response.ApiResponse;
import tunix.dto.response.ArtistResponse;
import tunix.model.ILibraryAsset;
import tunix.model.account.Artist;

public class ArtistApi {
    private final ApiClient apiClient;

    public ArtistApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<ILibraryAsset> getUserArtists(int longId) {
        ApiResponse<List<ArtistResponse>> userArtists = apiClient.get("/artists/user/" + longId, new TypeReference<ApiResponse<List<ArtistResponse>>>() {});
        if (userArtists.isSuccess()) {
            List<ILibraryAsset> assets = new ArrayList<>();
            for (ArtistResponse artist : userArtists.getData()) {
                assets.add(new Artist(artist.getId(), "Artist " + artist.getId(), "", null, artist.getFollowersCount(), artist.isVerified()));
            }
            return assets;
        }
        
        return new ArrayList<>();
    }
    
}
