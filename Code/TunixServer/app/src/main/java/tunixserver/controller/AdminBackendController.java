package tunixserver.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tunixserver.dto.request.BanRequest;
import tunixserver.dto.response.ApiResponse;
import tunixserver.service.AdminBackendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBackendController {

    private final AdminBackendService adminBackendService;

    // =========================
    // ISSUE WARNING
    // =========================
    @PostMapping("/issueWarning")
    public ResponseEntity<ApiResponse<Boolean>> issueWarning(
            @RequestBody BanRequest request
    ) {

        Boolean result = adminBackendService.issueWarning(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Warning issued successfully",
                        result
                )
        );
    }

    // =========================
    // ISSUE BAN
    // =========================
        @PostMapping("/issueBan")
        public ResponseEntity<ApiResponse<Boolean>> issueBan(
                @RequestBody BanRequest request
        ) {

        Boolean result = adminBackendService.banArtist(
                Long.valueOf(request.getArtistId()),
                request.getReason()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        result,
                        "Artist banned successfully",
                        true
                )
        );
        }

        @GetMapping("/artistModerationHistory/{artistId}")
        public ResponseEntity<ApiResponse<List<String>>> getMethodName(@PathVariable int artistId) {
            List<String> warnings = adminBackendService.getWarningsById(artistId);

            return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Got warnings for artist id: " + artistId,
                        warnings
                )
            );
        }
        
}