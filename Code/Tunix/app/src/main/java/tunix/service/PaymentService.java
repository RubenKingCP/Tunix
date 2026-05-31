package tunix.service;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentService {

    // =========================================================
    // Dummy Premium Price
    // =========================================================
    private static final double PREMIUM_PRICE = 9.99;

    // =========================================================
    // Simulated Payment Processing
    // =========================================================
    public PaymentResult processPayment(
            String cardholderName,
            String cardNumber,
            String expiry,
            String cvv,
            String address,
            String city,
            String postcode,
            String country) {

        // ---------------------------------------------
        // Basic validation
        // ---------------------------------------------
        if (isBlank(cardholderName)
                || isBlank(cardNumber)
                || isBlank(expiry)
                || isBlank(cvv)
                || isBlank(address)
                || isBlank(city)
                || isBlank(postcode)
                || isBlank(country)) {

            return PaymentResult.failure(
                    "Please complete all payment fields.");
        }

        // ---------------------------------------------
        // Fake card validation
        // ---------------------------------------------
        String sanitizedCard = cardNumber.replaceAll("\\s+", "");

        if (!sanitizedCard.matches("\\d{16}")) {
            return PaymentResult.failure(
                    "Card number must contain 16 digits.");
        }

        if (!cvv.matches("\\d{3}")) {
            return PaymentResult.failure(
                    "CVV must contain 3 digits.");
        }

        // ---------------------------------------------
        // Dummy declined card
        // ---------------------------------------------
        if (sanitizedCard.equals("1111111111111111")) {
            return PaymentResult.failure(
                    "Your bank declined this transaction.");
        }

        // ---------------------------------------------
        // Simulated successful payment
        // ---------------------------------------------
        String transactionId =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        return PaymentResult.success(
                transactionId,
                PREMIUM_PRICE,
                LocalDateTime.now());
    }

    // =========================================================
    // Helpers
    // =========================================================
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    // =========================================================
    // Payment Result DTO
    // =========================================================
    public static class PaymentResult {

        private final boolean success;
        private final String message;

        private final String transactionId;
        private final double amount;
        private final LocalDateTime processedAt;

        private PaymentResult(
                boolean success,
                String message,
                String transactionId,
                double amount,
                LocalDateTime processedAt) {

            this.success = success;
            this.message = message;
            this.transactionId = transactionId;
            this.amount = amount;
            this.processedAt = processedAt;
        }

        // =====================================================
        // Factory Methods
        // =====================================================
        public static PaymentResult success(
                String transactionId,
                double amount,
                LocalDateTime processedAt) {

            return new PaymentResult(
                    true,
                    "Payment processed successfully.",
                    transactionId,
                    amount,
                    processedAt);
        }

        public static PaymentResult failure(String message) {

            return new PaymentResult(
                    false,
                    message,
                    null,
                    0,
                    null);
        }

        // =====================================================
        // Getters
        // =====================================================
        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public double getAmount() {
            return amount;
        }

        public LocalDateTime getProcessedAt() {
            return processedAt;
        }

        @Override
        public String toString() {

            if (!success) {
                return "Payment failed: " + message;
            }

            return "Payment successful | Transaction ID: "
                    + transactionId
                    + " | Amount: €"
                    + amount
                    + " | Processed at: "
                    + processedAt;
        }
    }
}