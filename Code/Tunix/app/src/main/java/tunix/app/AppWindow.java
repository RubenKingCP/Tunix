package tunix.app;
import javax.swing.*;
import java.awt.*;

import tunix.event.EventBus;
import tunix.event.LoginSuccessEvent;
import tunix.event.LogoutEvent;
import tunix.event.RegisterSuccessfulEvent;
import tunix.service.auth.SessionService;

public class AppWindow extends JFrame {

    private final CardLayout rootLayout = new CardLayout();
    private final JPanel root = new JPanel(rootLayout);
    private final EventBus eventBus;
    private final tunix.view.main.MainPanel mainPanel;

    public static final String AUTH = "auth";
    public static final String MAIN = "main";


    public AppWindow(JPanel authPanel,
                     tunix.view.main.MainPanel mainPanel,
                     EventBus eventBus) {
        
        this.eventBus = eventBus;
        this.mainPanel = mainPanel;
        
        setTitle("Tunix");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        root.add(authPanel, AUTH);
        root.add(mainPanel, MAIN);

        add(root);

        subscribe(eventBus);
    }

    private void subscribe(EventBus eventBus) {
        eventBus.subscribe(RegisterSuccessfulEvent.class, e -> {
            mainPanel.showHome();
            showMain();
        });

        eventBus.subscribe(LoginSuccessEvent.class, e -> {
            mainPanel.showHome();
            showMain();
        });

        eventBus.subscribe(LogoutEvent.class, e -> {
            if (SessionService.Instance != null) {
                SessionService.Instance.clear();
            }
            showAuth();
        });
    }

    public void showAuth() {
        rootLayout.show(root, AUTH);
    }

    public void showMain() {
        rootLayout.show(root, MAIN);
    }
}