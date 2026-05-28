
package tunix.app;


import tunix.api.AdminApi;
import tunix.api.AlbumApi;
import tunix.api.ApiClient;
import tunix.api.ArtistApi;
import tunix.api.ArtistRequestApiClient;
import tunix.api.LibraryApiClient;
import tunix.api.LoginApiClient;
import tunix.api.PlaylistApiClient;
import tunix.api.RegisterApiClient;
import tunix.api.SongApiClient;
import tunix.controller.AdminController;
import tunix.controller.SearchController;
import tunix.controller.auth.LoginController;
import tunix.controller.auth.RegisterController;
import tunix.controller.main.LibraryController;
import tunix.controller.main.MusicPlayerController;
import tunix.controller.main.TopBarController;
import tunix.model.AppContext;
import tunix.navigation.ScreenRegistry;
import tunix.navigation.events.EventBus;
import tunix.service.AdminService;
import tunix.service.ArtistRequestService;
import tunix.service.LibraryService;
import tunix.service.MusicPlayerService;
import tunix.service.PlaylistService;
import tunix.service.SearchService;
import tunix.service.SongService;
import tunix.service.auth.LoginService;
import tunix.service.auth.RegisterService;
import tunix.service.auth.SessionService;
import tunix.ui.AdminPanel;
import tunix.ui.AuthPanel;
import tunix.ui.MainPanel;

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
        LoginService loginService =
                new LoginService(new LoginApiClient(apiClient), eventBus);

        RegisterService registerService =
                new RegisterService(new RegisterApiClient(apiClient), eventBus);

        SessionService sessionService = new SessionService(context);

        LoginController loginController =
                new LoginController(loginService, sessionService, eventBus);

        RegisterController registerController =
                new RegisterController(registerService, sessionService, eventBus);

        AuthPanel authPanel = new AuthPanel(loginController.getView(), registerController.getView(), eventBus);

        // Create main panel
        MainPanel mainPanel = createMainPanel(context);

        // =========================
        // ADMIN (placeholder)
        // =========================
        AdminPanel adminPanel = new AdminPanel(new AdminController(eventBus, new AdminService(new AdminApi(context.apiClient), new SongApiClient(apiClient)), new ArtistRequestService(new ArtistRequestApiClient(apiClient)), new SongService(eventBus, new SongApiClient(apiClient))));

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

        private static MainPanel createMainPanel(AppContext context) {
                // =========================
                // MAIN MODULE
                // =========================
                PlaylistApiClient playlistApiClient = new PlaylistApiClient(context.apiClient);
                AlbumApi albumApiClient = new AlbumApi(context.apiClient);
                ArtistApi artistApiClient = new ArtistApi(context.apiClient);
                LibraryApiClient libraryApiClient = new LibraryApiClient(context.apiClient);
                LibraryService libraryService = new LibraryService(libraryApiClient);
                PlaylistService playlistService = new PlaylistService(playlistApiClient);
                MusicPlayerService musicPlayerService = new MusicPlayerService();
                SearchService searchService = new SearchService(new SongApiClient(context.apiClient), playlistApiClient, albumApiClient);
                SearchController searchController = new SearchController(searchService, context.eventBus);

                LibraryController libraryController =
                        new LibraryController(libraryService, playlistService, context);

                MusicPlayerController musicPlayerController =
                        new MusicPlayerController(musicPlayerService, context.eventBus);

                TopBarController topBarController =
                        new TopBarController(searchController, context.eventBus);

                ScreenRegistry registry = new ScreenRegistry();

                return new MainPanel(
                        topBarController.getView(),
                        libraryController.getView(),
                        musicPlayerController.getView(),
                        registry,
                        context,
                        searchController);
        }
        
}
