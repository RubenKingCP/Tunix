package tunix.ui.views.main.center;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class SearchView extends JPanel {

    // ── Palette (matches screenshot) ──────────────────────────────────────
    private static final Color BG           = new Color(0x121212);
    private static final Color SURFACE      = new Color(0x1E1E1E);
    private static final Color SURFACE2     = new Color(0x2A2A2A);
    private static final Color TEXT_PRIMARY  = new Color(0xFFFFFF);
    private static final Color TEXT_SECONDARY= new Color(0xB3B3B3);
    private static final Color ACCENT        = new Color(0xFFFFFF);

    // Card accent colours (mimicking the coloured album-art placeholders)
    private static final Color[] CARD_COLORS = {
        new Color(0x4A5568), new Color(0x6B46C1), new Color(0x718096),
        new Color(0x744210), new Color(0x276749), new Color(0x9B2335),
        new Color(0x2B6CB0), new Color(0xC05621), new Color(0x285E61),
        new Color(0x702459), new Color(0x2C7A7B), new Color(0x553C9A),
    };

    // Tag-pill colours
    private static final Color TAG_SONG     = new Color(0x1A1A2E);
    private static final Color TAG_ALBUM    = new Color(0x1A2E1A);
    private static final Color TAG_PLAYLIST = new Color(0x2E1A1A);

    // ── Fonts ──────────────────────────────────────────────────────────────
    private static final Font FONT_TITLE   = new Font("SansSerif", Font.BOLD,  22);
    private static final Font FONT_SECTION = new Font("SansSerif", Font.BOLD,  16);
    private static final Font FONT_CARD    = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font FONT_SUB     = new Font("SansSerif", Font.PLAIN, 11);
    private static final Font FONT_TAG     = new Font("SansSerif", Font.BOLD,   9);
    private static final Font FONT_LETTER  = new Font("SansSerif", Font.BOLD,  32);

    // ── Data ───────────────────────────────────────────────────────────────
    private record CardData(String letter, String title, String subtitle, String tag, Color color) {}

    private static final CardData[] BROWSE_CATEGORIES = {
        new CardData("♪", "Podcasts",        "",  "",  new Color(0x8B5CF6)),
        new CardData("♬", "Live Events",     "",  "",  new Color(0xEC4899)),
        new CardData("♩", "Made For You",    "",  "",  new Color(0x3B82F6)),
        new CardData("★", "New Releases",    "",  "",  new Color(0x10B981)),
        new CardData("♫", "Hip-Hop",         "",  "",  new Color(0xF59E0B)),
        new CardData("♭", "Pop",             "",  "",  new Color(0xEF4444)),
        new CardData("♮", "Rock",            "",  "",  new Color(0x6366F1)),
        new CardData("♯", "Electronic",      "",  "",  new Color(0x14B8A6)),
        new CardData("♪", "Indie",           "",  "",  new Color(0xF97316)),
        new CardData("♬", "R&B",             "",  "",  new Color(0x8B5CF6)),
        new CardData("♩", "Jazz",            "",  "",  new Color(0x059669)),
        new CardData("♫", "Classical",       "",  "",  new Color(0xDC2626)),
    };

    // ── State ──────────────────────────────────────────────────────────────
    private String searchText = "";
    private boolean showResults = false;

    // Search-result data (shown when user types)
    private static final CardData[] RESULT_SONGS = {
        new CardData("D", "Do I Wanna Know?",   "Arctic Monkeys", "Song",    CARD_COLORS[0]),
        new CardData("R", "R U Mine?",           "Arctic Monkeys", "Song",    CARD_COLORS[6]),
        new CardData("T", "The Less I Know",     "Tame Impala",    "Song",    CARD_COLORS[1]),
        new CardData("L", "Let It Happen",       "Tame Impala",    "Song",    CARD_COLORS[2]),
    };
    private static final CardData[] RESULT_ALBUMS = {
        new CardData("A", "AM",                  "Arctic Monkeys", "Album",   CARD_COLORS[3]),
        new CardData("C", "Currents",            "Tame Impala",    "Album",   CARD_COLORS[4]),
        new CardData("T", "Tranquility Base",    "Arctic Monkeys", "Album",   CARD_COLORS[5]),
        new CardData("I", "Innerspeaker",        "Tame Impala",    "Album",   CARD_COLORS[7]),
    };

    // ── Search field ref ───────────────────────────────────────────────────
    private JTextField searchField;

    // ══════════════════════════════════════════════════════════════════════
    public SearchView() {
        setLayout(new BorderLayout());
        setBackground(BG);
        setOpaque(true);

        add(buildTopBar(), BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(buildContent());
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setBackground(BG);
        add(scroll, BorderLayout.CENTER);
    }

    // ── Top search bar ─────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        bar.setBackground(SURFACE);
        bar.setBorder(new EmptyBorder(4, 16, 4, 16));

        JLabel icon = new JLabel("🔍");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 16));
        bar.add(icon);

        searchField = new RoundTextField(28);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBackground(SURFACE2);
        searchField.setForeground(TEXT_PRIMARY);
        searchField.setCaretColor(TEXT_PRIMARY);
        searchField.setBorder(new EmptyBorder(6, 12, 6, 12));

        // Placeholder behaviour
        searchField.setText("What do you want to listen to?");
        searchField.setForeground(TEXT_SECONDARY);
        searchField.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("What do you want to listen to?")) {
                    searchField.setText("");
                    searchField.setForeground(TEXT_PRIMARY);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("What do you want to listen to?");
                    searchField.setForeground(TEXT_SECONDARY);
                    searchText = "";
                    showResults = false;
                    refreshContent();
                }
            }
        });
        searchField.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                String t = searchField.getText().trim();
                boolean wasShowing = showResults;
                searchText = t;
                showResults = !t.isEmpty() && !t.equals("What do you want to listen to?");
                if (wasShowing != showResults) refreshContent();
            }
        });
        bar.add(searchField);
        return bar;
    }

    // ── Scrollable content panel ───────────────────────────────────────────
    private JPanel contentPanel;

    private JPanel buildContent() {
        contentPanel = new JPanel();
        contentPanel.setBackground(BG);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        populateContent();
        return contentPanel;
    }

    private void refreshContent() {
        contentPanel.removeAll();
        populateContent();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void populateContent() {
        if (showResults) {
            contentPanel.add(sectionLabel("Songs"));
            contentPanel.add(Box.createVerticalStrut(12));
            contentPanel.add(cardRow(RESULT_SONGS));
            contentPanel.add(Box.createVerticalStrut(24));
            contentPanel.add(sectionLabel("Albums"));
            contentPanel.add(Box.createVerticalStrut(12));
            contentPanel.add(cardRow(RESULT_ALBUMS));
        } else {
            contentPanel.add(greetingLabel());
            contentPanel.add(Box.createVerticalStrut(24));
            contentPanel.add(sectionLabel("Browse all"));
            contentPanel.add(Box.createVerticalStrut(12));
            contentPanel.add(browseGrid());
        }
    }

    // ── Greeting ───────────────────────────────────────────────────────────
    private JLabel greetingLabel() {
        JLabel lbl = new JLabel("Search");
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    // ── Section label ──────────────────────────────────────────────────────
    private JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_SECTION);
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    // ── Horizontal card row ────────────────────────────────────────────────
    private JPanel cardRow(CardData[] cards) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        row.setBackground(BG);
        row.setAlignmentX(LEFT_ALIGNMENT);
        for (CardData c : cards) row.add(buildCard(c));
        return row;
    }

    // ── Browse grid (wrapping) ─────────────────────────────────────────────
    private JPanel browseGrid() {
        JPanel grid = new JPanel(new WrapLayout(FlowLayout.LEFT, 12, 12));
        grid.setBackground(BG);
        grid.setAlignmentX(LEFT_ALIGNMENT);
        for (CardData c : BROWSE_CATEGORIES) grid.add(buildBrowseCard(c));
        return grid;
    }

    // ── Standard music card (140×160) ─────────────────────────────────────
    private JPanel buildCard(CardData card) {
        JPanel panel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(140, 175));

        // Art placeholder
        ArtPanel art = new ArtPanel(card.color, card.letter, 140, 120, 8);
        art.setBounds(0, 0, 140, 120);
        panel.add(art);

        // Tag pill
        TagLabel tag = new TagLabel(card.tag);
        tag.setBounds(8, 94, 58, 18);
        panel.add(tag);

        // Title
        JLabel title = new JLabel(truncate(card.title, 16));
        title.setFont(FONT_CARD);
        title.setForeground(TEXT_PRIMARY);
        title.setBounds(8, 126, 124, 18);
        panel.add(title);

        // Subtitle
        JLabel sub = new JLabel(truncate(card.subtitle, 20));
        sub.setFont(FONT_SUB);
        sub.setForeground(TEXT_SECONDARY);
        sub.setBounds(8, 146, 124, 16);
        panel.add(sub);

        addHoverEffect(panel);
        return panel;
    }

    // ── Browse category card (160×80, wide) ───────────────────────────────
    private JPanel buildBrowseCard(CardData card) {
        JPanel panel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // gradient fill
                GradientPaint gp = new GradientPaint(
                    0, 0, card.color,
                    getWidth(), getHeight(), card.color.darker()
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(160, 80));

        JLabel letter = new JLabel(card.letter);
        letter.setFont(new Font("SansSerif", Font.BOLD, 28));
        letter.setForeground(new Color(0xFFFFFF, false));
        letter.setBounds(10, 8, 50, 40);
        panel.add(letter);

        JLabel name = new JLabel(card.title);
        name.setFont(new Font("SansSerif", Font.BOLD, 14));
        name.setForeground(TEXT_PRIMARY);
        name.setBounds(10, 50, 140, 22);
        panel.add(name);

        addHoverEffect(panel);
        return panel;
    }

    // ── Hover brightness effect ────────────────────────────────────────────
    private void addHoverEffect(JPanel panel) {
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(new Color(0x555555), 1, true));
                panel.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                panel.setBorder(null);
                panel.repaint();
            }
        });
    }

    // ── Helpers ────────────────────────────────────────────────────────────
    private static String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max - 1) + "…" : s;
    }

    // ══════════════════════════════════════════════════════════════════════
    // Inner components
    // ══════════════════════════════════════════════════════════════════════

    /** Rounded art placeholder with centred letter */
    private static class ArtPanel extends JPanel {
        private final Color bg;
        private final String letter;
        private final int radius;

        ArtPanel(Color bg, String letter, int w, int h, int radius) {
            this.bg = bg; this.letter = letter; this.radius = radius;
            setPreferredSize(new Dimension(w, h));
            setOpaque(false);
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setFont(FONT_LETTER);
            g2.setColor(new Color(0xFFFFFF, false));
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth()  - fm.stringWidth(letter)) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(letter, x, y);
            g2.dispose();
        }
    }

    /** Pill tag (Song / Album / Playlist) */
    private static class TagLabel extends JLabel {
        TagLabel(String text) {
            super(text);
            setFont(FONT_TAG);
            setForeground(TEXT_PRIMARY);
            setOpaque(false);
            setHorizontalAlignment(CENTER);
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color bg = "Song".equalsIgnoreCase(getText())     ? TAG_SONG
                     : "Album".equalsIgnoreCase(getText())    ? TAG_ALBUM
                     : TAG_PLAYLIST;
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Rounded text field */
    private static class RoundTextField extends JTextField {
        RoundTextField(int cols) { super(cols); setOpaque(false); }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(SURFACE2);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0x444444));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            g2.dispose();
        }
    }

    /**
     * FlowLayout variant that wraps children onto new rows,
     * reporting correct preferred height so the scroll pane works correctly.
     */
    private static class WrapLayout extends FlowLayout {
        WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }

        @Override public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }
        @Override public Dimension minimumLayoutSize(Container target) {
            return layoutSize(target, false);
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;

                int hgap = getHgap(), vgap = getVgap();
                Insets insets = target.getInsets();
                int maxWidth = targetWidth - insets.left - insets.right - hgap * 2;

                int rows = 1, rowWidth = 0, rowHeight = 0, totalHeight = 0;

                for (int i = 0; i < target.getComponentCount(); i++) {
                    Component c = target.getComponent(i);
                    if (!c.isVisible()) continue;
                    Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                    if (rowWidth + d.width > maxWidth && rowWidth > 0) {
                        totalHeight += rowHeight + vgap;
                        rowWidth = 0; rowHeight = 0; rows++;
                    }
                    rowWidth  += d.width + hgap;
                    rowHeight  = Math.max(rowHeight, d.height);
                }
                totalHeight += rowHeight + insets.top + insets.bottom + vgap * 2;
                return new Dimension(targetWidth, totalHeight);
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Quick preview main
    // ══════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tunix – Search");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 620);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(new Color(0x121212));
            frame.add(new SearchView());
            frame.setVisible(true);
        });
    }
}