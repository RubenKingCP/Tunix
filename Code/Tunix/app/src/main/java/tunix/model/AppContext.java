package tunix.model;

import tunix.api.ApiClient;
import tunix.app.AppWindow;
import tunix.navigation.events.EventBus;

public class AppContext {
    public final ApiClient apiClient;
    public final EventBus eventBus;

    private AppWindow appWindow;

    public AppContext(ApiClient apiClient, EventBus eventBus) {
        this.apiClient = apiClient;
        this.eventBus = eventBus;
    }

    public AppWindow getAppWindow() {
        return appWindow;
    }

    public void setAppWindow(AppWindow appWindow) {
        this.appWindow = appWindow;
    }
}