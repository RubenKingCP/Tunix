package tunixserver.mapper;

import tunixserver.dto.response.ArtistRequestResponse;
import tunixserver.entities.ArtistRequestEntity;

public class ArtistRequestMapper {

    public static ArtistRequestResponse toResponse(ArtistRequestEntity entity) {
        return new ArtistRequestResponse(
                entity.getRequestId(),
                entity.getUser() != null ? entity.getUser().getId() : null,
                getUsername(entity),
                entity.getStageName(),
                entity.getReason(),
                entity.getProfilePictureUrl(),
                entity.getStatus() != null ? entity.getStatus().name() : null, // ✅ FIX
                entity.getRequestedAt(),
                entity.getReviewedAt()
        );
    }

    private static String getUsername(ArtistRequestEntity entity) {
        if (entity.getUser() == null || entity.getUser().getAccount() == null)
            return null;

        return entity.getUser().getAccount().getUsername();
    }
}