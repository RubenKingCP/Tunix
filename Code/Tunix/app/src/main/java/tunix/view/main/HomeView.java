package tunix.view.main;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class HomeView extends JPanel {

    // =========================================================
    //  Constants & colours  (match LibraryView)
    // =========================================================
    private static final Color BG        = new Color(18,  18,  18);
    private static final Color BG_CARD   = new Color(28,  28,  28);
    private static final Color BG_HOVER  = new Color(38,  38,  38);
    private static final Color FG_PRIMARY   = Color.WHITE;
    private static final Color FG_SECONDARY = new Color(160, 160, 160);
    private static final Font  FONT_TITLE   = new Font("SansSerif", Font.BOLD,   22);
    private static final Font  FONT_SECTION = new Font("SansSerif", Font.BOLD,   16);
    private static final Font  FONT_CARD    = new Font("SansSerif", Font.BOLD,   13);
    private static final Font  FONT_SUB     = new Font("SansSerif", Font.PLAIN,  12);

    // =========================================================
    //  Constructor
    // =========================================================
    public HomeView() {
        setLayout(new BorderLayout());
        setBackground(BG);

        // Greeting + top bar
        JPanel topBar = buildTopBar();

        // Scrollable main content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG);
        content.setBorder(BorderFactory.createEmptyBorder(24, 24, 40, 24));

        content.add(buildSection("Recently played",     recentlyPlayed()));
        content.add(Box.createVerticalStrut(32));
        content.add(buildSection("Recommended for you", recommended()));
        content.add(Box.createVerticalStrut(32));
        content.add(buildSection("From your library",   fromLibrary()));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setBackground(BG);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.getVerticalScrollBar().setBackground(BG);

        add(topBar,  BorderLayout.NORTH);
        add(scroll,  BorderLayout.CENTER);
    }

    // =========================================================
    //  Top bar  (greeting + search icon + profile icon)
    // =========================================================
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG);
        bar.setBorder(BorderFactory.createEmptyBorder(20, 24, 4, 24));

        // Greeting
        String hour = String.valueOf(java.time.LocalTime.now().getHour());
        int h = Integer.parseInt(hour);
        String greeting = h < 12 ? "Good morning" : h < 18 ? "Good afternoon" : "Good evening";
        JLabel greet = new JLabel(greeting);
        greet.setFont(FONT_TITLE);
        greet.setForeground(FG_PRIMARY);

        // Right-side icon buttons
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setBackground(BG);
        //right.add(iconButton("\uD83D\uDD0D", "Search"));   // 🔍
        //right.add(iconButton("\uD83D\uDC64", "Profile"));  // 👤

        bar.add(greet, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    private JButton iconButton(String emoji, String tooltip) {
        JButton btn = new JButton(emoji);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btn.setForeground(FG_PRIMARY);
        btn.setBackground(new Color(40, 40, 40));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 70, 70), 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText(tooltip);
        return btn;
    }

    // =========================================================
    //  Section  (heading row + horizontal card strip)
    // =========================================================
    private JPanel buildSection(String title, List<CardItem> items) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(BG);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Heading row ---
        JPanel headRow = new JPanel(new BorderLayout());
        headRow.setBackground(BG);
        headRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        headRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        headRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_SECTION);
        titleLabel.setForeground(FG_PRIMARY);

        JButton showAll = new JButton("Show all");
        showAll.setFont(new Font("SansSerif", Font.BOLD, 11));
        showAll.setForeground(FG_SECONDARY);
        showAll.setBackground(BG);
        showAll.setBorderPainted(false);
        showAll.setFocusPainted(false);
        showAll.setContentAreaFilled(false);
        showAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showAll.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { showAll.setForeground(FG_PRIMARY); }
            @Override public void mouseExited(MouseEvent e)  { showAll.setForeground(FG_SECONDARY); }
        });

        headRow.add(titleLabel, BorderLayout.WEST);
        headRow.add(showAll,    BorderLayout.EAST);

        // --- Card strip ---
        JPanel strip = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        strip.setBackground(BG);
        strip.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (CardItem item : items) strip.add(buildCard(item));

        // Wrap in horizontal scroll so overflow doesn't break layout
        JScrollPane hScroll = new JScrollPane(strip);
        hScroll.setBorder(null);
        hScroll.setBackground(BG);
        hScroll.getViewport().setBackground(BG);
        hScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        hScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        hScroll.getHorizontalScrollBar().setUnitIncrement(30);
        hScroll.setPreferredSize(new Dimension(Integer.MAX_VALUE, 185));
        hScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 185));
        hScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        section.add(headRow);
        section.add(hScroll);
        return section;
    }

    // =========================================================
    //  Card
    // =========================================================
    private JPanel buildCard(CardItem item) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createEmptyBorder(12, 12, 14, 12));
        card.setPreferredSize(new Dimension(148, 176));
        card.setMaximumSize(new Dimension(148, 176));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Cover art placeholder
        JLabel cover = buildCover(item);
        cover.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(cover);
        card.add(Box.createVerticalStrut(10));

        // Name
        JLabel name = new JLabel(truncate(item.name, 16));
        name.setFont(FONT_CARD);
        name.setForeground(FG_PRIMARY);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Subtitle
        JLabel sub = new JLabel(truncate(item.subtitle, 20));
        sub.setFont(FONT_SUB);
        sub.setForeground(FG_SECONDARY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(name);
        card.add(Box.createVerticalStrut(3));
        card.add(sub);

        // Hover: show play button overlay + background change
        JButton playBtn = buildPlayButton();
        playBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        playBtn.setVisible(false);

        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                card.setBackground(BG_HOVER);
                playBtn.setVisible(true);
                card.revalidate(); card.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                card.setBackground(BG_CARD);
                playBtn.setVisible(false);
                card.revalidate(); card.repaint();
            }
        });

        return card;
    }

    private JLabel buildCover(CardItem item) {
        boolean circle = item.type == CardType.ARTIST;
        int size = 112;
        return new JLabel() {
            {
                setPreferredSize(new Dimension(size, size));
                setMinimumSize(new Dimension(size, size));
                setMaximumSize(new Dimension(size, size));
            }
            @Override protected void paintComponent(Graphics g) {
                int w = getWidth(), h = getHeight();
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(item.color);
                if (circle) g2.fillOval(0, 0, w, h);
                else        g2.fillRoundRect(0, 0, w, h, 10, 10);

                // Type badge (small pill bottom-left)
                String badge = item.type.label;
                g2.setFont(new Font("SansSerif", Font.BOLD, 9));
                FontMetrics fm = g2.getFontMetrics();
                int bw = fm.stringWidth(badge) + 10;
                int bh = fm.getHeight() + 4;
                g2.setColor(new Color(0, 0, 0, 140));
                g2.fillRoundRect(6, h - bh - 6, bw, bh, 6, 6);
                g2.setColor(Color.WHITE);
                g2.drawString(badge, 11, h - 6 - fm.getDescent());

                // Initial letter
                g2.setColor(new Color(255, 255, 255, 90));
                g2.setFont(new Font("SansSerif", Font.BOLD, w / 3));
                fm = g2.getFontMetrics();
                String ch = String.valueOf(item.name.charAt(0));
                g2.drawString(ch,
                    (w - fm.stringWidth(ch)) / 2,
                    (h - fm.getHeight()) / 2 + fm.getAscent());
                g2.dispose();
            }
        };
    }

    private JButton buildPlayButton() {
        return new JButton() {
            {
                setPreferredSize(new Dimension(40, 40));
                setMinimumSize(new Dimension(40, 40));
                setMaximumSize(new Dimension(40, 40));
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 215, 96));   // Spotify green
                g2.fillOval(0, 0, 40, 40);
                // Triangle
                int[] xs = {14, 28, 14};
                int[] ys = {11, 20, 29};
                g2.setColor(Color.BLACK);
                g2.fillPolygon(xs, ys, 3);
                g2.dispose();
            }
        };
    }

    // =========================================================
    //  Data model
    // =========================================================
    enum CardType {
        SONG("Song"), ALBUM("Album"), PLAYLIST("Playlist"), ARTIST("Artist");
        final String label;
        CardType(String l) { this.label = l; }
    }

    record CardItem(String name, String subtitle, CardType type, Color color) {}

    // =========================================================
    //  Placeholder data
    // =========================================================
    private List<CardItem> recentlyPlayed() {
        return List.of(
            new CardItem("Do I Wanna Know?", "Arctic Monkeys",      CardType.SONG,     new Color(50,  60, 100)),
            new CardItem("Let It Happen",    "Tame Impala",         CardType.SONG,     new Color(90,  40, 130)),
            new CardItem("AM",               "Arctic Monkeys",      CardType.ALBUM,    new Color(40,  40,  40)),
            new CardItem("Currents",         "Tame Impala",         CardType.ALBUM,    new Color(80,  30, 140)),
            new CardItem("Chill Vibes",      "Playlist \u2022 You", CardType.PLAYLIST, new Color(20, 160,  80)),
            new CardItem("Morning Hits",     "Playlist \u2022 You", CardType.PLAYLIST, new Color(60, 100, 190))
        );
    }

    private List<CardItem> recommended() {
        return List.of(
            new CardItem("R U Mine?",        "Arctic Monkeys",      CardType.SONG,     new Color(110,  40,  40)),
            new CardItem("The Less I Know",  "Tame Impala",         CardType.SONG,     new Color(60,   50, 160)),
            new CardItem("Tranquility Base", "Arctic Monkeys",      CardType.ALBUM,    new Color(30,   60,  90)),
            new CardItem("Innerspeaker",     "Tame Impala",         CardType.ALBUM,    new Color(140,  90,  20)),
            new CardItem("Indie Mix",        "Playlist \u2022 Spotify", CardType.PLAYLIST, new Color(160, 40, 80)),
            new CardItem("Deep Focus",       "Playlist \u2022 Spotify", CardType.PLAYLIST, new Color(20,  80, 140))
        );
    }

    private List<CardItem> fromLibrary() {
        return List.of(
            new CardItem("505",              "Arctic Monkeys",      CardType.SONG,     new Color(70,  70,  30)),
            new CardItem("Eventually",       "Tame Impala",         CardType.SONG,     new Color(40, 110, 100)),
            new CardItem("Humbug",           "Arctic Monkeys",      CardType.ALBUM,    new Color(80,  50,  20)),
            new CardItem("Lonerism",         "Tame Impala",         CardType.ALBUM,    new Color(50,  30, 110)),
            new CardItem("Late Night Drive", "Playlist \u2022 You", CardType.PLAYLIST, new Color(20,  40,  90)),
            new CardItem("Workout Mix",      "Playlist \u2022 You", CardType.PLAYLIST, new Color(180, 60,  20))
        );
    }

    // =========================================================
    //  Utilities
    // =========================================================
    private String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 1) + "\u2026";
    }

    // =========================================================
    //  Main (standalone test)
    // =========================================================
    public void display() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tunix \u2013 Home");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(new HomeView(), BorderLayout.CENTER);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}