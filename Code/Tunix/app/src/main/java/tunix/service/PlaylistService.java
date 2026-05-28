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
import tunix.service.auth.SessionService;
import tunix.model.account.Account;
import tunix.model.musicContent.Song;

public class PlaylistService {

    private final PlaylistApiClient playlistApiClient;

    // local frontend cache
    private final List<Playlist> cachedPlaylists = new ArrayList<>();

    public PlaylistService(PlaylistApiClient playlistApiClient) {
        this.playlistApiClient = playlistApiClient;
    }

    public boolean addSongToPlaylist(int playlistId, Song song) {

        int songId = song.getId();

        ApiResponse<AddSongResponse> response =
            playlistApiClient.addSongToPlaylist(
                playlistId,
                songId
            );

        if (response != null && response.isSuccess()) {

            // update local cache: find matching playlist and add song if not present
            for (Playlist playlist : cachedPlaylists) {

                if (playlist.getId() == playlistId) {

                        boolean exists = playlist.getDisplaySongs()
                            .stream()
                            .anyMatch(s -> s.getId() == song.getId());

                    if (!exists) {
                        playlist.addSong(song);
                    }

                    break;
                }
            }

        } else {
            System.out.println("PlaylistService.addSongToPlaylist failed.");
            if (response == null) {
                System.out.println("Response was null.");
            } else {
                System.out.println("Response success=" + response.isSuccess() + ", message=" + response.getMessage());
            }
        }

        return response != null && response.isSuccess();
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

    public Playlist getPlaylistById(int playlistId) {
        ILibraryAsset asset = playlistApiClient.getById(playlistId);
        return asset instanceof Playlist ? (Playlist) asset : null;
    }

    /**
     * Load current user's playlists from backend and populate cache.
     * Safe to call multiple times.
     */
    public void loadUserPlaylists() {
        Account account = SessionService.Instance == null ? null : SessionService.Instance.getAccount();
        if (account == null) return;

        List<ILibraryAsset> assets = playlistApiClient.getUserPlaylists(account.getLongId());
        List<Playlist> playlists = new ArrayList<>();

        if (assets != null) {
            for (ILibraryAsset asset : assets) {
                if (asset instanceof Playlist p) {
                    playlists.add(p);
                }
            }
        }

        setCachedPlaylists(playlists);
    }
}