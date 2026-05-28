package tunixserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.LibraryResponse;
import tunixserver.service.LibraryBackendService;
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
}