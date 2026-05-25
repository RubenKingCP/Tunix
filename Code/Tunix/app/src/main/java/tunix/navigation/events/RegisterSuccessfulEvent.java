package tunix.navigation.events;

import tunix.model.account.Account;

public record RegisterSuccessfulEvent(Account account) {
    public Account getAccount() {
        return account;
    }
}
