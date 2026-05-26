package tunix.ui.views.admin;

import tunix.controller.AdminController;
import tunix.dto.enums.ArtistRequestStatus;
import tunix.model.ArtistRequest;
import tunix.model.musicContent.Song;

import java.util.List;

import javax.swing.*;
import java.awt.*;

public class AdminView extends JPanel {
    private AdminController adminController;
    private List<Song> songs;
    private List<ArtistRequest> artistRequests;

    private JTabbedPane tabbedPane;
    private JPanel songsPanel;
    private JPanel artistApplicationsPanel;

    public void display() {
        this.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        songsPanel = new JPanel();
        songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.Y_AXIS));

        artistApplicationsPanel = new JPanel();
        artistApplicationsPanel.setLayout(new BoxLayout(artistApplicationsPanel, BoxLayout.Y_AXIS));

        tabbedPane.addTab("Songs", songsPanel);
        tabbedPane.addTab("Artist Applications", artistApplicationsPanel);

        this.add(tabbedPane, BorderLayout.CENTER);

        if (songs != null) {
            for (Song song : songs) {
                songsPanel.add(new SongGUI(song));
            }
        }

        if (artistRequests != null) {
            for (ArtistRequest application : artistRequests) {
                if (application.getStatus() == ArtistRequestStatus.Pending)
                    artistApplicationsPanel.add(new ApplicationGUI(application));
            }
        }
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
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
        artistApplicationsPanel.removeAll();
        for (ArtistRequest application : artistRequests) {
            if (application.getStatus() == ArtistRequestStatus.Pending)
                artistApplicationsPanel.add(new ApplicationGUI(application));
        }
        artistApplicationsPanel.revalidate();
        artistApplicationsPanel.repaint();
    }

    private void showArtistRequestDetails(ArtistRequest artistRequest) {
        // Logic to display artist request details in the view
    }

    public void onArtistRequestShowDetailsClicked(ArtistRequest artistRequest) {
        showArtistRequestDetails(artistRequest);
    }

    public void onApproveArtistRequestClicked(int requestId) {
        adminController.onApproveArtistRequestClicked(requestId);
    }

    public void onRejectArtistRequestClicked(int requestId) {
        adminController.onRejectArtistRequestClicked(requestId);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void setController(AdminController adminController) {
        this.adminController = adminController;
    }

    private class SongGUI extends JPanel {
        private JLabel label;
        private JButton button;

        public SongGUI(Song song) {
            super();
            this.label = new JLabel(song.getTitle());
            this.button = new JButton("Remove Media");
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.add(this.label);
            this.button.addActionListener(e -> adminController.onRemoveSongClicked(song.getId()));
            this.add(this.button);
        }
    }

    private class ApplicationGUI extends JPanel {
        private JLabel label;
        private JButton approve;
        private JButton reject;

        public ApplicationGUI(ArtistRequest application) {
            super();
            this.label = new JLabel(Integer.toString(application.getApplicantId()));
            this.approve = new JButton("Approve");
            this.reject = new JButton("Reject");
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.add(this.label);
            this.add(this.approve);
            this.add(this.reject);

            this.approve.addActionListener(e -> {
                adminController.onApproveArtistRequestClicked(application.getApplicantId());
            });

            this.reject.addActionListener(e -> {
                adminController.onRejectArtistRequestClicked(application.getApplicantId());
            });
        }
    }
}
