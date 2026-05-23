package tunixserver.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import tunixserver.dto.enums.Role;
import tunixserver.dto.request.LoginRequest;
import tunixserver.dto.request.RegisterRequest;
import tunixserver.dto.response.AccountResponse;
import tunixserver.dto.response.UserResponse;
import tunixserver.dto.response.ArtistResponse;
import tunixserver.entities.AccountEntity;
import tunixserver.entities.ArtistEntity;
import tunixserver.entities.UserEntity;
import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.UserBackendRepository;

@Service
public class UserBackendService {
    private final UserBackendRepository userBackendRepository;
    private final AccountBackendRepository accountBackendRepository;

    public UserBackendService(UserBackendRepository userBackendRepository, AccountBackendRepository accountBackendRepository) {
        this.userBackendRepository = userBackendRepository;
        this.accountBackendRepository = accountBackendRepository;
    }

    public AccountResponse registerUser(RegisterRequest registerRequest) {
        
        // Map Account request to entity
        AccountEntity accountEntity = AccountEntity.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .role(Role.USER)
                .build();

        AccountEntity savedAccount = accountBackendRepository.save(accountEntity);

        // Map user request to entity
        UserEntity userEntity = UserEntity.builder()
                .account(savedAccount)
                .displayName(registerRequest.getUsername())
                .premiumTrialUsed(false)
                .build();

        userBackendRepository.save(userEntity);

        // Get the user response
        UserResponse user = new UserResponse(userEntity.getDisplayName(), null, false);
        
        // Send the account response to user
        return new AccountResponse(
                savedAccount.getAccountId(),
                savedAccount.getUsername(),
                savedAccount.getEmail(),
                savedAccount.getRole(),
                user,
                null
        );
    }

    public AccountResponse loginUser(LoginRequest loginRequest) {

        Optional<AccountEntity> loginAccount = accountBackendRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());

        AccountEntity account = loginAccount.get();
        UserEntity user = account.getUser();
        ArtistEntity artist = account.getArtist();

        UserResponse userResponse = null;
        if (user != null) {
            userResponse = new UserResponse(
                user.getDisplayName(),
                user.getProfilePictureUrl(),
                user.isPremiumTrialUsed()
            );
        }

        ArtistResponse artistResponse = null;
        if (artist != null) {
            artistResponse = new ArtistResponse(
                artist.getStageName(),
                artist.getBio()
            );
        }

        return new AccountResponse(
            account.getAccountId(),
            account.getUsername(),
            account.getEmail(),
            account.getRole(),
            userResponse,
            artistResponse
        );
    }
}
