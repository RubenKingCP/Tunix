package tunixserver.mapper;

import java.util.List;

import tunixserver.dto.response.PlaylistResponse;
import tunixserver.entities.PlaylistEntity;

public class PlaylistResponseMapper {
    public static PlaylistResponse fromEntity(PlaylistEntity p) {

    if (p == null) return null;

    return new PlaylistResponse(
            p.getId(),
            p.getTitle(),
            p.getCreator() != null ? p.getCreator().getAccountId() : null,
            p.getCreator() != null ? p.getCreator().getUsername() : null,
            p.isPublic(),
            p.getCreatedAt(),
            p.getUpdatedAt(),

            p.getItems() != null
                    ? p.getItems().stream()
                        .map(item -> SongMapper.toResponse(item.getSong()))
                        .toList()
                    : List.of()
    );
}
}
