package tunix.view.center;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;

public class AlbumView extends JPanel {

    public AlbumView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        add(buildHeader());
        add(buildActionsBar());
        add(buildSongTable());
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


    // Header Stuff -- Album Details

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(24, 0)); // 24px horizontal gap
        header.setBackground(Color.DARK_GRAY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(buildAlbumCoverThumb(), BorderLayout.WEST);
        header.add(buildAlbumInfo(), BorderLayout.CENTER);

        return header;
    }

    private JLabel buildAlbumCoverThumb() {
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

    private JPanel buildAlbumInfo() {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.DARK_GRAY);

        JLabel type = new JLabel("Album");
        type.setForeground(Color.LIGHT_GRAY);
        type.setFont(new Font("Dialog", Font.PLAIN, 12));

        JLabel albumName = new JLabel("Album Name Placeholder");
        albumName.setForeground(Color.WHITE);
        albumName.setFont(new Font("Dialog", Font.BOLD, 28));

        JLabel artist = new JLabel("Artist Name Placeholder");
        artist.setForeground(Color.LIGHT_GRAY);
        artist.setFont(new Font("Dialog", Font.PLAIN, 14));

        JLabel details = new JLabel("2024  •  10 songs  •  42 min");
        details.setForeground(Color.LIGHT_GRAY);
        details.setFont(new Font("Dialog", Font.PLAIN, 12));

        info.add(Box.createVerticalGlue());
        info.add(type);
        info.add(Box.createVerticalStrut(8));
        info.add(albumName);
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
        JButton optionsButton = buildActionButton("⋯");

        bar.add(playButton);
        bar.add(shuffleButton);
        bar.add(saveButton);
        bar.add(downloadButton);
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


    // Song Table -- List of Songs for the Album
    private JScrollPane buildSongTable() {
        String[] columns = { "#", "Title", "🕐" };

        Object[][] rows = {
            { "1", "Song Title Placeholder", "3:45" },
            { "2", "Song Title Placeholder", "4:12" },
            { "3", "Song Title Placeholder", "2:58" },
            { "4", "Song Title Placeholder", "5:01" },
            { "5", "Song Title Placeholder", "3:33" },
        };

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
        table.getColumnModel().getColumn(2).setPreferredWidth(60);  // duration

        // center # and duration columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Color.DARK_GRAY);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        styleScrollBar(scrollPane);
        return scrollPane;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tunix - Album");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new AlbumView());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}