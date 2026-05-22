package tunix.model;
import tunix.dto.enums.Role;

public class Admin extends Account {
    public Admin(Long id, String username, String email, Role accountStatus) {
        super(id, username, email, accountStatus);
    }
}
