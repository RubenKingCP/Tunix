package tunix.navigation.events;
import tunix.model.musicContent.Song;

    public final class OpenSongViewEvent {

        private final Song song;

        public OpenSongViewEvent(Song song) {
            this.song = song;
        }

        public Song getSong() {
            return song;
        }
    }