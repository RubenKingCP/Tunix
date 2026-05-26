package tunixserver.mapper;

import tunixserver.dto.response.ArtistRequestResponse;
import tunixserver.entities.ArtistRequestEntity;

public class ArtistRequestMapper {

    public static ArtistRequestResponse toResponse(ArtistRequestEntity entity) {
        if (entity == null) return null;

        return new ArtistRequestResponse(
                entity.getRequestId(),
                entity.getUser() != null ? entity.getUser().getId() : null,
                extractUsername(entity),
                entity.getStageName(),
                entity.getBio(),
                entity.getProfilePictureUrl(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getRequestedAt(),
                entity.getReviewedAt()
        );
    }

    private static String extractUsername(ArtistRequestEntity entity) {
        if (entity.getUser() == null) return null;
        if (entity.getUser().getAccount() == null) return null;
        return entity.getUser().getAccount().getUsername();
    }
}