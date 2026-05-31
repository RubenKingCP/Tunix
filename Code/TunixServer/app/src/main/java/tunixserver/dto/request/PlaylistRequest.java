package tunixserver.dto.request;

public class PlaylistRequest {

    private Long playlistId;
    private Long songId;

    public PlaylistRequest() {}

    public PlaylistRequest(Long playlistId, Long songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }

    public Long getPlaylistId() {
        return playlistId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }
}