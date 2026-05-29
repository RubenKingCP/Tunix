package tunix.ui.views.main.center;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import tunix.controller.SearchController;
import tunix.dto.enums.LibraryAssetType;
import tunix.model.ILibraryAsset;
import tunix.navigation.events.EventBus;


public class SearchView extends JPanel {

    private static final Color BG = new Color(0x121212);
    private static final Color SURFACE = new Color(0x1E1E1E);
    private static final Color TEXT_PRIMARY = new Color(0xFFFFFF);
    private static final Color TEXT_SECONDARY = new Color(0xB3B3B3);
    private static final Color TAG_SONG = new Color(0x1A1A2E);
    private static final Color TAG_ALBUM = new Color(0x1A2E1A);
    private static final Color TAG_PLAYLIST = new Color(0x2E1A1A);

    private static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);
    private static final Font FONT_SECTION = new Font("SansSerif", Font.BOLD, 16);
    private static final Font FONT_CARD = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font FONT_SUB = new Font("SansSerif", Font.PLAIN, 11);
    private static final Font FONT_TAG = new Font("SansSerif", Font.BOLD, 9);
    private static final Font FONT_LETTER = new Font("SansSerif", Font.BOLD, 32);

    private static final CardData[] BROWSE_CATEGORIES = {
        new CardData("♪", "Podcasts", "", new Color(0x8B5CF6)),
        new CardData("♬", "Live Events", "", new Color(0xEC4899)),
        new CardData("♩", "Made For You", "", new Color(0x3B82F6)),
        new CardData("★", "New Releases", "", new Color(0x10B981)),
        new CardData("♫", "Hip-Hop", "", new Color(0xF59E0B)),
        new CardData("♭", "Pop", "", new Color(0xEF4444)),
        new CardData("♮", "Rock", "", new Color(0x6366F1)),
        new CardData("♯", "Electronic", "", new Color(0x14B8A6)),
        new CardData("♪", "Indie", "", new Color(0xF97316)),
        new CardData("♬", "R&B", "", new Color(0x8B5CF6)),
        new CardData("♩", "Jazz", "", new Color(0x059669)),
        new CardData("♫", "Classical", "", new Color(0xDC2626)),
    };

    private JPanel contentPanel;
    private List<ILibraryAsset> results;
    private final SearchController controller;

    public SearchView(List<ILibraryAsset> results) {
        this(results, null, null);
    }

    public SearchView(List<ILibraryAsset> results, EventBus eventBus, SearchController controller) {
        this.results = results == null ? List.of() : List.copyOf(results);
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(BG);
        setOpaque(true);

        JScrollPane scroll = new JScrollPane(buildContent());
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setBackground(BG);
        add(scroll, BorderLayout.CENTER);
    }

    public void setResults(List<ILibraryAsset> results) {
        this.results = results == null ? List.of() : List.copyOf(results);
        refreshContent();
    }

    public void refresh() {
        refreshContent();
    }

    

    private JPanel buildContent() {
        contentPanel = new JPanel();
        contentPanel.setBackground(BG);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        populateContent();
        return contentPanel;
    }

    private void refreshContent() {
        if (contentPanel == null) {
            return;
        }

        contentPanel.removeAll();
        populateContent();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void populateContent() {
        if (!results.isEmpty()) {
            contentPanel.add(sectionLabel("Search results"));
            contentPanel.add(Box.createVerticalStrut(12));
            contentPanel.add(buildResultGrid());
            return;
        }

        contentPanel.add(greetingLabel());
        contentPanel.add(Box.createVerticalStrut(24));
        contentPanel.add(sectionLabel("Browse all"));
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(browseGrid());
    }

    private JPanel buildResultGrid() {
        JPanel grid = new JPanel(new WrapLayout(FlowLayout.LEFT, 12, 12));
        grid.setBackground(BG);
        grid.setAlignmentX(LEFT_ALIGNMENT);

        for (ILibraryAsset asset : results) {
            grid.add(buildResultCard(asset));
        }

        return grid;
    }

    private JPanel buildResultCard(ILibraryAsset asset) {
        Color color = asset.getDisplayColor();
        String label = asset.getTitle().substring(0, 1).toUpperCase();
        String tag = tagText(asset.getType());

        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(140, 175));

        ArtPanel art = new ArtPanel(color, label, 140, 120, 8);
        art.setBounds(0, 0, 140, 120);
        panel.add(art);

        TagLabel tagLabel = new TagLabel(tag);
        tagLabel.setBounds(8, 94, 58, 18);
        panel.add(tagLabel);

        JLabel title = new JLabel(truncate(asset.getTitle(), 16));
        title.setFont(FONT_CARD);
        title.setForeground(TEXT_PRIMARY);
        title.setBounds(8, 126, 124, 18);
        panel.add(title);

        JLabel sub = new JLabel(truncate(asset.getSubtitle(), 20));
        sub.setFont(FONT_SUB);
        sub.setForeground(TEXT_SECONDARY);
        sub.setBounds(8, 146, 124, 16);
        panel.add(sub);

        addHoverEffect(panel);
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                openResult(asset);
            }
        });
        return panel;
    }

    private JPanel browseGrid() {
        JPanel grid = new JPanel(new WrapLayout(FlowLayout.LEFT, 12, 12));
        grid.setBackground(BG);
        grid.setAlignmentX(LEFT_ALIGNMENT);

        for (CardData card : BROWSE_CATEGORIES) {
            grid.add(buildBrowseCard(card));
        }

        return grid;
    }

    private JPanel buildBrowseCard(CardData card) {
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, card.color, getWidth(), getHeight(), card.color.darker());
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

    private JLabel greetingLabel() {
        JLabel lbl = new JLabel("Search");
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    private JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_SECTION);
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    private void addHoverEffect(JPanel panel) {
        panel.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(new Color(0x555555), 1, true));
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBorder(null);
                panel.repaint();
            }
        });
    }

    private void openResult(ILibraryAsset asset) {
        controller.openResult(asset);
    }

    private String truncate(String value, int maxLength) {
        if (value == null) return "";
        if (value.length() <= maxLength) return value;
        return value.substring(0, maxLength) + "...";
    }

    private static String tagText(LibraryAssetType type) {
        return switch (type) {
            case SONG -> "Song";
            case ALBUM -> "Album";
            case ARTIST -> "Artist";
            case PLAYLIST -> "Playlist";
        };
    }

    private static class CardData {
        private final String letter;
        private final String title;
        private final Color color;

        private CardData(String letter, String title, String subtitle, Color color) {
            this.letter = letter;
            this.title = title;
            this.color = color;
        }
    }

    private static class ArtPanel extends JPanel {
        private final Color bg;
        private final String letter;
        private final int radius;

        ArtPanel(Color bg, String letter, int w, int h, int radius) {
            this.bg = bg;
            this.letter = letter;
            this.radius = radius;
            setPreferredSize(new Dimension(w, h));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setFont(FONT_LETTER);
            g2.setColor(new Color(0xFFFFFF, false));
            java.awt.FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(letter)) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(letter, x, y);
            g2.dispose();
        }
    }

    private static class TagLabel extends JLabel {
        TagLabel(String text) {
            super(text);
            setFont(FONT_TAG);
            setForeground(TEXT_PRIMARY);
            setOpaque(false);
            setHorizontalAlignment(CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color bg = "Song".equalsIgnoreCase(getText()) ? TAG_SONG
                    : "Album".equalsIgnoreCase(getText()) ? TAG_ALBUM
                    :  "Artist".equalsIgnoreCase(getText())
                    ? new Color(0x1A263A)
                    : TAG_PLAYLIST;
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class WrapLayout extends FlowLayout {
        WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension preferredLayoutSize(java.awt.Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(java.awt.Container target) {
            return layoutSize(target, false);
        }

        private Dimension layoutSize(java.awt.Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) {
                    targetWidth = Integer.MAX_VALUE;
                }

                int hgap = getHgap();
                int vgap = getVgap();
                java.awt.Insets insets = target.getInsets();
                int maxWidth = targetWidth - insets.left - insets.right - hgap * 2;

                int rowWidth = 0;
                int rowHeight = 0;
                int totalHeight = 0;

                for (int i = 0; i < target.getComponentCount(); i++) {
                    Component c = target.getComponent(i);
                    if (!c.isVisible()) {
                        continue;
                    }
                    Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                    if (rowWidth + d.width > maxWidth && rowWidth > 0) {
                        totalHeight += rowHeight + vgap;
                        rowWidth = 0;
                        rowHeight = 0;
                    }
                    rowWidth += d.width + hgap;
                    rowHeight = Math.max(rowHeight, d.height);
                }

                totalHeight += rowHeight + insets.top + insets.bottom + vgap * 2;
                return new Dimension(targetWidth, totalHeight);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tunix – Search");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 620);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(new Color(0x121212));
            frame.add(new SearchView(List.of()));
            frame.setVisible(true);
        });
    }
}
