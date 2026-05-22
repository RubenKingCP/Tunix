package tunix.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tunix.dto.enums.Role;

@Getter
@NoArgsConstructor
public class AccountResponse {

    private Long accountId;
    private String username;
    private String email;
    private Role role;

    public AccountResponse(Long accountId, String username, String email, Role role) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}