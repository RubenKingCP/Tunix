package tunix.service;

import java.util.LinkedList;
import java.util.List;

import tunix.api.LibraryApiClient;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.LibraryResponse;
import tunix.model.account.Artist;

public class FollowService {

    private final LibraryApiClient libraryApiClient;
    private final int userId;

    public FollowService(LibraryApiClient libraryApiClient, int userId) {
        this.libraryApiClient = libraryApiClient;
        this.userId = userId;
    }

    public void followArtist(int userId, int artistId) {
        ApiResponse<LibraryResponse> response = libraryApiClient.followArtist(artistId);
        if (response == null || !response.isSuccess()) {
            String msg = response != null ? response.getMessage() : "Unknown error";
            throw new RuntimeException("followArtist failed: " + msg);
        }
    }

    public void unfollowArtist(int userId, int artistId) {
        ApiResponse<LibraryResponse> response = libraryApiClient.unfollowArtist(artistId);
        if (response == null || !response.isSuccess()) {
            String msg = response != null ? response.getMessage() : "Unknown error";
            throw new RuntimeException("unfollowArtist failed: " + msg);
        }
    }

    public List<Artist> getFollowedArtists(int userId) {
        // Populated from library data — extend when a dedicated endpoint exists
        return new LinkedList<>();
    }

    public boolean isFollowing(int userId, int artistId) {
        ApiResponse<LibraryResponse> response = libraryApiClient.getLibrary(userId);
        if (response == null || !response.isSuccess() || response.getData() == null) {
            return false;
        }
        return response.getData().getFollowedArtists()
                .stream()
                .anyMatch(a -> a.getId() == artistId);
    }
}