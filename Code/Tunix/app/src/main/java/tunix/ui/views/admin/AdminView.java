package tunix.ui.views.admin;

import tunix.controller.AdminController;
import tunix.dto.enums.ArtistRequestStatus;
import tunix.dto.request.SongRequest;
import tunix.dto.response.SongResponse;
import tunix.model.ArtistRequest;
import tunix.model.musicContent.Song;

import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class AdminView extends JPanel {

    // ── Tunix colour palette ──────────────────────────────────────────────────
    private static final Color BG_PRIMARY   = new Color(0x0A, 0x0A, 0x0A);
    private static final Color BG_CARD      = new Color(0x1A, 0x1A, 0x1A);
    private static final Color BG_TAB_BAR   = new Color(0x12, 0x12, 0x12);
    private static final Color TEXT_PRIMARY = new Color(0xFF, 0xFF, 0xFF);
    private static final Color TEXT_MUTED   = new Color(0xB3, 0xB3, 0xB3);
    private static final Color BTN_APPROVE  = new Color(0x1D, 0xB9, 0x54);
    private static final Color BTN_REJECT   = new Color(0xE2, 0x2B, 0x3E);
    private static final Color BTN_REMOVE   = new Color(0x3A, 0x3A, 0x3A);
    private static final Color DIVIDER      = new Color(0x2A, 0x2A, 0x2A);
    private static final Color UNDERLINE    = new Color(0xFF, 0xFF, 0xFF);

    private static final Font FONT_HEADING = new Font("SansSerif", Font.BOLD,  20);
    private static final Font FONT_BODY    = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font FONT_BTN     = new Font("SansSerif", Font.BOLD,  12);

    // ─────────────────────────────────────────────────────────────────────────

    private final AdminController adminController;
    private List<Song> songs;
    private List<ArtistRequest> artistRequests;

    // Tab bar built manually — avoids JTabbedPane LAF quirks entirely
    private JPanel tabBar;
    private JButton tabSongs;
    private JButton tabApplications;
    private JPanel contentArea;   // CardLayout host
    private CardLayout cardLayout;

    private JPanel songsPanel;
    private JPanel artistApplicationsPanel;

    public AdminView(AdminController controller) {
        this.adminController = controller;
        display();
    }

    // ── display() ─────────────────────────────────────────────────────────────

    public void display() {
        setLayout(new BorderLayout());
        setBackground(BG_PRIMARY);

        // ── Header ────────────────────────────────────────────────────────────
        JPanel header = buildHeader();
        add(header, BorderLayout.NORTH);

        // ── Content (CardLayout) ──────────────────────────────────────────────
        cardLayout  = new CardLayout();
        contentArea = new JPanel(cardLayout);
        contentArea.setBackground(BG_PRIMARY);

        songsPanel = new JPanel();
        songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.Y_AXIS));
        songsPanel.setBackground(BG_PRIMARY);
        songsPanel.setBorder(new EmptyBorder(12, 16, 12, 16));

        artistApplicationsPanel = new JPanel();
        artistApplicationsPanel.setLayout(new BoxLayout(artistApplicationsPanel, BoxLayout.Y_AXIS));
        artistApplicationsPanel.setBackground(BG_PRIMARY);
        artistApplicationsPanel.setBorder(new EmptyBorder(12, 16, 12, 16));

        contentArea.add(wrapScroll(songsPanel),              "Songs");
        contentArea.add(wrapScroll(artistApplicationsPanel), "Artists");

        add(contentArea, BorderLayout.CENTER);

        // populate if data already set
        if (songs != null)
            for (Song s : songs) songsPanel.add(new SongGUI(s));

        if (artistRequests != null)
            for (ArtistRequest a : artistRequests)
                if (a.getStatus() == ArtistRequestStatus.Pending)
                    artistApplicationsPanel.add(new ApplicationGUI(a));

        // default to Songs tab
        selectTab("Songs");
    }

    // ── Header + tab bar ──────────────────────────────────────────────────────

    private JPanel buildHeader() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_PRIMARY);
        wrapper.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Title row with logout button on the right
        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);

        JLabel title = new JLabel("Admin Panel");
        title.setFont(FONT_HEADING);
        title.setForeground(TEXT_PRIMARY);
        title.setBorder(new EmptyBorder(22, 24, 10, 24));
        titleRow.add(title, BorderLayout.WEST);

        JButton logoutButton = makeButton("Logout", BTN_REJECT, Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(100, 32));
        logoutButton.addActionListener(e -> adminController.onLogoutButtonClicked());
        JPanel logoutWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        logoutWrapper.setOpaque(false);
        logoutWrapper.add(logoutButton);
        titleRow.add(logoutWrapper, BorderLayout.EAST);

        wrapper.add(titleRow, BorderLayout.NORTH);

        // Tab bar
        tabBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabBar.setBackground(BG_TAB_BAR);
        tabBar.setBorder(new EmptyBorder(0, 16, 0, 16));

        tabSongs        = makeTabButton("Songs");
        tabApplications = makeTabButton("Artist Applications");

        tabSongs.addActionListener(e        -> selectTab("Songs"));
        tabApplications.addActionListener(e -> selectTab("Artists"));

        tabBar.add(tabSongs);
        tabBar.add(Box.createHorizontalStrut(4));
        tabBar.add(tabApplications);

        wrapper.add(tabBar, BorderLayout.SOUTH);

        // Bottom border under tab bar
        JPanel divider = new JPanel();
        divider.setBackground(DIVIDER);
        divider.setPreferredSize(new Dimension(1, 1));

        JPanel outerWrapper = new JPanel(new BorderLayout());
        outerWrapper.setBackground(BG_PRIMARY);
        outerWrapper.add(wrapper,  BorderLayout.CENTER);
        outerWrapper.add(divider,  BorderLayout.SOUTH);
        return outerWrapper;
    }

    private JButton makeTabButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // background
                g2.setColor(BG_TAB_BAR);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // underline when selected
                if (Boolean.TRUE.equals(getClientProperty("selected"))) {
                    g2.setColor(UNDERLINE);
                    g2.fillRect(0, getHeight() - 2, getWidth(), 2);
                }
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(TEXT_MUTED);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 18, 10, 18));
        return btn;
    }

    private void selectTab(String card) {
    cardLayout.show(contentArea, card);

    boolean songsActive = "Songs".equals(card);
    tabSongs.putClientProperty("selected", songsActive);
    tabApplications.putClientProperty("selected", !songsActive);
    tabSongs.setForeground(songsActive ? TEXT_PRIMARY : TEXT_MUTED);
    tabApplications.setForeground(!songsActive ? TEXT_PRIMARY : TEXT_MUTED);
    tabSongs.repaint();
    tabApplications.repaint();

    if (songsActive) {
    List<Song> updatedSongs = adminController.onSongsClicked();
    songsPanel.removeAll();
    for (Song s : updatedSongs) songsPanel.add(new SongGUI(s));
    songsPanel.revalidate();
    songsPanel.repaint();
} else {
    onArtistRequestsClicked();
}
}
    // ── Scroll wrapper ────────────────────────────────────────────────────────

    private JScrollPane wrapScroll(JPanel inner) {
        JScrollPane sp = new JScrollPane(inner,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.setBackground(BG_PRIMARY);
        sp.getViewport().setBackground(BG_PRIMARY);
        sp.getVerticalScrollBar().setUI(new TunixScrollBarUI());
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    // ── Pill button factory ───────────────────────────────────────────────────

    private static JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.brighter() : bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(fg);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, 32));
        btn.setMaximumSize(new Dimension(110, 32));
        return btn;
    }

    // ── Public API (logic unchanged) ──────────────────────────────────────────

    public void setSongs(List<Song> songs) { this.songs = songs; }

    public void setArtistRequests(List<ArtistRequest> ar) { this.artistRequests = ar; }

    public void onArtistRequestsClicked() {
        List<ArtistRequest> ar = adminController.onArtistRequestsClicked();
        setArtistRequests(ar);
        displayArtistRequests(ar);
    }

    public void displayArtistRequests(List<ArtistRequest> artistRequests) {
        artistApplicationsPanel.removeAll();
        for (ArtistRequest a : artistRequests)
            if (a.getStatus() == ArtistRequestStatus.Pending)
                artistApplicationsPanel.add(new ApplicationGUI(a));
        artistApplicationsPanel.revalidate();
        artistApplicationsPanel.repaint();
    }

    private void showArtistRequestDetails(ArtistRequest artistRequest) {}

    public void onArtistRequestShowDetailsClicked(ArtistRequest ar) { showArtistRequestDetails(ar); }

    public void onApproveArtistRequestClicked(int id) { adminController.onApproveArtistRequestClicked(id); }

    public void onRejectArtistRequestClicked(int id) { adminController.onRejectArtistRequestClicked(id); }

    public void showMessage(String message) { JOptionPane.showMessageDialog(this, message); }

    // ── Row widgets ───────────────────────────────────────────────────────────

    private class SongGUI extends JPanel {
        public SongGUI(Song song) {
            setLayout(new BorderLayout());
            setOpaque(false);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
            setBorder(new EmptyBorder(4, 0, 4, 0));

            JPanel card = new JPanel(new BorderLayout()) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(BG_CARD);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                    g2.dispose();
                }
            };
            card.setOpaque(false);
            card.setBorder(new EmptyBorder(10, 14, 10, 14));

            card.add(colorThumb(song.getTitle()), BorderLayout.WEST);

            JLabel label = new JLabel(song.getTitle());
            label.setFont(FONT_BODY);
            label.setForeground(TEXT_PRIMARY);
            label.setBorder(new EmptyBorder(0, 14, 0, 0));
            card.add(label, BorderLayout.CENTER);

            JButton btn = makeButton("Remove", BTN_REMOVE, TEXT_PRIMARY);
            btn.addActionListener(e -> adminController.onRemoveSongClicked(song.getId()));
            JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            right.setOpaque(false);
            right.add(btn);
            card.add(right, BorderLayout.EAST);

            add(card, BorderLayout.CENTER);
        }
    }

    private class ApplicationGUI extends JPanel {
        public ApplicationGUI(ArtistRequest application) {
            setLayout(new BorderLayout());
            setOpaque(false);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
            setBorder(new EmptyBorder(4, 0, 4, 0));

            JPanel card = new JPanel(new BorderLayout()) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(BG_CARD);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                    g2.dispose();
                }
            };
            card.setOpaque(false);
            card.setBorder(new EmptyBorder(10, 14, 10, 14));

            card.add(colorThumb(String.valueOf(application.getStageName().charAt(0))), BorderLayout.WEST);

            JLabel label = new JLabel(application.getStageName());
            label.setFont(FONT_BODY);
            label.setForeground(TEXT_PRIMARY);
            label.setBorder(new EmptyBorder(0, 14, 0, 0));
            card.add(label, BorderLayout.CENTER);

            JButton approve = makeButton("Approve", BTN_APPROVE, Color.WHITE);
            JButton reject  = makeButton("Reject",  BTN_REJECT,  Color.WHITE);
            approve.addActionListener(e -> adminController.onApproveArtistRequestClicked(application.getApplicantId()));
            reject.addActionListener (e -> adminController.onRejectArtistRequestClicked(application.getApplicantId()));

            JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            right.setOpaque(false);
            right.add(approve);
            right.add(reject);
            card.add(right, BorderLayout.EAST);

            add(card, BorderLayout.CENTER);
        }
    }

    // ── Color thumbnail ───────────────────────────────────────────────────────

    private static JPanel colorThumb(String seed) {
        Color[] palette = {
            new Color(0x4A, 0x6F, 0xA5), new Color(0x6C, 0x5C, 0xB0),
            new Color(0x1D, 0xB9, 0x54), new Color(0xC0, 0x39, 0x2B),
            new Color(0xE6, 0x7E, 0x22), new Color(0x16, 0xA0, 0x85),
            new Color(0x8E, 0x44, 0xAD), new Color(0x2E, 0x86, 0xC1)
        };
        Color bg = palette[Math.abs(seed.hashCode()) % palette.length];
        String initial = seed.isEmpty() ? "?" : String.valueOf(seed.charAt(0)).toUpperCase();
        return new JPanel() {
            { setPreferredSize(new Dimension(36, 36)); setOpaque(false); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, 36, 36, 8, 8);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(initial, (36 - fm.stringWidth(initial)) / 2,
                              (36 - fm.getHeight()) / 2 + fm.getAscent());
                g2.dispose();
            }
        };
    }

    // ── Custom dark scroll bar ────────────────────────────────────────────────

    private static class TunixScrollBarUI extends BasicScrollBarUI {
        @Override protected void configureScrollBarColors() {
            thumbColor = new Color(0x40, 0x40, 0x40);
            trackColor = BG_PRIMARY;
        }
        @Override protected JButton createDecreaseButton(int o) { return ghost(); }
        @Override protected JButton createIncreaseButton(int o) { return ghost(); }
        private JButton ghost() {
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(0, 0));
            return b;
        }
        @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(thumbColor);
            g2.fillRoundRect(r.x + 2, r.y + 2, r.width - 4, r.height - 4, 6, 6);
            g2.dispose();
        }
        @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
            g.setColor(trackColor);
            g.fillRect(r.x, r.y, r.width, r.height);
        }
    }
}