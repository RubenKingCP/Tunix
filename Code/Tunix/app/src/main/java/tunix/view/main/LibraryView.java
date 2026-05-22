package tunix.view.main;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

import tunix.controller.main.LibraryController;
import tunix.dto.request.PlaylistCreateRequest;
import tunix.view.library.LibraryAssetView;

public class LibraryView extends JPanel {

    // --- State ---
    private boolean isExpanded  = false;
    private boolean isCollapsed = false;
    private String  activeFilter = "All";
    private boolean isGridView() { return isExpanded; }

    // --- Panels (kept as fields so toggles can re-add them) ---
    private JPanel headerPanel;
    private JPanel filterPanel;
    private JPanel contentPanel;

    // --- Constants ---
    // Percentage of the parent container's width (0.0 – 1.0)
    private static final double SIDEBAR_PCT   = 0.22;   // ~22 % of the window
    private static final double COLLAPSED_PCT = 0.05;   // ~5  % of the window
    private static final int    MIN_SIDEBAR   = 200;    // never narrower than 200 px
    private static final int    MIN_COLLAPSED = 50;     // never narrower than 50  px
    private static final Color  BG = new Color(18, 18, 18);

    // Controllers and stuff
    private LibraryController libraryController;
    private List<LibraryAssetView> libraryAssetViews;


    // =========================================================
    //  Constructor
    // =========================================================
    public LibraryView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BG);
        // Don't set a hard-coded preferred size here — getPreferredSize() handles it.

        headerPanel  = buildHeader();
        filterPanel  = buildFilterRow();
        contentPanel = buildContent();

        add(headerPanel);
        add(filterPanel);
        add(contentPanel);

        // Re-evaluate preferred width whenever our parent is resized
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.PARENT_CHANGED) != 0) {
                Container parent = getParent();
                if (parent != null) {
                    parent.addComponentListener(new ComponentAdapter() {
                        @Override public void componentResized(ComponentEvent ce) {
                            revalidate();
                        }
                    });
                }
            }
        });
    }

    // =========================================================
    //  Percentage-based preferred width
    // =========================================================
    @Override
    public Dimension getPreferredSize() {
        int parentWidth = getParentWidth();
        int w = isCollapsed
                ? Math.max(MIN_COLLAPSED, (int)(parentWidth * COLLAPSED_PCT))
                : Math.max(MIN_SIDEBAR,   (int)(parentWidth * SIDEBAR_PCT));
        // Height: let the layout manager decide (return 0 so BorderLayout fills it)
        return new Dimension(w, 0);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(isCollapsed ? MIN_COLLAPSED : MIN_SIDEBAR, 0);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(getPreferredSize().width, Integer.MAX_VALUE);
    }

    /** Returns the width of the nearest non-null parent, or a sensible fallback. */
    private int getParentWidth() {
        Container p = getParent();
        while (p != null) {
            int w = p.getWidth();
            if (w > 0) return w;
            p = p.getParent();
        }
        return 1280; // design-time fallback
    }

    // =========================================================
    //  HEADER
    // =========================================================
    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 8, 16));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Left: collapse button + title ---
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftPanel.setBackground(BG);

        JLabel titleLabel = new JLabel("Library");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton collapseButton = new JButton("<<");
        collapseButton.setForeground(Color.WHITE);
        collapseButton.setBackground(BG);
        collapseButton.setBorderPainted(false);
        collapseButton.setFocusPainted(false);
        collapseButton.setContentAreaFilled(false);
        collapseButton.setOpaque(false);
        collapseButton.setFont(new Font("Arial", Font.PLAIN, 12));
        collapseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        collapseButton.setVisible(true);
        collapseButton.addActionListener(e -> toggleCollapsed());

        leftPanel.add(collapseButton);
        leftPanel.add(titleLabel);

        // --- Right: "+" create button ---
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        rightPanel.setBackground(BG);

        JButton createButton = new JButton("+");
        createButton.setForeground(Color.WHITE);
        createButton.setBackground(new Color(40, 40, 40));
        createButton.setFocusPainted(false);
        createButton.setContentAreaFilled(false);
        createButton.setFont(new Font("Arial", Font.BOLD, 18));
        createButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createButton.setPreferredSize(new Dimension(32, 32));
        createButton.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true));
        createButton.setOpaque(false);

        rightPanel.add(createButton);

        panel.add(leftPanel,  BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        return panel;
    }

    // =========================================================
    //  FILTER ROW
    // =========================================================
    private JPanel buildFilterRow() {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(BG);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));

        // Row 1 — filter pills
        JPanel filterButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        filterButtons.setBackground(BG);

        String[] filters = {"All", "Playlists", "Artists", "Albums", "Songs"};
        ButtonGroup group = new ButtonGroup();

        for (String filter : filters) {
            JToggleButton btn = new JToggleButton(filter);
            btn.setSelected(filter.equals(activeFilter));
            styleFilterButton(btn, filter.equals(activeFilter));
            btn.addActionListener(e -> {
                activeFilter = filter;
                for (Component c : filterButtons.getComponents()) {
                    if (c instanceof JToggleButton tb)
                        styleFilterButton(tb, tb.getText().equals(activeFilter));
                }
                refreshContent();
            });
            group.add(btn);
            filterButtons.add(btn);
        }

        // Row 2 — search + sort
        JPanel controlsRow = new JPanel(new BorderLayout(8, 0));
        controlsRow.setBackground(BG);
        controlsRow.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));

        JTextField searchField = new JTextField();
        searchField.setBackground(new Color(40, 40, 40));
        searchField.setForeground(Color.GRAY);
        searchField.setCaretColor(Color.WHITE);
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchField.setText("Search in Your Library");
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search in Your Library")) {
                    searchField.setText("");
                    searchField.setForeground(Color.WHITE);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Search in Your Library");
                }
            }
        });

        JButton sortButton = new JButton("↕ Recent");
        sortButton.setForeground(Color.WHITE);
        sortButton.setBackground(BG);
        sortButton.setBorderPainted(false);
        sortButton.setFocusPainted(false);
        sortButton.setContentAreaFilled(false);
        sortButton.setFont(new Font("Arial", Font.PLAIN, 13));
        sortButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sortButton.addActionListener(e -> showSortMenu(sortButton));

        controlsRow.add(searchField, BorderLayout.CENTER);
        controlsRow.add(sortButton,  BorderLayout.EAST);

        wrapper.add(filterButtons);
        wrapper.add(controlsRow);
        return wrapper;
    }

    private void styleFilterButton(JToggleButton btn, boolean selected) {
        btn.setForeground(selected ? Color.BLACK : Color.WHITE);
        btn.setBackground(selected ? Color.WHITE : new Color(40, 40, 40));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60), 1, true),
            BorderFactory.createEmptyBorder(5, 14, 5, 14)
        ));
    }

    private void showSortMenu(JButton anchor) {
        JPopupMenu menu = new JPopupMenu();
        menu.setBackground(new Color(40, 40, 40));
        menu.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));

        addMenuSectionLabel(menu, "Sort by");
        for (String opt : new String[]{"Recently Added", "Alphabetical (A–Z)", "Alphabetical (Z–A)", "Creator"}) {
            JMenuItem item = styledMenuItem(opt);
            item.addActionListener(ev -> anchor.setText("↕ " + opt.split(" ")[0]));
            menu.add(item);
        }
        menu.show(anchor, 0, anchor.getHeight());
    }

    private void addMenuSectionLabel(JPopupMenu menu, String text) {
        JLabel label = new JLabel("  " + text);
        label.setForeground(new Color(180, 180, 180));
        label.setFont(new Font("Arial", Font.BOLD, 11));
        label.setBorder(BorderFactory.createEmptyBorder(6, 4, 4, 4));
        menu.add(label);
    }

    private JMenuItem styledMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setBackground(new Color(40, 40, 40));
        item.setForeground(Color.WHITE);
        item.setFont(new Font("Arial", Font.PLAIN, 13));
        return item;
    }

    // =========================================================
    //  CONTENT
    // =========================================================
    private JPanel buildContent() {
        contentPanel = new JPanel();
        contentPanel.setBackground(BG);

        refreshContent();

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);
        scroll.setBackground(BG);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    private void refreshContent() {
        if (contentPanel == null) return;
        contentPanel.removeAll();

        List<LibraryItem> items = getDummyItems().stream()
            .filter(item -> activeFilter.equals("All") ||
                item.type().equals(activeFilter.substring(0, activeFilter.length() - 1)))
            .toList();

        if (isGridView()) {
            int panelWidth = getWidth();
            int cols = panelWidth > 100 ? panelWidth / 160 : 4;
            contentPanel.setLayout(new GridLayout(0, cols, 12, 12));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));
            contentPanel.setAlignmentY(Component.TOP_ALIGNMENT);
            for (LibraryItem item : items) contentPanel.add(buildGridItem(item));
        } else {
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            for (LibraryItem item : items) contentPanel.add(buildListItem(item));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel buildGridItem(LibraryItem item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        panel.setPreferredSize(new Dimension(140, 160));

        int imgSize = 100;
        JLabel img = buildImagePlaceholder(item, imgSize, item.type().equals("Artist"));
        img.setAlignmentX(Component.CENTER_ALIGNMENT);
        img.setMaximumSize(new Dimension(imgSize, imgSize));
        panel.add(img);
        panel.add(Box.createVerticalStrut(8));

        JLabel name = new JLabel(item.name());
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SansSerif", Font.BOLD, 12));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel(item.subtitle());
        sub.setForeground(new Color(160, 160, 160));
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(name);
        panel.add(Box.createVerticalStrut(2));
        panel.add(sub);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel buildListItem(LibraryItem item) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        panel.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel img = buildImagePlaceholder(item, 48, item.type().equals("Artist"));
        panel.add(img, BorderLayout.WEST);

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBackground(BG);

        JLabel name = new JLabel(item.name());
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel sub = new JLabel(item.subtitle());
        sub.setForeground(new Color(160, 160, 160));
        sub.setFont(new Font("Arial", Font.PLAIN, 12));

        text.add(Box.createVerticalGlue());
        text.add(name);
        text.add(sub);
        text.add(Box.createVerticalGlue());
        panel.add(text, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(30, 30, 30));
                text.setBackground(new Color(30, 30, 30));
            }
            @Override public void mouseExited(MouseEvent e) {
                panel.setBackground(BG);
                text.setBackground(BG);
            }
        });

        return panel;
    }

    private JLabel buildImagePlaceholder(LibraryItem item, int size, boolean circle) {
        return new JLabel() {
            { setPreferredSize(new Dimension(size, size));
              setMinimumSize(new Dimension(size, size));
              setMaximumSize(new Dimension(size, size)); }

            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(item.color());
                if (circle) g2.fillOval(0, 0, size, size);
                else        g2.fillRoundRect(0, 0, size, size, 12, 12);

                g2.setColor(new Color(255, 255, 255, 100));
                g2.setFont(new Font("Arial", Font.BOLD, size / 3));
                FontMetrics fm = g2.getFontMetrics();
                String ch = String.valueOf(item.name().charAt(0));
                g2.drawString(ch,
                    (size - fm.stringWidth(ch)) / 2,
                    (size - fm.getHeight()) / 2 + fm.getAscent());
                g2.dispose();
            }
        };
    }

    // =========================================================
    //  COLLAPSED STRIP
    // =========================================================
    private JPanel buildCollapsedStrip() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);
        // Width comes from getPreferredSize() — no hard-coded size here
        panel.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        JButton uncollapseBtn = new JButton(">>");
        uncollapseBtn.setForeground(Color.WHITE);
        uncollapseBtn.setBackground(BG);
        uncollapseBtn.setBorderPainted(false);
        uncollapseBtn.setFocusPainted(false);
        uncollapseBtn.setContentAreaFilled(false);
        uncollapseBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        uncollapseBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        uncollapseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        uncollapseBtn.addActionListener(e -> toggleCollapsed());

        JButton createBtn = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(40, 40, 40));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                String plus = "+";
                g2.drawString(plus,
                    (getWidth()  - fm.stringWidth(plus)) / 2,
                    (getHeight() - fm.getHeight()) / 2 + fm.getAscent());
                g2.dispose();
            }
        };
        createBtn.setPreferredSize(new Dimension(36, 36));
        createBtn.setMinimumSize(new Dimension(36, 36));
        createBtn.setMaximumSize(new Dimension(36, 36));
        createBtn.setContentAreaFilled(false);
        createBtn.setBorderPainted(false);
        createBtn.setFocusPainted(false);
        createBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(uncollapseBtn);
        panel.add(Box.createVerticalStrut(16));
        panel.add(createBtn);
        panel.add(Box.createVerticalStrut(20));

        JPanel iconsPanel = new JPanel();
        iconsPanel.setLayout(new BoxLayout(iconsPanel, BoxLayout.Y_AXIS));
        iconsPanel.setBackground(BG);

        for (LibraryItem item : getDummyItems()) {
            if (!activeFilter.equals("All") &&
                !item.type().equals(activeFilter.substring(0, activeFilter.length() - 1))) continue;

            JLabel icon = buildImagePlaceholder(item, 40, item.type().equals("Artist"));
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);
            icon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            icon.setToolTipText(item.name());

            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setBackground(BG);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
            row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
            row.add(Box.createHorizontalGlue());
            row.add(icon);
            row.add(Box.createHorizontalGlue());
            iconsPanel.add(row);
        }

        JScrollPane scroll = new JScrollPane(iconsPanel);
        scroll.setBorder(null);
        scroll.setBackground(BG);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        panel.add(scroll);

        return panel;
    }

    // =========================================================
    //  TOGGLES
    // =========================================================
    private void toggleCollapsed() {
        isCollapsed = !isCollapsed;
        removeAll();

        if (isCollapsed) {
            add(buildCollapsedStrip());
        } else {
            add(headerPanel);
            add(filterPanel);
            add(contentPanel);
        }

        // Invalidate size caches and ask the parent BorderLayout to redistribute space
        revalidate();
        repaint();
        Container parent = getParent();
        if (parent != null) {
            parent.revalidate();
            parent.repaint();
        }
    }

    // =========================================================
    //  DATA
    // =========================================================
    record LibraryItem(String name, String type, String subtitle, Color color) {}

    private List<LibraryItem> getDummyItems() {
        return List.of(
            new LibraryItem("Chill Vibes",     "Playlist", "Playlist • You",           new Color(30, 215, 96)),
            new LibraryItem("Morning Hits",    "Playlist", "Playlist • You",           new Color(80, 120, 200)),
            new LibraryItem("Tame Impala",     "Artist",   "Artist",                   new Color(180, 80, 80)),
            new LibraryItem("Arctic Monkeys",  "Artist",   "Artist",                   new Color(200, 140, 40)),
            new LibraryItem("AM",              "Album",    "Album • Arctic Monkeys",   new Color(60, 60, 60)),
            new LibraryItem("Currents",        "Album",    "Album • Tame Impala",      new Color(100, 60, 160)),
            new LibraryItem("Do I Wanna Know?","Song",     "Song • Arctic Monkeys",    new Color(50, 50, 80)),
            new LibraryItem("Let It Happen",   "Song",     "Song • Tame Impala",       new Color(80, 40, 120))
        );
    }

    public void onRightClickOnLibrary() {
        this.showLibraryOptions();
    }

    public void showLibraryOptions() {}

    public void onCreatePlaylistClicked() {
        this.showPlaylistCreationMenu();
    }

    public void showPlaylistCreationMenu() {}

    public void onPlaylistCreateConfirmClicked(PlaylistCreateRequest playlistRequest) {
        libraryController.createPlaylist(playlistRequest);
    }

    public void setController(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

    // =========================================================
    //  MAIN (for testing standalone)
    // =========================================================
    public void display() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tunix – Library");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            LibraryView library = new LibraryView();
            frame.add(library, BorderLayout.WEST);

            JPanel center = new JPanel();
            center.setBackground(new Color(12, 12, 12));
            frame.add(center, BorderLayout.CENTER);

            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}