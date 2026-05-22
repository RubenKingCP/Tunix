package tunix.event;

import javax.swing.JPanel;

public record SwitchMainScreen(JPanel newScreen) {
    public JPanel getNewScreen() {
        return newScreen;
    }
    
}
