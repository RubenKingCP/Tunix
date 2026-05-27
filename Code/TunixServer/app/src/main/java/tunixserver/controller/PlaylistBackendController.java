package tunixserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tunixserver.dto.request.PlaylistCreateRequest;
import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.PlaylistResponse;
import tunixserver.entities.PlaylistEntity;
import tunixserver.service.PlaylistBackendService;

@RestController
@RequestMapping("/playlists")
public class PlaylistBackendController {
    private final PlaylistBackendService playlistBackendService;

    public PlaylistBackendController(PlaylistBackendService playlistBackendService) {
        this.playlistBackendService = playlistBackendService;
    }

    @PostMapping("/{playlistId}/add/{songId}")
    public ResponseEntity<ApiResponse<Void>> addSongToPlaylist(Long playlistId, Long songId) {
        if (playlistBackendService.addSongToPlaylist(playlistId, songId)) {
            // Return a success response (e.g., HTTP 200 OK)
            return ResponseEntity.ok(ApiResponse.success());
        } else {
            return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(
                ApiResponse.error("Song already exists")
            );
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PlaylistResponse>> createPlaylist(
            @RequestBody PlaylistCreateRequest playlistCreateRequest) {

        try {
            System.out.println("PlaylistBackendController: Got request\nPlaylistBackendController Request: "
                    + playlistCreateRequest.getTitle());

            PlaylistEntity playlist = playlistBackendService.createPlaylist(playlistCreateRequest);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Playlist created successfully",
                            PlaylistResponse.fromEntity(playlist)
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponse<>(
                            false,
                            "Failed to create playlist: " + e.getMessage(),
                            null
                    )
            );
        }
    }
}
