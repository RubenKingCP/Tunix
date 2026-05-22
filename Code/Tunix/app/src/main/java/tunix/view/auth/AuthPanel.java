package tunix.view.auth;

import javax.swing.JPanel;

import tunix.event.EventBus;
import tunix.event.GoToLoginButtonClicked;
import tunix.event.GoToRegisterButtonClicked;

import java.awt.CardLayout;

public class AuthPanel extends JPanel {

    private final EventBus eventBus;
    private CardLayout layout = new CardLayout();
    

    public static final String LOGIN = "login";
    public static final String REGISTER = "register";

    public AuthPanel(LoginView loginView, RegisterView registerView, EventBus eventBus) {

        this.eventBus = eventBus;

        setLayout(layout);

        add(loginView, LOGIN);
        add(registerView, REGISTER);

        subscribe(eventBus);
    }

    public void subscribe(EventBus eventBus) {
        eventBus.subscribe(GoToRegisterButtonClicked.class, e -> showRegister());

        eventBus.subscribe(GoToLoginButtonClicked.class, e -> showLogin());
    }
    public void showLogin() {
        layout.show(this, LOGIN);
    }

    public void showRegister() {
        layout.show(this, REGISTER);
    }
}