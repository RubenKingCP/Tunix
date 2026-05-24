
package tunix.app;


import tunix.api.ApiClient;
import tunix.api.LoginApiClient;
import tunix.api.PlaylistApiClient;
import tunix.api.RegisterApiClient;
import tunix.controller.HomeController;
import tunix.controller.auth.LoginController;
import tunix.controller.auth.RegisterController;
import tunix.controller.main.LibraryController;
import tunix.controller.main.MusicPlayerController;
import tunix.controller.main.TopBarController;
import tunix.core.AppContext;
import tunix.service.LibraryService;
import tunix.service.MusicPlayerService;
import tunix.service.PlaylistService;
import tunix.service.SearchService;
import tunix.service.auth.LoginService;
import tunix.service.auth.RegisterService;
import tunix.service.auth.SessionService;
import tunix.view.admin.AdminPanel;
import tunix.view.auth.AuthPanel;
import tunix.view.auth.LoginView;
import tunix.view.auth.RegisterView;
import tunix.view.main.HomeView;
import tunix.view.main.LibraryView;
import tunix.view.main.MainPanel;
import tunix.view.main.MusicPlayerView;
import tunix.view.main.TopBarView;
import tunix.view.profile.ProfilePanel;
import tunix.event.EventBus;

public class AppLauncher {
    public static final String BASE_URL = "http://localhost:8080";

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {

        ApiClient apiClient = new ApiClient(BASE_URL);
        EventBus eventBus = new EventBus();

        AppContext context = new AppContext(apiClient, eventBus);

        AppWindow window = new AppWindow(context);
        context.setAppWindow(window);

        // =========================
        // AUTH MODULE
        // =========================
        LoginView loginView = new LoginView();
        RegisterView registerView = new RegisterView();

        LoginService loginService =
                new LoginService(new LoginApiClient(apiClient), eventBus);

        RegisterService registerService =
                new RegisterService(new RegisterApiClient(apiClient), eventBus);

        SessionService sessionService = new SessionService(eventBus);

        LoginController loginController =
                new LoginController(loginView, loginService, sessionService, eventBus);

        RegisterController registerController =
                new RegisterController(registerView, registerService, sessionService, eventBus);

        loginView.setController(loginController);
        registerView.setController(registerController);

        AuthPanel authPanel = new AuthPanel(loginView, registerView, eventBus);

        // =========================
        // MAIN MODULE
        // =========================
        LibraryView libraryView = new LibraryView();
        LibraryService libraryService = new LibraryService();
        PlaylistService playlistService = new PlaylistService(new PlaylistApiClient(apiClient));

        LibraryController libraryController =
                new LibraryController(libraryView, libraryService, playlistService);

        libraryView.setController(libraryController);

        MusicPlayerView musicPlayerView = new MusicPlayerView();
        MusicPlayerService musicPlayerService = new MusicPlayerService();

        MusicPlayerController musicPlayerController =
                new MusicPlayerController(musicPlayerView, musicPlayerService, eventBus);

        musicPlayerView.setController(musicPlayerController);

        HomeView homeView = new HomeView();
        HomeController homeController = new HomeController(homeView);

        TopBarView topBarView = new TopBarView();

        SearchService searchService = new SearchService(apiClient);

        TopBarController topBarController =
                new TopBarController(topBarView, searchService, eventBus, homeView);

        topBarView.setController(topBarController);

        MainPanel mainPanel =
                new MainPanel(topBarView, libraryView, homeView, musicPlayerView, context);

        // =========================
        // ADMIN (placeholder)
        // =========================
        AdminPanel adminPanel = new AdminPanel();

        // =========================
        // PROFILE (placeholder)
        // =========================
        ProfilePanel profilePanel = new ProfilePanel();

        // =========================
        // REGISTER SCREENS
        // =========================
        window.register(AuthPanel.class, authPanel);
        window.register(MainPanel.class, mainPanel);
        window.register(AdminPanel.class, adminPanel);
        window.register(ProfilePanel.class, profilePanel);

        // START APP
        window.show(AuthPanel.class);
        window.setVisible(true);
        }
}
