package tunix.api;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;

import tunix.dto.response.ApiResponse;
import tunix.dto.response.SongResponse;
import tunix.dto.response.AlbumResponse;
import tunix.model.ILibraryAsset;
import tunix.model.account.Artist;
import tunix.model.musicContent.Album;
import tunix.model.musicContent.Song;

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

    public ILibraryAsset getById(long id) {
        ApiResponse<AlbumResponse> response = apiClient.get(
            "/albums/" + id,
            new TypeReference<ApiResponse<AlbumResponse>>() {}
        );
        if (response == null || !response.isSuccess()) return null;

        AlbumResponse data = response.getData();

        // DEBUG
        System.out.println("AlbumResponse.getSongResponses() = " + data.getSongResponses());

        List<Song> songs = data.getSongResponses() == null ? new ArrayList<>() :
            data.getSongResponses().stream()
                .map(SongResponse::toSong)
                .collect(Collectors.toList());

        System.out.println("Mapped songs count: " + songs.size());

        Artist artist = new Artist(data.getArtistId(), null, null, null, 0, false);

        return new Album(
            data.getTitle(),
            data.getId().intValue(),
            artist,
            songs,
            data.getReleaseDate() != null ? Date.valueOf(data.getReleaseDate()) : null
        );
    }
}
