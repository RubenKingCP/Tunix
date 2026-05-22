package tunix.model;

import tunix.dto.enums.Role;

public abstract class Account {
    private int id;
    private String username;
    private String email;
    private Role accountStatus;

    public Account(int id, String username, String email, Role accountStatus) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.accountStatus = accountStatus;
    }
}