package tunix.service;
import tunix.model.musicContent.Song;
public class MusicPlayerService {
    public void loadSong(Song song) {
        System.out.println("Selected song: " + song.getTitle());
    }
}