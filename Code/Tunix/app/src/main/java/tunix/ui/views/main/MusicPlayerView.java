package tunix.ui.views.main;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import tunix.controller.main.MusicPlayerController;

public class MusicPlayerView extends JPanel {

    // ── Tunix palette (matches screenshot) ──────────────────────────────────
    private static final Color BG          = new Color(0x0D0D0D);   // near-black
    private static final Color SURFACE     = new Color(0x1A1A1A);   // slightly lifted surface
    private static final Color BORDER      = new Color(0x2E2E2E);   // subtle divider
    private static final Color FG_PRIMARY  = new Color(0xF0F0F0);   // white-ish text
    private static final Color ACCENT      = new Color(0xFFFFFF);   // active/hover white
    private static final Color BTN_HOVER   = new Color(0x2A2A2A);   // button hover fill
    private static final Color BTN_PRESS   = new Color(0x383838);   // button press fill

    private MusicPlayerController musicPlayerController;

    public MusicPlayerView() {
        setLayout(new BorderLayout());
        setBackground(BG);
        // Top border line separates player bar from content above
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // ── Song title (left side) ───────────────────────────────────────────
        JLabel songTitleLabel = new JLabel("Song Title");
        songTitleLabel.setForeground(FG_PRIMARY);
        songTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        songTitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        add(songTitleLabel, BorderLayout.WEST);

        // ── Center controls ──────────────────────────────────────────────────
        JPanel centerControls = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        centerControls.setOpaque(false);

        JButton shuffleButton  = createControlButton("Shuffle",    false);
        JButton prevButton     = createControlButton("Previous",   false);
        JButton playButton     = createControlButton("Play / Pause", true);  // primary
        JButton nextButton     = createControlButton("Next",       false);

        shuffleButton.addActionListener(e -> onShuffleButtonClicked());
        prevButton   .addActionListener(e -> onPreviousButtonClicked());
        playButton   .addActionListener(e -> onPlayPauseButtonClicked());
        nextButton   .addActionListener(e -> onNextButtonClicked());

        centerControls.add(shuffleButton);
        centerControls.add(prevButton);
        centerControls.add(playButton);
        centerControls.add(nextButton);

        add(centerControls, BorderLayout.CENTER);
    }

    // ── Button factory ───────────────────────────────────────────────────────
    /**
     * Creates a flat, dark-themed control button consistent with the Tunix UI.
     *
     * @param label   Button text
     * @param primary When true the button gets a filled white background (play/pause emphasis)
     */
    private JButton createControlButton(String label, boolean primary) {
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

                // Rounded pill shape
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Border ring
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
        btn.setPreferredSize(new Dimension(primary ? 110 : 88, 32));

        // Subtle hover colour swap for non-primary labels
        if (!primary) {
            btn.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) {
                    btn.setForeground(ACCENT);
                }
                @Override public void mouseExited(MouseEvent e) {
                    btn.setForeground(FG_PRIMARY);
                }
            });
        }

        return btn;
    }

    // ── Wiring ───────────────────────────────────────────────────────────────
    public void setController(MusicPlayerController musicPlayerController) {
        this.musicPlayerController = musicPlayerController;
    }

    public void onShuffleButtonClicked()   { musicPlayerController.onShuffleButtonClicked();   }
    public void onPreviousButtonClicked()  { musicPlayerController.onPreviousButtonClicked();  }
    public void onPlayPauseButtonClicked() { musicPlayerController.onPlayPauseButtonClicked(); }
    public void onNextButtonClicked()      { musicPlayerController.onNextButtonClicked();      }
}