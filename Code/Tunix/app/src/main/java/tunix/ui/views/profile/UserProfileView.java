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

    public UserProfileView() {
        initGui();
    }

    public void initGui() {
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
        var user = SessionService.Instance.getUser();
        addDetailRow(details, "Display name", user.getUsername());
        addDetailRow(details, "Email", user.getEmail());
        addDetailRow(details, "Listening mode", "Personalized");
        addDetailRow(details, "Downloaded songs", "12");

        section.add(details);
        return section;
    }

    private JPanel buildPremiumSection() {
        JPanel section = buildSectionContainer("Premium");

        premiumPanel.setLayout(new BoxLayout(premiumPanel, BoxLayout.Y_AXIS));
        premiumPanel.setBackground(CARD_BG);
        premiumPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

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
        JButton trialButton = makePrimaryButton("Start trial", PREMIUM);
        trialButton.addActionListener(e -> showPremiumTrialScreen());
        buttonRow.add(trialButton);
        //buttonRow.add(planButton);

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

        section.add(actions);
        return section;
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

    private JButton makeSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(40, 40, 40));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        return button;
    }


    public void showPremiumTrialScreen() {
        planStatusLabel.setText("Current plan: Free");
        trialStatusLabel.setText("Trial: available");
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
