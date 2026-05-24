package tunix.app;
import javax.swing.*;

import tunix.core.AppContext;
import tunix.event.EventBus;
import tunix.event.SwitchScreenEvent;

import java.awt.CardLayout;
import java.util.Map;
import java.util.HashMap;

public class AppWindow extends JFrame {

    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);

    private final Map<Class<?>, JPanel> screens = new HashMap<>();

    public AppWindow(AppContext context) {
        setTitle("Tunix");

        setContentPane(root);
        
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        subscriptions(context.eventBus);
    }

    public void subscriptions(EventBus eventBus) {
        eventBus.subscribe(SwitchScreenEvent.class, e -> show(e.getScreen()));
    }

    public void register(Class<?> key, JPanel panel) {
        System.err.println("Class Registered: " + key.getSimpleName());
        screens.put(key, panel);
        root.add(panel, key.getSimpleName());
    }

    public void show(Class<?> key) {
        if (!screens.containsKey(key)) {
            throw new IllegalStateException("Screen not found: " + key.getSimpleName());
        }
        System.err.println("Current panel showing: " + key.getSimpleName());
        layout.show(root, key.getSimpleName());
    }
}