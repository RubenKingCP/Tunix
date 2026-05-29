package tunix.controller;

import java.util.List;

import tunix.navigation.events.EventBus;
import tunix.navigation.events.LogoutEvent;
import tunix.service.AdminService;
import tunix.service.ArtistRequestService;
import tunix.service.SongService;
import tunix.ui.views.admin.AdminView;
import tunix.dto.enums.ArtistRequestStatus;
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

        List<ArtistRequest> req = artistRequestService.getArtistRequests();
        System.out.println("==========================\nADMIN CONTROLLER CHECK\n=======================");
        // 🔥 NULL CHECK
        if (req == null) {
            System.err.println("ArtistRequests = NULL");
            return List.of();
        }

        // 🔥 SIZE CHECK
        System.err.println("ArtistRequests size = " + req.size());

        // 🔥 STATUS BREAKDOWN CHECK
        long pending = req.stream()
                .filter(r -> r != null && r.getStatus() == ArtistRequestStatus.Pending)
                .count();

        long nonPending = req.stream()
                .filter(r -> r != null && r.getStatus() != ArtistRequestStatus.Pending)
                .count();

        System.err.println("Pending = " + pending);
        System.err.println("Non-Pending = " + nonPending);

        // 🔥 NULL STATUS CHECK
        long nullStatus = req.stream()
                .filter(r -> r == null || r.getStatus() == null)
                .count();

        System.err.println("Null or missing status = " + nullStatus);

        return req;
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

    public boolean onRemoveSongClicked(int songId){
        adminService.postRemoveSongById(songId);
        List<Song> updatedSongs = onSongsClicked();
        adminView.displaySongs(updatedSongs);
        return true;
    }

    public boolean onIssueStrikeClicked(int artistId, String reason) {
        boolean success = adminService.postIssueWarning(artistId);
        String note = reason == null || reason.isBlank() ? "" : " Reason: " + reason;
        adminView.showMessage(success ? "Strike issued successfully." + note : "Failed to issue strike.");
        return success;
    }

    public boolean onIssueBanClicked(int artistId, String reason) {
        boolean success = adminService.postBan(artistId,reason);
        String note = reason == null || reason.isBlank() ? "" : " Reason: " + reason;
        adminView.showMessage(success ? "Ban issued successfully." + note : "Failed to issue ban.");
        return success;
    }

    public void onLogoutButtonClicked() {
        eventBus.publish(new LogoutEvent());
    }

    public String getProfilePic(ArtistRequest ar) {
        return adminService.getProfilePic(ar);
    }

    public List<String> getArtistModerationHistory(int artistId) {
        return adminService.getArtistModerationHistory(artistId);
    }
}
