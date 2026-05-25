package tunix.model.account;
import java.math.BigInteger;

import tunix.dto.enums.Role;


public class Admin extends Account {

    public Admin(Long id, String username, String email) {
        super(id, username, email, Role.ADMIN);
    }

    // optional admin-specific behavior later
}
