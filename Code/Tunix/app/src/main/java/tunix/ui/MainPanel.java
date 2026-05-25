package tunix.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import tunix.model.AppContext;
import tunix.model.account.Account;
import tunix.navigation.ScreenRegistry;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.LibraryPlaylistClicked;
import tunix.navigation.events.SwitchCenterScreenEvent;
import tunix.navigation.events.SwitchProfileScreenEvent;
import tunix.service.auth.SessionService;
import tunix.ui.views.main.center.MusicView;
import tunix.ui.views.profile.AdminProfileView;
import tunix.ui.views.profile.ArtistProfileView;
import tunix.ui.views.profile.UserProfileView;
import tunix.controller.main.center.MusicController;
import tunix.dto.enums.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPanel extends JPanel {

    private final JPanel centerRouter = new JPanel(new CardLayout());
    private final CardLayout layout = (CardLayout) centerRouter.getLayout();

    private final ScreenRegistry registry;
    private final AppContext context;

    private UserProfileView userProfileView;
    private AdminProfileView adminProfileView;
    private ArtistProfileView artistProfileView;
    private Map<MusicView, MusicController> musicMap = new HashMap<>();

    public MainPanel(JPanel topBar,
                     JPanel libraryPanel,
                     JPanel musicPlayer,
                     ScreenRegistry registry,
                     AppContext context) {

        this.registry = registry;
        this.context = context;

        setLayout(new BorderLayout());

        add(topBar, BorderLayout.NORTH);
        add(libraryPanel, BorderLayout.WEST);
        add(musicPlayer, BorderLayout.SOUTH);
        add(centerRouter, BorderLayout.CENTER);

        subscribe(context.eventBus);
    }

    private void subscribe(EventBus eventBus) {

        eventBus.subscribe(SwitchCenterScreenEvent.class,
                e -> show(e.getScreen()));
        
        eventBus.subscribe(LibraryPlaylistClicked.class, e -> createMusicView());

        eventBus.subscribe(SwitchProfileScreenEvent.class, e -> {

            Account user = SessionService.Instance.getUser();
            if (user == null) return;

            Role role = user.getAccountStatus();

            switch (role) {
                case USER -> openUserProfile();
                case ADMIN -> openAdminProfile();
                case ARTIST -> openArtistProfile();
            }
        });
    }

    public void register(Class<?> key, JPanel panel) {
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

    // =========================
    // PROFILE SCREENS
    // =========================

    public void openUserProfile() {
        if (userProfileView == null) {
            userProfileView = new UserProfileView();
            new tunix.controller.UserProfileController(userProfileView, context.eventBus);

            register(UserProfileView.class, userProfileView);
        }
        show(UserProfileView.class);
    }

    public void openAdminProfile() {
        if (adminProfileView == null) {
            adminProfileView = new AdminProfileView();
            register(AdminProfileView.class, adminProfileView);
        }
        show(AdminProfileView.class);
    }

    public void openArtistProfile() {
        if (artistProfileView == null) {
            artistProfileView = new ArtistProfileView();
            register(ArtistProfileView.class, artistProfileView);
        }
        show(ArtistProfileView.class);
    }

    public void createMusicView() {

        MusicView view = new MusicView();
        MusicController controller = new MusicController(view, context.eventBus);
        view.setController(controller);
        register(MusicView.class, view);
        show(MusicView.class);
    }
}