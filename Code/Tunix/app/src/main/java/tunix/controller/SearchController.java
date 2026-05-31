package tunix.controller;

import java.util.List;

import javax.swing.JPanel;

import tunix.dto.enums.LibraryAssetType;
import tunix.model.ILibraryAsset;
import tunix.model.account.Artist;
import tunix.model.musicContent.Album;
import tunix.service.SearchService;
import tunix.ui.views.main.center.SearchView;
import tunix.navigation.events.*;


public class SearchController {

    private final SearchService service;
    private final EventBus eventBus;
    private final SearchView view;

    public SearchController(SearchService service, EventBus eventBus) {
        this.service = service;
        this.eventBus = eventBus;

        // Keep ONE persistent view instance
        this.view = new SearchView(List.of(), eventBus,this);
    }

    public JPanel getView() {
        return view;
    }

    public List<ILibraryAsset> search(String query, String type) {

        List<ILibraryAsset> results = service.search(query, type);

        // Update existing registered panel instead of replacing it
        view.setResults(results);
        view.refresh();

        return results;
    }
    public void openResult(ILibraryAsset asset) {
    if (eventBus != null) {
        // Fetch full details (songs included) before publishing
        ILibraryAsset fullAsset = service.getFullAsset(asset.getId(), asset.getType());

        if (asset.getType() == LibraryAssetType.ARTIST) {
            Artist artist = null;
            if (fullAsset instanceof Artist fullArtist) {
                artist = fullArtist;
            } else if (asset instanceof Artist searchArtist) {
                artist = searchArtist;
            }

            if (artist != null) {
                eventBus.publish(new OpenArtistViewEvent(artist));
            }
            return;
        }

        if (asset.getType() == LibraryAssetType.ALBUM) {
            Album album = null;
            if (fullAsset instanceof Album fullAlbum) {
                album = fullAlbum;
            } else if (asset instanceof Album searchAlbum) {
                album = searchAlbum;
            }

            if (album != null) {
                eventBus.publish(new OpenAlbumViewEvent(album));
            }
            return;
        }

        eventBus.publish(new LibraryPlaylistClicked(fullAsset != null ? fullAsset : asset));
    }
}
}