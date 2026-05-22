package tunix.view.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import tunix.controller.main.LibraryController;
import tunix.dto.request.PlaylistCreateRequest;
import tunix.model.Album;
import tunix.model.Artist;
import tunix.model.ILibraryAsset;
import tunix.model.Playlist;
import tunix.model.Song;

public class LibraryView extends JPanel {

    private boolean isExpanded = false;
    private boolean isCollapsed = false;
    private String activeFilter = "All";
    private String searchQuery = "";
    private String sortMode = "Recent";

    private boolean isGridView() {
        return isExpanded;
    }

    private JPanel headerPanel;
    private JPanel filterPanel;
    private JPanel contentPanel;
    private JTextField searchField;
    private JButton sortButton;

    private static final double SIDEBAR_PCT = 0.22;
    private static final double COLLAPSED_PCT = 0.05;
    private static final int MIN_SIDEBAR = 200;
    private static final int MIN_COLLAPSED = 50;
    private static final Color BG = new Color(18, 18, 18);

    private LibraryController libraryController;
    private List<ILibraryAsset> libraryAssets = List.of();

    public LibraryView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BG);

        headerPanel = buildHeader();
        filterPanel = buildFilterRow();
        contentPanel = buildContent();

        add(headerPanel);
        add(filterPanel);
        add(contentPanel);

        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.PARENT_CHANGED) != 0) {
                Container parent = getParent();
                if (parent != null) {
                    parent.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent ce) {
                            revalidate();
                        }
                    });
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        int parentWidth = getParentWidth();
        int w = isCollapsed
                ? Math.max(MIN_COLLAPSED, (int) (parentWidth * COLLAPSED_PCT))
                : Math.max(MIN_SIDEBAR, (int) (parentWidth * SIDEBAR_PCT));
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

    private int getParentWidth() {
        Container p = getParent();
        while (p != null) {
            int w = p.getWidth();
            if (w > 0) {
                return w;
            }
            p = p.getParent();
        }
        return 1280;
    }

    public void setLibraryAssets(List<ILibraryAsset> assets) {
        this.libraryAssets = assets == null ? List.of() : List.copyOf(assets);
        refreshContent();
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 8, 16));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

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
        collapseButton.addActionListener(e -> toggleCollapsed());

        leftPanel.add(collapseButton);
        leftPanel.add(titleLabel);

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
        createButton.addActionListener(e -> onCreatePlaylistClicked());

        rightPanel.add(createButton);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildFilterRow() {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(BG);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));

        JPanel filterButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        filterButtons.setBackground(BG);

        String[] filters = {"All", "Playlists", "Artists", "Albums", "Songs"};
        javax.swing.ButtonGroup group = new javax.swing.ButtonGroup();

        for (String filter : filters) {
            JToggleButton btn = new JToggleButton(filter);
            btn.setSelected(filter.equals(activeFilter));
            styleFilterButton(btn, filter.equals(activeFilter));
            btn.addActionListener(e -> {
                activeFilter = filter;
                for (Component c : filterButtons.getComponents()) {
                    if (c instanceof JToggleButton tb) {
                        styleFilterButton(tb, tb.getText().equals(activeFilter));
                    }
                }
                refreshContent();
            });
            group.add(btn);
            filterButtons.add(btn);
        }

        JPanel controlsRow = new JPanel(new BorderLayout(8, 0));
        controlsRow.setBackground(BG);
        controlsRow.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));

        searchField = new JTextField();
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
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search in Your Library")) {
                    searchField.setText("");
                    searchField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Search in Your Library");
                    searchQuery = "";
                    refreshContent();
                }
            }
        });
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearchQuery();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearchQuery();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearchQuery();
            }

            private void updateSearchQuery() {
                String text = searchField.getText();
                searchQuery = text.equals("Search in Your Library") ? "" : text.trim();
                refreshContent();
            }
        });

        sortButton = new JButton("↕ Recent");
        sortButton.setForeground(Color.WHITE);
        sortButton.setBackground(BG);
        sortButton.setBorderPainted(false);
        sortButton.setFocusPainted(false);
        sortButton.setContentAreaFilled(false);
        sortButton.setFont(new Font("Arial", Font.PLAIN, 13));
        sortButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sortButton.addActionListener(e -> showSortMenu(sortButton));

        controlsRow.add(searchField, BorderLayout.CENTER);
        controlsRow.add(sortButton, BorderLayout.EAST);

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
        for (String option : new String[]{"Recent", "Alphabetical A-Z", "Alphabetical Z-A"}) {
            JMenuItem item = styledMenuItem(option);
            item.addActionListener(ev -> {
                sortMode = option;
                anchor.setText("↕ " + option);
                refreshContent();
            });
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
        if (contentPanel == null) {
            return;
        }

        contentPanel.removeAll();

        List<LibraryAssetData> visible = getVisibleAssets();

        if (visible.isEmpty()) {
            JLabel emptyLabel = new JLabel("No library assets match your filters.");
            emptyLabel.setForeground(new Color(180, 180, 180));
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(emptyLabel, BorderLayout.NORTH);
        } else if (isGridView()) {
            int panelWidth = getWidth();
            int cols = Math.max(1, panelWidth > 100 ? panelWidth / 160 : 4);
            contentPanel.setLayout(new java.awt.GridLayout(0, cols, 12, 12));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));
            for (LibraryAssetData item : visible) {
                contentPanel.add(buildGridItem(item));
            }
        } else {
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            for (LibraryAssetData item : visible) {
                contentPanel.add(buildListItem(item));
            }
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private List<LibraryAssetData> getVisibleAssets() {
        List<LibraryAssetData> all = new ArrayList<>();
        for (ILibraryAsset asset : libraryAssets) {
            LibraryAssetData data = toDisplayData(asset);
            if (data == null) {
                continue;
            }
            if (!matchesFilter(data)) {
                continue;
            }
            if (!searchQuery.isBlank() && !data.title().toLowerCase().contains(searchQuery.toLowerCase())) {
                continue;
            }
            all.add(data);
        }

        all.sort(getComparator());
        return all;
    }

    private boolean matchesFilter(LibraryAssetData data) {
        if (activeFilter.equals("All")) {
            return true;
        }
        return data.type().equals(activeFilter.substring(0, activeFilter.length() - 1));
    }

    private Comparator<LibraryAssetData> getComparator() {
        return switch (sortMode) {
            case "Alphabetical A-Z" -> Comparator.comparing(LibraryAssetData::title, String.CASE_INSENSITIVE_ORDER);
            case "Alphabetical Z-A" -> Comparator.comparing(LibraryAssetData::title, String.CASE_INSENSITIVE_ORDER).reversed();
            default -> Comparator.comparingInt(LibraryAssetData::sortPosition);
        };
    }

    private LibraryAssetData toDisplayData(ILibraryAsset asset) {
        if (asset instanceof Song song) {
            return new LibraryAssetData(
                    song.getTitle(),
                    song.getArtist().getTitle(),
                    "Song",
                    computeColor(song.getTitle()),
                    false,
                    0
            );
        }
        if (asset instanceof Album album) {
            return new LibraryAssetData(
                    album.getTitle(),
                    album.getArtist().getTitle(),
                    "Album",
                    computeColor(album.getTitle()),
                    false,
                    1
            );
        }
        if (asset instanceof Playlist playlist) {
            return new LibraryAssetData(
                    playlist.getTitle(),
                    "Playlist • " + playlist.getPlaylistItems().size() + " songs",
                    "Playlist",
                    computeColor(playlist.getTitle()),
                    false,
                    2
            );
        }
        if (asset instanceof Artist artist) {
            return new LibraryAssetData(
                    artist.getTitle(),
                    artist.getFollowersCount() + " followers",
                    "Artist",
                    computeColor(artist.getTitle()),
                    true,
                    3
            );
        }
        return null;
    }

    private Color computeColor(String text) {
        int hash = Math.abs(text.hashCode());
        int r = 40 + (hash % 80);
        int g = 50 + ((hash / 7) % 80);
        int b = 70 + ((hash / 11) % 80);
        return new Color(r, g, b);
    }

    private JPanel buildGridItem(LibraryAssetData item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        panel.setPreferredSize(new Dimension(140, 160));

        int imgSize = 100;
        JLabel img = buildImagePlaceholder(item, imgSize);
        img.setAlignmentX(Component.CENTER_ALIGNMENT);
        img.setMaximumSize(new Dimension(imgSize, imgSize));
        panel.add(img);
        panel.add(Box.createVerticalStrut(8));

        JLabel name = new JLabel(item.title());
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

    private JPanel buildListItem(LibraryAssetData item) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        panel.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel img = buildImagePlaceholder(item, 48);
        panel.add(img, BorderLayout.WEST);

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBackground(BG);

        JLabel name = new JLabel(item.title());
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
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(30, 30, 30));
                text.setBackground(new Color(30, 30, 30));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(BG);
                text.setBackground(BG);
            }
        });

        return panel;
    }

    private JLabel buildImagePlaceholder(LibraryAssetData item, int size) {
        return new JLabel() {
            {
                setPreferredSize(new Dimension(size, size));
                setMinimumSize(new Dimension(size, size));
                setMaximumSize(new Dimension(size, size));
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(item.color());
                if (item.circle()) {
                    g2.fillOval(0, 0, size, size);
                } else {
                    g2.fillRoundRect(0, 0, size, size, 12, 12);
                }

                g2.setColor(new Color(255, 255, 255, 100));
                g2.setFont(new Font("Arial", Font.BOLD, size / 3));
                FontMetrics fm = g2.getFontMetrics();
                String ch = String.valueOf(item.title().charAt(0));
                g2.drawString(ch,
                        (size - fm.stringWidth(ch)) / 2,
                        (size - fm.getHeight()) / 2 + fm.getAscent());
                g2.dispose();
            }
        };
    }

    private JPanel buildCollapsedStrip() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);
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
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(40, 40, 40));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                String plus = "+";
                g2.drawString(plus,
                        (getWidth() - fm.stringWidth(plus)) / 2,
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
        createBtn.addActionListener(e -> onCreatePlaylistClicked());

        panel.add(uncollapseBtn);
        panel.add(Box.createVerticalStrut(16));
        panel.add(createBtn);
        panel.add(Box.createVerticalStrut(20));

        JPanel iconsPanel = new JPanel();
        iconsPanel.setLayout(new BoxLayout(iconsPanel, BoxLayout.Y_AXIS));
        iconsPanel.setBackground(BG);

        for (LibraryAssetData item : getVisibleAssets()) {
            JLabel icon = buildImagePlaceholder(item, 40);
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);
            icon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            icon.setToolTipText(item.title());

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
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        panel.add(scroll);

        return panel;
    }

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

        revalidate();
        repaint();
        Container parent = getParent();
        if (parent != null) {
            parent.revalidate();
            parent.repaint();
        }
    }

    public void onRightClickOnLibrary() {
        showLibraryOptions();
    }

    public void showLibraryOptions() {
    }

    public void onCreatePlaylistClicked() {
        showPlaylistCreationMenu();
    }

    public void showPlaylistCreationMenu() {
    }

    public void onPlaylistCreateConfirmClicked(PlaylistCreateRequest playlistRequest) {
        if (libraryController != null) {
            libraryController.createPlaylist(playlistRequest);
        }
    }

    public void setController(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

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

    private record LibraryAssetData(String title, String subtitle, String type, Color color, boolean circle, int sortPosition) {
    }
}
