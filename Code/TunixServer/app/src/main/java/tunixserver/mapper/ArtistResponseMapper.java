package tunixserver.mapper;

import tunixserver.dto.response.ArtistResponse;
import tunixserver.dto.response.SongResponse;
import tunixserver.entities.ArtistEntity;

public class ArtistResponseMapper {
        public static ArtistResponse fromEntity(ArtistEntity artist) {

                if (artist == null) return null;

                return new ArtistResponse(
                        artist.getId(),
                        artist.getAccount() != null ? artist.getAccount().getAccountId() : null,
                        artist.getDisplayName(),
                        artist.getBiography(),
                        artist.getFollowersCount(),
                        artist.isVerified(),
                        artist.getSongs().stream()
                                .map(SongResponse::fromEntity)
                                .toList()
                );
        }
}
