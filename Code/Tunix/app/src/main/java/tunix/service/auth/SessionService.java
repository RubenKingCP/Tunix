package tunix.service.auth;

import tunix.model.AppContext;
import tunix.model.account.Account;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.LoginSuccessEvent;
import tunix.navigation.events.RegisterSuccessfulEvent;

public class SessionService {
    private Account currentUser;
    public static SessionService Instance;
    private final AppContext appContext;
    public SessionService(AppContext appContext){
        this.appContext = appContext;
        Instance = this;

        appContext.eventBus.subscribe(RegisterSuccessfulEvent.class, e -> {
            this.setUser(e);
            System.out.println("Reached session");
            System.out.println(currentUser.getUsername());
        });

        appContext.eventBus.subscribe(LoginSuccessEvent.class, e -> {
            this.setUser(e);
            System.out.println("Reached session");
            System.out.println(currentUser.getUsername());
            }
        );
    }

    public void setUser(RegisterSuccessfulEvent event) {
        this.currentUser = event.getAccount();
    }

    public void setUser(LoginSuccessEvent event){
        this.currentUser = event.getAccount();
    }

    public EventBus getEventBus() {
        return appContext.eventBus;
    }

    public Account getUser() {
        return currentUser;
    }

    public void clear() {
        currentUser = null;
    }
}
