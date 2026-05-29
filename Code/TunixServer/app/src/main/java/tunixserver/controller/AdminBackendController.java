package tunixserver.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tunixserver.dto.request.BanRequest;
import tunixserver.dto.response.ApiResponse;
import tunixserver.service.AdminBackendService;

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
            @RequestBody Integer artistId
    ) {

        Boolean result = adminBackendService.issueWarning(artistId);

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
}