package tunixserver.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tunixserver.dto.enums.Role;

@Getter
@NoArgsConstructor
public class AccountResponse {

    private Long accountId;
    private String username;
    private String email;
    private Role role;

    private UserResponse userResponse;     
    private ArtistResponse artistResponse;

    public AccountResponse(Long accountId, String username, String email, Role role, UserResponse userResponse, ArtistResponse artistResponse) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.userResponse = userResponse;
        this.artistResponse = artistResponse;
    } 
}