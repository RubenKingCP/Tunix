package tunix.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import tunix.navigation.ScreenRegistry;

import tunix.core.AppContext;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.SwitchCenterScreenEvent;

public class MainPanel extends JPanel {

    private final JPanel centerRouter = new JPanel(new CardLayout());
    private final CardLayout layout = (CardLayout) centerRouter.getLayout();

    private final ScreenRegistry registry;

    public MainPanel(JPanel topBar,
                     JPanel libraryPanel,
                     JPanel musicPlayer,
                     ScreenRegistry registry,
                     AppContext context) {

        this.registry = registry;

        setLayout(new BorderLayout());

        add(topBar, BorderLayout.NORTH);
        add(libraryPanel, BorderLayout.WEST);
        add(musicPlayer, BorderLayout.SOUTH);
        add(centerRouter, BorderLayout.CENTER);
        
        //Test
        subscribe(context.eventBus);
    }

    private void subscribe(EventBus eventBus) {
        eventBus.subscribe(SwitchCenterScreenEvent.class,
                e -> show(e.getScreen()));
    }

    public void register(Class<?> key, JPanel panel) {
        registry.register(key, panel);

        String name = registry.getName(key);
        centerRouter.add(panel, name);

        System.out.println("Registered screen: " + key.getSimpleName());
        System.out.println("centerRouter instance: " + System.identityHashCode(centerRouter));
        System.out.println("panel added: " + panel.getClass().getSimpleName());
        System.out.println("ADDING:");
        System.out.println("  class = " + key.getName());
        System.out.println("  name  = " + registry.getName(key));
        System.out.println("  panel = " + panel);
    }

    public void show(Class<?> key) {
        String name = registry.getName(key);

        if (name == null) {
            throw new IllegalStateException("Screen not registered: " + key.getSimpleName());
        }

        System.out.println("Showing screen: " + key.getSimpleName());
        
        layout.show(centerRouter, name);
        centerRouter.revalidate();
        centerRouter.repaint();
    }
}