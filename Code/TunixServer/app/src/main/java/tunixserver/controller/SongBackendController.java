package tunixserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tunixserver.service.SongBackendService;
import tunixserver.dto.request.SongRequest;
import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.SongResponse;
import tunixserver.entities.SongEntity;

@RestController
@RequestMapping("/songs")
public class SongBackendController {
    private final SongBackendService songService; 

    public SongBackendController(SongBackendService songService) {
        this.songService = songService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<SongResponse>> uploadSong(@RequestBody SongRequest songRequest) {
        try {
            System.out.println("SongBackendController: Got request\nSongBackendController Request: " + songRequest.getTitle());
            SongEntity song = songService.uploadSong(songRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Song uploaded successfully", SongResponse.fromEntity(song)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, "Failed to upload song: " + e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SongResponse>>> getAllSongs() {

        try {

            List<SongResponse> songs = songService.getAllSongs();

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Songs fetched successfully",
                            songs
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            false,
                            "Failed to fetch songs: " + e.getMessage(),
                            null
                    )
            );
        }
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse<List<SongResponse>>> getSongsByName(
            @RequestParam String query
    ) {

        List<SongResponse> songs = songService.searchByName(query);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Songs found", songs)
        );
    }

    @DeleteMapping("/remove/{id}")
    public ApiResponse<Void> removeSong(@PathVariable Long id) {
        songService.deleteSong(id);

        return new ApiResponse<>(
            true,
            "Song deleted successfully",
            null
        );
    }

}
