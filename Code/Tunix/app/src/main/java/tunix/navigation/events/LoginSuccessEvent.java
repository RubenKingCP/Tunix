package tunix.navigation.events;

import tunix.model.Account;

public record LoginSuccessEvent(Account account) {
    public Account getAccount() {
        return account;
    }
}
