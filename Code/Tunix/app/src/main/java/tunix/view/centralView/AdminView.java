package tunix.view.centralView;

import tunix.controller.AdminController;
import tunix.model.ArtistRequest;
import java.util.List;

public class AdminView {
    private final AdminController adminController;
    private List<ArtistRequest> artistRequests;

    public AdminView(AdminController adminController) {
        this.adminController = adminController;
    }

    public void display() {
        // Logic to display the admin view
    }

    public void setArtistRequests(List<ArtistRequest> artistRequests) {
        this.artistRequests = artistRequests;
    }

    public void onArtistRequestsClicked() {
        List<ArtistRequest> artistRequests = adminController.onArtistRequestsClicked();
        setArtistRequests(artistRequests);
        displayArtistRequests(artistRequests);
    }

    public void displayArtistRequests(List<ArtistRequest> artistRequests) {
        // Logic to display artist requests in the view
    }

    public void showArtistRequestDetails(ArtistRequest artistRequest) {
        // Logic to display artist request details in the view
    }

    public void onArtistRequestShowDetailsClicked(ArtistRequest artistRequest) {
        showArtistRequestDetails(artistRequest);
    }

    public void onApproveArtistRequestClicked(int requestId) {
        // Logic to approve the artist request
        adminController.onApproveArtistRequestClicked(requestId);
    }

    public void onRejectArtistRequestClicked(int requestId) {
        // Logic to reject the artist request
        adminController.onRejectArtistRequestClicked(requestId);
    }

    public void showMessage(String message) {
        
    }
}
