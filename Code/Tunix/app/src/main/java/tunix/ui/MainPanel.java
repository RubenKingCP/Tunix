package tunix.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import tunix.controller.AdminProfileController;
import tunix.controller.ArtistProfileController;
import tunix.controller.HomeController;
import tunix.controller.SearchController;
import tunix.controller.UserProfileController;
import tunix.controller.main.center.MusicController;
import tunix.dto.enums.Role;
import tunix.model.AppContext;
import tunix.model.account.Account;
import tunix.navigation.ScreenRegistry;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.LibraryPlaylistClicked;
import tunix.navigation.events.SwitchCenterScreenEvent;
import tunix.navigation.events.SwitchProfileScreenEvent;
import tunix.service.ArtistProfileService;
import tunix.service.auth.SessionService;

public class MainPanel extends JPanel {

    private final JPanel centerRouter = new JPanel(new CardLayout());
    private final CardLayout layout = (CardLayout) centerRouter.getLayout();

    private final ScreenRegistry registry;
    private final AppContext context;
    private final SearchController searchController;

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

        eventBus.subscribe(LibraryPlaylistClicked.class, e -> {
            showController(MusicController.class);

            JPanel musicPanel = registry.get(MusicController.class);
            if (musicPanel instanceof tunix.ui.views.main.center.MusicView musicView) {
                musicView.setAsset(e.getPlaylist());
            }
        });

        eventBus.subscribe(SwitchProfileScreenEvent.class, e -> {
            Account user = SessionService.Instance == null ? null : SessionService.Instance.getUser();
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
            return new MusicController(context.eventBus).getView();
        }
        if (controllerClass == UserProfileController.class) {
            return new UserProfileController(context.eventBus).getView();
        }
        if (controllerClass == AdminProfileController.class) {
            return new AdminProfileController().getView();
        }
        if (controllerClass == ArtistProfileController.class) {
            return new ArtistProfileController(new ArtistProfileService(context.eventBus), context.eventBus).getView();
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