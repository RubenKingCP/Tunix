package tunixserver.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.ArtistResponse;
import tunixserver.service.ArtistBackendService;

@RestController                  // ← was missing entirely
@RequestMapping("/artist")
public class ArtistBackendController {

    private final ArtistBackendService artistBackendService;

    public ArtistBackendController(ArtistBackendService artistBackendService) {
        this.artistBackendService = artistBackendService;
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse<List<ArtistResponse>>> getArtistsByName(@RequestParam String query) {
        System.out.println("ArtistBackendController: Got request query: " + query);
        List<ArtistResponse> artists = artistBackendService.searchByName(query);

        return ResponseEntity.ok(
            new ApiResponse<>(true, "Artists Found!", artists)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArtistResponse>> getArtistById(@PathVariable("id") Long id) {
        // ↑ "id" matches /{id}, and Long matches your entity's PK type
        System.out.println("Fetching artist by id: " + id);
        ArtistResponse artist = artistBackendService.searchById(id.intValue());

        return ResponseEntity.ok(new ApiResponse<>(true, "Artist By Id fetched", artist));
    }
}