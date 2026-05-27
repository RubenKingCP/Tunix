package tunix.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private final String username;
    private final String password;

    public LoginRequest(String username, String password){
        this.username = username;
        this.password = password;
        
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    } 
}
