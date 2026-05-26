package tunix.controller;

import java.util.List;

import tunix.navigation.events.EventBus;
import tunix.navigation.events.LogoutEvent;
import tunix.service.AdminService;
import tunix.service.ArtistRequestService;
import tunix.service.SongService;
import tunix.ui.views.admin.AdminView;
import tunix.dto.request.SongRequest;
import tunix.dto.response.SongResponse;
import tunix.model.ArtistRequest;
import tunix.model.musicContent.Song;

public class AdminController {
    private final AdminView adminView;
    private final AdminService adminService;
    private final SongService songService;
    private final ArtistRequestService artistRequestService;
    private final EventBus eventBus;

    public AdminController(EventBus eventBus, AdminService adminService, ArtistRequestService artistRequestService, SongService songService) {
    this.eventBus = eventBus;
    this.adminService = adminService;
    this.artistRequestService = artistRequestService;
    this.songService = songService;
    this.adminView = new AdminView(this); // moved to last
}

    public AdminView getView() {
        return adminView;
    }

    public List<ArtistRequest> onArtistRequestsClicked() {
        return artistRequestService.getArtistRequests();
    }

    public void showArtistRequestDetails(ArtistRequest artistRequest) {
        // Logic to display artist request details in the view
    }

    public void onApproveArtistRequestClicked(int requestId) {
        if(artistRequestService.approveArtistRequest(requestId)) {
            adminView.showMessage("Approve success!");
        } else {
            adminView.showMessage("Approve fail");
        }
    }

    public List<Song> onSongsClicked(){
        return songService.getSongs();
    }

    public void onRejectArtistRequestClicked(int requestId) {
        if(artistRequestService.rejectArtistRequest(requestId)) {
            adminView.showMessage("Rejection success!");
        } else {
            adminView.showMessage("Rejection fail");
        }
    }

    public void onRemoveSongClicked(int songId){
        //Code to remove media
    }

    public void onLogoutButtonClicked() {
        eventBus.publish(new LogoutEvent());
    }
}
