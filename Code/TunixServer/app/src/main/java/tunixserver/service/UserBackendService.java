package tunixserver.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import tunixserver.dto.enums.Role;
import tunixserver.dto.request.LoginRequest;
import tunixserver.dto.request.RegisterRequest;
import tunixserver.dto.response.AccountResponse;
import tunixserver.dto.response.UserResponse;
import tunixserver.dto.response.ArtistResponse;
import tunixserver.entities.AccountEntity;
import tunixserver.entities.LibraryEntity;
import tunixserver.entities.UserEntity;
import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.LibraryRepository;
import tunixserver.repository.UserBackendRepository;

@Service
public class UserBackendService {

    private final UserBackendRepository userRepo;
    private final AccountBackendRepository accountRepo;
    private final LibraryRepository libraryRepository;

    public UserBackendService(UserBackendRepository userRepo,
                              AccountBackendRepository accountRepo,
                                LibraryRepository libraryRepository) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.libraryRepository = libraryRepository;
    }

    public AccountResponse registerUser(RegisterRequest req) {

        AccountEntity account = AccountEntity.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(req.getPassword())
                .role(Role.USER)
                .build();

        AccountEntity savedAccount = accountRepo.save(account);

        createLibraryForAccount(savedAccount);
        
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

    public void createLibraryForAccount(AccountEntity account) {

        if (libraryRepository.findByAccount(account).isPresent()) {
                return;
        }

        LibraryEntity library = new LibraryEntity();
        library.setAccount(account);
        library.setCreatedAt(LocalDateTime.now());

        libraryRepository.save(library);
        }
}