package tunix.core;

import tunix.api.ApiClient;
import tunix.app.AppWindow;
import tunix.event.EventBus;

public class AppContext {
    public final ApiClient apiClient;
    public final EventBus eventBus;

    private Session session;
    private AppWindow appWindow;

    public AppContext(ApiClient apiClient, EventBus eventBus) {
        this.apiClient = apiClient;
        this.eventBus = eventBus;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public AppWindow getAppWindow() {
        return appWindow;
    }

    public void setAppWindow(AppWindow appWindow) {
        this.appWindow = appWindow;
    }
}