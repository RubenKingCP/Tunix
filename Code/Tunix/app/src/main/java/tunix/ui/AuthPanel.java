package tunix.ui;

import javax.swing.JPanel;

import tunix.navigation.events.EventBus;
import tunix.navigation.events.GoToLoginButtonClicked;
import tunix.navigation.events.GoToRegisterButtonClicked;
import tunix.ui.views.auth.LoginView;
import tunix.ui.views.auth.RegisterView;

import java.awt.CardLayout;
import java.awt.Color;

public class AuthPanel extends JPanel {
    private CardLayout layout = new CardLayout();
    

    public static final String LOGIN = "login";
    public static final String REGISTER = "register";

    public AuthPanel(LoginView loginView, RegisterView registerView, EventBus eventBus) {
        setLayout(layout);
        setBackground(Color.red);
        setOpaque(true);
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