package tunix.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tunix.dto.enums.Role;

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
}