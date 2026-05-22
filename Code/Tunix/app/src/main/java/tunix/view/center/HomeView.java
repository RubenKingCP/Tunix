package tunix.view.center;

import javax.swing.JPanel;


import java.awt.BorderLayout;
import javax.swing.*;
import tunix.view.View;

public class HomeView extends JPanel implements View{
    public HomeView() {
        setLayout(new BorderLayout());
        JLabel announcementLabel = new JLabel("There are no announcements yet", SwingConstants.CENTER);
        announcementLabel.setHorizontalAlignment(SwingConstants.CENTER);
        announcementLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(announcementLabel, BorderLayout.CENTER);
    }

    public void display() {
        // Placeholder method kept for compatibility with Main.
    }
}

