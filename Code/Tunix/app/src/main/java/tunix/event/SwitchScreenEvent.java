package tunix.event;

public class SwitchScreenEvent {
    private final Class<?> screen;

    public SwitchScreenEvent(Class<?> screen) {
        this.screen = screen;
    }

    public Class<?> getScreen() {
        return screen;
    }
}