package tunix.navigation.events;

public class SwitchCenterScreenEvent {
    private final Class<?> screen;

    public SwitchCenterScreenEvent(Class<?> screen) {
        this.screen = screen;
    }

    public Class<?> getScreen() {
        return screen;
    }
}
