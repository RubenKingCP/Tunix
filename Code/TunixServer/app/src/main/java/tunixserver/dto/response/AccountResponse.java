package tunixserver.dto.response;

import lombok.*;
import tunixserver.dto.enums.Role;
import tunixserver.entities.AccountEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long accountId;
    private String username;
    private String email;
    private Role role;

    private UserResponse user;
    private ArtistResponse artist;

    public static AccountResponse fromEntity(AccountEntity acc) {
        return new AccountResponse(
                acc.getAccountId(),
                acc.getUsername(),
                acc.getEmail(),
                acc.getRole(),
                acc.getUser() != null ? UserResponse.fromEntity(acc.getUser()) : null,
                acc.getArtist() != null ? ArtistResponse.fromEntity(acc.getArtist()) : null
        );
    }
}