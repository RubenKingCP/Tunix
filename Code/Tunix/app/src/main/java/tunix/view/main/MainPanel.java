package tunix.view.main;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class MainPanel extends JPanel {

    public MainPanel(JPanel topBar,
                     JPanel libraryPanel,
                     JPanel centerPanel,
                     JPanel musicPlayer) {

        setLayout(new BorderLayout());

        add(topBar, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(musicPlayer, BorderLayout.SOUTH);
        add(libraryPanel, BorderLayout.WEST);
    }
}