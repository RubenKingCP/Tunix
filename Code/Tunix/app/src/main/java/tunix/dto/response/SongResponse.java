package tunix.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tunix.model.account.Artist;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SongResponse {

    private Long songId;
    private String title;
    private Artist artist;
    private int duration;
    private String filePathUrl;
    private String coverImageUrl;
}