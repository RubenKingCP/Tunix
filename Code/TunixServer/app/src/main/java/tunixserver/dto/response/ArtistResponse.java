package tunixserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tunixserver.entities.ArtistEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistResponse {

    private Long id;
    private Long accountId;
    private String displayName;
    private String biography;
    private int followersCount;
    private boolean verified;

    public static ArtistResponse fromEntity(ArtistEntity artist) {
        return new ArtistResponse(
                artist.getId(),
                artist.getAccount() != null ? artist.getAccount().getAccountId() : null,
                artist.getDisplayName(),
                artist.getBiography(),
                artist.getFollowersCount(),
                artist.isVerified()
        );
    }
}