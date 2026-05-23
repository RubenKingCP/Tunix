package tunix.view.auth;

import javax.swing.*;
import javax.swing.border.*;
import tunix.controller.auth.RegisterController;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class RegisterView extends JPanel {

    // ── Tunix colour palette ─────────────────────────────────────────────────
    private static final Color BG_DARK       = new Color(0x1E1E1E);
    private static final Color BG_CARD       = new Color(0x2A2A2A);
    private static final Color BG_INPUT      = new Color(0x333333);
    private static final Color ACCENT        = new Color(0xFFFFFF);
    private static final Color ACCENT_DIM    = new Color(0xB3B3B3);
    private static final Color BORDER_COLOR  = new Color(0x3D3D3D);
    private static final Color BTN_PRIMARY   = new Color(0xFFFFFF);
    private static final Color BTN_PRIMARY_FG= new Color(0x1E1E1E);
    private static final Color BTN_SECONDARY = new Color(0x2A2A2A);
    private static final Color BTN_SEC_HOVER = new Color(0x3D3D3D);

    private JTextField     usernameField;
    private JTextField     emailField;
    private JPasswordField passwordField;
    private JButton        registerButton;
    private JButton        loginButton;
    private RegisterController controller;

    public RegisterView() {
        initUI();
    }

    private void initUI() {
        setBackground(BG_DARK);
        setLayout(new GridBagLayout());

        // ── Card panel ───────────────────────────────────────────────────────
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(BORDER_COLOR);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f,
                                                   getWidth()-1, getHeight()-1, 16, 16));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(380, 490));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill  = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;

        // ── Logo / icon ──────────────────────────────────────────────────────
        JLabel iconLabel = new JLabel(musicNoteIcon(), SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(64, 64));
        gc.gridy  = 0;
        gc.insets = new Insets(40, 40, 0, 40);
        card.add(iconLabel, gc);

        // ── App name ─────────────────────────────────────────────────────────
        JLabel appName = new JLabel("Tunix", SwingConstants.CENTER);
        appName.setFont(new Font("SansSerif", Font.BOLD, 28));
        appName.setForeground(ACCENT);
        gc.gridy  = 1;
        gc.insets = new Insets(12, 40, 4, 40);
        card.add(appName, gc);

        // ── Subtitle ─────────────────────────────────────────────────────────
        JLabel subtitle = new JLabel("Create your account", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(ACCENT_DIM);
        gc.gridy  = 2;
        gc.insets = new Insets(0, 40, 24, 40);
        card.add(subtitle, gc);

        // ── Username field ───────────────────────────────────────────────────
        gc.gridy  = 3;
        gc.insets = new Insets(0, 40, 12, 40);
        card.add(buildFieldStack("Username", usernameField = buildTextField("Your username")), gc);

        // ── Email field ──────────────────────────────────────────────────────
        gc.gridy  = 4;
        gc.insets = new Insets(0, 40, 12, 40);
        card.add(buildFieldStack("Email", emailField = buildTextField("you@example.com")), gc);

        // ── Password field ───────────────────────────────────────────────────
        gc.gridy  = 5;
        gc.insets = new Insets(0, 40, 24, 40);
        card.add(buildFieldStack("Password", passwordField = buildPasswordField("••••••••")), gc);

        // ── Register button ──────────────────────────────────────────────────
        registerButton = buildPrimaryButton("Create Account");
        registerButton.addActionListener(e -> {
            if (controller != null) controller.onRegisterButtonClicked();
        });
        gc.gridy  = 6;
        gc.insets = new Insets(0, 40, 12, 40);
        card.add(registerButton, gc);

        // ── Login button ─────────────────────────────────────────────────────
        loginButton = buildSecondaryButton("Already have an account? Log In");
        loginButton.addActionListener(e -> {
            if (controller != null) controller.onGoToLoginButtonClicked();
        });
        gc.gridy  = 7;
        gc.insets = new Insets(0, 40, 40, 40);
        card.add(loginButton, gc);

        add(card);
    }

    // ── Field helpers ────────────────────────────────────────────────────────

    private JPanel buildFieldStack(String labelText, JComponent field) {
        JPanel stack = new JPanel(new BorderLayout(0, 6));
        stack.setOpaque(false);
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setForeground(ACCENT_DIM);
        stack.add(lbl, BorderLayout.NORTH);
        stack.add(field, BorderLayout.CENTER);
        return stack;
    }

    private JTextField buildTextField(String placeholder) {
        JTextField f = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_INPUT);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                super.paintComponent(g);
                g2.dispose();
            }
        };
        styleInputField(f, placeholder);
        return f;
    }

    private JPasswordField buildPasswordField(String placeholder) {
        JPasswordField f = new JPasswordField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_INPUT);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                super.paintComponent(g);
                g2.dispose();
            }
        };
        styleInputField(f, placeholder);
        return f;
    }

    private void styleInputField(JTextField f, String placeholder) {
        f.setOpaque(false);
        f.setBackground(new Color(0, 0, 0, 0));
        f.setForeground(ACCENT);
        f.setCaretColor(ACCENT);
        f.setFont(new Font("SansSerif", Font.PLAIN, 14));
        f.setBorder(new CompoundBorder(
            new RoundedBorder(8, BORDER_COLOR),
            new EmptyBorder(10, 14, 10, 14)
        ));
        f.setPreferredSize(new Dimension(0, 44));
        f.putClientProperty("placeholder", placeholder);
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { f.repaint(); }
            @Override public void focusLost(FocusEvent e)   { f.repaint(); }
        });
    }

    // ── Button helpers ───────────────────────────────────────────────────────

    private JButton buildPrimaryButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? new Color(0xE0E0E0) : BTN_PRIMARY);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 24, 24));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(BTN_PRIMARY_FG);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 44));
        return btn;
    }

    private JButton buildSecondaryButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? BTN_SEC_HOVER : BTN_SECONDARY);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 24, 24));
                g2.setColor(BORDER_COLOR);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f,
                                                   getWidth()-1, getHeight()-1, 24, 24));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setForeground(ACCENT_DIM);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 44));
        return btn;
    }

    // ── Music note icon (drawn, no external assets needed) ───────────────────

    private Icon musicNoteIcon() {
        return new Icon() {
            public int getIconWidth()  { return 48; }
            public int getIconHeight() { return 48; }
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_INPUT);
                g2.fillOval(x, y, 48, 48);
                g2.setColor(ACCENT_DIM);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(x+29, y+12, x+29, y+32);
                g2.drawLine(x+29, y+12, x+36, y+16);
                g2.fillOval(x+20, y+28, 11, 9);
                g2.dispose();
            }
        };
    }

    // ── Rounded border helper ─────────────────────────────────────────────────

    private static class RoundedBorder extends AbstractBorder {
        private final int   radius;
        private final Color color;
        RoundedBorder(int radius, Color color) { this.radius = radius; this.color = color; }

        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1f));
            g2.draw(new RoundRectangle2D.Float(x+0.5f, y+0.5f, w-1, h-1, radius, radius));
            g2.dispose();
        }

        @Override public Insets getBorderInsets(Component c) { return new Insets(1,1,1,1); }
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public void setController(RegisterController controller) {
        this.controller = controller;
    }

    public String getUsername() { return usernameField.getText(); }
    public String getEmail()    { return emailField.getText(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
}