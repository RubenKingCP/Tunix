package tunixserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.ArtistRequestResponse;
import tunixserver.service.ArtistRequestBackendService;
import java.util.List;

@RestController
@RequestMapping("/artist-requests")
public class ArtistRequestBackendController {
    private final ArtistRequestBackendService artistRequestBackendService;

    public ArtistRequestBackendController(ArtistRequestBackendService artistRequestBackendService) {
        this.artistRequestBackendService = artistRequestBackendService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ArtistRequestResponse>>> getAllArtistRequests() {
        return artistRequestBackendService.getAllArtistRequests();
    }
    
    @GetMapping("/approve")
    public ResponseEntity<ApiResponse<Void>> approveArtistRequest(int requestId) {
        artistRequestBackendService.approveArtistRequest(requestId);
        return null; // Placeholder return statement
    }

    @GetMapping("/reject")
    public ResponseEntity<ApiResponse<Void>> rejectArtistRequest(int requestId) {
        // Code to reject artist request and return response
        artistRequestBackendService.rejectArtistRequest(requestId);
        return null; // Placeholder return statement
    }
}
