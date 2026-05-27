package tunix.dto.request;

import lombok.Getter;

@Getter
public class PlaylistCreateRequest {

    private final String title;
    private final String description;
    private final String coverImagePath;
    private final Long creatorId;

    public PlaylistCreateRequest(String title,
                                 String description,
                                 String coverImagePath,
                                 Long creatorId) {
        this.title = title;
        this.description = description;
        this.coverImagePath = coverImagePath;
        this.creatorId = creatorId;
    }
}