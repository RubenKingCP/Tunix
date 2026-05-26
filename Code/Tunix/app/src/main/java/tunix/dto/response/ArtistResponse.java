package tunix.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistResponse {

    private Long id;
    private Long accountId;
    private String biography;
    private int followersCount;
    private boolean verified;
}