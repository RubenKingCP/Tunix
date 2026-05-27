package tunix;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import tunix.ui.views.main.center.PaymentView;

public class PaymentViewTest {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame frame = new JFrame("Tunix - Payment Screen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 750);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            PaymentView paymentView = new PaymentView();

            frame.add(paymentView, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}