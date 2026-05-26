package tunix.ui.views.profile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tunix.controller.UserProfileController;
import tunix.dto.enums.Role;
import tunix.model.account.User;
import tunix.service.auth.SessionService;

public class UserProfileView extends JPanel {

    private static final Color BG = new Color(18, 18, 18);
    private static final Color CARD_BG = new Color(28, 28, 28);
    private static final Color CARD_HOVER = new Color(36, 36, 36);
    private static final Color MUTED = new Color(160, 160, 160);
    private static final Color PREMIUM = new Color(245, 195, 86);

    private final JLabel planStatusLabel = new JLabel("Current plan: Free");
    private final JLabel trialStatusLabel = new JLabel("Trial: not started");
    private final JPanel premiumPanel = new JPanel();
    private final UserProfileController controller;

    public UserProfileView(UserProfileController controller) {
        this.controller = controller;
        initGui();
    }

    public void initGui() {
        removeAll();

        setLayout(new BorderLayout());
        setBackground(BG);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG);
        content.setBorder(BorderFactory.createEmptyBorder(24, 24, 32, 24));

        content.add(buildHeader());
        content.add(Box.createVerticalStrut(24));
        content.add(buildAccountSection());
        content.add(Box.createVerticalStrut(24));
        content.add(buildPremiumSection());
        content.add(Box.createVerticalStrut(24));
        content.add(buildQuickActionsSection());

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setBackground(BG);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(20);

        add(scroll, BorderLayout.CENTER);
    }

    public void refresh() {
        initGui();
        revalidate();
        repaint();
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout(16, 0));
        panel.setBackground(BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel userInfo = new JPanel();
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
        userInfo.setBackground(BG);

        JLabel title = new JLabel("Profile");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Manage your account, listen history, and premium access.");
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        userInfo.add(title);
        userInfo.add(Box.createVerticalStrut(4));
        userInfo.add(subtitle);

        JLabel avatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D g2 = (Graphics2D) graphics.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(60, 60, 60));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, getWidth() / 3));
                FontMetrics metrics = g2.getFontMetrics();
                String initial = "U";
                g2.drawString(initial,
                        (getWidth() - metrics.stringWidth(initial)) / 2,
                        (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent());
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(72, 72));
        avatar.setMinimumSize(new Dimension(72, 72));
        avatar.setMaximumSize(new Dimension(72, 72));
        avatar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.add(avatar, BorderLayout.WEST);
        panel.add(userInfo, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildAccountSection() {
        JPanel section = buildSectionContainer("Account");

        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setBackground(CARD_BG);
        details.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        var user = SessionService.Instance == null ? null : SessionService.Instance.getAccount();
        if (user == null) {
            addDetailRow(details, "Display name", "Not signed in");
            addDetailRow(details, "Email", "—");
        } else {
            addDetailRow(details, "Display name", user.getUsername());
            addDetailRow(details, "Email", user.getEmail());
        }

        addDetailRow(details, "Listening mode", "Personalized");
        addDetailRow(details, "Downloaded songs", "12");

        section.add(details);
        return section;
    }

    private JPanel buildPremiumSection() {
        JPanel section = buildSectionContainer("Premium");

        premiumPanel.removeAll();
        premiumPanel.setLayout(new BoxLayout(premiumPanel, BoxLayout.Y_AXIS));
        premiumPanel.setBackground(CARD_BG);
        premiumPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        PremiumUiState premiumUiState = getPremiumUiState(getCurrentUser());
        planStatusLabel.setText(premiumUiState.planText());
        trialStatusLabel.setText(premiumUiState.trialText());

        JLabel summary = new JLabel("Upgrade to unlock offline downloads, ad-free playback, and premium audio quality.");
        summary.setForeground(Color.WHITE);
        summary.setFont(new Font("SansSerif", Font.PLAIN, 13));
        summary.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        statusRow.setBackground(CARD_BG);
        statusRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        planStatusLabel.setForeground(MUTED);
        planStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        trialStatusLabel.setForeground(MUTED);
        trialStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        statusRow.add(planStatusLabel);
        statusRow.add(trialStatusLabel);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonRow.setBackground(CARD_BG);
        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton purchaseButton = makePrimaryButton("Purchase premium", PREMIUM);
        purchaseButton.addActionListener(e -> controller.purchasePremiumPlan());
        buttonRow.add(purchaseButton);

        if (controller.checkTrialEligibility()) {
            JButton trialButton = makePrimaryButton("Start trial", PREMIUM);
            trialButton.addActionListener(e -> controller.startTrial());
            buttonRow.add(trialButton);
        }

        if (premiumUiState.showCancelButton()) {
            JButton cancelButton = makePrimaryButton("Cancel premium", new Color(110, 110, 110));
            cancelButton.setForeground(Color.WHITE);
            cancelButton.addActionListener(e -> this.controller.cancelPremium());
            buttonRow.add(cancelButton);
        }

        premiumPanel.add(summary);
        premiumPanel.add(Box.createVerticalStrut(12));
        premiumPanel.add(statusRow);
        premiumPanel.add(Box.createVerticalStrut(14));
        premiumPanel.add(buttonRow);

        section.add(premiumPanel);
        return section;
    }

    private JPanel buildQuickActionsSection() {
        JPanel section = buildSectionContainer("Quick actions");

        JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        actions.setBackground(CARD_BG);
        actions.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        actions.add(buildActionRow("Download settings", "Manage offline music and storage"));
        actions.add(Box.createVerticalStrut(10));
        actions.add(buildActionRow("Privacy & security", "Review account privacy settings"));
        actions.add(Box.createVerticalStrut(10));
        actions.add(buildActionRow("Notifications", "Control push alerts and updates"));

        if (shouldShowArtistRequestButton()) {
            actions.add(Box.createVerticalStrut(10));
            JButton artistRequestButton = makePrimaryButton("Request to become an artist", PREMIUM);
            artistRequestButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            artistRequestButton.addActionListener(e -> showArtistRequestForm());
            actions.add(artistRequestButton);
        }

        section.add(actions);
        return section;
    }

    private User getCurrentUser() {
        if (SessionService.Instance == null) {
            return null;
        }

        var account = SessionService.Instance.getAccount();
        return account instanceof User user ? user : null;
    }

    static PremiumUiState getPremiumUiState(User user) {
        if (user == null) {
            return new PremiumUiState("Current plan: Free", "Trial: not started", false, true, false);
        }

        if (user.isPremium()) {
            return new PremiumUiState("Current plan: Premium", "Trial: active", false, true, true);
        }

        if (user.hasUsedPremiumTrial()) {
            return new PremiumUiState("Current plan: Free", "Trial: used", false, true, false);
        }

        return new PremiumUiState("Current plan: Free", "Trial: available", true, true, false);
    }

    private boolean shouldShowArtistRequestButton() {
        if (SessionService.Instance == null) {
            return false;
        }

        var account = SessionService.Instance.getAccount();
        return account != null && account.getAccountStatus() == Role.USER;
    }

    private void showArtistRequestForm() {
        if (!shouldShowArtistRequestButton()) {
            JOptionPane.showMessageDialog(this,
                    "Please sign in as a regular user to request artist access.",
                    "Artist request",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextField stageNameField = new JTextField(24);
        JTextArea bioArea = new JTextArea(8, 24);
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        JScrollPane bioScroll = new JScrollPane(bioArea);

        JPanel form = new JPanel();
        form.setBackground(CARD_BG);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel stageNameLabel = new JLabel("Stage name");
        stageNameLabel.setForeground(Color.WHITE);
        JLabel bioLabel = new JLabel("Tell us about your music and why you want to become an artist");
        bioLabel.setForeground(Color.WHITE);

        form.add(stageNameLabel);
        form.add(Box.createVerticalStrut(6));
        form.add(stageNameField);
        form.add(Box.createVerticalStrut(12));
        form.add(bioLabel);
        form.add(Box.createVerticalStrut(6));
        form.add(bioScroll);

        int result = JOptionPane.showConfirmDialog(this,
                form,
                "Request to become an artist",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String stageName = stageNameField.getText().trim();
        String bio = bioArea.getText().trim();

        if (stageName.isEmpty() || bio.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please complete both the stage name and bio fields before submitting.",
                    "Artist request",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        controller.requestArtistStatus(stageName, bio);

        JOptionPane.showMessageDialog(this,
                "Your artist request has been submitted for review.",
                "Artist request",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static record PremiumUiState(String planText, String trialText, boolean showTrialButton, boolean showPurchaseButton,
            boolean showCancelButton) {
    }

    private JPanel buildSectionContainer(String title) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(BG);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel(title);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("SansSerif", Font.BOLD, 16));
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        section.add(heading);
        section.add(Box.createVerticalStrut(10));
        return section;
    }

    private void addDetailRow(JPanel container, String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(CARD_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelView = new JLabel(label);
        labelView.setForeground(MUTED);
        labelView.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel valueView = new JLabel(value);
        valueView.setForeground(Color.WHITE);
        valueView.setFont(new Font("SansSerif", Font.BOLD, 12));

        row.add(labelView, BorderLayout.WEST);
        row.add(valueView, BorderLayout.EAST);
        container.add(row);
    }

    private JPanel buildActionRow(String title, String subtitle) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(CARD_BG);
        row.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBackground(CARD_BG);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setForeground(MUTED);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        text.add(titleLabel);
        text.add(subtitleLabel);

        JLabel chevron = new JLabel(">");
        chevron.setForeground(MUTED);
        chevron.setFont(new Font("SansSerif", Font.BOLD, 18));

        row.add(text, BorderLayout.WEST);
        row.add(chevron, BorderLayout.EAST);

        row.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                row.setBackground(CARD_HOVER);
                text.setBackground(CARD_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                row.setBackground(CARD_BG);
                text.setBackground(CARD_BG);
            }
        });

        return row;
    }

    private JButton makePrimaryButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        return button;
    }

    public void showPremiumTrialScreen() {
        User user = getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this,
                    "Please sign in to start a premium trial.",
                    "Premium Trial",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        user.setPremium(true);
        user.setPremiumTrialUsed(true);
        refresh();
        JOptionPane.showMessageDialog(this,
                "Premium trial opened. Enjoy an ad-free upgrade for a limited time.",
                "Premium Trial",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void showTrialStartedMessage() {
        JOptionPane.showMessageDialog(this,
                "Your premium trial has started successfully.",
                "Trial Started",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
