package tunix.navigation.events;


public class SwitchProfileScreenEvent {
    private final Class<?> screen;

    public SwitchProfileScreenEvent(Class<?> screen) {
        this.screen = screen;
    }

    public Class<?> getScreen() {
        return screen;
    }

}
