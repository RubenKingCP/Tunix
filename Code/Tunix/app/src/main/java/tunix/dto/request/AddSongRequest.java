package tunix.dto.request;

public class AddSongRequest {
    private int songId;

    public AddSongRequest(int songId) {
        this.songId = songId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
}
