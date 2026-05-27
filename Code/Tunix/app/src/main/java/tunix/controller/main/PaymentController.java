package tunix.controller.main;

import javax.swing.JOptionPane;

import tunix.service.PaymentService;
import tunix.service.PaymentService.PaymentResult;
import tunix.ui.views.main.center.PaymentView;

public class PaymentController {

    private final PaymentView paymentView;
    private final PaymentService paymentService;

    public PaymentController(
            PaymentView paymentView,
            PaymentService paymentService) {

        this.paymentView = paymentView;
        this.paymentService = paymentService;

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

        } else {

            JOptionPane.showMessageDialog(
                    paymentView,
                    result.getMessage(),
                    "Payment Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================
    // View Access
    // =========================================================
    public PaymentView getView() {
        return paymentView;
    }
}