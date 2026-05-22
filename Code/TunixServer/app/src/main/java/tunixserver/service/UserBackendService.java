package tunixserver.service;

import org.springframework.stereotype.Service;

import tunixserver.dto.request.RegisterRequest;
import tunixserver.repository.UserBackendRepository;

@Service
public class UserBackendService {
    private final UserBackendRepository userBackendRepository;

    public UserBackendService(UserBackendRepository userBackendRepository) {
        this.userBackendRepository = userBackendRepository;
    }

    public boolean registerUser(RegisterRequest registerRequest){
        return false;
    }
}
