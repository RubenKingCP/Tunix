package tunixserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tunixserver.dto.request.LoginRequest;
import tunixserver.dto.request.RegisterRequest;
import tunixserver.dto.response.AccountResponse;
import tunixserver.dto.response.ApiResponse;
import tunixserver.service.UserBackendService;

@RestController
@RequestMapping("/account")
public class UserBackendController {
    private final UserBackendService userBackendService;

    public UserBackendController(UserBackendService userBackendService) {
        this.userBackendService = userBackendService;
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
            return ResponseEntity.ok(new ApiResponse<>(true, "User login succesfull!", accountResponse));

        } catch(Exception exception) {
            return ResponseEntity.ok(new ApiResponse<>(false, "Failed to login User" + exception, null));
        }
    }
}
