package tunix.event;

import tunix.model.Account;

public record RegisterSuccessfulEvent(Account account) {
    public Account getAccount() {
        return account;
    }
}
