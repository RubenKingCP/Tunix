package tunix.dto.request;

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

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

}
