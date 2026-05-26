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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tunix.service.auth.SessionService;

public class ArtistProfileView extends JPanel {

    private static final Color BG = new Color(18, 18, 18);

    private static final Color CARD_BG = new Color(28, 28, 28);

    private static final Color CARD_HOVER = new Color(36, 36, 36);

    private static final Color MUTED = new Color(160, 160, 160);

    private static final Color GREEN = new Color(30, 215, 96);

    private JButton uploadSongButton;

    public ArtistProfileView() {

        initGui();
    }

    public void initGui() {

        removeAll();

        setLayout(new BorderLayout());

        setBackground(BG);

        JPanel content = new JPanel();

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.setBackground(BG);

        content.setBorder(
                BorderFactory.createEmptyBorder(
                        24,
                        24,
                        32,
                        24
                )
        );

        content.add(buildHeader());

        content.add(Box.createVerticalStrut(24));

        content.add(buildArtistSection());

        content.add(Box.createVerticalStrut(24));

        content.add(buildUploadSection());

        content.add(Box.createVerticalStrut(24));

        content.add(buildQuickActionsSection());

        JScrollPane scroll = new JScrollPane(content);

        scroll.setBorder(null);

        scroll.setBackground(BG);

        scroll.getViewport().setBackground(BG);

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.getVerticalScrollBar().setUnitIncrement(20);

        add(scroll, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {

        JPanel panel = new JPanel(new BorderLayout(16, 0));

        panel.setBackground(BG);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel artistInfo = new JPanel();

        artistInfo.setLayout(new BoxLayout(artistInfo, BoxLayout.Y_AXIS));

        artistInfo.setBackground(BG);

        JLabel title = new JLabel("Artist Profile");

        title.setForeground(Color.WHITE);

        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitle = new JLabel(
                "Manage your uploaded music and artist profile."
        );

        subtitle.setForeground(MUTED);

        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        artistInfo.add(title);

        artistInfo.add(Box.createVerticalStrut(4));

        artistInfo.add(subtitle);

        JLabel avatar = new JLabel() {

            @Override
            protected void paintComponent(Graphics graphics) {

                Graphics2D g2 = (Graphics2D) graphics.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(new Color(60, 60, 60));

                g2.fillOval(0, 0, getWidth(), getHeight());

                g2.setColor(Color.WHITE);

                g2.setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                getWidth() / 3
                        )
                );

                FontMetrics metrics = g2.getFontMetrics();

                String initial = "A";

                g2.drawString(
                        initial,
                        (getWidth() - metrics.stringWidth(initial)) / 2,
                        (getHeight() - metrics.getHeight()) / 2
                                + metrics.getAscent()
                );

                g2.dispose();
            }
        };

        avatar.setPreferredSize(new Dimension(72, 72));

        avatar.setMinimumSize(new Dimension(72, 72));

        avatar.setMaximumSize(new Dimension(72, 72));

        avatar.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        panel.add(avatar, BorderLayout.WEST);

        panel.add(artistInfo, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildArtistSection() {

        JPanel section = buildSectionContainer("Artist Information");

        JPanel details = new JPanel();

        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        details.setBackground(CARD_BG);

        details.setBorder(
                BorderFactory.createEmptyBorder(
                        16,
                        16,
                        16,
                        16
                )
        );

        var user =
                SessionService.Instance == null
                        ? null
                        : SessionService.Instance.getUser();

        if (user == null) {

            addDetailRow(details, "Artist Name", "Not signed in");

            addDetailRow(details, "Followers", "—");

        } else {

            addDetailRow(details, "Artist Name", user.getUsername());

            addDetailRow(details, "Followers", "0");
        }

        addDetailRow(details, "Uploaded Songs", "0");

        addDetailRow(details, "Monthly Listeners", "0");

        section.add(details);

        return section;
    }

    private JPanel buildUploadSection() {

        JPanel section = buildSectionContainer("Music Upload");

        JPanel uploadPanel = new JPanel();

        uploadPanel.setLayout(new BoxLayout(uploadPanel, BoxLayout.Y_AXIS));

        uploadPanel.setBackground(CARD_BG);

        uploadPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        16,
                        16,
                        16,
                        16
                )
        );

        JLabel description = new JLabel(
                "Upload a new song to your artist library."
        );

        description.setForeground(Color.WHITE);

        description.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        uploadSongButton = new JButton("Upload Song");

        uploadSongButton.setBackground(GREEN);

        uploadSongButton.setForeground(Color.BLACK);

        uploadSongButton.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        uploadSongButton.setFocusPainted(false);

        uploadSongButton.setBorderPainted(false);

        uploadSongButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        uploadSongButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        uploadSongButton.setMaximumSize(
                new Dimension(180, 42)
        );

        uploadPanel.add(description);

        uploadPanel.add(Box.createVerticalStrut(16));

        JPanel buttonRow = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 12, 0)
        );

        buttonRow.setBackground(CARD_BG);

        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        buttonRow.add(uploadSongButton);

        JButton uploadAlbumButton =
                new JButton("Upload Album");

        uploadAlbumButton.setBackground(GREEN);

        uploadAlbumButton.setForeground(Color.BLACK);

        uploadAlbumButton.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        uploadAlbumButton.setFocusPainted(false);

        uploadAlbumButton.setBorderPainted(false);

        uploadAlbumButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        buttonRow.add(uploadAlbumButton);

        uploadPanel.add(buttonRow);

        section.add(uploadPanel);

        return section;
    }

    private JPanel buildQuickActionsSection() {

        JPanel section = buildSectionContainer("Quick actions");

        JPanel actions = new JPanel();

        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));

        actions.setBackground(CARD_BG);

        actions.setBorder(
                BorderFactory.createEmptyBorder(
                        16,
                        16,
                        16,
                        16
                )
        );

        actions.add(
                buildActionRow(
                        "Manage Songs",
                        "Edit uploaded songs and metadata"
                )
        );

        actions.add(Box.createVerticalStrut(10));

        actions.add(
                buildActionRow(
                        "Artist Analytics",
                        "View streams and engagement"
                )
        );

        actions.add(Box.createVerticalStrut(10));

        actions.add(
                buildActionRow(
                        "Profile Customization",
                        "Edit bio and artist details"
                )
        );

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

        heading.setFont(
                new Font("SansSerif", Font.BOLD, 16)
        );

        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        section.add(heading);

        section.add(Box.createVerticalStrut(10));

        return section;
    }

    private void addDetailRow(
            JPanel container,
            String label,
            String value
    ) {

        JPanel row = new JPanel(new BorderLayout());

        row.setBackground(CARD_BG);

        row.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 30)
        );

        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelView = new JLabel(label);

        labelView.setForeground(MUTED);

        labelView.setFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );

        JLabel valueView = new JLabel(value);

        valueView.setForeground(Color.WHITE);

        valueView.setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );

        row.add(labelView, BorderLayout.WEST);

        row.add(valueView, BorderLayout.EAST);

        container.add(row);
    }

    private JPanel buildActionRow(
            String title,
            String subtitle
    ) {

        JPanel row = new JPanel(new BorderLayout());

        row.setBackground(CARD_BG);

        row.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        8,
                        8,
                        8
                )
        );

        row.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        row.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 56)
        );

        JPanel text = new JPanel();

        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        text.setBackground(CARD_BG);

        JLabel titleLabel = new JLabel(title);

        titleLabel.setForeground(Color.WHITE);

        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        JLabel subtitleLabel = new JLabel(subtitle);

        subtitleLabel.setForeground(MUTED);

        subtitleLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );

        text.add(titleLabel);

        text.add(subtitleLabel);

        JLabel chevron = new JLabel(">");

        chevron.setForeground(MUTED);

        chevron.setFont(
                new Font("SansSerif", Font.BOLD, 18)
        );

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

    public void setUploadSongListener(ActionListener listener) {
        uploadSongButton.addActionListener(listener);
    }
}


