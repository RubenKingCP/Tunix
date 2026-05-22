
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

public class TunixApp {
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
        // Login
        LoginView loginView = new LoginView();
        LoginService loginService = new LoginService(loginApiClient);
        LoginController loginController = new LoginController(loginView, loginService, eventBus);
        
        loginView.setController(loginController);
        loginView.setVisible(true);

        // Register
        RegisterView registerView = new RegisterView();
        RegisterService registerService= new RegisterService(registerApiClient);
        RegisterController registerController = new RegisterController(registerView, registerService, eventBus);
        
        registerView.setController(registerController);

        // Setup Authorization Panel
        AuthPanel authPanel = new AuthPanel(loginView, registerView, eventBus);

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
        HomeController homeController = new HomeController();

        // Setup Main Panel
        MainPanel mainPanel = new MainPanel(topBarView, libraryView, homeView, musicPlayerView);

        // Setup AppWindow
        AppWindow appWindow = new AppWindow(authPanel, mainPanel, eventBus);
        appWindow.setVisible(true);
    }
}
