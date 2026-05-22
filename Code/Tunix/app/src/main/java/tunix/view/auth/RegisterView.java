package tunix.view.auth;

import javax.swing.*;

import tunix.controller.auth.RegisterController;

import java.awt.*;

public class RegisterView extends JPanel {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginButton;

    private RegisterController controller;

    public RegisterView() {
        initUI();
    }

    private void initUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel emaiLabel = new JLabel("Email");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("login");
        registerButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(emaiLabel);
        panel.add(emailField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        // Button click → controller
        registerButton.addActionListener(e -> {
            if (controller != null) {
                controller.onRegisterButtonClicked();
            }
        });

        // Press go to login
        loginButton.addActionListener(e -> {
            if(controller != null) {
                controller.onGoToLoginButtonClicked();
            }
        });
    }


    public void setController(RegisterController controller) {
        this.controller = controller;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword(){
        return new String(passwordField.getPassword());
    }
}