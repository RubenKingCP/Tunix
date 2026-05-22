package tunix.event;

import tunix.model.Account;

public record LoginSuccessEvent(Account account) {
    public Account getAccount() {
        return account;
    }
}
