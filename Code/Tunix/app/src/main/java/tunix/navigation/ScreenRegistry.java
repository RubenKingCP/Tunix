package tunix.navigation;

import java.util.Map;
import javax.swing.JPanel;
import java.util.HashMap;

public class ScreenRegistry {

    private final Map<Class<?>, JPanel> screens = new HashMap<>();
    private final Map<Class<?>, String> names = new HashMap<>();

    public void register(Class<?> key, JPanel panel) {
        String name = key.getName(); // safer than simpleName
        System.out.println("Registered to registry: " + key.getSimpleName());

        screens.put(key, panel);
        names.put(key, name);
    }

    public JPanel get(Class<?> key) {
        return screens.get(key);
    }

    public String getName(Class<?> key) {
        return names.get(key);
    }
}