package tunixserver.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import tunixserver.dto.enums.Role;
import tunixserver.dto.request.LoginRequest;
import tunixserver.dto.request.RegisterRequest;
import tunixserver.dto.response.AccountResponse;
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

    UserEntity user = new UserEntity();
    user.setDisplayName(req.getUsername());
    user.setPremium(false);
    user.setPremiumTrialUsed(false);

    AccountEntity account = AccountEntity.builder()
            .username(req.getUsername())
            .email(req.getEmail())
            .password(req.getPassword())
            .role(Role.USER)
            .user(user)  // set relationship here
            .build();

    user.setAccount(account); // set both sides of the relationship

    AccountEntity savedAccount = accountRepo.save(account); // cascade saves user too

    return AccountResponse.fromEntity(savedAccount);
}
        public AccountResponse loginUser(LoginRequest req) {

                Optional<AccountEntity> optional = accountRepo
                        .findByUsernameAndPassword(
                                req.getUsername(),
                                req.getPassword()
                        );

                AccountEntity account = optional
                        .orElseThrow(() ->
                                new RuntimeException("Invalid credentials"));

                // Check if banned
                if (account.isBanned()) {
                        throw new RuntimeException(
                                "Account is banned. Reason: " +
                                (account.getBanReason() == null
                                        ? "No reason provided"
                                        : account.getBanReason())
                        );
                }

                return AccountResponse.fromEntity(account);
        
        }
        public boolean startPremium(Long userId) {

                System.out.println("START PREMIUM SERVICE");
                System.out.println("User ID: " + userId);

                UserEntity user = userRepo.findById(userId)
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

                UserEntity user = userRepo.findByAccount_AccountId(userId)
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
                
                UserEntity user = userRepo.findByAccount_AccountId(userId)
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