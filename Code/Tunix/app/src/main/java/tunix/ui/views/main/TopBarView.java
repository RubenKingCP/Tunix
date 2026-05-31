package tunix.ui.views.main;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import tunix.controller.main.TopBarController;

public class TopBarView extends JPanel {

    // ── Tunix palette ────────────────────────────────────────────────────────
    private static final Color BG         = new Color(0x0D0D0D);
    private static final Color SURFACE    = new Color(0x1A1A1A);
    private static final Color BORDER     = new Color(0x2E2E2E);
    private static final Color BORDER_FOC = new Color(0x5A5A5A);  // focused input ring
    private static final Color FG_PRIMARY = new Color(0xF0F0F0);
    private static final Color FG_MUTED   = new Color(0x6A6A6A);
    private static final Color ACCENT     = new Color(0xFFFFFF);
    private static final Color BTN_HOVER  = new Color(0x2A2A2A);
    private static final Color BTN_PRESS  = new Color(0x383838);

    private TopBarController topBarController;

    public TopBarView() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 8, 6));
        setBackground(BG);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));

        // ── Home button ──────────────────────────────────────────────────────
        JButton homeButton = createFlatButton("Home", false);
        homeButton.addActionListener(e -> onHomeButtonClicked());
        add(homeButton);

        // ── Search field ─────────────────────────────────────────────────────
        JTextField searchField = createSearchField();
        add(searchField);

        // ── Search type selector ───────────────────────────────────────────────
        String[] searchTypes = new String[]{"Song", "Playlist", "Album", "Artist"};
        String[] selectedSearchType = new String[]{searchTypes[0]};
        JButton searchTypeButton = createSearchTypeButton(selectedSearchType[0]);
        JPopupMenu searchTypeMenu = createSearchTypeMenu(searchTypes, selectedSearchType, searchTypeButton);
        searchTypeButton.addActionListener(e -> searchTypeMenu.show(searchTypeButton, 0, searchTypeButton.getHeight()));
        add(searchTypeButton);

        // ── Search button ─────────────────────────────────────────────────────
        JButton searchButton = createFlatButton("Search", true);
        searchButton.addActionListener(e -> onSearchButtonClicked(
                searchField.getText(),
                selectedSearchType[0]
        ));
        add(searchButton);

        // ── Profile button (right-aligned via spacer panel) ───────────────────
        JButton profileButton = createFlatButton("Profile", false);
        profileButton.addActionListener(e -> onProfileButtonClicked());
        add(profileButton);

        // ── Logout button ─────────────────────────────────────────────────────
        JButton logoutButton = createFlatButton("Logout", false);
        logoutButton.addActionListener(e -> onLogoutButtonClicked());
        add(logoutButton);
    }

    // ── Component factories ──────────────────────────────────────────────────

    private JButton createFlatButton(String label, boolean primary) {
        JButton btn = new JButton(label) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                ButtonModel model = getModel();
                Color fill;
                if (primary) {
                    fill = model.isPressed()  ? new Color(0xCCCCCC)
                         : model.isRollover() ? new Color(0xE8E8E8)
                         : ACCENT;
                } else {
                    fill = model.isPressed()  ? BTN_PRESS
                         : model.isRollover() ? BTN_HOVER
                         : SURFACE;
                }
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(primary ? BG : FG_PRIMARY);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(primary ? 80 : 74, 30));

        if (!primary) {
            btn.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { btn.setForeground(ACCENT); }
                @Override public void mouseExited(MouseEvent e)  { btn.setForeground(FG_PRIMARY); }
            });
        }

        return btn;
    }

    private JTextField createSearchField() {
        JTextField field = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isFocusOwner() ? BORDER_FOC : BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };

        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setForeground(FG_PRIMARY);
        field.setCaretColor(FG_PRIMARY);
        field.setOpaque(false);
        field.setBackground(SURFACE);   // fallback
        field.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        field.setPreferredSize(new Dimension(220, 30));

        // Placeholder behaviour
        final String placeholder = "Search in Your Library";
        field.setForeground(FG_MUTED);
        field.setText(placeholder);
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(FG_PRIMARY);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(FG_MUTED);
                    field.setText(placeholder);
                }
            }
        });

        return field;
    }

    private JButton createSearchTypeButton(String value) {
        JButton btn = createFlatButton(value + " ▾", false);
        btn.setPreferredSize(new Dimension(120, 30));
        btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn.setMargin(new java.awt.Insets(0, 10, 0, 10));
        return btn;
    }

    private JPopupMenu createSearchTypeMenu(String[] searchTypes, String[] selectedSearchType, JButton searchTypeButton) {
        JPopupMenu menu = new JPopupMenu();
        menu.setBorder(BorderFactory.createLineBorder(BORDER));
        menu.setBackground(SURFACE);

        for (String searchType : searchTypes) {
            JMenuItem item = new JMenuItem(searchType);
            item.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            item.setForeground(FG_PRIMARY);
            item.setBackground(SURFACE);
            item.setOpaque(true);
            item.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
            item.addActionListener(e -> {
                selectedSearchType[0] = searchType;
                searchTypeButton.setText(searchType + " ▾");
                searchTypeButton.revalidate();
                searchTypeButton.repaint();
            });
            menu.add(item);
        }

        return menu;
    }

    // ── Wiring ───────────────────────────────────────────────────────────────

    public void setController(TopBarController topBarController) {
        this.topBarController = topBarController;
    }

    public void onSearchButtonClicked(String query, String searchType) {
        topBarController.onSearch(query, searchType);
    }

    public void onHomeButtonClicked() {
        topBarController.onHomeButtonClicked();
    }

    public void onProfileButtonClicked() {
        topBarController.onProfileButtonClicked();
    }

    public void onLogoutButtonClicked() {
        topBarController.onLogoutButtonClicked(); 
    }
}