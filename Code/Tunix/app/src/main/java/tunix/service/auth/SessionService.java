package tunix.service.auth;

import tunix.model.Account;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.LoginSuccessEvent;
import tunix.navigation.events.RegisterSuccessfulEvent;

public class SessionService {
    private Account currentUser;
    private final EventBus eventBus;
    public static SessionService Instance;
    public SessionService(EventBus eventBus){
        this.eventBus = eventBus;
        Instance = this;

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

    public EventBus getEventBus() {
        return eventBus;
    }

    public Account getUser() {
        return currentUser;
    }

    public void clear() {
        currentUser = null;
    }
}
