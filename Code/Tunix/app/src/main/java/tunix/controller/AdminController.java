package tunix.controller;

import java.util.List;

import javax.swing.JPanel;

import tunix.service.AdminService;
import tunix.service.ArtistRequestService;
import tunix.ui.views.admin.AdminView;
import tunix.model.ArtistRequest;

public class AdminController {
    private final AdminView adminView;
    private final AdminService adminService;
    private final ArtistRequestService artistRequestService;

    public AdminController(AdminService adminService, ArtistRequestService artistRequestService) {
        this.adminView = new AdminView();
        this.adminView.setController(this);
        this.adminService = adminService;
        this.artistRequestService = artistRequestService;
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
}
