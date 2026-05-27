package tunix.ui.views.main.center;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PaymentView extends JPanel {

    // =========================================================
    // Colors
    // =========================================================
    private static final Color BG = new Color(18, 18, 18);
    private static final Color CARD_BG = new Color(28, 28, 28);
    private static final Color TEXT = Color.WHITE;
    private static final Color MUTED = new Color(160, 160, 160);
    private static final Color ACCENT = new Color(245, 195, 86);

    // =========================================================
    // Fields
    // =========================================================
    private JTextField cardholderField;
    private JTextField cardNumberField;
    private JTextField expiryField;
    private JTextField cvvField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField postcodeField;

    private JComboBox<String> countryDropdown;
    private JComboBox<String> planDropdown;

    private JButton payButton;

    // =========================================================
    // Constructor
    // =========================================================
    public PaymentView() {
        initGui();
    }

    // =========================================================
    // GUI
    // =========================================================
    private void initGui() {

        setLayout(new BorderLayout());
        setBackground(BG);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG);
        content.setBorder(BorderFactory.createEmptyBorder(24, 24, 40, 24));

        content.add(buildHeader());
        content.add(Box.createVerticalStrut(24));

        content.add(buildSubscriptionSection());
        content.add(Box.createVerticalStrut(24));

        content.add(buildPaymentSection());
        content.add(Box.createVerticalStrut(24));

        content.add(buildBillingSection());
        content.add(Box.createVerticalStrut(24));

        content.add(buildBottomSection());

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.setBackground(BG);
        scrollPane.getViewport().setBackground(BG);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        add(scrollPane, BorderLayout.CENTER);
    }

    // =========================================================
    // Header
    // =========================================================
    private JPanel buildHeader() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Premium Checkout");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));

        JLabel subtitle = new JLabel(
                "Complete your payment details to activate your Tunix Premium subscription.");
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        panel.add(title);
        panel.add(Box.createVerticalStrut(6));
        panel.add(subtitle);

        return panel;
    }

    // =========================================================
    // Subscription Section
    // =========================================================
    private JPanel buildSubscriptionSection() {

        JPanel section = buildSection("Subscription Plan");

        JPanel card = createCard();

        JLabel label = new JLabel("Choose your plan");
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));

        planDropdown = new JComboBox<>(new String[] {
                "Premium Individual - €9.99/month",
                "Premium Student - €4.99/month",
                "Premium Family - €14.99/month"
        });

        styleComboBox(planDropdown);

        card.add(label);
        card.add(Box.createVerticalStrut(10));
        card.add(planDropdown);

        section.add(card);

        return section;
    }

    // =========================================================
    // Payment Section
    // =========================================================
    private JPanel buildPaymentSection() {

        JPanel section = buildSection("Card Information");

        JPanel card = createCard();

        cardholderField = createTextField("Cardholder Name");
        cardNumberField = createTextField("Card Number");
        expiryField = createTextField("MM/YY");
        cvvField = createTextField("CVV");

        card.add(createFieldLabel("Cardholder Name"));
        card.add(cardholderField);

        card.add(Box.createVerticalStrut(14));

        card.add(createFieldLabel("Card Number"));
        card.add(cardNumberField);

        card.add(Box.createVerticalStrut(14));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        row.setBackground(CARD_BG);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel expiryPanel = new JPanel();
        expiryPanel.setLayout(new BoxLayout(expiryPanel, BoxLayout.Y_AXIS));
        expiryPanel.setBackground(CARD_BG);

        expiryPanel.add(createFieldLabel("Expiry"));
        expiryPanel.add(expiryField);

        JPanel cvvPanel = new JPanel();
        cvvPanel.setLayout(new BoxLayout(cvvPanel, BoxLayout.Y_AXIS));
        cvvPanel.setBackground(CARD_BG);

        cvvPanel.add(createFieldLabel("CVV"));
        cvvPanel.add(cvvField);

        row.add(expiryPanel);
        row.add(cvvPanel);

        card.add(row);

        section.add(card);

        return section;
    }

    // =========================================================
    // Billing Section
    // =========================================================
    private JPanel buildBillingSection() {

        JPanel section = buildSection("Billing Address");

        JPanel card = createCard();

        addressField = createTextField("Address");
        cityField = createTextField("City");
        postcodeField = createTextField("Postcode");

        countryDropdown = new JComboBox<>(new String[] {
                "Greece",
                "United Kingdom",
                "Germany",
                "France",
                "United States"
        });

        styleComboBox(countryDropdown);

        card.add(createFieldLabel("Address"));
        card.add(addressField);

        card.add(Box.createVerticalStrut(14));

        card.add(createFieldLabel("City"));
        card.add(cityField);

        card.add(Box.createVerticalStrut(14));

        card.add(createFieldLabel("Postcode"));
        card.add(postcodeField);

        card.add(Box.createVerticalStrut(14));

        card.add(createFieldLabel("Country"));
        card.add(countryDropdown);

        section.add(card);

        return section;
    }

    // =========================================================
    // Bottom Section
    // =========================================================
    private JPanel buildBottomSection() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel info = new JLabel("Your payment information is securely encrypted.");
        info.setForeground(MUTED);
        info.setFont(new Font("SansSerif", Font.PLAIN, 12));

        payButton = new JButton("Confirm Payment");
        payButton.setBackground(ACCENT);
        payButton.setForeground(Color.BLACK);
        payButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        payButton.setFocusPainted(false);
        payButton.setBorderPainted(false);
        payButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        payButton.setPreferredSize(new Dimension(180, 42));

        panel.add(info, BorderLayout.WEST);
        panel.add(payButton, BorderLayout.EAST);

        return panel;
    }

    // =========================================================
    // Helpers
    // =========================================================
    private JPanel buildSection(String title) {

        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(BG);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel(title);
        heading.setForeground(TEXT);
        heading.setFont(new Font("SansSerif", Font.BOLD, 17));

        section.add(heading);
        section.add(Box.createVerticalStrut(10));

        return section;
    }

    private JPanel createCard() {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        return card;
    }

    private JLabel createFieldLabel(String text) {

        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    private JTextField createTextField(String placeholder) {

        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 38));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        field.setBackground(new Color(40, 40, 40));
        field.setForeground(TEXT);
        field.setCaretColor(TEXT);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        return field;
    }

    private void styleComboBox(JComboBox<String> comboBox) {

        comboBox.setBackground(new Color(40, 40, 40));
        comboBox.setForeground(TEXT);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));

        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));

        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // =========================================================
    // Standalone Test
    // =========================================================
    public void display() {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Tunix - Payment");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);

            frame.setLayout(new BorderLayout());
            frame.add(new PaymentView(), BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}