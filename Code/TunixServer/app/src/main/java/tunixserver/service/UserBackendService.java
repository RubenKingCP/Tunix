package tunixserver.service;


import org.springframework.stereotype.Service;

import tunixserver.dto.enums.Role;
import tunixserver.dto.request.RegisterRequest;
import tunixserver.dto.response.AccountResponse;
import tunixserver.entities.AccountEntity;
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

        System.out.println("Reached service\n" 
                    + registerRequest.getUsername());
        AccountEntity accountEntity = AccountEntity.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .role(Role.USER)
                .build();

        AccountEntity savedAccount = accountBackendRepository.save(accountEntity);

        UserEntity userEntity = UserEntity.builder()
                .account(savedAccount)
                .displayName(registerRequest.getUsername())
                .premiumTrialUsed(false)
                .build();

        userBackendRepository.save(userEntity);

        System.err.println("EXITED SERVICE\n");
        return new AccountResponse(
                savedAccount.getAccountId(),
                savedAccount.getUsername(),
                savedAccount.getEmail(),
                savedAccount.getRole().name()
        );
    }
}
