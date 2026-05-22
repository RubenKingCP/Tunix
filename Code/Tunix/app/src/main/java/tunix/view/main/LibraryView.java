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
import javax.swing.ButtonGroup;
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
import tunix.model.ILibraryAsset;

public class LibraryView extends JPanel {

    private boolean isExpanded = false;
    private boolean isCollapsed = false;
    private String activeFilter = "All";
    private String searchQuery = "";
    private String sortMode = "Recent";

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
        Container parent = getParent();
        while (parent != null) {
            int width = parent.getWidth();
            if (width > 0) {
                return width;
            }
            parent = parent.getParent();
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
        ButtonGroup group = new ButtonGroup();

        for (String filter : filters) {
            JToggleButton btn = new JToggleButton(filter);
            btn.setSelected(filter.equals(activeFilter));
            styleFilterButton(btn, filter.equals(activeFilter));
            btn.addActionListener(e -> {
                activeFilter = filter;
                for (Component component : filterButtons.getComponents()) {
                    if (component instanceof JToggleButton toggleButton) {
                        styleFilterButton(toggleButton, toggleButton.getText().equals(activeFilter));
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

    private void styleFilterButton(JToggleButton button, boolean selected) {
        button.setForeground(selected ? Color.BLACK : Color.WHITE);
        button.setBackground(selected ? Color.WHITE : new Color(40, 40, 40));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
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
            item.addActionListener(event -> {
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

        List<ILibraryAsset> visible = getVisibleAssets();

        if (visible.isEmpty()) {
            JLabel emptyLabel = new JLabel("No library assets match your filters.");
            emptyLabel.setForeground(new Color(180, 180, 180));
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(emptyLabel, BorderLayout.NORTH);
        } else if (isGridView()) {
            int panelWidth = getWidth();
            int columns = Math.max(1, panelWidth > 100 ? panelWidth / 160 : 4);
            contentPanel.setLayout(new java.awt.GridLayout(0, columns, 12, 12));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));
            for (ILibraryAsset asset : visible) {
                contentPanel.add(buildGridItem(asset));
            }
        } else {
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            for (ILibraryAsset asset : visible) {
                contentPanel.add(buildListItem(asset));
            }
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private List<ILibraryAsset> getVisibleAssets() {
        List<ILibraryAsset> visible = new ArrayList<>(libraryAssets);

        visible.removeIf(asset -> !matchesFilter(asset));
        if (!searchQuery.isBlank()) {
            String query = searchQuery.toLowerCase();
            visible.removeIf(asset -> !asset.getTitle().toLowerCase().contains(query));
        }

        visible.sort(getComparator());
        return visible;
    }

    private boolean matchesFilter(ILibraryAsset asset) {
        if (activeFilter.equals("All")) {
            return true;
        }
        return asset.getType().equals(activeFilter.substring(0, activeFilter.length() - 1));
    }

    private Comparator<ILibraryAsset> getComparator() {
        return switch (sortMode) {
            case "Alphabetical A-Z" -> Comparator.comparing(ILibraryAsset::getTitle, String.CASE_INSENSITIVE_ORDER);
            case "Alphabetical Z-A" -> Comparator.comparing(ILibraryAsset::getTitle, String.CASE_INSENSITIVE_ORDER).reversed();
            default -> Comparator.comparingInt(ILibraryAsset::getId).reversed();
        };
    }

    private boolean isGridView() {
        return isExpanded;
    }

    private JPanel buildGridItem(ILibraryAsset asset) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        panel.setPreferredSize(new Dimension(140, 160));

        JLabel image = buildImagePlaceholder(asset, 100);
        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        image.setMaximumSize(new Dimension(100, 100));

        JLabel title = new JLabel(asset.getTitle());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 12));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(asset.getSubtitle());
        subtitle.setForeground(new Color(160, 160, 160));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(image);
        panel.add(Box.createVerticalStrut(8));
        panel.add(title);
        panel.add(Box.createVerticalStrut(2));
        panel.add(subtitle);
        panel.add(Box.createVerticalGlue());

        attachClickHandler(panel, asset);
        return panel;
    }

    private JPanel buildListItem(ILibraryAsset asset) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        panel.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel image = buildImagePlaceholder(asset, 48);
        panel.add(image, BorderLayout.WEST);

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBackground(BG);

        JLabel title = new JLabel(asset.getTitle());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel subtitle = new JLabel(asset.getSubtitle());
        subtitle.setForeground(new Color(160, 160, 160));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));

        text.add(Box.createVerticalGlue());
        text.add(title);
        text.add(subtitle);
        text.add(Box.createVerticalGlue());

        panel.add(text, BorderLayout.CENTER);
        panel.setToolTipText(asset.getType() + ": " + asset.getTitle());
        attachClickHandler(panel, asset);

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

    private void attachClickHandler(JPanel panel, ILibraryAsset asset) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleAssetClick(asset);
            }
        });
    }

    private void handleAssetClick(ILibraryAsset asset) {
        asset.onClick();
    }

    private JLabel buildImagePlaceholder(ILibraryAsset asset, int size) {
        return new JLabel() {
            {
                setPreferredSize(new Dimension(size, size));
                setMinimumSize(new Dimension(size, size));
                setMaximumSize(new Dimension(size, size));
            }

            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D g2 = (Graphics2D) graphics.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(asset.getDisplayColor());
                if (asset.isCircularAvatar()) {
                    g2.fillOval(0, 0, size, size);
                } else {
                    g2.fillRoundRect(0, 0, size, size, 12, 12);
                }

                g2.setColor(new Color(255, 255, 255, 100));
                g2.setFont(new Font("Arial", Font.BOLD, size / 3));
                FontMetrics metrics = g2.getFontMetrics();
                String initial = String.valueOf(asset.getTitle().charAt(0));
                g2.drawString(initial,
                        (size - metrics.stringWidth(initial)) / 2,
                        (size - metrics.getHeight()) / 2 + metrics.getAscent());
                g2.dispose();
            }
        };
    }

    private JPanel buildCollapsedStrip() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        JButton uncollapseButton = new JButton(">>");
        uncollapseButton.setForeground(Color.WHITE);
        uncollapseButton.setBackground(BG);
        uncollapseButton.setBorderPainted(false);
        uncollapseButton.setFocusPainted(false);
        uncollapseButton.setContentAreaFilled(false);
        uncollapseButton.setFont(new Font("Arial", Font.PLAIN, 14));
        uncollapseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        uncollapseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uncollapseButton.addActionListener(e -> toggleCollapsed());

        JButton createButton = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D g2 = (Graphics2D) graphics.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(40, 40, 40));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 18));
                FontMetrics metrics = g2.getFontMetrics();
                String plus = "+";
                g2.drawString(plus,
                        (getWidth() - metrics.stringWidth(plus)) / 2,
                        (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent());
                g2.dispose();
            }
        };
        createButton.setPreferredSize(new Dimension(36, 36));
        createButton.setMinimumSize(new Dimension(36, 36));
        createButton.setMaximumSize(new Dimension(36, 36));
        createButton.setContentAreaFilled(false);
        createButton.setBorderPainted(false);
        createButton.setFocusPainted(false);
        createButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.addActionListener(e -> onCreatePlaylistClicked());

        panel.add(uncollapseButton);
        panel.add(Box.createVerticalStrut(16));
        panel.add(createButton);
        panel.add(Box.createVerticalStrut(20));

        JPanel iconsPanel = new JPanel();
        iconsPanel.setLayout(new BoxLayout(iconsPanel, BoxLayout.Y_AXIS));
        iconsPanel.setBackground(BG);

        for (ILibraryAsset asset : getVisibleAssets()) {
            JLabel icon = buildImagePlaceholder(asset, 40);
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);
            icon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            icon.setToolTipText(asset.getTitle());

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
}
