package tunixserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tunixserver.dto.response.ApiResponse;
import tunixserver.service.LibraryBackendService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/library")
public class LibraryBackendController {

    private final LibraryBackendService libraryService;

    public LibraryBackendController(LibraryBackendService libraryBackendService) {
        this.libraryService = libraryBackendService;
    }

    @PostMapping("/song/add")
    public ResponseEntity<ApiResponse<Void>> addSong(
            @RequestParam Long accountId,
            @RequestParam Long songId) {

        return ResponseEntity.ok(
                libraryService.addSongToLibrary(accountId, songId)
        );
    }

    @DeleteMapping("/song/remove")
    public ResponseEntity<ApiResponse<Void>> removeSong(
            @RequestParam Long accountId,
            @RequestParam Long songId) {

        return ResponseEntity.ok(
                libraryService.removeSongFromLibrary(accountId, songId)
        );
    }
}