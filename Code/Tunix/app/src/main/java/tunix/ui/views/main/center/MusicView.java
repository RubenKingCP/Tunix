package tunix.ui.views.main.center;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.JTableHeader;

import tunix.controller.main.center.MusicController;
import tunix.dto.enums.LibraryAssetType;
import tunix.model.ILibraryAsset;
import tunix.model.PlaylistItem;
import tunix.model.account.Artist;
import tunix.model.musicContent.Playlist;
import tunix.model.musicContent.Song;
import tunix.service.auth.SessionService;
import tunix.ui.views.main.LibraryView;

public class MusicView extends JPanel {
    private ILibraryAsset musicAsset;
    private MusicController controller;
    private Playlist playlist;
    private LibraryView libraryPanel; // for accessing library assets when adding songs to playlists

    public MusicView(MusicController controller, LibraryView libraryPanel) {
        this.controller = controller;
        this.libraryPanel = libraryPanel;
        initGui();
    }
    public void setLibraryPanel(LibraryView libraryPanel) {
        this.libraryPanel = libraryPanel;
    }
    public void initGui() {
        removeAll();

        playlist = buildPlaylistForAsset(musicAsset);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        add(buildHeader());
        add(buildActionsBar());
        add(buildSongTable());
    }

    public void setAsset(ILibraryAsset asset) {
        this.musicAsset = asset;
        this.playlist = buildPlaylistForAsset(asset);
        initGui();
    }

    public void refresh() {
        initGui();
        revalidate();
        repaint();
    }

    private void styleScrollBar(JScrollPane scrollPane) {
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        bar.setPreferredSize(new Dimension(6, 0));
        bar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(180, 180, 180, 160);
                trackColor = new Color(50, 50, 50, 255);
            }

            @Override
            protected JButton createDecreaseButton(int o) {
                return invisibleButton();
            }

            @Override
            protected JButton createIncreaseButton(int o) {
                return invisibleButton();
            }

            private JButton invisibleButton() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, java.awt.Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(thumbColor);
                g2.fillRoundRect(r.x, r.y, r.width, r.height, 6, 6);
                g2.dispose();
            }
        });
    }

    private Playlist buildPlaylistForAsset(ILibraryAsset asset) {
        var currentUser = SessionService.Instance == null ? null : SessionService.Instance.getAccount();
        Playlist builtPlaylist = new Playlist(asset == null ? "Testing" : asset.getTitle(),
                asset == null ? 1 : asset.getId(), currentUser);

        List<Song> songs = asset == null ? List.of() : asset.getDisplaySongs();
        if (songs == null || songs.isEmpty()) {
            builtPlaylist.addSong(createDummySong());
            return builtPlaylist;
        }

        for (Song song : songs) {
            builtPlaylist.addSong(song);
        }

        return builtPlaylist;
    }

    private Song createDummySong() {
        return new Song(
                "Demo Song",
                1L,
                new Artist(1L, "Demo Artist", "demo@example.com", null, 210, false),
                210,
                "path",
                "path");
    }

    private String getArtistName() {
        if (musicAsset != null && musicAsset.getType() == LibraryAssetType.PLAYLIST) {
            return playlist.getCreator() == null ? "Guest" : playlist.getCreator().getUsername();
        }

        if (!playlist.getPlaylistItems().isEmpty()) {
            Song firstSong = playlist.getPlaylistItems().get(0).getSong();
            if (firstSong != null && firstSong.getArtist() != null) {
                return firstSong.getArtist().getTitle();
            }
        }

        var currentUser = SessionService.Instance == null ? null : SessionService.Instance.getAccount();
        return currentUser == null ? "Guest" : currentUser.getUsername();
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(24, 0));
        header.setBackground(Color.DARK_GRAY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(buildPlaylistCoverThumb(), BorderLayout.WEST);
        header.add(buildPlaylistInfo(), BorderLayout.CENTER);

        return header;
    }

    private JLabel buildPlaylistCoverThumb() {
        int size = 180;
        BufferedImage placeholder = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = placeholder.createGraphics();
        g2.setColor(new Color(0x3A3A3A));
        g2.fillRect(0, 0, size, size);
        g2.setColor(new Color(0x606060));
        g2.setFont(new Font("Dialog", Font.PLAIN, 48));
        FontMetrics fm = g2.getFontMetrics();
        String note = "♪";
        g2.drawString(note, (size - fm.stringWidth(note)) / 2, (size + fm.getAscent()) / 2);
        g2.dispose();

        return new JLabel(new ImageIcon(placeholder));
    }

    private JPanel buildPlaylistInfo() {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.DARK_GRAY);

        JLabel playlistName = new JLabel(musicAsset == null ? playlist.getTitle() : musicAsset.getTitle());
        playlistName.setForeground(Color.WHITE);
        playlistName.setFont(new Font("Dialog", Font.BOLD, 28));

        JLabel artist = new JLabel(getArtistName());
        artist.setForeground(Color.LIGHT_GRAY);
        artist.setFont(new Font("Dialog", Font.PLAIN, 14));

        JLabel details = new JLabel(musicAsset == null ? playlist.getSubtitle() : musicAsset.getSubtitle());
        details.setForeground(Color.LIGHT_GRAY);
        details.setFont(new Font("Dialog", Font.PLAIN, 12));

        info.add(Box.createVerticalGlue());
        info.add(playlistName);
        info.add(Box.createVerticalStrut(8));
        info.add(artist);
        info.add(Box.createVerticalStrut(8));
        info.add(details);
        info.add(Box.createVerticalGlue());

        return info;
    }

    private JPanel buildActionsBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        bar.setBackground(Color.DARK_GRAY);
        bar.setAlignmentX(Component.LEFT_ALIGNMENT);
        bar.setBorder(BorderFactory.createEmptyBorder(24, 0, 24, 0));

        JButton playButton = buildCirclePlayButton();
        JButton shuffleButton = buildActionButton("⇄");
        JButton saveButton = buildActionButton("♡");
        JButton downloadButton = buildActionButton("↓");
        JButton addSongButton = buildActionButton("+");
        JButton optionsButton = buildActionButton("⋯");

        playButton.addActionListener(e -> onPlayClicked());
        shuffleButton.addActionListener(e -> onShuffleClicked());
        saveButton.addActionListener(e -> onSaveClicked());
        downloadButton.addActionListener(e -> onDownloadClicked());
        addSongButton.addActionListener(e -> onAddSongClicked());
        optionsButton.addActionListener(e -> onOptionsClicked());

        bar.add(playButton);
        bar.add(shuffleButton);
        bar.add(saveButton);
        bar.add(downloadButton);
        bar.add(addSongButton);
        bar.add(optionsButton);

        return bar;
    }

    private JButton buildCirclePlayButton() {
        JButton button = new JButton("▶") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(0xDDDDDD) : Color.WHITE);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.BLACK);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2 + 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        button.setPreferredSize(new Dimension(52, 52));
        button.setFont(new Font("Dialog", Font.BOLD, 18));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }

    private JButton buildActionButton(String label) {
        JButton button = new JButton(label);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(new Color(0xAAAAAA));
        button.setFont(new Font("Dialog", Font.PLAIN, 26));
        button.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(0xAAAAAA));
            }
        });

        return button;
    }

    private JScrollPane buildSongTable() {
        String[] columns = { "#", "Title", "Artist", "🕐", "", "" };
        List<PlaylistItem> playlistItems = playlist.getPlaylistItems();

        Object[][] rows = new Object[playlistItems.size()][6];
        for (PlaylistItem item : playlistItems) {
            int pos = item.getPosition();
            Song song = item.getSong();
            rows[pos][0] = pos + 1;
            rows[pos][1] = song.getTitle();
            rows[pos][2] = song.getSubtitle();
            int minutes = song.getDuration() / 60;
            int seconds = song.getDuration() - (minutes * 60);
            rows[pos][3] = minutes + ":" + seconds;
            rows[pos][4] = "↓"; // download placeholder
            rows[pos][5] = "⋯"; // add to playlist
        }

        JTable table = new JTable(rows, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Dialog", Font.PLAIN, 14));
        table.setRowHeight(48);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(0x3A3A3A));
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                label.setBackground(Color.DARK_GRAY);
                label.setForeground(Color.LIGHT_GRAY);
                label.setFont(new Font("Dialog", Font.PLAIN, 12));
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0x555555)));
                return label;
            }
        });

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(400);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(40);
        table.getColumnModel().getColumn(5).setPreferredWidth(40);

        // render action columns centered and styled
        javax.swing.table.DefaultTableCellRenderer actionRenderer = new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBackground(Color.DARK_GRAY);
                label.setForeground(new Color(0xAAAAAA));
                label.setFont(new Font("Dialog", Font.PLAIN, 18));
                return label;
            }
        };

        table.getColumnModel().getColumn(4).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(actionRenderer);

        // handle clicks on action cells
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row < 0 || col < 0) return;

                PlaylistItem item = playlistItems.get(row);
                Song song = item.getSong();

                if (col == 4) {
                    // download column — intentionally no logic for now
                    // reserved for future download handling
                    return;
                }

                if (col == 5) {
                    // show popup with available playlists
                    JPopupMenu menu = new JPopupMenu();
                    java.util.List<String> names = getAvailablePlaylistNames();
                    for (String name : names) {
                        JMenuItem mi = new JMenuItem(name);
                        mi.addActionListener(ev -> {
                            JOptionPane.showMessageDialog(MusicView.this,
                                    "Added \"" + song.getTitle() + "\" to \"" + name + "\"");
                        });
                        menu.add(mi);
                    }

                    menu.show(table, e.getX(), e.getY());
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Color.DARK_GRAY);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        styleScrollBar(scrollPane);
        return scrollPane;
    }

    protected List<String> getAvailablePlaylistNames() {
        List<ILibraryAsset> assets = libraryPanel.getCurrentLibraryAssets();
        if (assets == null) return List.of();
        List<String> names = new ArrayList<>();
        for (ILibraryAsset asset : assets) {
            if (asset instanceof Playlist) {
                names.add(((Playlist) asset).getName());
            }
        }
        return names;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 700);
    }

    public void onPlayClicked() {
    }

    public void onShuffleClicked() {
    }

    public void onSaveClicked() {
    }

    public void onDownloadClicked() {
    }

    public void onAddSongClicked() {
    }

    public void onOptionsClicked() {
    }

    public void getPlaylist() {
    }

    public void setController(MusicController controller) {
        this.controller = controller;
    }
    public void setSong(Song song) {
        if (song == null) return;

        this.musicAsset = song;

        Playlist singleSongPlaylist = new Playlist(
                song.getTitle(),
                song.getId(),
                song.getArtist()
        );

        singleSongPlaylist.addSong(song);

        this.playlist = singleSongPlaylist;

        initGui();
        revalidate();
        repaint();
    }
}
