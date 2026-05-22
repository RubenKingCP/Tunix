package tunix.app;
import javax.swing.*;
import java.awt.*;

import tunix.event.EventBus;
import tunix.event.RegisterSuccessfulEvent;

public class AppWindow extends JFrame {

    private final CardLayout rootLayout = new CardLayout();
    private final JPanel root = new JPanel(rootLayout);
    private final EventBus eventBus;

    public static final String AUTH = "auth";
    public static final String MAIN = "main";


    public AppWindow(JPanel authPanel,
                     JPanel mainPanel,
                     EventBus eventBus) {
        
        this.eventBus = eventBus;
        
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
        eventBus.subscribe(RegisterSuccessfulEvent.class, e -> showMain());
    }

    public void showAuth() {
        rootLayout.show(root, AUTH);
    }

    public void showMain() {
        rootLayout.show(root, MAIN);
    }
}