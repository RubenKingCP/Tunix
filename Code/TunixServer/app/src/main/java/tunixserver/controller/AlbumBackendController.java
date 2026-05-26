package tunixserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tunixserver.dto.request.AlbumRequest;
import tunixserver.dto.response.AlbumResponse;
import tunixserver.dto.response.ApiResponse;
import tunixserver.entities.AlbumEntity;
import tunixserver.service.AlbumBackendService;


@RestController
@RequestMapping("/api/albums")
public class AlbumBackendController {
    private final AlbumBackendService albumBackendService;
    AlbumBackendController(AlbumBackendService albumBackendService) {
        this.albumBackendService = albumBackendService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<AlbumResponse>> uploadAlbum(@RequestBody AlbumRequest albumRequest){
        try {
            AlbumEntity album = albumBackendService.uploadAlbum(albumRequest);
            return ResponseEntity.ok(new ApiResponse<AlbumResponse>(true, "Album uploaded successfully", AlbumResponse.fromEntity(album)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<AlbumResponse>(false, "Failed to upload album: " + e.getMessage(), null));
        }
    }

    @PostMapping("/fetch")
    public ResponseEntity<ApiResponse<AlbumResponse>> fetchAlbum(@RequestBody AlbumRequest albumRequest){
        try {
            AlbumEntity album = albumBackendService.fetchAlbum(albumRequest);
            return ResponseEntity.ok(new ApiResponse<AlbumResponse>(true, "Album fetched successfully", AlbumResponse.fromEntity(album)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<AlbumResponse>(false, "Failed to fetch album: " + e.getMessage(), null));
        }
    }
}
