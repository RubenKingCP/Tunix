package tunix.view.auth;

import javax.swing.*;

import tunix.controller.auth.LoginController;

import java.awt.*;

public class LoginView extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private LoginController controller;

    public LoginView() {
        initUI();
    }

    private void initUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(registerButton);
        panel.add(loginButton);

        

        add(panel);

        // Login Button Action
        loginButton.addActionListener(e -> {
            if (controller != null) {
                controller.onLogin(
                        usernameField.getText(),
                        new String(passwordField.getPassword())
                );
            }
        });

        // Register button action
        registerButton.addActionListener(e -> onGoToRegisterButtonClicked());
    }

    public void setController(LoginController controller) {
        this.controller = controller;
    }

    public void onGoToRegisterButtonClicked() {
        controller.onGoToRegisterButtonClicked();
    }
}