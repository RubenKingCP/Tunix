package tunix.service.auth;

import tunix.model.AppContext;
import tunix.model.account.Account;
import tunix.model.account.Admin;
import tunix.model.account.Artist;
import tunix.model.account.User;
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

    public Account getAccount() {
        return currentUser;
    }

    public User getUser(){
        if (currentUser.getClass().equals(User.class)){
            return (User)currentUser;
        }
        return null;
    }

    public Artist getArtist(){
        if (currentUser.getClass().equals(Artist.class)){
            return (Artist)currentUser;
        }
        return null;
    }

    public Admin getAdmin(){
        if (currentUser.getClass().equals(Admin.class)){
            return (Admin)currentUser;
        }
        return null;
    }

    public void clear() {
        currentUser = null;
    }
}
