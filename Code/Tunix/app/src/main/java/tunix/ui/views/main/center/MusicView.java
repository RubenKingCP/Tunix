package tunix.ui.views.main.center;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import tunix.controller.main.center.MusicController;
import tunix.model.ILibraryAsset;
import tunix.model.musicContent.Playlist;
import tunix.service.auth.SessionService;
// -- STUFF I ADDED --
import tunix.model.musicContent.Song;
import tunix.model.PlaylistItem;
// TEMPORARY FOR TESTING PURPOSES
import tunix.model.account.Artist;

public class MusicView extends JPanel {
    private ILibraryAsset musicAsset;
    private MusicController controller;
    // I put it here because we needed it for the header, too, so multiple functions use it
    private Playlist playlist;

    public MusicView() {
        initGui();
    }

    public void initGui() {
        removeAll();

        var currentUser = SessionService.Instance == null ? null : SessionService.Instance.getUser();
        playlist = new Playlist("Testing", 1, currentUser);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        add(buildHeader());
        add(buildActionsBar());
        add(buildSongTable());
    }

    public void refresh() {
        initGui();
        revalidate();
        repaint();
    }

    // Scroll Bar
    private void styleScrollBar(JScrollPane scrollPane) {
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        bar.setPreferredSize(new Dimension(6, 0));
        bar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(180, 180, 180, 160);
                trackColor = new Color(50, 50, 50, 255);
            }
            @Override protected JButton createDecreaseButton(int o) { return invisibleButton(); }
            @Override protected JButton createIncreaseButton(int o) { return invisibleButton(); }
            private JButton invisibleButton() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(thumbColor);
                g2.fillRoundRect(r.x, r.y, r.width, r.height, 6, 6);
                g2.dispose();
            }
        });
    }


    // Header Stuff -- Playlist Details

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(24, 0)); // 24px horizontal gap
        header.setBackground(Color.DARK_GRAY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(buildPlaylistCoverThumb(), BorderLayout.WEST);
        header.add(buildPlaylistInfo(), BorderLayout.CENTER);

        return header;
    }

    private JLabel buildPlaylistCoverThumb() {
        int SIZE = 180;
        BufferedImage placeholder = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = placeholder.createGraphics();
        g2.setColor(new Color(0x3A3A3A));
        g2.fillRect(0, 0, SIZE, SIZE);
        g2.setColor(new Color(0x606060));
        g2.setFont(new Font("Dialog", Font.PLAIN, 48));
        FontMetrics fm = g2.getFontMetrics();
        String note = "♪";
        g2.drawString(note, (SIZE - fm.stringWidth(note)) / 2, (SIZE + fm.getAscent()) / 2);
        g2.dispose();

        return new JLabel(new ImageIcon(placeholder));
    }

    private JPanel buildPlaylistInfo() {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.DARK_GRAY);

        // -------- IM NOT CHANGING THAT, I DONT KNOW HOW WE'LL KNOW IF IT IS PLAYLIST/ALBUM
        // SO I CANT CREATE AN INSTANCE OF A MODEL... (I SUPPOSE ALBUMS ARE )

        JLabel playlistName = new JLabel(playlist.getTitle());
        playlistName.setForeground(Color.WHITE);
        playlistName.setFont(new Font("Dialog", Font.BOLD, 28));

        String creatorName = playlist.getCreator() == null ? "Guest" : playlist.getCreator().getUsername();
        JLabel artist = new JLabel(creatorName);
        artist.setForeground(Color.LIGHT_GRAY);
        artist.setFont(new Font("Dialog", Font.PLAIN, 14));

        JLabel details = new JLabel(playlist.getSubtitle());
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


    // Actions Bar -- Buttons

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


        //Add event listeners
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
                int x = (getWidth() - fm.stringWidth(getText())) / 2 + 2; // +2 nudges ▶ visually center
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
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton buildActionButton(String label) {
        JButton button = new JButton(label);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(new Color(0xAAAAAA));
        button.setFont(new Font("Dialog", Font.PLAIN, 26)); // larger icons
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
            @Override public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(0xAAAAAA));
            }
        });

        return button;
    }


    // Song Table -- List of Songs for the Playlist
    private JScrollPane buildSongTable() {
        // TEMPORARY SONG FOR TESTING
        playlist.addSong(new Song("Song Name", 1, new Artist(1L, "Artist Name", "artist@gmail.com", null, 1109, false), 321, "path", "path"));
        String[] columns = { "#", "Title", "Artist", "🕐" };
        java.util.List<PlaylistItem> playlistItems = playlist.getPlaylistItems();

        // ------------ CHANGED CODE, ADDED MODEL STUFF ------------------
        Object[][] rows = new Object[playlistItems.size()][4];
        for (PlaylistItem item : playlistItems) {
            int pos = item.getPosition();
            Song sg = item.getSong();
            rows[pos][0] = pos+1;
            rows[pos][1] = sg.getTitle();
            rows[pos][2] = sg.getSubtitle();
            int minutes = sg.getDuration()/60;
            int seconds = sg.getDuration()-(minutes*60);
            rows[pos][3] = minutes + ":" + seconds;
        }

        JTable table = new JTable(rows, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // cells are not editable
            }
        };

        // styling
        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Dialog", Font.PLAIN, 14));
        table.setRowHeight(48);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(0x3A3A3A));
        table.setSelectionForeground(Color.WHITE);

        // header styling
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(Color.DARK_GRAY);
                label.setForeground(Color.LIGHT_GRAY);
                label.setFont(new Font("Dialog", Font.PLAIN, 12));
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0x555555)));
                return label;
            }
        });

        // column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(40);  // #
        table.getColumnModel().getColumn(1).setPreferredWidth(400); // title
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // album
        table.getColumnModel().getColumn(3).setPreferredWidth(60);  // duration

        // center # and duration columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Color.DARK_GRAY);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        styleScrollBar(scrollPane);
        return scrollPane;
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 700);
    }

    // Button Actions
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
}
