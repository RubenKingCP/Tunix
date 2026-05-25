package tunix.model.account;

import java.math.BigInteger;

import lombok.Setter;
import tunix.dto.enums.Role;
import tunix.dto.response.AccountResponse;

@Setter
public abstract class Account {

    protected Long id;
    protected String username;
    protected String email;
    protected Role accountStatus;

    public Account(Long id,
                   String username,
                   String email,
                   Role accountStatus) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.accountStatus = accountStatus;
    }

    public int getLongId() {
        return id.intValue();
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

    public static Account from(AccountResponse dto) {

    return switch (dto.getRole()) {

        case USER -> new User(
                dto.getAccountId(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getUsername(), // displayName default
                null,              // profile picture
                false,             // premium
                false,             // premium trial used
                0                  // downloaded songs
        );

        case ARTIST -> new Artist(
            dto.getAccountId(),
            dto.getUsername(),
            dto.getEmail(),
            "",       // biography
            0,        // followers count
            false     // verified
        );

        case ADMIN -> new Admin(
                dto.getAccountId(),
                dto.getUsername(),
                dto.getEmail()
        );
        
    };
}   
}