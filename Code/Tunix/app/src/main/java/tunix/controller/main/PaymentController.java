package tunix.controller.main;

import java.awt.Window;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import tunix.service.PaymentService;
import tunix.service.PaymentService.PaymentResult;
import tunix.ui.views.main.center.PaymentView;

public class PaymentController {

    private final PaymentView paymentView;
    private final PaymentService paymentService;
    private boolean paymentSucceeded;

    public PaymentController(
            PaymentView paymentView,
            PaymentService paymentService) {

        this.paymentView = paymentView;
        this.paymentService = paymentService;

        initialize();
    }
    public PaymentController(){
        this.paymentView = new PaymentView();
        this.paymentService = new PaymentService();

        initialize();
    }

    // =========================================================
    // Initialize Listeners
    // =========================================================
    private void initialize() {

        paymentView.getPayButton().addActionListener(e -> {
            onPaymentClicked();
        });
    }

    // =========================================================
    // Payment Action
    // =========================================================
    public void onPaymentClicked() {

        PaymentResult result = paymentService.processPayment(
                paymentView.getCardholderName(),
                paymentView.getCardNumber(),
                paymentView.getExpiry(),
                paymentView.getCvv(),
                paymentView.getAddress(),
                paymentView.getCity(),
                paymentView.getPostcode(),
                paymentView.getCountry());

        paymentSucceeded = result.isSuccess();

        if (result.isSuccess()) {

            JOptionPane.showMessageDialog(
                    paymentView,
                    """
                    Premium activated successfully!

                    Transaction ID: %s
                    Amount Paid: €%.2f
                    """
                            .formatted(
                                    result.getTransactionId(),
                                    result.getAmount()),
                    "Payment Successful",
                    JOptionPane.INFORMATION_MESSAGE);

            Window window = SwingUtilities.getWindowAncestor(paymentView);
            if (window != null) {
                window.dispose();
            }

        } else {

            JOptionPane.showMessageDialog(
                    paymentView,
                    result.getMessage(),
                    "Payment Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================
    // Popup Flow
    // =========================================================
    public boolean showPaymentPopup() {
        paymentView.createDialog().setVisible(true);
        return paymentSucceeded;
    }

    // =========================================================
    // View Access
    // =========================================================
    public PaymentView getView() {
        return paymentView;
    }
}