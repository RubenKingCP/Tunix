package tunixserver.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tunixserver.service.SongBackendService;


@RestController
@RequestMapping("/playlists")
public class PlaylistBackendController {
    private final SongBackendService songBackendService;
    private final PlaylistBackendService playlistBackendService;

    public PlaylistBackendController(PlaylistBackendService playlistBackendService, SongBackendService songBackendService) {
        this.playlistBackendService = playlistBackendService;
        this.songBackendService = songBackendService;
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

    @GetMapping("/name")
    public ResponseEntity<ApiResponse<List<PlaylistResponse>>> getPlaylistsByName(
        @RequestParam String query
    ) {
        System.out.println("PlaylistBackendController: Got request query: " + query);
        List<PlaylistResponse> playlists = playlistBackendService.searchByName(query);

        return ResponseEntity.ok(
            new ApiResponse<>(true, "Playlists Found!", playlists)
        );
    }
    
    @GetMapping("/{playlistId}")
    public ResponseEntity<ApiResponse<PlaylistResponse>> getMethodName(@PathVariable Long playlistId) {
        try {
                PlaylistResponse response = playlistBackendService.getPlaylist(playlistId);
                
                return ResponseEntity.ok(
                    new ApiResponse<>(
                        true,
                        "Playlist Fetched successfully",
                        response)
                );
        } catch (Exception e) {
                // TODO: handle exception
                return ResponseEntity.ok(
                    new ApiResponse<>(true, "Playlist not found", null)
                );
        }
    }
}
    

