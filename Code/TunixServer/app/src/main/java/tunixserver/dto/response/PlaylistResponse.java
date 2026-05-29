package tunixserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tunixserver.entities.PlaylistEntity;
import tunixserver.mapper.SongMapper;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public static PlaylistResponse fromEntity(PlaylistEntity p) {
        return new PlaylistResponse(
                p.getId(),
                p.getTitle(),
                p.getCreator() != null ? p.getCreator().getAccountId() : null,
                p.getCreator() != null ? p.getCreator().getUsername() : null,
                p.isPublic(),
                p.getCreatedAt(),
                p.getUpdatedAt(),
                p.getItems().stream()
                        .map(item -> SongMapper.toResponse(item.getSong()))
                        .toList()
        );
    }

    @JsonProperty("isPublic")
    public boolean isPublic() {
        return isPublic;
    }
}