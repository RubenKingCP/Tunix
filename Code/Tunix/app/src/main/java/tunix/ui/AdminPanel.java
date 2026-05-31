package tunix.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import tunix.controller.AdminController;

public class AdminPanel extends JPanel {
    private final AdminController controller;

    public AdminPanel(AdminController controller) {
        super(new BorderLayout());   // was default FlowLayout — caused the tiny shrink-to-fit box
        this.controller = controller;
        this.add(controller.getView(), BorderLayout.CENTER);
    }
}