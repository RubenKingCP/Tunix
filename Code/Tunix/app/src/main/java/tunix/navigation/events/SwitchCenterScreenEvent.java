package tunix.navigation.events;

public class SwitchCenterScreenEvent {
    private final Class<?> controllerClass;

    public SwitchCenterScreenEvent(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }
}


