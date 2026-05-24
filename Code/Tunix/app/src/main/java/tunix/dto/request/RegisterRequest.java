package tunix.dto.request;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private final String username;

    private final String email;

    private final String password;

    private String avatarUrl;

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
