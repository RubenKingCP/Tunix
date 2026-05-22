package tunixserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tunixserver.dto.request.RegisterRequest;
import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.RegisterResponse;
import tunixserver.service.UserBackendService;

@RestController
@RequestMapping("/account")
public class UserBackendController {
    private final UserBackendService userBackendService;

    public UserBackendController(UserBackendService userBackendService) {
        this.userBackendService = userBackendService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) {
        userBackendService.registerUser(registerRequest);
        return null;
    }
}
