package tunix.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;


import tunix.api.*;
import tunix.controller.*;
import tunix.controller.main.LibraryController.OpenSongViewEvent;
import tunix.controller.main.center.MusicController;
import tunix.dto.enums.Role;
import tunix.model.AppContext;
import tunix.model.account.Account;
import tunix.navigation.ScreenRegistry;
import tunix.navigation.events.*;
import tunix.service.*;
import tunix.service.auth.SessionService;
import tunix.ui.views.main.center.UploadSongView;
import tunix.model.musicContent.Album;
import tunix.ui.views.main.center.AlbumView;
import tunix.ui.views.main.center.MusicView;

public class MainPanel extends JPanel {

    private ArtistController artistController;
    private final JPanel centerRouter = new JPanel(new CardLayout());
    private final CardLayout layout = (CardLayout) centerRouter.getLayout();
    private final ScreenRegistry registry;
    private final AppContext context;
    private final SearchController searchController;
    private MusicController musicController;
    

    public MainPanel(JPanel topBar,
                     JPanel libraryPanel,
                     JPanel musicPlayer,
                     ScreenRegistry registry,
                     AppContext context,
                     SearchController searchController) {

        this.registry = registry;
        this.context = context;
        this.searchController = searchController;

        setLayout(new BorderLayout());

        add(topBar, BorderLayout.NORTH);
        add(libraryPanel, BorderLayout.WEST);
        add(musicPlayer, BorderLayout.SOUTH);
        add(centerRouter, BorderLayout.CENTER);

        subscribe(context.eventBus);
        showController(HomeController.class);
    }

    private void subscribe(EventBus eventBus) {
        eventBus.subscribe(SwitchCenterScreenEvent.class,
                e -> showController(e.getControllerClass()));
        
        eventBus.subscribe(OpenSongViewEvent.class, event -> {
            showController(MusicController.class);

            JPanel panel = registry.get(MusicController.class);
            if (panel instanceof MusicView musicView) {
                musicView.setSong(event.getSong());
            }
        });

        eventBus.subscribe(OpenAlbumViewEvent.class, event -> {

            Album album = event.getAlbum();

            AlbumView albumView = new AlbumView(album);

            register(AlbumView.class, albumView);

            show(AlbumView.class);
        });
        //TODO: Figure what the fuck is happening here
        eventBus.subscribe(LibraryPlaylistClicked.class, e -> {
            showController(MusicController.class);

            JPanel musicPanel = registry.get(MusicController.class);
            if (musicPanel instanceof tunix.ui.views.main.center.MusicView musicView) {
                musicView.setAsset(e.getPlaylist());
            }
        });
        eventBus.subscribe(OpenArtistViewEvent.class, event -> {

            showController(ArtistController.class);

            JPanel panel = registry.get(ArtistController.class);

            if (panel instanceof tunix.ui.views.main.center.ArtistView view) {

                view.setArtistData(
                        event.getArtist(),
                        java.util.List.of(),
                        java.util.List.of()
                );
            }
        });
        eventBus.subscribe(SwitchProfileScreenEvent.class, e -> {
            Account user = SessionService.Instance == null ? null : SessionService.Instance.getAccount();
            if (user == null) return;

            Role role = user.getAccountStatus();

            switch (role) {
                case USER -> openUserProfile();
                case ADMIN -> openAdminProfile();
                case ARTIST -> openArtistProfile();
            }
        });
    }

    private JPanel createPanelForController(Class<?> controllerClass) {
        if (controllerClass == HomeController.class) {
            return new HomeController().getView();
        }
        if (controllerClass == SearchController.class) {
            return searchController.getView();
        }
        if (controllerClass == MusicController.class) {
            if (musicController == null) {
                musicController = new MusicController(context.eventBus);
            }
            return musicController.getView();
        }
        if (controllerClass == UserProfileController.class) {
            return new UserProfileController(context.eventBus, new UserService(new UserApi(context.apiClient)),new ArtistRequestService(new ArtistRequestApiClient(context.apiClient))).getView();
        }
        if (controllerClass == AdminProfileController.class) {
            return new AdminProfileController().getView();
        }
        if (controllerClass == ArtistController.class) {
            return new ArtistController().getView();
        }
        if (controllerClass == ArtistProfileController.class) {
            return new ArtistProfileController(new ArtistProfileService(context.eventBus), context.eventBus).getView();
        }
        if (controllerClass == UploadSongController.class) {
        return new UploadSongController(
                new UploadSongView(),
                new SongService(
                        context.eventBus,
                        new SongApiClient(
                                context.apiClient
                        )
                ),
                context.eventBus
        ).getView();
        }

        throw new IllegalArgumentException("Unsupported controller: " + controllerClass.getSimpleName());
    }

    private void showController(Class<?> controllerClass) {
        JPanel panel;

        if (controllerClass == SearchController.class) {
            panel = searchController.getView();
            register(controllerClass, panel);
        } else {
            panel = registry.get(controllerClass);

            if (panel == null) {
                panel = createPanelForController(controllerClass);
                register(controllerClass, panel);
            }
        }

        if (panel instanceof tunix.ui.views.profile.UserProfileView userProfileView) {
            userProfileView.refresh();
        }

        if (panel instanceof tunix.ui.views.main.center.MusicView musicView) {
            musicView.refresh();
        }

        show(controllerClass);
    }

    public void register(Class<?> key, JPanel panel) {
        JPanel existing = registry.get(key);

        if (existing == panel) {
            return;
        }

        if (existing != null) {
            centerRouter.remove(existing);
        }

        registry.register(key, panel);

        String name = registry.getName(key);
        centerRouter.add(panel, name);

        System.out.println("Registered screen: " + key.getSimpleName());
    }

    public void show(Class<?> key) {
        String name = registry.getName(key);

        if (name == null) {
            throw new IllegalStateException("Screen not registered: " + key.getSimpleName());
        }

        layout.show(centerRouter, name);
        centerRouter.revalidate();
        centerRouter.repaint();
    }

    public void openUserProfile() {
        showController(UserProfileController.class);
    }

    public void openAdminProfile() {
        showController(AdminProfileController.class);
    }

    public void openArtistProfile() {
        showController(ArtistProfileController.class);
    }
}