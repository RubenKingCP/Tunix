package tunix.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponse {

    private Long id;
    private String title;
    private Long creatorId;
    private boolean isPublic;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<SongResponse> songs;
}