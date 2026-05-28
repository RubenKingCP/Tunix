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
import tunixserver.entities.UserEntity;
import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.UserBackendRepository;

@Service
public class UserBackendService {

    private final UserBackendRepository userRepo;
    private final AccountBackendRepository accountRepo;

    public UserBackendService(UserBackendRepository userRepo,
                              AccountBackendRepository accountRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
    }

    public AccountResponse registerUser(RegisterRequest req) {

        AccountEntity account = AccountEntity.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(req.getPassword())
                .role(Role.USER)
                .build();

        AccountEntity savedAccount = accountRepo.save(account);
        
        UserEntity user = new UserEntity();
        user.setAccount(savedAccount);
        user.setDisplayName(req.getUsername());
        user.setPremium(false);
        user.setPremiumTrialUsed(false);

        userRepo.save(user);

        UserResponse userResponse = UserResponse.fromEntity(user);

        return new AccountResponse(
                savedAccount.getAccountId(),
                savedAccount.getUsername(),
                savedAccount.getEmail(),
                savedAccount.getRole(),
                userResponse,
                null
        );
    }

    public AccountResponse loginUser(LoginRequest req) {

        Optional<AccountEntity> optional = accountRepo
                .findByUsernameAndPassword(req.getUsername(), req.getPassword());

        AccountEntity account = optional
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        UserResponse userResponse =
                account.getUser() != null ? UserResponse.fromEntity(account.getUser()) : null;

        ArtistResponse artistResponse =
                account.getArtist() != null ? ArtistResponse.fromEntity(account.getArtist()) : null;

        return new AccountResponse(
                account.getAccountId(),
                account.getUsername(),
                account.getEmail(),
                account.getRole(),
                userResponse,
                artistResponse
        );
    }

        public boolean startPremium(Long userId) {

                System.out.println("START PREMIUM SERVICE");
                System.out.println("User ID: " + userId);

                UserEntity user = userRepo.findById(userId.intValue())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                System.out.println("Found user: " + user.getId());

                user.setPremium(true);

                userRepo.save(user);

                System.out.println("Premium ENABLED for user: " + userId);

                return true;
        }

        public boolean cancelPremium(Long userId) {

                System.out.println("CANCEL PREMIUM SERVICE");
                System.out.println("User ID: " + userId);

                UserEntity user = userRepo.findById(userId.intValue())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                System.out.println("Found user: " + user.getId());

                user.setPremium(false);

                userRepo.save(user);

                System.out.println("Premium DISABLED for user: " + userId);

                return true;
        }

        public boolean startTrial(Long userId) {

                System.out.println("START PREMIUM SERVICE");
                System.out.println("User ID: " + userId);

                UserEntity user = userRepo.findById(userId.intValue())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                System.out.println("Found user: " + user.getId());

                user.setPremium(true);
                //Add method to set trial used to true
                user.setPremiumTrialUsed(true);
                userRepo.save(user);

                System.out.println("Premium ENABLED for user: " + userId);

                return true;
        }
}