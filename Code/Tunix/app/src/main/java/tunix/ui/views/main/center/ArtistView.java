package tunix.ui.views.main.center;

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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import tunix.model.musicContent.Album;
import tunix.model.musicContent.Song;
import tunix.model.account.Artist;

public class ArtistView extends JPanel {

    private Artist artist;

    private List<Song> topSongs = new ArrayList<>();

    private List<Album> albums = new ArrayList<>();

    public ArtistView() {

        initGui();
    }

    public void setArtistData(
            Artist artist,
            List<Song> topSongs,
            List<Album> albums
    ) {

        this.artist = artist;

        this.topSongs =
                topSongs == null
                        ? new ArrayList<>()
                        : topSongs;

        this.albums =
                albums == null
                        ? new ArrayList<>()
                        : albums;

        initGui();

        revalidate();

        repaint();
    }

    private void initGui() {

        removeAll();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(Color.DARK_GRAY);

        setBorder(
                BorderFactory.createEmptyBorder(
                        24,
                        24,
                        24,
                        24
                )
        );

        add(buildHeader());

        add(Box.createVerticalStrut(24));

        add(buildActionsBar());

        add(Box.createVerticalStrut(24));

        add(buildPopularSongsSection());

        add(Box.createVerticalStrut(32));

        add(buildAlbumsSection());
    }

    private JPanel buildHeader() {

        JPanel header =
                new JPanel(new BorderLayout(24, 0));

        header.setBackground(Color.DARK_GRAY);

        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(
                buildArtistAvatar(),
                BorderLayout.WEST
        );

        header.add(
                buildArtistInfo(),
                BorderLayout.CENTER
        );

        return header;
    }

    private JLabel buildArtistAvatar() {

        int size = 180;

        BufferedImage image =
                new BufferedImage(
                        size,
                        size,
                        BufferedImage.TYPE_INT_RGB
                );

        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(50, 50, 50));

        g2.fillOval(0, 0, size, size);

        g2.setColor(Color.WHITE);

        g2.setFont(
                new Font(
                        "Dialog",
                        Font.BOLD,
                        64
                )
        );

        String initial =
                artist == null
                        ? "A"
                        : artist.getTitle()
                                .substring(0, 1)
                                .toUpperCase();

        FontMetrics metrics =
                g2.getFontMetrics();

        g2.drawString(
                initial,
                (size - metrics.stringWidth(initial)) / 2,
                (size + metrics.getAscent()) / 2
        );

        g2.dispose();

        return new JLabel(new ImageIcon(image));
    }

    private JPanel buildArtistInfo() {

        JPanel info = new JPanel();

        info.setLayout(
                new BoxLayout(
                        info,
                        BoxLayout.Y_AXIS
                )
        );

        info.setBackground(Color.DARK_GRAY);

        JLabel verified =
                new JLabel(
                        artist != null && artist.isVerified()
                                ? "✓ Verified Artist"
                                : "Artist"
                );

        verified.setForeground(
                new Color(120, 180, 255)
        );

        verified.setFont(
                new Font(
                        "Dialog",
                        Font.BOLD,
                        13
                )
        );

        JLabel artistName =
                new JLabel(
                        artist == null
                                ? "Unknown Artist"
                                : artist.getTitle()
                );

        artistName.setForeground(Color.WHITE);

        artistName.setFont(
                new Font(
                        "Dialog",
                        Font.BOLD,
                        42
                )
        );

        JLabel followers =
                new JLabel(
                        artist == null
                                ? "0 followers"
                                : artist.getFollowersCount()
                                        + " followers"
                );

        followers.setForeground(Color.LIGHT_GRAY);

        followers.setFont(
                new Font(
                        "Dialog",
                        Font.PLAIN,
                        14
                )
        );

        JLabel biography =
                new JLabel(
                        artist == null
                                ? ""
                                : "<html><div style='width:500px;'>"
                                        + artist.getBiography()
                                        + "</div></html>"
                );

        biography.setForeground(Color.LIGHT_GRAY);

        biography.setFont(
                new Font(
                        "Dialog",
                        Font.PLAIN,
                        13
                )
        );

        info.add(Box.createVerticalGlue());

        info.add(verified);

        info.add(Box.createVerticalStrut(10));

        info.add(artistName);

        info.add(Box.createVerticalStrut(10));

        info.add(followers);

        info.add(Box.createVerticalStrut(16));

        info.add(biography);

        info.add(Box.createVerticalGlue());

        return info;
    }

    private JButton buildCirclePlayButton() {

        JButton button =
                new JButton("▶") {

                    @Override
                    protected void paintComponent(
                            Graphics g
                    ) {

                        Graphics2D g2 =
                                (Graphics2D) g.create();

                        g2.setRenderingHint(
                                RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON
                        );

                        g2.setColor(
                                getModel().isRollover()
                                        ? new Color(0xDDDDDD)
                                        : Color.WHITE
                        );

                        g2.fillOval(
                                0,
                                0,
                                getWidth(),
                                getHeight()
                        );

                        g2.setColor(Color.BLACK);

                        g2.setFont(getFont());

                        FontMetrics fm =
                                g2.getFontMetrics();

                        int x =
                                (getWidth()
                                        - fm.stringWidth(
                                                getText()
                                        )) / 2 + 2;

                        int y =
                                (getHeight()
                                        + fm.getAscent()
                                        - fm.getDescent()) / 2;

                        g2.drawString(
                                getText(),
                                x,
                                y
                        );

                        g2.dispose();
                    }
                };

        button.setPreferredSize(
                new Dimension(52, 52)
        );

        button.setFont(
                new Font(
                        "Dialog",
                        Font.BOLD,
                        18
                )
        );

        button.setContentAreaFilled(false);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private JButton buildActionButton(
            String label
    ) {

        JButton button =
                new JButton(label);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setContentAreaFilled(false);

        button.setForeground(
                new Color(0xAAAAAA)
        );

        button.setFont(
                new Font(
                        "Dialog",
                        Font.BOLD,
                        18
                )
        );

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private JPanel buildPopularSongsSection() {

        JPanel section = new JPanel();

        section.setLayout(
                new BoxLayout(
                        section,
                        BoxLayout.Y_AXIS
                )
        );

        section.setBackground(Color.DARK_GRAY);

        section.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel title =
                new JLabel("Popular");

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Dialog",
                        Font.BOLD,
                        24
                )
        );

        section.add(title);

        section.add(Box.createVerticalStrut(16));

        section.add(buildSongsTable());

        return section;
    }

    private JScrollPane buildSongsTable() {

        String[] columns =
                { "#", "Title", "Duration" };

        Object[][] rows =
                new Object[topSongs.size()][3];

        for (int i = 0; i < topSongs.size(); i++) {

            Song song = topSongs.get(i);

            rows[i][0] = i + 1;

            rows[i][1] = song.getTitle();

            int minutes =
                    song.getDuration() / 60;

            int seconds =
                    song.getDuration() % 60;

            rows[i][2] =
                    minutes
                            + ":"
                            + String.format(
                                    "%02d",
                                    seconds
                            );
        }

        JTable table =
                new JTable(rows, columns) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        table.setBackground(Color.DARK_GRAY);

        table.setForeground(Color.WHITE);

        table.setFont(
                new Font(
                        "Dialog",
                        Font.PLAIN,
                        14
                )
        );

        table.setRowHeight(46);

        table.setShowGrid(false);

        table.setIntercellSpacing(
                new Dimension(0, 0)
        );

        table.setSelectionBackground(
                new Color(0x3A3A3A)
        );

        JTableHeader header =
                table.getTableHeader();

        header.setDefaultRenderer(
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column
                    ) {

                        JLabel label =
                                (JLabel)
                                        super.getTableCellRendererComponent(
                                                table,
                                                value,
                                                isSelected,
                                                hasFocus,
                                                row,
                                                column
                                        );

                        label.setBackground(
                                Color.DARK_GRAY
                        );

                        label.setForeground(
                                Color.LIGHT_GRAY
                        );

                        label.setFont(
                                new Font(
                                        "Dialog",
                                        Font.PLAIN,
                                        12
                                )
                        );

                        return label;
                    }
                }
        );

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        table.getColumnModel()
                .getColumn(0)
                .setCellRenderer(center);

        table.getColumnModel()
                .getColumn(2)
                .setCellRenderer(center);

        JScrollPane scroll =
                new JScrollPane(table);

        scroll.setBorder(null);

        scroll.getViewport()
                .setBackground(Color.DARK_GRAY);

        scroll.setBackground(Color.DARK_GRAY);

        styleScrollBar(scroll);

        return scroll;
    }

    private JPanel buildAlbumsSection() {

        JPanel section = new JPanel();

        section.setLayout(
                new BoxLayout(
                        section,
                        BoxLayout.Y_AXIS
                )
        );

        section.setBackground(Color.DARK_GRAY);

        section.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel title =
                new JLabel("Albums");

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Dialog",
                        Font.BOLD,
                        24
                )
        );

        section.add(title);

        section.add(Box.createVerticalStrut(20));

        JPanel albumsRow =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                20,
                                0
                        )
                );

        albumsRow.setBackground(Color.DARK_GRAY);

        for (Album album : albums) {

            albumsRow.add(
                    buildAlbumCard(album)
            );
        }

        section.add(albumsRow);

        return section;
    }

    private JPanel buildAlbumCard(
            Album album
    ) {

        JPanel card = new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setPreferredSize(
                new Dimension(180, 240)
        );

        card.setBackground(
                new Color(32, 32, 32)
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        12,
                        12,
                        12
                )
        );

        JLabel cover =
                buildAlbumCover();

        JLabel title =
                new JLabel(album.getTitle());

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Dialog",
                        Font.BOLD,
                        14
                )
        );

        JLabel subtitle =
                new JLabel(
                        album.getSongs().size()
                                + " songs"
                );

        subtitle.setForeground(
                Color.LIGHT_GRAY
        );

        subtitle.setFont(
                new Font(
                        "Dialog",
                        Font.PLAIN,
                        12
                )
        );

        card.add(cover);

        card.add(Box.createVerticalStrut(12));

        card.add(title);

        card.add(Box.createVerticalStrut(4));

        card.add(subtitle);

        return card;
    }

    private JLabel buildAlbumCover() {

        int size = 156;

        BufferedImage image =
                new BufferedImage(
                        size,
                        size,
                        BufferedImage.TYPE_INT_RGB
                );

        Graphics2D g2 = image.createGraphics();

        g2.setColor(new Color(60, 60, 60));

        g2.fillRect(0, 0, size, size);

        g2.setColor(Color.WHITE);

        g2.setFont(
                new Font(
                        "Dialog",
                        Font.PLAIN,
                        42
                )
        );

        String note = "♪";

        FontMetrics fm =
                g2.getFontMetrics();

        g2.drawString(
                note,
                (size - fm.stringWidth(note)) / 2,
                (size + fm.getAscent()) / 2
        );

        g2.dispose();

        return new JLabel(new ImageIcon(image));
    }

    private void styleScrollBar(
            JScrollPane scrollPane
    ) {

        JScrollBar bar =
                scrollPane.getVerticalScrollBar();

        bar.setPreferredSize(
                new Dimension(6, 0)
        );

        bar.setUI(new BasicScrollBarUI() {

            @Override
            protected void configureScrollBarColors() {

                thumbColor =
                        new Color(
                                180,
                                180,
                                180,
                                160
                        );

                trackColor =
                        new Color(
                                50,
                                50,
                                50,
                                255
                        );
            }

            @Override
            protected JButton createDecreaseButton(
                    int orientation
            ) {
                return invisibleButton();
            }

            @Override
            protected JButton createIncreaseButton(
                    int orientation
            ) {
                return invisibleButton();
            }

            private JButton invisibleButton() {

                JButton button =
                        new JButton();

                button.setPreferredSize(
                        new Dimension(0, 0)
                );

                return button;
            }

            @Override
            protected void paintThumb(
                    Graphics g,
                    JComponent c,
                    java.awt.Rectangle r
            ) {

                Graphics2D g2 =
                        (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(thumbColor);

                g2.fillRoundRect(
                        r.x,
                        r.y,
                        r.width,
                        r.height,
                        6,
                        6
                );

                g2.dispose();
            }
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Artist artist =
                    new Artist(
                            1L,
                            "Arctic Pulse",
                            "artist@test.com",
                            "An electronic alternative artist blending ambient textures with synth-driven melodies.",
                            245120,
                            true
                    );

            List<Song> songs =
                    List.of(
                            new Song(
                                    "Neon Dreams",
                                    1L,
                                    artist,
                                    212,
                                    "",
                                    ""
                            ),
                            new Song(
                                    "Afterlight",
                                    2L,
                                    artist,
                                    184,
                                    "",
                                    ""
                            ),
                            new Song(
                                    "Static Horizon",
                                    3L,
                                    artist,
                                    201,
                                    "",
                                    ""
                            )
                    );

            List<Album> albums =
                    List.of(
                            new Album(
                                    "Midnight Echoes",
                                    1,
                                    artist,
                                    songs,
                                    null
                            ),
                            new Album(
                                    "Synthetic Bloom",
                                    2,
                                    artist,
                                    songs,
                                    null
                            )
                    );

            ArtistView view =
                    new ArtistView();

            view.setArtistData(
                    artist,
                    songs,
                    albums
            );
        });
    }

// =========================
// CONTROLLER HOOK (SAFE)
// =========================

private tunix.controller.ArtistController controller;

public void setController(tunix.controller.ArtistController controller) {
    this.controller = controller;
}

// =========================
// INTERACTION HOOKS
// =========================

private void onFollowClicked() {
    if (artist == null) return;

    // Placeholder logic (safe no-op for now)
    System.out.println("Follow clicked for: " + artist.getTitle());
}

// Future navigation-ready hook
private void onAlbumClicked(Album album) {
    if (album == null) return;

    System.out.println("Album clicked: " + album.getTitle());

    // Later you will plug EventBus here:
    // eventBus.publish(new OpenAlbumViewEvent(album));
}

// =========================
// WIRE ACTIONS INTO UI
// =========================

private JPanel buildActionsBar() {

    JPanel bar =
            new JPanel(
                    new FlowLayout(
                            FlowLayout.LEFT,
                            16,
                            0
                    )
            );

    bar.setBackground(Color.DARK_GRAY);
    bar.setAlignmentX(Component.LEFT_ALIGNMENT);

    JButton playButton = buildCirclePlayButton();

    JButton followButton = buildActionButton("Follow");
    followButton.addActionListener(e -> onFollowClicked());

    JButton shuffleButton = buildActionButton("Shuffle");
    JButton optionsButton = buildActionButton("⋯");

    bar.add(playButton);
    bar.add(followButton);
    bar.add(shuffleButton);
    bar.add(optionsButton);

    return bar;
}



}

