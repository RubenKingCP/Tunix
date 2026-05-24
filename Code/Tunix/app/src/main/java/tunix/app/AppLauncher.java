
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
import tunix.navigation.ScreenRegistry;
import tunix.navigation.events.EventBus;
import tunix.service.LibraryService;
import tunix.service.MusicPlayerService;
import tunix.service.PlaylistService;
import tunix.service.SearchService;
import tunix.service.auth.LoginService;
import tunix.service.auth.RegisterService;
import tunix.service.auth.SessionService;
import tunix.ui.AdminPanel;
import tunix.ui.AuthPanel;
import tunix.ui.MainPanel;
import tunix.ui.ProfilePanel;
import tunix.ui.views.auth.LoginView;
import tunix.ui.views.auth.RegisterView;
import tunix.ui.views.main.LibraryView;
import tunix.ui.views.main.MusicPlayerView;
import tunix.ui.views.main.TopBarView;
import tunix.ui.views.main.center.HomeView;

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

        // Create main panel
        MainPanel mainPanel = creatMainPanel(context);

        // =========================
        // ADMIN (placeholder)
        // =========================
        AdminPanel adminPanel = new AdminPanel();

        // =========================
        // REGISTER SCREENS
        // =========================
        window.register(AuthPanel.class, authPanel);
        window.register(MainPanel.class, mainPanel);
        window.register(AdminPanel.class, adminPanel);

        // START APP
        window.show(AuthPanel.class);
        window.setVisible(true);
        }

        private static MainPanel creatMainPanel(AppContext context) {
                // =========================
                // MAIN MODULE
                // =========================
                LibraryView libraryView = new LibraryView();
                LibraryService libraryService = new LibraryService();
                PlaylistService playlistService = new PlaylistService(new PlaylistApiClient(context.apiClient));

                LibraryController libraryController =
                        new LibraryController(libraryView, libraryService, playlistService);

                libraryView.setController(libraryController);

                MusicPlayerView musicPlayerView = new MusicPlayerView();
                MusicPlayerService musicPlayerService = new MusicPlayerService();

                MusicPlayerController musicPlayerController =
                        new MusicPlayerController(musicPlayerView, musicPlayerService, context.eventBus);

                musicPlayerView.setController(musicPlayerController);

                

                TopBarView topBarView = new TopBarView();

                SearchService searchService = new SearchService(context.apiClient);

                TopBarController topBarController =
                        new TopBarController(topBarView, searchService, context.eventBus);

                topBarView.setController(topBarController);
                

                // Setup center panel classes
                //Home
                HomeView homeView = new HomeView();
                HomeController homeController = new HomeController(homeView);

                //Profile
                


                // Create screen registry
                ScreenRegistry registry = new ScreenRegistry();

                // Create main panel
                MainPanel mainPanel =
                        new MainPanel(topBarView, libraryView, musicPlayerView, registry, context);

                //Register classes that main panel needs
                mainPanel.register(HomeView.class, homeView);


                return mainPanel;
        }
}
