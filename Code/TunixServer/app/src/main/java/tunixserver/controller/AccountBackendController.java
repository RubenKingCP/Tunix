package tunixserver.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tunixserver.dto.request.LoginRequest;
import tunixserver.dto.request.RegisterRequest;
import tunixserver.dto.response.AccountResponse;
import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.ArtistResponse;
import tunixserver.service.ArtistBackendService;
import tunixserver.service.UserBackendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/account")
public class AccountBackendController {
    private final UserBackendService userBackendService;
    private final ArtistBackendService artistBackendService; 

    public AccountBackendController(UserBackendService userBackendService, ArtistBackendService artistBackendService) {
        this.userBackendService = userBackendService;
        this.artistBackendService = artistBackendService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AccountResponse>> register(@RequestBody RegisterRequest registerRequest) {
        try {
            
            AccountResponse accountResponse = userBackendService.registerUser(registerRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "User Registered successcfully!", accountResponse));
        } catch(Exception exception) {
            return ResponseEntity.ok(new ApiResponse<>(false, "Failed to register User" + exception, null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccountResponse>> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("\nReached Backend" +
                                "\nUsername: " + loginRequest.getUsername() +
                                "\nPassword: " + loginRequest.getPassword()
            );
            AccountResponse accountResponse = userBackendService.loginUser(loginRequest);
            System.out.println("Data from reponse: " + accountResponse.getUsername());
            return ResponseEntity.ok(new ApiResponse<>(true, "User login succesfull!", accountResponse));

        } catch(Exception exception) {
            return ResponseEntity.ok(new ApiResponse<>(false, "Failed to login User" + exception, null));
        }
    }

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<Boolean>> startPremium(@RequestBody Long userId) {

        try {
            System.out.println("START PREMIUM REQUEST");
            System.out.println("User ID: " + userId);

            boolean result = userBackendService.startPremium(userId);

            System.out.println("Premium activated for user: " + userId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Premium started successfully!", result)
            );

        } catch (Exception e) {
            System.out.println("ERROR starting premium: " + e.getMessage());

            return ResponseEntity.ok(
                    new ApiResponse<>(false, "Failed to start premium: " + e.getMessage(), false)
            );
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse<Boolean>> cancelPremium(@RequestBody Long userId) {

        try {
            System.out.println("CANCEL PREMIUM REQUEST");
            System.out.println("User ID: " + userId);

            boolean result = userBackendService.cancelPremium(userId);

            System.out.println("Premium cancelled for user: " + userId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Premium cancelled successfully!", result)
            );

        } catch (Exception e) {
            System.out.println("ERROR cancelling premium: " + e.getMessage());

            return ResponseEntity.ok(
                    new ApiResponse<>(false, "Failed to cancel premium: " + e.getMessage(), false)
            );
        }
    }

    @PostMapping("/trial")
    public ResponseEntity<ApiResponse<Boolean>> startTrial(@RequestBody Long userId) {

        try {
            System.out.println("START PREMIUM REQUEST");
            System.out.println("User ID: " + userId);

            boolean result = userBackendService.startTrial(userId);

            System.out.println("Premium activated for user: " + userId);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Trial started successfully!", result)
            );

        } catch (Exception e) {
            System.out.println("ERROR starting trial: " + e.getMessage());

            return ResponseEntity.ok(
                    new ApiResponse<>(false, "Failed to start trial: " + e.getMessage(), false)
            );
        }
    }

    @GetMapping("/artists/name")
    public ResponseEntity<ApiResponse<List<ArtistResponse>>> getArtistsByName(@RequestParam String query) {
        System.out.println("PlaylistBackendController: Got request query: " + query);
        List<ArtistResponse> artist = artistBackendService.searchByName(query);
        
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Artists Found!", artist)
        );
    }
    
}
