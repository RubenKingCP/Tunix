package tunixserver.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountResponse {

    private Long accountId;
    private String username;
    private String email;
    private String role;

    public AccountResponse(Long accountId, String username, String email, String role) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}