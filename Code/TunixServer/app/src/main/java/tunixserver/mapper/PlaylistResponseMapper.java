package tunixserver.mapper;

import java.util.List;

import tunixserver.dto.response.PlaylistResponse;
import tunixserver.dto.response.SongResponse;
import tunixserver.entities.PlaylistEntity;

public class PlaylistResponseMapper {
    public static PlaylistResponse fromEntity(PlaylistEntity p) {

    if (p == null) return null;

    return new PlaylistResponse(
            p.getId(),
            p.getTitle(),
            p.getCreator() != null ? p.getCreator().getAccountId() : null,
            p.isPublic(),
            p.getCreatedAt(),
            p.getUpdatedAt(),

            p.getSongs() != null
                    ? p.getSongs().stream()
                        .map(SongResponse::fromEntity)
                        .toList()
                    : List.of()
    );
}
}
