package tunix.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tunix.model.account.Artist;
import tunix.model.musicContent.Playlist;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponse {

    private Long id;
    private String title;
    private Long creatorId;
    private String creatorName;
    @JsonProperty("isPublic")
    private boolean isPublic;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<SongResponse> songs;
    
    public Playlist toPlaylist() {

        Artist creator = new Artist(
                creatorId,
                creatorName,
                null,
                null,
                0,
                false
        );

        Playlist playlist = new Playlist(
                title,
                id.intValue(),
                creator
        );

        if (songs != null) {
            songs.stream()
                    .map(SongResponse::toSong)
                    .forEach(playlist::addSong);
        }

        return playlist;
    }
}