package tunix.navigation.events;

import tunix.model.account.Account;

public record LoginSuccessEvent(Account account) {
    public Account getAccount() {
        return account;
    }
}
