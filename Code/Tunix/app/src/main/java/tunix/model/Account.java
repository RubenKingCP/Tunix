package tunix.model;

import lombok.Setter;
import tunix.dto.enums.Role;
import tunix.dto.response.AccountResponse;

@Setter
public class Account {

    private Long id;
    private String username;
    private String email;
    private Role accountStatus;

    public Account(Long id, String username, String email, Role accountStatus) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.accountStatus = accountStatus;
    }

    public int getId() {
        return Math.toIntExact(id);
    }

    public Long getLongId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getAccountStatus() {
        return accountStatus;
    }

    public static Account from(AccountResponse response) {
        return new Account(
            response.getAccountId(),
            response.getUsername(),
            response.getEmail(),
            response.getRole()
        );
    }
}