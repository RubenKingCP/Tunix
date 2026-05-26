package tunixserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tunixserver.service.SongBackendService;
import tunixserver.dto.request.SongRequest;
import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.SongResponse;
import tunixserver.entities.SongEntity;

@RestController
@RequestMapping("/api/songs")
public class SongBackendController {
    private final SongBackendService songService;

    public SongBackendController(SongBackendService songService) {
        this.songService = songService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<SongResponse>> uploadSong(@RequestBody SongRequest songRequest) {
        try {
            SongEntity song = songService.uploadSong(songRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Song uploaded successfully", SongResponse.fromEntity(song)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, "Failed to upload song: " + e.getMessage(), null));
        }
    }
}
