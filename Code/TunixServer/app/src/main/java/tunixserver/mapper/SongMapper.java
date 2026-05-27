package tunixserver.mapper;

import tunixserver.dto.response.SongResponse;
import tunixserver.entities.SongEntity;

public class SongMapper {

    public static SongResponse toResponse(SongEntity entity) {

        return new SongResponse(
                entity.getId(),
                entity.getTitle(),

                // artistId
                entity.getArtist() != null
                        ? entity.getArtist().getId()
                        : null,

                // artistName
                getArtistUsername(entity),

                entity.getDuration(),
                entity.getFilePathUrl(),
                entity.getCoverImageUrl()
        );
    }

    private static String getArtistUsername(SongEntity entity) {

        if (entity.getArtist() == null ||
            entity.getArtist().getAccount() == null) {

            return null;
        }

        return entity.getArtist()
                .getAccount()
                .getUsername();
    }
}