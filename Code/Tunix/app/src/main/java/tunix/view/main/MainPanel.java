package tunix.view.main;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

import tunix.event.EventBus;
import tunix.event.SwitchMainScreen;

public class MainPanel extends JPanel {

    private JPanel currentCenterPanel;

    public MainPanel(JPanel topBar,
                     JPanel libraryPanel,
                     JPanel centerPanel,
                     JPanel musicPlayer,
                     EventBus eventBus) {

        setLayout(new BorderLayout());

        this.currentCenterPanel = centerPanel;

        add(topBar, BorderLayout.NORTH);
        add(currentCenterPanel, BorderLayout.CENTER);
        add(musicPlayer, BorderLayout.SOUTH);
        add(libraryPanel, BorderLayout.WEST);

        subscribe(eventBus);
    }

    private void subscribe(EventBus eventBus) {
        eventBus.subscribe(SwitchMainScreen.class, e -> switchCenter(e.getNewScreen()));
    }

    private void switchCenter(JPanel newCenterPanel) {
        Runnable update = () -> {
            if (newCenterPanel == null || newCenterPanel == currentCenterPanel) {
                return;
            }

            remove(currentCenterPanel);
            currentCenterPanel = newCenterPanel;
            add(currentCenterPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        };

        if (SwingUtilities.isEventDispatchThread()) {
            update.run();
        } else {
            SwingUtilities.invokeLater(update);
        }
    }
}