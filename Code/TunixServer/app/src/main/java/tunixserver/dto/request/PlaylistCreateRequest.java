package tunixserver.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PlaylistCreateRequest {

    private final String title;
    private final String description;
    private final String coverImagePath;
    private final Long creatorId;

    @JsonCreator
    public PlaylistCreateRequest(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("coverImagePath") String coverImagePath,
            @JsonProperty("creatorId") Long creatorId
    ) {
        this.title = title;
        this.description = description;
        this.coverImagePath = coverImagePath;
        this.creatorId = creatorId;
    }
}