package tunix.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArtistResponse {
    private String stageName;
    private String bio;

    public ArtistResponse(String stageName, String bio) {
        this.stageName = stageName;
        this.bio = bio;
    }
}
