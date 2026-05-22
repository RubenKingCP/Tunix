package tunix.service.auth;

import tunix.event.EventBus;
import tunix.event.LoginSuccessEvent;
import tunix.event.RegisterSuccessfulEvent;
import tunix.model.Account;

public class SessionService {
    private Account currentUser;
    private final EventBus eventBus;

    public SessionService(EventBus eventBus){
        this.eventBus = eventBus;

        eventBus.subscribe(RegisterSuccessfulEvent.class, e -> {
            this.setUser(e);
            System.out.println("Reached session");
            System.out.println(currentUser);
        });

        eventBus.subscribe(LoginSuccessEvent.class, e -> this.setUser(e));
    }

    public void setUser(RegisterSuccessfulEvent event) {
        this.currentUser = event.getAccount();
    }

    public void setUser(LoginSuccessEvent event){
        this.currentUser = event.getAccount();
    }

    public Account getUser() {
        return currentUser;
    }

    public void clear() {
        currentUser = null;
    }
}
