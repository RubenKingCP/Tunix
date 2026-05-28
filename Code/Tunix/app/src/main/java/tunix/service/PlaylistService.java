package tunix.service;

import java.util.ArrayList;
import java.util.List;

import tunix.api.PlaylistApiClient;
import tunix.dto.request.PlaylistCreateRequest;
import tunix.dto.response.AddSongResponse;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.PlaylistResponse;
import tunix.model.ILibraryAsset;
import tunix.model.musicContent.Playlist;

public class PlaylistService {

    private final PlaylistApiClient playlistApiClient;

    // local frontend cache
    private final List<Playlist> cachedPlaylists = new ArrayList<>();

    public PlaylistService(PlaylistApiClient playlistApiClient) {
        this.playlistApiClient = playlistApiClient;
    }

    public boolean addSongToPlaylist(int playlistId, int songId) {

        ApiResponse<AddSongResponse> response =
                playlistApiClient.addSongToPlaylist(
                        playlistId,
                        songId
                );

        if (response.isSuccess()) {

            // update local cache
            for (Playlist playlist : cachedPlaylists) {

                if (playlist.getId() == playlistId) {

                    // optional:
                    // add temporary frontend-only song state here
                    // if you already have the Song object available

                    break;
                }
            }

        } else {

            System.out.println(
                    "Failed: " + response.getMessage()
            );
        }

        return response.isSuccess();
    }

    public boolean createPlaylist(
            PlaylistCreateRequest playlistRequest,
            List<ILibraryAsset> libraryAssets) {

        System.out.println(
                "PlaylistService: create Playlist Message send");

        ApiResponse<PlaylistResponse> response =
                playlistApiClient.createPlaylist(
                        playlistRequest);

        return response.isSuccess();
    }

    // CACHE METHODS

    public List<Playlist> getCachedPlaylists() {
        return cachedPlaylists;
    }

    public void setCachedPlaylists(List<Playlist> playlists) {

        cachedPlaylists.clear();

        if (playlists != null) {
            cachedPlaylists.addAll(playlists);
        }
    }

    public void addPlaylistToCache(Playlist playlist) {

        if (playlist != null) {
            cachedPlaylists.add(playlist);
        }
    }
}