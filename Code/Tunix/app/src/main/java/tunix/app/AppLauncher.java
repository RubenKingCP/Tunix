
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
import tunix.service.LibraryService;
import tunix.service.LoginService;
import tunix.service.MusicPlayerService;
import tunix.service.PlaylistService;
import tunix.service.RegisterService;
import tunix.service.SearchService;
import tunix.view.auth.AuthPanel;
import tunix.view.auth.LoginView;
import tunix.view.auth.RegisterView;
import tunix.view.center.HomeView;
import tunix.view.main.LibraryView;
import tunix.view.main.MainPanel;
import tunix.view.main.MusicPlayerView;
import tunix.view.main.TopBarView;
import tunix.event.EventBus;

public class AppLauncher {
    public static final String BASE_URL = "http://localhost:8080";

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.print("Testing");

        // Setup ApiClient first
        ApiClient apiClient = new ApiClient(BASE_URL);
        System.out.print(apiClient);

        // Setup EventBus
        EventBus eventBus = new EventBus();

        // Setup Api shortcuts
        // Login Api
        LoginApiClient loginApiClient = new LoginApiClient(apiClient);
        RegisterApiClient registerApiClient = new RegisterApiClient(apiClient);

        // Playlist Api
        PlaylistApiClient playlistApiClient = new PlaylistApiClient(apiClient);

        // Setup Authorization Components
        AuthPanel authPanel = createAuthPanel(apiClient, eventBus);

        

        // Setup Main Panel
        MainPanel mainPanel = createMainPanel(apiClient, eventBus, playlistApiClient);

        // Setup AppWindow
        AppWindow appWindow = new AppWindow(authPanel, mainPanel, eventBus);
        appWindow.setVisible(true);
    }

    private static AuthPanel createAuthPanel(ApiClient apiClient, EventBus eventBus) {

        // Setup login
        LoginView loginView = new LoginView();

        LoginService loginService =
                new LoginService(
                        new LoginApiClient(apiClient)
                );

        LoginController loginController =
                new LoginController(
                        loginView,
                        loginService,
                        eventBus
                );

        loginView.setController(loginController);
        

        // Setup register
        RegisterView registerView = new RegisterView();

        RegisterService registerService =
                new RegisterService(
                        new RegisterApiClient(apiClient)
                );

        RegisterController registerController =
                new RegisterController(
                        registerView,
                        registerService,
                        eventBus
                );

        registerView.setController(registerController);

        return new AuthPanel(
                loginView,
                registerView,
                eventBus
        );
    }

    private static MainPanel createMainPanel(ApiClient apiClient, EventBus eventBus, PlaylistApiClient playlistApiClient) {
        // Setup Main Panel Components
        // Library
        LibraryView libraryView = new LibraryView();
        LibraryService libraryService = new LibraryService();
        PlaylistService playlistService = new PlaylistService(playlistApiClient);
        LibraryController libraryController = new LibraryController(libraryView, libraryService, playlistService);

        libraryView.setController(libraryController);

        // Music Player
        MusicPlayerView musicPlayerView = new MusicPlayerView();
        MusicPlayerService musicPlayerService = new MusicPlayerService();
        MusicPlayerController musicPlayerController = new MusicPlayerController(musicPlayerView, musicPlayerService, eventBus);
        
        musicPlayerView.setController(musicPlayerController);

        // Top Bar Controller
        TopBarView topBarView = new TopBarView();
        SearchService searchService = new SearchService(apiClient);
        TopBarController topBarController = new TopBarController(topBarView, searchService, eventBus);
    
        topBarView.setController(topBarController);

        // Home
        HomeView homeView = new HomeView();
        HomeController homeController = new HomeController(homeView);

        return new MainPanel(topBarView, libraryView, homeView, musicPlayerView);
    }
}
