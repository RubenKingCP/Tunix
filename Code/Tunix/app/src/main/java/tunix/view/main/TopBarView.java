package tunix.view.main;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tunix.controller.main.TopBarController;

public class TopBarView extends JPanel{
    private TopBarController topBarController;

    public TopBarView() {
        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(e -> onHomeButtonClicked());
        JTextField searchField = new JTextField(20);
        JComboBox<String> searchTypeComboBox = new JComboBox<>(new String[]{"Song", "Playlist", "Album"});
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> onSearchButtonClicked(searchField.getText(), (String) searchTypeComboBox.getSelectedItem()));
        JButton profileButton = new JButton("Profile");
        profileButton.addActionListener(e -> onProfileButtonClicked());
        add(homeButton);
        add(searchField);
        add(searchTypeComboBox);
        add(searchButton);
        add(profileButton);
    }

    public void setController(TopBarController topBarController) {
        this.topBarController = topBarController;
    }

    public void onSearchButtonClicked(String query, String searchType) {
        topBarController.onSearch(query, searchType);
    }
    public void onHomeButtonClicked() {
        topBarController.onHomeButtonClicked();
    }
    public void onProfileButtonClicked() {
        topBarController.onProfileButtonClicked();
    }

}
