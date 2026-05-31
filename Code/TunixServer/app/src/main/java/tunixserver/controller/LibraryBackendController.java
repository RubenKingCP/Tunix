package tunixserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.LibraryResponse;
import tunixserver.service.LibraryBackendService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/library")
public class LibraryBackendController {

    private final LibraryBackendService libraryService;

    public LibraryBackendController(LibraryBackendService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    ("/{accountId}")
    public ResponseEntity<ApiResponse<LibraryResponse>> getLibrary(
            @PathVariable Long accountId
    ) {

        LibraryResponse response = libraryService.getLibrary(accountId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Library fetched successfully", response)
        );
    }

    @PostMapping("/add/{artistId}/{userId}")
    public ResponseEntity<ApiResponse<LibraryResponse>> followArtist(
            @PathVariable Long artistId,
            @PathVariable Long userId
    ) {
        LibraryResponse response = libraryService.followArtist(artistId,userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Artist followed successfully", response)
        );
    }
    @DeleteMapping("/remove/{artistId}/{userId}")
public ResponseEntity<ApiResponse<LibraryResponse>> unfollowArtist(
        @PathVariable Long artistId,
        @PathVariable Long userId
) {
    LibraryResponse response = libraryService.unfollowArtist(artistId, userId);
    return ResponseEntity.ok(
            new ApiResponse<>(true, "Artist unfollowed successfully", response)
    );
}
}